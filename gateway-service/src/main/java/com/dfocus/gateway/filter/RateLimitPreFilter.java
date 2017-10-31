/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dfocus.gateway.filter;

import com.dfocus.gateway.config.DefaultRateLimitKeyGenerator;
import com.dfocus.gateway.config.properties.RateLimitProperties;
import com.dfocus.gateway.config.repository.Rate;
import com.dfocus.gateway.config.repository.RedisRateLimiter;
import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.FORM_BODY_WRAPPER_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

/**
 * @author Marcos Barbero
 * @author Michal Šváb
 * @author Liel Chayoun
 */
public class RateLimitPreFilter extends AbstractGatewayFilter {

    private final Logger logger = LoggerFactory.getLogger(RateLimitPreFilter.class);

    private final RedisRateLimiter rateLimiter;
    private final DefaultRateLimitKeyGenerator rateLimitKeyGenerator;
    private final RateLimitProperties properties;

    public RateLimitPreFilter(final RateLimitProperties properties, final RouteLocator routeLocator,
                              final UrlPathHelper urlPathHelper, final RedisRateLimiter rateLimiter,
                              final DefaultRateLimitKeyGenerator rateLimitKeyGenerator) {
        super(routeLocator,urlPathHelper);
        this.rateLimiter = rateLimiter;
        this.properties = properties;
        this.rateLimitKeyGenerator = rateLimitKeyGenerator;
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FORM_BODY_WRAPPER_FILTER_ORDER;
    }

    public boolean shouldFilter() {
        return properties.isEnabled() && policy(getMatchingRoute()).isPresent();
    }

    @Override
    public Object run() {
        logger.info("=====================限流中======================");
        final RequestContext ctx = RequestContext.getCurrentContext();
        final HttpServletResponse response = ctx.getResponse();
        final HttpServletRequest request = ctx.getRequest();
        final Route route = getMatchingRoute();

        policy(route).ifPresent(policy -> {
            final String key = rateLimitKeyGenerator.key(request, route, policy);
            final Rate rate = rateLimiter.consume(policy, key, null);

            final Long limit = policy.getLimit();
            final Long remaining = rate.getRemaining();
            if (limit != null) {
                response.setHeader(LIMIT_HEADER, String.valueOf(limit));
                response.setHeader(REMAINING_HEADER, String.valueOf(Math.max(remaining, 0)));
            }

            final Long quota = policy.getQuota();
            final Long remainingQuota = rate.getRemainingQuota();
            if (quota != null) {
                RequestContextHolder.getRequestAttributes()
                        .setAttribute(REQUEST_START_TIME, System.currentTimeMillis(), SCOPE_REQUEST);
                response.setHeader(QUOTA_HEADER, String.valueOf(quota));
                response.setHeader(REMAINING_QUOTA_HEADER,
                        String.valueOf(MILLISECONDS.toSeconds(Math.max(remainingQuota, 0))));
            }

            response.setHeader(RESET_HEADER, String.valueOf(rate.getReset()));

            if ((limit != null && remaining < 0) || (quota != null && remainingQuota < 0)) {
                HttpStatus tooManyRequests = HttpStatus.TOO_MANY_REQUESTS;
                ctx.setResponseStatusCode(tooManyRequests.value());
                ctx.put("rateLimitExceeded", "true");
                ctx.setSendZuulResponse(false);
                ZuulException zuulException = new ZuulException(tooManyRequests.toString(), tooManyRequests.value(),
                        null);
                throw new ZuulRuntimeException(zuulException);
            }
        });

        return null;
    }

    public Optional<RateLimitProperties.Policy> policy(final Route route) {
        if (route != null) {
            logger.debug("==========限流：获取路由========"+route.getId());
            return properties.getPolicy(route.getId());
        }
        return Optional.ofNullable(properties.getDefaultPolicy());
    }
}

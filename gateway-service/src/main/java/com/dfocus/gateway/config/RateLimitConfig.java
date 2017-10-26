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

package com.dfocus.gateway.config;

import com.dfocus.gateway.config.properties.RateLimitProperties;
import com.dfocus.gateway.config.repository.RedisRateLimiter;
import com.dfocus.gateway.filter.RateLimitPostFilter;
import com.dfocus.gateway.filter.RateLimitPreFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.util.UrlPathHelper;

import static com.dfocus.gateway.config.properties.RateLimitProperties.PREFIX;

/**
 * @author Marcos Barbero
 */
@Configuration
//@EnableConfigurationProperties(RateLimitProperties.class)
@ConditionalOnProperty(prefix = PREFIX, name = "enabled", havingValue = "true")
public class RateLimitConfig {

    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Bean
    public ZuulFilter rateLimiterPreFilter(final RateLimitProperties rateLimitProperties,
                                           final RouteLocator routeLocator,
                                           final UrlPathHelper urlPathHelper,
                                           final RedisRateLimiter redisRateLimiter,
                                           final DefaultRateLimitKeyGenerator rateLimitKeyGenerator) {
        return new RateLimitPreFilter(rateLimitProperties, routeLocator, urlPathHelper, redisRateLimiter,
                rateLimitKeyGenerator);
    }

    @Bean
    public ZuulFilter rateLimiterPostFilter(final RedisRateLimiter redisRateLimiter,
                                            final RateLimitProperties rateLimitProperties,
                                            final RouteLocator routeLocator,
                                            final DefaultRateLimitKeyGenerator rateLimitKeyGenerator) {
        return new RateLimitPostFilter(rateLimitProperties, routeLocator, urlPathHelper, redisRateLimiter,
                rateLimitKeyGenerator);
    }

    @Bean
    @ConditionalOnMissingBean(DefaultRateLimitKeyGenerator.class)
    public DefaultRateLimitKeyGenerator ratelimitKeyGenerator(final RateLimitProperties properties) {
        return new DefaultRateLimitKeyGenerator(properties);
    }

    @ConditionalOnClass(RedisTemplate.class)
    @ConditionalOnMissingBean(RedisRateLimiter.class)
    @ConditionalOnProperty(prefix = PREFIX, name = "repository", havingValue = "REDIS")
    public static class RedisConfiguration {

        @Bean("rateLimiterRedisTemplate")
        public StringRedisTemplate redisTemplate(final RedisConnectionFactory connectionFactory) {
            return new StringRedisTemplate(connectionFactory);
        }

        @Bean
        public RedisRateLimiter redisRateLimiter(@Qualifier("rateLimiterRedisTemplate") final RedisTemplate redisTemplate) {
            return new RedisRateLimiter(redisTemplate);
        }
    }


}

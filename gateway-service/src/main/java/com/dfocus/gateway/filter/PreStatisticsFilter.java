package com.dfocus.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * User: qfwang
 * Date: 2017-10-10
 * Time: 下午2:44
 */

public class PreStatisticsFilter extends AbstractGatewayFilter {

    private final Logger logger = LoggerFactory.getLogger(PreStatisticsFilter.class);


    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -1000;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    public PreStatisticsFilter(RouteLocator routeLocator, UrlPathHelper urlPathHelper) {
        super(routeLocator, urlPathHelper);
    }

    @Override
    public Object run() {
        System.out.println("==============统计分析埋点钟==============");
        RequestContext ctx = RequestContext.getCurrentContext();
        try {
           String body = ctx.getRequest().getReader().lines().reduce("",(accumulator, actual) -> accumulator + actual);
           logger.info("请求体："+body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctx.put("startTimeMillis", System.currentTimeMillis());
        ctx.set("requestInfo",ctx.getRequest());
/*        if (true) {
            HttpStatus tooManyRequests = HttpStatus.TOO_MANY_REQUESTS;
            ctx.setResponseStatusCode(tooManyRequests.value());
            ctx.put("ips", "true");
            ctx.setSendZuulResponse(false);

            //ctx.set("sendErrorFilter.ran");
            ZuulException zuulException = new ZuulException(tooManyRequests.toString(), tooManyRequests.value(),
                    null);
            throw new ZuulRuntimeException(zuulException);
        }*/


/*        try {
            int a =10;
            int b=a/0;
        } catch (Exception t) {
            logger.info(t.toString());
            throw new ZuulRuntimeException(new ZuulException(t, HttpStatus.BAD_REQUEST.value(), t.getMessage()));
        }*/

        return null;

    }

    @Override
    public Optional policy(Route route) {
        return null;
    }
}

package com.dfocus.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * User: qfwang
 * Date: 2017-10-10
 * Time: 下午2:44
 */
@Component
public class PreStatisticsFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        System.out.println("==============PreStatisticsFilter==============");
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.put("startTimeMillis", System.currentTimeMillis());

/*        if (true) {
            HttpStatus tooManyRequests = HttpStatus.TOO_MANY_REQUESTS;
            ctx.setResponseStatusCode(tooManyRequests.value());
            ctx.put("ips", "true");
            ctx.setSendZuulResponse(false);
            //ctx.set("sendErrorFilter.ran");
            ZuulException zuulException = new ZuulException(tooManyRequests.toString(), tooManyRequests.value(),
                    null);
            ctx.setThrowable(zuulException);
            throw new ZuulRuntimeException(zuulException);
        }*/
        return null;

    }
}

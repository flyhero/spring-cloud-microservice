package com.dfocus.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * User: qfwang
 * Date: 2017-10-10
 * Time: 下午2:46
 */
public class PostStatisticsFilter extends ZuulFilter{
    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        long startTimeMillis = (long)ctx.get("startTimeMillis");
        long endTimeMillis = System.currentTimeMillis();
        long execTimeMillis = startTimeMillis - endTimeMillis;

        return null;
    }
}

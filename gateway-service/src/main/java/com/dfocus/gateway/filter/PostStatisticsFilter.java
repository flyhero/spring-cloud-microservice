package com.dfocus.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * User: qfwang
 * Date: 2017-10-10
 * Time: 下午2:46
 */
@Component
public class PostStatisticsFilter extends ZuulFilter{
    Logger logger = LoggerFactory.getLogger(PostStatisticsFilter.class);
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
        System.out.println("================PostStatisticsFilter================");
        RequestContext ctx = RequestContext.getCurrentContext();
        long startTimeMillis = (long)ctx.get("startTimeMillis");
        long endTimeMillis = System.currentTimeMillis();
        long execTimeMillis = startTimeMillis - endTimeMillis;
        logger.info("execTimeMillis:"+execTimeMillis+" ms");
        return null;
    }
}

package com.dfocus.gateway.filter;

import com.dfocus.gateway.service.StatisticsService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

import java.util.Optional;

/**
 * 请求统计
 * User: qfwang
 * Date: 2017-10-10
 * Time: 下午2:46
 */

public class PostStatisticsFilter extends AbstractGatewayFilter{

    Logger logger = LoggerFactory.getLogger(PostStatisticsFilter.class);

    @Autowired
    private StatisticsService statisticsService;

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1000;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    public PostStatisticsFilter(RouteLocator routeLocator, UrlPathHelper urlPathHelper) {
        super(routeLocator, urlPathHelper);
    }

    @Override
    public Object run() {
        logger.info("================PostStatisticsFilter================");
        RequestContext ctx = RequestContext.getCurrentContext();
        String info = statisticsService.logApiInfo();
        if(ctx.containsKey("sendErrorFilter.ran")){
            logger.info("=================失败了");
        }
        logger.info("=========json:"+info);
        return null;
    }

    @Override
    public Optional policy(Route route) {
        return null;
    }
}

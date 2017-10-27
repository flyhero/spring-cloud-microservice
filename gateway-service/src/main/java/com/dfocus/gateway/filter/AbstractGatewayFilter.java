package com.dfocus.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.dfocus.gateway.base.GatewayEnum;
import com.dfocus.gateway.base.GatewayResult;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.http.MediaType;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Author: qfwang
 * Date: 2017-10-25 下午8:02
 */
public abstract class AbstractGatewayFilter extends ZuulFilter{

    Logger logger = LoggerFactory.getLogger(AbstractGatewayFilter.class);

    public static final String QUOTA_HEADER = "X-RateLimit-Quota";
    public static final String REMAINING_QUOTA_HEADER = "X-RateLimit-Remaining-Quota";
    public static final String LIMIT_HEADER = "X-RateLimit-Limit";
    public static final String REMAINING_HEADER = "X-RateLimit-Remaining";
    public static final String RESET_HEADER = "X-RateLimit-Reset";
    public static final String REQUEST_START_TIME = "rateLimitRequestStartTime";

    private final RouteLocator routeLocator;
    private final UrlPathHelper urlPathHelper;

    public AbstractGatewayFilter(RouteLocator routeLocator, UrlPathHelper urlPathHelper) {
        this.routeLocator = routeLocator;
        this.urlPathHelper = urlPathHelper;
    }
    public void responseHandler(RequestContext ctx, GatewayEnum gatewayEnum){
        ctx.setSendZuulResponse(false);//zuul过滤请求，false为不路由，true为路由
        HttpServletResponse httpResponse = ctx.getResponse();
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpResponse.setStatus(gatewayEnum.getCode());
        ctx.setResponseBody(JSON.toJSONString(new GatewayResult(gatewayEnum.getCode(),gatewayEnum.getMsg(),"")));
        ctx.setResponse(httpResponse);//返回response信息
    }
    Route getMatchingRoute() {
        String requestURI = urlPathHelper.getPathWithinApplication(RequestContext.getCurrentContext().getRequest());
        logger.info("requestURI:"+requestURI);
        return routeLocator.getMatchingRoute(requestURI);
    }

    public abstract Optional policy(final Route route);

}

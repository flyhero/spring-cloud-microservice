package com.dfocus.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.Claim;
import com.dfocus.gateway.auth.UcenterAuth;
import com.dfocus.gateway.base.GatewayEnum;
import com.dfocus.gateway.base.GatewayResult;
import com.dfocus.gateway.config.properties.JwtProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.http.MediaType;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * Author: qfwang
 * Date: 2017-10-25 下午7:44
 */
public class PreJwtFilter extends AbstractGatewayFilter{
    private Logger logger = LoggerFactory.getLogger(PreJwtFilter.class);

    private final JwtProperties properties;


    @Autowired
    private UcenterAuth ucenterAuth;

    public PreJwtFilter(JwtProperties properties, RouteLocator routeLocator, UrlPathHelper urlPathHelper) {
        super(routeLocator,urlPathHelper);
        this.properties = properties;

    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return properties.isEnabled() && policy(getMatchingRoute()).isPresent();
    }

    @Override
    public Object run() {
        logger.info("================JWT验证中=================");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getHeader("Authorization");



        if (token == null || token.equals("")) {
            responseHandler(ctx, GatewayEnum.MISS_TOKEN);
            return null;
   /*          ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);

            ZuulException zuulException = new ZuulException(tooManyRequests.toString(), tooManyRequests.value(), null);
            throw new ZuulRuntimeException(zuulException);*/
        }else {
            try{
                Map<String, Claim> map= ucenterAuth.verify(token);
                if (map == null) {
                    // responseHandler(ctx, GatewayEnum.INVALID_TOKEN);
                    return null;
                }

            }catch (Exception e){

            }
        }


        return null;
    }


    public Optional<Boolean> policy(final Route route) {
        if (route != null) {
            logger.debug("==========JWT：获取路由========"+route.getId());
            return properties.getBo(route.getId());
        }
        return Optional.ofNullable(null);
    }
}

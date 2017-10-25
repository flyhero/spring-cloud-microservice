package com.dfocus.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.Claim;
import com.dfocus.common.base.JSONResult;
import com.dfocus.common.base.TipEnum;
import com.dfocus.gateway.auth.UcenterAuth;
import com.dfocus.gateway.base.GatewayEnum;
import com.dfocus.gateway.base.GatewayResult;
import com.dfocus.gateway.config.properties.JwtProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.DEBUG_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

public class AuthPreFilter extends AbstractGatewayFilter{

    private Logger logger = LoggerFactory.getLogger(AuthPreFilter.class);

    private final JwtProperties properties;

    @Autowired
    private UcenterAuth ucenterAuth;
    @Override
    public String filterType() {
        return PRE_TYPE;
    }
    //优先级为0，数字越大，优先级越低
    @Override
    public int filterOrder() {
        return DEBUG_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return properties.isEnabled() && policy(getMatchingRoute()).isPresent();
    }


    public AuthPreFilter(JwtProperties properties, UrlPathHelper urlPathHelper, RouteLocator routeLocator) {
        super(routeLocator,urlPathHelper);
        this.properties = properties;
/*        this.urlPathHelper = urlPathHelper;
        this.routeLocator = routeLocator;*/
    }

    @Override
    public Object run() {
        System.out.println("=============AuthPreFilter==============");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getHeader("Authorization");
        HttpStatus tooManyRequests = HttpStatus.EXPECTATION_FAILED;


        if (token == null || token.equals("")) {
                super.responseHandler(ctx,GatewayEnum.MISS_TOKEN);
                return  null;
/*            ZuulException zuulException = new ZuulException(tooManyRequests.toString(), tooManyRequests.value(), null);
            throw new ZuulRuntimeException(zuulException);*/
        }else {
            try{
                Map<String, Claim> map= ucenterAuth.verify(token);
                if (map == null) {
                    super.responseHandler(ctx, GatewayEnum.INVALID_TOKEN);
                    return null;
                }

            }catch (Exception e){

            }
        }


        return null;
    }

    public Optional<Boolean> policy(final Route route) {
        if (route != null) {
            return properties.getBo(route.getId());
        }
        return Optional.ofNullable(null);
    }
}

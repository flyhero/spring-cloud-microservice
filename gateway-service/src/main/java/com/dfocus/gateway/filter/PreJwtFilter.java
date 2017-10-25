package com.dfocus.gateway.filter;

import com.auth0.jwt.interfaces.Claim;
import com.dfocus.gateway.auth.UcenterAuth;
import com.dfocus.gateway.base.GatewayEnum;
import com.dfocus.gateway.config.properties.JwtProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * Author: qfwang
 * Date: 2017-10-25 下午7:44
 */
public class PreJwtFilter extends ZuulFilter{
    private Logger logger = LoggerFactory.getLogger(PreJwtFilter.class);

    private final JwtProperties properties;
    private final RouteLocator routeLocator;
    private final UrlPathHelper urlPathHelper;

    @Autowired
    private UcenterAuth ucenterAuth;

    public PreJwtFilter(JwtProperties properties, RouteLocator routeLocator, UrlPathHelper urlPathHelper) {
        this.properties = properties;
        this.routeLocator = routeLocator;
        this.urlPathHelper = urlPathHelper;
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
        System.out.println("=============AuthPreFilter==============");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getHeader("Authorization");



        if (token == null || token.equals("")) {
            //responseHandler(ctx, GatewayEnum.MISS_TOKEN);
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
   /*         return  null;
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

    Route getMatchingRoute() {
        String requestURI = urlPathHelper.getPathWithinApplication(RequestContext.getCurrentContext().getRequest());
        return routeLocator.getMatchingRoute(requestURI);
    }
    public Optional<Boolean> policy(final Route route) {
        if (route != null) {
            return properties.getBo(route.getId());
        }
        return Optional.ofNullable(null);
    }

}

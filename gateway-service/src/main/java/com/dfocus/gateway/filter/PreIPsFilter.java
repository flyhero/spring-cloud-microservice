package com.dfocus.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.dfocus.common.util.HttpUtils;
import com.dfocus.gateway.base.GatewayEnum;
import com.dfocus.gateway.base.GatewayResult;
import com.dfocus.gateway.config.properties.IPsRestrictionProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * User: qfwang
 * Date: 2017-10-10
 * Time: 下午2:44
 */

public class PreIPsFilter extends AbstractGatewayFilter {

    private final Logger logger = LoggerFactory.getLogger(PreIPsFilter.class);

    private IPsRestrictionProperties iPsRestrictionProperties;

    public PreIPsFilter(IPsRestrictionProperties iPsRestrictionProperties, RouteLocator routeLocator, UrlPathHelper urlPathHelper) {
        super(routeLocator,urlPathHelper);
        this.iPsRestrictionProperties = iPsRestrictionProperties;
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -2;
    }

    @Override
    public boolean shouldFilter() {
        boolean flag = policy(getMatchingRoute()).isPresent();
        logger.debug("是否启用IP限制："+flag);
        return iPsRestrictionProperties.isEnabled() && flag;
    }

    @Override
    public Object run() {
        logger.info("================IP限制中==============");
        RequestContext ctx = RequestContext.getCurrentContext();
        String ip = HttpUtils.getIpAddr(ctx.getRequest());
        Route route = getMatchingRoute();
        policy(route).ifPresent(ipList -> {
            final String black = ipList.getBlacklist();
            final String white = ipList.getWhitelist();
            if(black != null && !black.equals("")){
                final String[] blacklist = black.split(",");
                for(String s : blacklist){
                    String suffix = s.substring(s.lastIndexOf(".")+1,s.length());
                    String prefixThree= s.substring(0,s.lastIndexOf("."));
                    if(suffix.equals("*")){
                        if(ip.contains(prefixThree)){
                            responseHandler(ctx, GatewayEnum.ACCESS_DENIED);
                            break;
                        }
                    }else {
                        if(ip.equals(s)){
                            // 禁止
                            responseHandler(ctx, GatewayEnum.ACCESS_DENIED);
                            break;
                        }
                    }
                       // 192.168.0.*   192.168.0.12
                }
            }else {
                final String[] whitelist = ipList.getWhitelist().split(",");
                for (String w : whitelist){
                    String suffix = w.substring(w.lastIndexOf(".")+1,w.length());
                    String prefixThree= w.substring(0,w.lastIndexOf("."));
                    if(suffix.equals("*")){
                        if(!ip.contains(prefixThree)){
                            responseHandler(ctx, GatewayEnum.ACCESS_DENIED);
                            break;
                        }
                    }else {
                        if(!ip.equals(w)){
                            responseHandler(ctx, GatewayEnum.ACCESS_DENIED);
                            break;
                        }
                    }
                }
            }




        });

        return null;
    }


    public Optional<IPsRestrictionProperties.IPList> policy(final Route route) {
        if (route != null) {
            logger.debug("==========IP限制：获取路由========"+route.getId());
            return iPsRestrictionProperties.getIps(route.getId());
        }
        return Optional.ofNullable(null);
    }

}

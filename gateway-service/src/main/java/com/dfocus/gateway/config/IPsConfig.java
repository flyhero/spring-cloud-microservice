package com.dfocus.gateway.config;

import com.dfocus.gateway.config.properties.IPsRestrictionProperties;
import com.dfocus.gateway.config.properties.JwtProperties;
import com.dfocus.gateway.filter.AuthPreFilter;
import com.dfocus.gateway.filter.PreIPsFilter;
import com.netflix.zuul.ZuulFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UrlPathHelper;

/**
 * Author: qfwang
 * Date: 2017-10-24 下午7:37
 */
@Configuration
@ConditionalOnProperty(prefix = "zuul.ip",name = "enabled",havingValue = "true")
public class IPsConfig {
    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Bean
    public ZuulFilter IpsPreFilter(IPsRestrictionProperties properties, RouteLocator routeLocator, UrlPathHelper urlPathHelper){
        return new PreIPsFilter(properties,routeLocator,urlPathHelper);
    }
}

package com.dfocus.gateway.config;

import com.dfocus.gateway.filter.AuthPreFilter;
import com.netflix.zuul.ZuulFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UrlPathHelper;

/**
 * Author: qfwang
 * Date: 2017-10-23 下午1:38
 */
@Configuration
//@EnableConfigurationProperties(JwtProperties.class)
@ConditionalOnProperty(prefix = "zuul.jwt",name = "enabled",havingValue = "true")
@RefreshScope
public class JwtConfig {
    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Autowired
    private JwtProperties jwtProperties;
    @Bean
    public ZuulFilter authPreFilter(JwtProperties jwtProperties, RouteLocator routeLocator,UrlPathHelper urlPathHelper){
        return new AuthPreFilter(jwtProperties,urlPathHelper,routeLocator);
    }
}

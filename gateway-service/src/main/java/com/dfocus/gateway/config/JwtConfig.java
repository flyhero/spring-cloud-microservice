package com.dfocus.gateway.config;

import com.dfocus.gateway.config.properties.JwtProperties;
import com.dfocus.gateway.filter.PreJwtFilter;
import com.netflix.zuul.ZuulFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UrlPathHelper;

/**
 * Author: qfwang
 * Date: 2017-10-23 下午1:38
 */
@Configuration
//@EnableConfigurationProperties(JwtProperties.class) 相当于对JwtProperties加注解@Component
@ConditionalOnProperty(prefix = "zuul.jwt",name = "enabled",havingValue = "true")
public class JwtConfig {
    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Bean
    public ZuulFilter authPreFilter(JwtProperties jwtProperties, RouteLocator routeLocator, UrlPathHelper urlPathHelper){
        return new PreJwtFilter(jwtProperties,routeLocator,urlPathHelper);
    }
}

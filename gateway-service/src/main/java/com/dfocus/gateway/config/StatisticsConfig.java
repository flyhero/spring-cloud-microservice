package com.dfocus.gateway.config;

import com.dfocus.gateway.config.properties.JwtProperties;
import com.dfocus.gateway.filter.PostStatisticsFilter;
import com.dfocus.gateway.filter.PreJwtFilter;
import com.dfocus.gateway.filter.PreStatisticsFilter;
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
public class StatisticsConfig {
    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Bean
    public ZuulFilter preStatisticsFilter(RouteLocator routeLocator, UrlPathHelper urlPathHelper){
        System.out.println("+++++++++++++++++++StatisticsConfig");
        return new PreStatisticsFilter(routeLocator,urlPathHelper);
    }
    @Bean
    public ZuulFilter postStatisticsFilter(RouteLocator routeLocator, UrlPathHelper urlPathHelper){
        return new PostStatisticsFilter(routeLocator,urlPathHelper);
    }
}

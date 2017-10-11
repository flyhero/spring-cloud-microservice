package com.dfocus.gateway.event;

import com.dfocus.gateway.route.CustomRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by xujingfeng on 2017/4/1.
 */
@Service
public class RefreshRouteService {

    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    RouteLocator routeLocator;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void refreshRoute() {
        RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routeLocator);
        publisher.publishEvent(routesRefreshedEvent);
    }

    public int addRoute(CustomRouteLocator.ZuulRouteVO zuulRouteVO){
        return jdbcTemplate.update("INSERT INTO api_route(path,service_id,url,enabled,api_name,created_time) VALUES (?,?,?,?,?,now())",new Object[]{zuulRouteVO.getPath(),zuulRouteVO.getServiceId(),zuulRouteVO.getUrl(),zuulRouteVO.getEnabled(),zuulRouteVO.getApiName()});
    }

}

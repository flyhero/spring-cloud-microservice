package com.dfocus.gateway.event;

import com.dfocus.gateway.route.CustomRouteLocator;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


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
        int num=jdbcTemplate.update("INSERT INTO api_route(id,path,service_id,url,enabled,api_name,created_time) VALUES (?,?,?,?,?,?,now())",new Object[]{zuulRouteVO.getServiceId(),zuulRouteVO.getPath(),zuulRouteVO.getServiceId(),zuulRouteVO.getUrl(),zuulRouteVO.getEnabled(),zuulRouteVO.getApiName()});
        refreshRoute();
        return num;
    }
    public int updateRoute(CustomRouteLocator.ZuulRouteVO zuulRouteVO){
        StringBuffer sql = new StringBuffer("UPDATE api_route SET enabled = ?,");
        Object[] params = new Object[]{zuulRouteVO.getEnabled()};
        if(zuulRouteVO.getServiceId()!=null && !zuulRouteVO.getServiceId().equals("")){
            sql.append("id = ? ,service_id = ?,");
            ArrayUtils.add(params,zuulRouteVO.getServiceId());
            ArrayUtils.add(params,zuulRouteVO.getServiceId());
        }
        if(zuulRouteVO.getPath()!=null && !zuulRouteVO.getPath().equals("")){
            sql.append("path = ?,");
            ArrayUtils.add(params,zuulRouteVO.getPath());
        }
        if(zuulRouteVO.getUrl()!=null && !zuulRouteVO.getUrl().equals("")){
            sql.append("url = ?,");
            ArrayUtils.add(params,zuulRouteVO.getUrl());
        }
        sql.append("strip_prefix = ? where route_id = ?");
        ArrayUtils.add(params,zuulRouteVO.isStripPrefix());
        ArrayUtils.add(params,zuulRouteVO.getRouteId());
        int num= jdbcTemplate.update(sql.toString(),params);
        refreshRoute();
        return num;
    }
    public List findRouteList(){
        List<CustomRouteLocator.ZuulRouteVO> results = jdbcTemplate.query("select * from api_route",new BeanPropertyRowMapper<>(CustomRouteLocator.ZuulRouteVO.class));
        return results;
    }
    public List findRouteList(boolean enabled){
        List<CustomRouteLocator.ZuulRouteVO> results = jdbcTemplate.query("select * from api_route where enabled = ? ",new Object[]{enabled},new BeanPropertyRowMapper<>(CustomRouteLocator.ZuulRouteVO.class));
        return results;
    }

}

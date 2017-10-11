package com.dfocus.gateway.controller;

import com.dfocus.common.base.JSONResult;
import com.dfocus.gateway.event.RefreshRouteService;
import com.dfocus.gateway.route.CustomRouteLocator;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class RouteController {

    @Autowired
    RefreshRouteService refreshRouteService;

    @GetMapping("/refreshRoute")
    public String refreshRoute(){
        refreshRouteService.refreshRoute();
        return "refreshRoute";
    }

    @Autowired
    ZuulHandlerMapping zuulHandlerMapping;

    @GetMapping("/watchNowRoute")
    public String watchNowRoute(){
        //可以用debug模式看里面具体是什么
        Map<String, Object> handlerMap = zuulHandlerMapping.getHandlerMap();
        return "watchNowRoute";
    }




    @ApiOperation(value = "添加路由信息")
    @PostMapping("addRoute")
    public JSONResult addRoute(CustomRouteLocator.ZuulRouteVO zuulRouteVO){
        if (refreshRouteService.addRoute(zuulRouteVO) != 0) {
            return JSONResult.ok();
        }
        return JSONResult.error();
    }


}

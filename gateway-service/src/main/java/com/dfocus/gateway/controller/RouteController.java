package com.dfocus.gateway.controller;

import com.dfocus.common.base.JSONResult;
import com.dfocus.gateway.event.RefreshRouteService;
import com.dfocus.gateway.route.CustomRouteLocator;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 路由操作
 */
@Controller
@RequestMapping("route")
public class RouteController {

    private Logger logger= LoggerFactory.getLogger(RouteController.class);

    @RequestMapping("test")
    public String test(){
        return "test";
    }


    @Autowired
    RefreshRouteService refreshRouteService;

    @GetMapping("/refreshRoute")
    public String refreshRoute(){
        refreshRouteService.refreshRoute();
        return "refreshRoute";
    }

    @Autowired
    ZuulHandlerMapping zuulHandlerMapping;

    /**
     *
     * @Title: watchNowRoute
     * @author qfwang
     * @params []
     * @return java.lang.String
     * @date 2017/10/12 上午9:42
     */
    @GetMapping("/watchNowRoute")
    public String watchNowRoute(){
        //可以用debug模式看里面具体是什么
        Map<String, Object> handlerMap = zuulHandlerMapping.getHandlerMap();
        return "watchNowRoute";
    }
    /**
     *
     * @title: addRoute
     * @author qfwang
     * @params [ZuulRouteVO]
     * @return ModelAndView
     * @date 2017/10/12 上午10:09
     */
    @ApiOperation(value = "添加路由信息")
    @PostMapping("addRoute")
    public ModelAndView addRoute(CustomRouteLocator.ZuulRouteVO zuulRouteVO){
        logger.info(zuulRouteVO.toString());
        ModelAndView mv = new ModelAndView();
/*        if (refreshRouteService.addRoute(zuulRouteVO) != 0) {
            return JSONResult.ok();
        }*/
        mv.setViewName("/route");
        return mv;

    }


}

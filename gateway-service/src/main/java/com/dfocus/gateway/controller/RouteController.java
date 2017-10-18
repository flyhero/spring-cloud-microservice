package com.dfocus.gateway.controller;

import com.dfocus.common.base.JSONResult;
import com.dfocus.common.controller.BaseController;
import com.dfocus.gateway.event.RefreshRouteService;
import com.dfocus.gateway.route.CustomRouteLocator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 路由操作
 */
@Api(value = "服务api管理")
@Controller
@RequestMapping("route")
public class RouteController extends BaseController{

    private Logger logger= LoggerFactory.getLogger(RouteController.class);

    @RequestMapping("test")
    public String test(){
        return "test";
    }


    @Autowired
    RefreshRouteService refreshRouteService;

    @GetMapping("/refreshRoute")
    @ResponseBody
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
    @ResponseBody
    public String watchNowRoute(){
        //可以用debug模式看里面具体是什么
        Map<String, Object> handlerMap = zuulHandlerMapping.getHandlerMap();
        for (String key:handlerMap.keySet()){
            System.out.println("key:"+key);
            System.out.println(handlerMap.get(key).toString());
            System.out.println(handlerMap.get(key).getClass());
           // ZuulProperties.ZuulRoute route = (ZuulProperties.ZuulRoute)handlerMap.get(key);
            //System.out.println("路由信息："+route.getId()+"--"+route.getServiceId());
        }
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
        List<CustomRouteLocator.ZuulRouteVO> list = null;
        try {
            if (refreshRouteService.addRoute(zuulRouteVO) != 0) {
                mv.addObject("msg","ok");
            }else {
                mv.addObject("msg","error");
            }
            list = refreshRouteService.findRouteList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mv.addObject("apiRoute",list);
        mv.setViewName("/list");
        return mv;

    }


}

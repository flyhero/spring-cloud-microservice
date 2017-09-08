/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dfoucs.ribbon.controller;


import com.dfoucs.ribbon.service.LoadBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

/**
 *
 * @author darren.shuxing.liu
 */
@RestController
@RequestMapping("/v1/lb")
public class LoadBalanceController {
    /**
     *
     */
    protected static final Logger logger = Logger.getLogger(LoadBalanceController.class.getName());
    
    /*
    * 调用LoadBalanceService
    */
    
    @Autowired
    LoadBalanceService lbService;
    
    /*
    * 测试负载均衡之微服务的端口
    * http://.../v1/lb/testport?name={microserviceName}
    * @param name
    * @return 微服务实例的端口
    */
    @RequestMapping(value = "/testport")
    public String TestPort(@RequestParam("name") String microserviceName){
        String response = lbService.TestPort(microserviceName);
        logger.info(response);
        return response;
    } 
    
    /*
    * 测试负载均衡之微服务响应
    * http://.../v1/lb/testorderapi?name={microserviceName}&userid={userid}
    * @param name
    * @param userid
    * @return 端口以及通过用户id获取的订单信息
    */
    
    @RequestMapping(value = "/testorderapi")
    public String TestService(@RequestParam("name") String microserviceName,
            @RequestParam("userid") String userId){
        String servicePort = lbService.TestPort(microserviceName);
        String serviceResponse = lbService.TestGetOrderByUserId(microserviceName, userId);
        
        logger.info(String.format("invoked by {%s}, port {%s}", 
                lbService.getClass().getName(), servicePort));
        
        String response = String.format("%s, response: %s",
                servicePort, serviceResponse);
        return response;
    }   
}

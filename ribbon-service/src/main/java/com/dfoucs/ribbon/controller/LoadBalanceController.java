/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dfoucs.ribbon.controller;


import com.dfoucs.ribbon.service.LoadBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("test")
    public String test(HttpServletRequest request){
        System.out.println("request.getContextPath():"+request.getContextPath());
        System.out.println("request.getPathInfo():"+request.getPathInfo());

        String query=request.getQueryString();

        String uri =null;
        try {
            System.out.println(URLDecoder.decode(query,"UTF-8"));
            String s[]=URLDecoder.decode(query,"UTF-8").split("&");
            String name=null;
            String api=null;

            name = s[0].split("=")[1];
            api = s[1].split("=")[1];
            uri = String.format("http://%s/%s", name,api);
        }catch (UnsupportedEncodingException e){

        }

            return restTemplate.getForObject(uri, String.class);

    }
    @PostMapping("test")
    public String post(HttpServletRequest request){
        System.out.println("request.getContextPath():"+request.getContextPath());
        System.out.println("request.getPathInfo():"+request.getPathInfo());

        String query=request.getQueryString();

        String uri =null;
        try {
            System.out.println(URLDecoder.decode(query,"UTF-8"));
            String s[]=URLDecoder.decode(query,"UTF-8").split("&");
            String name=null;
            String api=null;

            name = s[0].split("=")[1];
            api = s[1].split("=")[1];
            uri = String.format("http://%s/%s", name,api);
        }catch (UnsupportedEncodingException e){

        }

        return restTemplate.getForObject(uri, String.class);

    }
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

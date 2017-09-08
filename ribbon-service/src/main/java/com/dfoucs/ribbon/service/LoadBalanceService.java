/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dfoucs.ribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author darren.shuxing.liu
 */
@Service
public class LoadBalanceService {
    @Autowired
    RestTemplate restTemplate;
    
    /*
     *通过注解HystrixCommand引入熔断器机制
     *fallbackMethod指定回调函数
     */
    @HystrixCommand(fallbackMethod = "TestPortFallback")
    public String TestPort(String microserviceName) {
        String uri = String.format("http://%s/v1/lbtest/port", microserviceName);
        return restTemplate.getForObject(uri, String.class);
    }
    /*回调函数要和对应的调用函数的参数保持一致*/
    public String TestPortFallback(String microserviceName) {
        return "Hystrix works when BOOKINGCAR-SERVICE is down";
    }
    
    public String TestGetOrderByUserId(String microserviceName, String userId) {
        String uri = String.format("http://%s/v1/order?userid=%s", 
                microserviceName, userId);
        return restTemplate.getForObject(uri, String.class);
    }
}
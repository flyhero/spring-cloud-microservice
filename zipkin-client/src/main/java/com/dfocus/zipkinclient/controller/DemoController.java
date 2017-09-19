package com.dfocus.zipkinclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * User: qfwang
 * Date: 2017-09-19
 * Time: 下午3:01
 */
@RestController
public class DemoController {

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getTemp(){
        return new RestTemplate();
    }

    @RequestMapping("/call")
    public String callHome(){
        String result= this.restTemplate.getForObject("http://localhost:8084/user/hello", String.class);
        return result+" world";
    }
}

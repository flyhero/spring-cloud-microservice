package com.dfoucs.order.call;

import com.dfoucs.order.fallback.UserFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "UCENTER-SERVICE",fallback = UserFallback.class)
public interface UserClient {

    @RequestMapping(value = "/user/hello",method = RequestMethod.GET)
    public String hello1();
}

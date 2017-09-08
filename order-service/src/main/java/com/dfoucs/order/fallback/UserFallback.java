package com.dfoucs.order.fallback;

import com.dfoucs.order.call.UserClient;
import org.springframework.stereotype.Component;

@Component
public class UserFallback implements UserClient {
    @Override
    public String hello1() {
        return "断路器作用";
    }
}

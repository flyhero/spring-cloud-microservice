package com.dfoucs.gateway.auth;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UcenterAuth implements Auth{
    @Override
    public String getToken(Map<String, Object> map) {
        return null;
    }

    @Override
    public boolean verify() {
        return false;
    }
}

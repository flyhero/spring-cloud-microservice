package com.dfoucs.gateway.auth;

import java.util.Map;

public interface Auth {
    String getToken(Map<String,Object> map);
    boolean verify();
}

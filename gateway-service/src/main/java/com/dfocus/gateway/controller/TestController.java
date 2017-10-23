package com.dfocus.gateway.controller;

import com.netflix.client.config.IClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: qfwang
 * Date: 2017-10-18 下午2:41
 */
@RestController
@RefreshScope
public class TestController {
    public static final String RESPONSE_BODY = "ResponseBody";

    @Value("${foo}")
    public String name;
    @Value("${zuul.jwt.enabled}")
    public String jwt;

    @GetMapping("/serviceA")
    public ResponseEntity<String> serviceA() {
        return ResponseEntity.ok(RESPONSE_BODY);
    }

    @GetMapping("/serviceB")
    public ResponseEntity<String> serviceB() {
        return ResponseEntity.ok("sb");
    }

    @GetMapping("/serviceC")
    public ResponseEntity<String> serviceC() {
        return ResponseEntity.ok(name+jwt);
    }

    @GetMapping("/serviceD/{paramName}")
    public ResponseEntity<String> serviceD(@PathVariable String paramName) {
        return ResponseEntity.ok(RESPONSE_BODY + " " + paramName);
    }

    @GetMapping("/serviceE")
    public ResponseEntity<String> serviceE() throws InterruptedException {
        Thread.sleep(1100);
        return ResponseEntity.ok(RESPONSE_BODY);
    }
}

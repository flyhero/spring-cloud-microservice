package com.dfocus.gateway.controller;

import com.dfocus.common.base.JSONResult;
import com.dfocus.gateway.base.GatewayResult;
import com.netflix.client.config.IClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: qfwang
 * Date: 2017-10-18 下午2:41
 */
@RestController
@RefreshScope
public class TestController implements ErrorController{
    public static final String RESPONSE_BODY = "ResponseBody";

    @Value("${zuul.ip.enabled}")
    private String enabled;


    @GetMapping("/serviceA")
    public ResponseEntity<String> serviceA() {
        return ResponseEntity.ok(enabled);
    }

    @GetMapping("/serviceB")
    public ResponseEntity<String> serviceB() {
        return ResponseEntity.ok("sb"+enabled);
    }

    @GetMapping("/serviceC")
    public ResponseEntity<String> serviceC() {
        return ResponseEntity.ok(RESPONSE_BODY);
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

    /**
     * 当filter出现异常时会请求该接口
     * @title: error
     * @author qfwang
     * @params []
     * @return com.dfocus.gateway.base.GatewayResult
     * @date 2017/10/26 下午8:19
     */
    @RequestMapping("/error")
    public GatewayResult error(){
        return new GatewayResult(500,"出现错误","");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}

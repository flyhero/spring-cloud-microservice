package com.dfocus.config.client.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: qfwang
 * Date: 2017-09-19
 * Time: 上午9:47
 */
@RestController
public class ConfigClientController {

    @Value("foo")
    private String foo;

    @GetMapping("config")
    public String test(){
        System.out.println(foo);
        return foo;
    }
}

package com.dfoucs.ucenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("hello")
    public String hello(){
        return "8084 get hello world";
    }
    @PostMapping("hello")
    public String hello1(){
        return "post hello world";
    }

    @DeleteMapping("del")
    public String del(){
        return "delete success";
    }

    @GetMapping("test")
    public String test(){
        return "get test";
    }
}

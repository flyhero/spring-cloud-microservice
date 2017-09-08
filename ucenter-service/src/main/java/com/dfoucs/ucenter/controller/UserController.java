package com.dfoucs.ucenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("hello")
    public String hello(){
        return "get hello world";
    }
    @PostMapping("hello")
    public String hello1(){
        return "post hello world";
    }
}

package io.github.flyhero.user.controller;

import java.util.HashMap;
import java.util.Map;

import cn.hutool.jwt.JWTUtil;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.flyhero.common.model.UserHolder;


@RestController
public class UserController {

	@GetMapping("/user/login")
	public String getUser() {
		Map<String, Object> payload = new HashMap<>();
		payload.put("userId", 123);
		payload.put("userName", "Mr.Wang");
		payload.put("tenantId", 234);
		String token = JWTUtil.createToken(payload, "123456".getBytes());
		return token;
	}

	@GetMapping("/user/getToken")
	public String getToken() {
		System.out.println(UserHolder.get().toString());
		return "success";
	}
}

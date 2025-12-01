package io.github.flyhero.order.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.flyhero.common.exception.BizException;
import io.github.flyhero.user.api.DemoService;

@RestController
public class OrderTestController {
	@DubboReference
	private DemoService demoService;
	@GetMapping("/order/test")
	public String getOrder() {
		throw new BizException("主动抛出异常");
	}

	@GetMapping("/order/test2")
	public String getOrder2(@RequestParam("name") String name) {
		String s = demoService.sayHello(name);
		return s;
	}
}

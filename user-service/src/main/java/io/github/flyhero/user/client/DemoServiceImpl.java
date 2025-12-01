package io.github.flyhero.user.client;

import org.apache.dubbo.config.annotation.DubboService;

import io.github.flyhero.user.api.DemoService;

@DubboService
public class DemoServiceImpl implements DemoService {
	public String sayHello(String name) {
		return "hello " + name;
	}
}

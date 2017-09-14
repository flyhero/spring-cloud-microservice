package com.dfoucs.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


@SpringBootApplication
@EnableDiscoveryClient //注册服务
@EnableZuulProxy	//开启路由
public class GatewayServiceApplication {
	private static final Logger logger = LoggerFactory.getLogger(GatewayServiceApplication.class);
	public static void main(String[] args) {
		logger.info("======================正在启动网关服务=======================");
		logger.debug("======================正在启动网关服务=======================");
		SpringApplication.run(GatewayServiceApplication.class, args);
		//logger.info("======================网关服务启动完毕=======================");
	}
}

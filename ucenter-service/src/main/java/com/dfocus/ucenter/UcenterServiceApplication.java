package com.dfocus.ucenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UcenterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UcenterServiceApplication.class, args);
	}
}
//curl -i -X POST  --url http://127.0.0.1:8001/apis/userapi --data 'upstream_url=http://service.dfocus.com/user/hello'
//curl -i -X POST  --url http://127.0.0.1:8001/upstreams/service.dfocus.com/targets --data 'target=192.168.0.102:8084' --data 'weight=500'
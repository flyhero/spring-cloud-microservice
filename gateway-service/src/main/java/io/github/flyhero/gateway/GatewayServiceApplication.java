package io.github.flyhero.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.github.flyhero.gateway.config.GatewayProperty;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties(GatewayProperty.class)
public class GatewayServiceApplication {

	public static void main(String[] args) {
		System.setProperty("io.netty.resolver.dns.DnsNameResolverProvider", "DEBUG");
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

}

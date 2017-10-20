package com.dfocus.gateway;

import com.netflix.client.ClientException;
import com.netflix.client.ClientFactory;
import com.netflix.client.IClient;
import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;


import javax.servlet.http.HttpServletRequest;


@SpringBootApplication
//@EnableDiscoveryClient //注册服务
@EnableZuulProxy	//开启路由
public class GatewayServiceApplication {
	private static final Logger logger = LoggerFactory.getLogger(GatewayServiceApplication.class);

/*	@Bean
	public IClient setRibbon(){
		logger.info("================进行负载均衡配置=================");
		String listOfServers = "http://localhost:8990/,http://localhost:8991/";
		IClient client =null;
		try {
			client= ClientFactory.createNamedClient("compute",new DefaultClientConfigImpl().set(CommonClientConfigKey.ListOfServers,listOfServers).getClass());
		} catch (ClientException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		return client;
	}*/

	@Bean(name = "zuulProperties")
	@RefreshScope
	@ConfigurationProperties("zuul")
	@Primary
	public ZuulProperties zuulProperties() {
		ZuulProperties zuulProperties = new ZuulProperties();
		return zuulProperties;
	}
	public static void main(String[] args) {
		logger.info("======================正在启动网关服务=======================");

		SpringApplication.run(GatewayServiceApplication.class, args);
		//logger.info("======================网关服务启动完毕=======================");
	}
/*	mysql001:
	image: mysql
	volumes:
			- /docker/mysql/data:/var/lib/mysql
	environment:
	TZ: 'Asia/Shanghai'
	MYSQL_ROOT_PASSWORD: 123456
	ports:
			- "3306:3306"
	networks:
			- "netName1"

			FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD ./target/gateway-service-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
EXPOSE 8082

			*/

}

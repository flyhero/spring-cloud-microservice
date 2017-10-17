package com.dfocus.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


@SpringBootApplication
//@EnableDiscoveryClient //注册服务
@EnableZuulProxy	//开启路由
public class GatewayServiceApplication {
	private static final Logger logger = LoggerFactory.getLogger(GatewayServiceApplication.class);
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

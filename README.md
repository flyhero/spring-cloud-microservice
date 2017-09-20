# spring-cloud-microservice

## 功能描述
使用spring cloud eureka,zuul,ribbon搭建的微服务框架

## 开发环境


## 项目结构


| 服务名称 | 模块名称 | 服务端口 | 管理端口 | 作用
|--------|--------|--------|--------|--------|
|eureka-service|ureka-service|8081|        |服务注册与发现
|gateway-service| gateway-service | 8082 |        |API网关
|ribbon-service| ribbon-service| 8083    |        |负载均衡
|config-server|config-server| 8881     |        |配置服务端
|config-client| config-client-service |8882 |        | 配置客户端
|monitoring-service|monitoring-service|8001||熔断监控服务
|zipkin-server|zipkin-server|9411||调用链追踪



``` lua
spring-cloud-microservice
├── common -- 框架公共模块
├── admin-service -- 后台管理模板
├── config-client-service -- 配置中心客户端[端口:1000]
├── config-repo -- 配置文件位置[端口:1001]
├── config-service -- 配置中心服务端
├── eureka-service -- 服务注册与发现
├── gateway-service -- API网关[端口:1000]
├── monitoring-service -- 监控[端口:1001]
├── order-service -- 订单服务模块
├── ribbon-service -- 负载均衡[端口:1000]
├── ucenter-service -- 用户模块[端口:1001]
└── zipkin-service -- 追踪

```

## 微服务架构图


## 部署说明

zipkin client 依赖
```
	<!--日志追踪依赖 -->
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-sleuth-zipkin-stream</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-stream-rabbit</artifactId>
	</dependency>

```
config client 依赖
```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-config</artifactId>
		</dependency>

```



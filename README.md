# spring-cloud-microservice

## 功能描述
使用spring cloud eureka,zuul,ribbon搭建的微服务框架

## 环境搭建
### 开发工具
- MySql: 数据库
- SVN|Git: 版本管理
- Nginx: 反向代理服务器
- IntelliJ IDEA: 开发IDE
- Navicat for MySQL: 数据库客户端

### 开发环境
- Jdk8
- Mysql5.5+
- Redis
- RabbitMQ

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
├── config-client-service -- 配置中心客户端[端口:8882]
├── config-repo -- 配置文件位置
├── config-server -- 配置中心服务端[端口:8881]
├── eureka-service -- 服务注册与发现[端口:8081]
├── gateway-service -- API网关[端口:8082]
├── monitoring-service -- 监控[端口:8001]
├── order-service -- 订单服务模块
├── ribbon-service -- 负载均衡[端口:8083]
├── ucenter-service -- 用户模块[端口:8084]
└── zipkin-server -- 追踪[端口:9411]

```

## 微服务架构图
![架构图](doc/spring-cloud-architecture.png)

## 部署说明

## 业务微服务依赖
zipkin client 依赖
```
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
# 相关界面
- 服务发现与注册界面
![服务发现与注册界面](doc/eureka.png)

- 消息队列
![消息队列](doc/rabbitmq.png)

- 调用链追踪
![调用链追踪](doc/zipkin.png)
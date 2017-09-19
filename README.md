# spring-cloud-microservice
使用spring cloud eureka,zuul,ribbon搭建的微服务


| 服务名称 | 模块名称 | 服务端口 | 管理端口 | 作用
|--------|--------|--------|--------|--------|
|eureka-service|ureka-service|8081|        |服务注册与发现
|gateway-service| gateway-service | 8082 |        |API网关
|ribbon-service| ribbon-service| 8083    |        |负载均衡
|config-service|config-service| 8881     |        |配置服务端
|config-client| config-client-service |8882 |        | 配置客户端
|monitoring-service|monitoring-service|8001||熔断监控服务
|zipkin-service|zipkin-service|9411||追踪



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
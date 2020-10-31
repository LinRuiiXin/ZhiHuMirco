# ZhiHuMicro(开发中)

一个模仿知乎的后端系统，并提供了Android端---[ZhiHu](https://github.com/LinRuiiXin/ZhiHu) ,喜欢的话就点个Star吧！

基于微服务架构的ZhiHu服务端，将原本的单个应用拆分为多个模块，系统的扩展性和维护性得到有效改善

## 模块结构

|     模块     |                            介绍                            |
| :----------: | :--------------------------------------------------------: |
| EurekaServer |                        服务注册中心                        |
|   GateWay    |                          API网关                           |
|    Basic     | ZhiHuMicro的基础模块，包含所用数据对象，以及一些自动装配类 |
|     User     |                          用户模块                          |
|    Answer    |                          回答模块                          |
|   Question   |                          问题模块                          |
|   Comment    |                          评论模块                          |
| ...(开发中)  |                        ...(开发中)                         |



## 使用的框架 

基于 **SpringCloud Hoxton.SR8** 版本

1. SpringCloud-Netflix套件
   - Eureka : 服务发现
   - Zuul : 服务网关
   - Feign ：声明式服务调用，依赖 Ribbon 实现客户端负载均衡，以及 Hystrix 进行容错保护
2. Swagger API 文档框架
3. Mybatis 数据库ORM框架
4. SpringMVC web框架

## 中间件

1. Redis 缓存
2. RabbitMq 消息队列


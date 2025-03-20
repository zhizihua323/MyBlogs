# Druid 连接池
## 前言
**缘由**：想要优化数据库的性能，一般来说数据源连接池是最佳的优化方案（对比线程池来说）<br>

**分析传统连接**：采用数据源连接池方案可以极大的提高数据处理能力，*因为像Java连接数据库是是比较耗时的*，如果每次查询都重新连接数据库那样性能非常低效。<br>

**连接池原理**：换成连接池以后，*数据库操作无需每次都连接数据库*，只是复用连接，从而提升性能。<br>

---

## Druid
SpringBoot官方集成连接池是`HikariCP`,是性能最佳的。<br>

但是这里介绍阿里巴巴`Druid`，优势:<br>
- 性能优越（略低于 HikariCP）
- 扩展能力强
- 具备SQL拦截功能
- 具备数据统计分析功能，比如慢 SQL 分析

维护一个产品时，除了要考虑性能问题、还要考虑可维护性，那么*监控*就是一个重要手段<br>
Druid在这方面做的非常好，而且很稳定 [Druid](https://mvnrepository.com/artifact/com.alibaba/druid-spring-boot-starter)

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.1.23</version>
</dependency>
```
[相关配置，Druid Spring Boot Starter 中文文档](https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter)

## 如何开启监控
前面讲到Druid的监控对可维护性问题是一个良好的解决方案，那么如何配置呢？<br>
需要在`application.properties`文件添加配置项
```xml
spring.datasource.druid.stat-view-servlet.enabled=true
spring.datasource.druid.stat-view-servlet.url-pattern=/druid/*
spring.datasource.druid.stat-view-servlet.login-username=druid
spring.datasource.druid.stat-view-servlet.login-password=druid
spring.datasource.druid.stat-view-servlet.allow=
spring.datasource.druid.stat-view-servlet.deny=
```
- stat-view-servlet.enabled 用于开启Druid的监控统计功能。设置为true时，Druid会提供一个内置的Servlet来展示监控数据。
- stat-view-servlet.url-pattern 指定Druid监控页面的访问路径
- login-username 访问Druid监控页面的用户名
- login-password 密码
- allow 允许访问Druid监控页面的IP地址。（空表示允许所有ip）
- deny 拒绝访问的IP地址

http://localhost:8080/druid/login.html
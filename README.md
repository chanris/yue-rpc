# yue-rpc
手写rpc框架 power by [鱼皮rpc](https://github.com/liyupi/yu-rpc)。
基于Java + Etcd + Vert.x的高性能RPC框架，使用多种最新技术。其中，你可以学习到基于Vert.x的网络服务器开发、多种序列化器的实现、基于Etcd、Zookeeper等的注册中心、Java反射、动态代理、自定义网络协议开发、多种设计模式（单例、工厂、装饰者）、多种负载均衡实现、多种重试和容错机制、Spring Boot Stater开发等，大幅度提高对rpc底层原理的理解。

## 技术选型

### 主要技术栈

- Vert.x 框架
- Etcd 云原生存储中间件（jetcd 客户端）
- ZooKeeper 分布式协调工具（curator 客户端）
- SPI 机制
- ⭐️ 多种序列化器
    - JSON 序列化
    - Kryo 序列化
    - Hessian 序列化
- ⭐️ 多种设计模式
    - 双检锁单例模式
    - 工厂模式
    - 代理模式
    - 装饰者模式
- ⭐️ Spring Boot Starter 开发
- ⭐️反射和注解驱动
- Guava Retrying 重试库
- JUnit 单元测试
- Logback 日志库
- Hutool、Lombok 工具库

### 下一步工作
#### 完善文档
- [ ] 测试rpc框架性能，与常见的rpc框架进行对比
- [ ] 完善README文档
#### rpc核心
- 注册中心
  - [ ] 实现基于zookeeper的注册中心
  - [ ] 实现基于redis的注册中心
- 重试&容错
  - [ ] 实现降级到其他服务的容错策略
  - [ ] 实现转移到其他服务节点的容错策略
  - [ ] 引入多种重试策略，目前只有一种（固定时间间隔重试）
- 序列化器
  - [ ] 实现Kryo 序列化
  - [ ] Hessian 序列化

#### 框架优化
- [ ] 远程服务的注册与调用都需要引入公共服务依赖包，系统耦合性高，需要解耦。


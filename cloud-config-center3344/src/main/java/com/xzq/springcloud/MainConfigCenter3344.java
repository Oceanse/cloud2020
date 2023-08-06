package com.xzq.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 原理：
 * SpringCloud Config分为服务端和客户端两部分。
 * 服务端也称为分布式配置中心，它是一个独立的微服务应用，用来连接配置服务器(github/gitee)并为客户端提供获取配置信息，加密/解密信息等访问接口。
 * 配置服务器默认采用git来存储配置信息，这样就有助于对环境配置进行版本管理，并且可以通过git客户端工具来方便的管理和访问配置内容。
 * 客户端则是通过指定的配置中心来管理应用资源，以及与业务相关的配置内容，并在启动的时候从配置中心获取和加载配置信息.
 *
 * 优点：
 * 集中管理配置文件
 * 不同环境不同配置，动态化的配置更新，分环境部署比如dev/test/prod/beta/release
 * 运行期间动态调整配置，不再需要在每个服务部署的机器上编写配置文件，服务会向配置中心统一拉取配置自己的信息
 * 当配置发生变动时，服务不需要重启即可感知到配置的变化并应用新的配置
 * 将配置信息以REST接口的形式暴露 - post/crul访问刷新即可…
 */
@SpringBootApplication
@EnableConfigServer
public class MainConfigCenter3344 {
    public static void main(String[] args) {
        SpringApplication.run(MainConfigCenter3344.class, args);
    }
}

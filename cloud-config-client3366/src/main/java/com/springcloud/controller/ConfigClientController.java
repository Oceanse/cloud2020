package com.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Bus总线动态刷新全局广播
 * curl -X POST "http://localhost:3344/actuator/bus-refresh"
 * 利用消息总线触发一个服务端ConfigServer的/bus/refresh端点，而刷新所有客户端的配置；也就是当配置文件修改后，手动执行上面command,那么所有的客户端都会刷新加载最新配置
 *
 * 什么是总线
 * 在微服务架构的系统中，通常会使用轻量级的消息代理来构建一个共用的消息主题，并让系统中所有微服务实例都连接上来。由于该主题中产生的消息会被所有实例监听和消费，所以称它为消息总线。
 * 在总线上的各个实例，都可以方便地广播一些需要让其他连接在该主题上的实例都知道的消息。
 *
 * 基本原理
 * ConfigClient实例都监听消费MQ中同一个topic(默认是Spring Cloud Bus)。当一个服务刷新数据的时候，它会把这个信息放入到Topic中，这样其它监听同一Topic的服务就能得到通知，
 * 然后去更新自身的配置。
 * 发送上面curl请求的时候观察RabbitMQ GUI界面中的队列，会发现队列中先放入消息，然后马上被消费
 *
 * 思路：
 * 1 利用消息总线触发一个客户端/bus/refresh,而刷新所有客户端的配置
 * 2 利用消息总线触发一个服务端ConfigServer的/bus/refresh端点，而刷新所有客户端的配置
 * 一般采用思路2，因为思路1缺点：
 * 打破了微服务的职责单一性，因为微服务本身是业务模块，它本不应该承担配置刷新的职责。
 * 破坏了微服务各节点的对等性。
 * 有一定的局限性。例如，微服务在迁移时，它的网络地址常常会发生变化，此时如果想要做到自动刷新，那就会增加更多的修改。
 */
@RestController
@RefreshScope
public class ConfigClientController {

    @Value("${config.info}")
    private String configInfo;

    /**
     * http://localhost:3366/configInfo
     * @return
     */
    @GetMapping("/configInfo")
    public String getConfigInfo() {
        return configInfo;
    }
}

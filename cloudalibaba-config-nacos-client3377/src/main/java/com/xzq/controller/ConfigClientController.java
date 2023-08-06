package com.xzq.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * nacos作为主流的分布式配置中心和服务注册中心，已经应用在绝大多数的公司中，用起来很便捷
 * @RefreshScope: 实现配置自动更新，客户端是通过一个定时任务来检查自己监听的配置项的数据的，一旦服务端的数据发生变化时，客户端将会获取到最新的数据
 */
@RestController
@RefreshScope
public class ConfigClientController {

    //这里的@Value会去自动绑定nacos配置中心中的指定配置文件，并且这个一定要存在这个值，否则启动会报错
    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/config/info")
    public String getConfigInfo() {
        return configInfo;
    }
}

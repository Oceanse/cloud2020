package com.xzq.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName: OrderNacosController
 * @description:
 * @author: XZQ
 * @create: 2020/3/11 15:36
 **/
@RestController
public class OrderNacosController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-user-service}")
    private String serverUrl;

    @GetMapping("/fetchConfig")
    public String paymentInfo() {
        return restTemplate.getForObject(serverUrl + "/config/info", String.class);
    }

}

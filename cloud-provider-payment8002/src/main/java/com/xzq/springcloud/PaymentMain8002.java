package com.xzq.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 *  @EnableDiscoveryClient与@EnableEurekaClient区别：https://blog.csdn.net/wwg18895736195/article/details/83002637
 * @ClassName: PaymentMain8002
 * @description:
 * @author: XZQ
 * @create: 2020/3/6 9:30
 **/
@SpringBootApplication
@EnableEurekaClient//标识该服务为Eureka Client
@EnableDiscoveryClient
public class PaymentMain8002 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8002.class, args);
    }
}

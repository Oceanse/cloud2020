package com.xzq.springcloud.service;

import com.xzq.springcloud.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 在Spring Cloud中使用Feign，可以做到使用HTTP请求访问远程服务，就像调用本地方法一样的，开发者完全感知不到这是在调用远程方法，更感知不到在访问HTTP请求。
 * openfeign取代了之前的RestTemplate+ribbon,可同时实现http请求和负载均衡
 *
 * 这个接口指明了调用哪个微服务下面的哪个方法，注解指明了访问的微服务，抽象方法就是哪个微服务的方法
 **/
@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
//@FeignClient(value = "payment-route") //这里的value是springcloud-config中的route id,但是存在： Load balancer does not have available server for client: payment-route
public interface PaymentFeignService {
    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/payment/get/{id}")
    CommonResult getPaymentById(@PathVariable("id") Long id);

    /**
     * 模拟feign超时
     * openfeign底层默认超时时间是1s, 可以在yaml中配置
     * 这里payment支付服务的响应时间是3S, 消费端在yaml中设置的连接超时大于3s,所以不会超时
     * @return
     */
    @GetMapping(value = "/payment/feign/timeout")
    String paymentFeignTimeout();
}

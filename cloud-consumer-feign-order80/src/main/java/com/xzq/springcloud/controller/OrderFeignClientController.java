package com.xzq.springcloud.controller;

import com.xzq.springcloud.entities.CommonResult;
import com.xzq.springcloud.service.PaymentFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: OrderFeignClientController
 * @description:
 * @author: XZQ
 * @create: 2020/3/8 15:28
 **/
@RestController
@RequestMapping("/consumer/feign")
public class OrderFeignClientController {

    /**
     * 这里的PaymentFeignService就像本地服务一样，openfeign请求访问远程服务，就像调用本地方法一样的，
     */
    @Autowired
    private PaymentFeignService paymentFeignService;

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/paymentCommonResult/get/{id}")
    public CommonResult getPaymentCommonResultById(@PathVariable("id") Long id) {
        return paymentFeignService.getPaymentById(id);
    }


    /**
     * 根据id查询
     *feign集成了Ribbon, 默认启动轮询负载均衡
     * @param id
     * @return
     */
    @GetMapping(value = "/paymentBody/get/{id}")
    public String getPaymentBodyById(@PathVariable("id") Long id) {
        return paymentFeignService.getPaymentById(id).getMessage();
    }


    /**
     * 模拟feign超时，openfeign底层默认超时时间是1s, 可以在yaml中配置
     * 支付服务中睡眠3s, yaml中设置超时2s
     * 超时会报： Read timed out executing GET http://CLOUD-PAYMENT-SERVICE/payment/feign/timeout
     * 可以yaml中通过ribbon.ReadTimeout 进行设置
     * @return
     */
    @GetMapping(value = "/payment/timeout")
    public String paymentFeignTimeout() {
        // openfeign-ribbon, 客户端一般默认等待1秒钟
        return paymentFeignService.paymentFeignTimeout();
    }


}

package com.xzq.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.xzq.springcloud.service.PaymentHystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: OrderHystrixController
 * @description:
 * @author: XZQ
 * @create: 2020/3/8 17:06
 **/
@RestController
@RequestMapping("/consumer")
//@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")// 当@HystrixCommand没有指明降级方法时就用类上注解指明的降级方法：@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {

    @Autowired
    private PaymentHystrixService paymentHystrixService;

    /**
     * 正常访问
     * http://localhost/consumer/payment/hystrix/ok/1
     *
     * @param id
     * @return
     */
    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        return paymentHystrixService.paymentInfo_OK(id);
    }

    /**
     * 消费测进行超时或者异常降级
     * 关于降级处理方法可分为通用降级方法和独自降级方法
     * 消费测进行超时控制降级，这里可以在业务方法上进行降级处理，但是这样会把业务方法和降级方法耦合在一起，所以可以把通用降级方法封装到PaymentFallbackService
     * 当然也可以单独设置独自的降级方法，独自的降级方法可以设置详细的降级参数
     * 另外当@HystrixCommand没有指明降级方法时就用类上注解指明的降级方法
     * @param id
     * @return
     */
    @GetMapping("/payment/hystrix/timeout/{id}")
 //独自降级方法
//    @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod", commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "600")
//    })
   // @HystrixCommand 当@HystrixCommand没有指明降级方法时就用类上注解指明的降级方法：@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
//        int age = 10 / 0;
        return paymentHystrixService.paymentInfo_TimeOut(id);
    }

    /**
     * 独自降级方法
     * @param id
     * @return
     */
    private String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id) {
        return "消费者80，对方支付系统繁忙，或自己运行出错请检查自己，请10秒后再试。";
    }

    /**
     * 全局fallback方法
     *
     * @return
     */
    private String payment_Global_FallbackMethod() {
        return "Global异常处理信息,请稍后再试。";
    }
}
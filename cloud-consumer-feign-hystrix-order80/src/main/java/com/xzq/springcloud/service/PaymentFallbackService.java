package com.xzq.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * 为了解决代码膨胀问题和业务逻辑及降级方法的紧耦合问题，把降级方法封装到PaymentFallbackService,
 * 当出现如下状况时候会调用统一的降级方法：
 * 超时：默认超时大于1s
 * 异常
 * 宕机(故意关闭微服务8001)
 *
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "----PaymentFallbackService fall back--paymentInfo_OK";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "----PaymentFallbackService fall back--paymentInfo_TimeOut";
    }
}

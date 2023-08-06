package com.xzq.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: PaymentService
 * @description:
 * @author: XZQ
 * @create: 2020/3/8 15:00
 **/
@Service
public class PaymentService {

    /**
     * 正常访问
     *
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "paymentinfo_ok,id:" + id + "\t";
    }

    /**
     * 服务降级：宕机、超时访问、异常
     * HystrixCommand:一旦调用服务方法失败并抛出了错误信息后,会自动调用@HystrixCommand标注好的fallbckMethod调用类中的指定方法
     * 方法返回超时或者产生异常都会调用相应的fallback兜底方法
     *
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
    })
    public String paymentInfo_TimeOut(Integer id) {
        int timeNumber = 1300;
        //int age = 10 / 0; //模拟系统运行异常
        try {
            Thread.sleep(timeNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + "paymentinfo_Timeout,id:" + id + "\t" + "耗时(秒):" + timeNumber/1000.0;
    }




    private String paymentInfo_TimeOutHandler(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "8001系统繁忙或者运行报错,请稍后再试,id:" + id + "\t";
    }


    /**
     * 服务熔断：
     * 断路器的三个重要参数：快照时间窗、请求总数阈值、错误百分比阈值
     * 1.快照时间窗：断路器确定是否打开需要统计一些请求和错误数据，而统计的时间范围就是快照时间窗，默认为最近的10秒
     * 2.请求总数阈值：在快照时间内，必须满足请求总数阈值才有资格熔断。默认为20，意味着在10秒内，如果该hystrix命令的调用次数不足20次，即使所有的请求都超时或其他原因失败，断路器都不会打开
     * 3.错误百分比阈值：当请求总数在快照时间窗内超过阈值，比如发生了30次调用，如果在这30次调用中，有15次发生了超时异常，也就是超过50%的错误百分比，在默认设定50%阈值情况下，这时候就会将断路器打开
     *
     * 断路器开启或关闭的条件：
     * 1.当满足一定的阈值的时候（默认10秒内超过20个请求次数）
     * 2.当失败率达到一定的时候（默认10秒内超过50%的请求失败）
     * 3.到达以上阈值，断路器将会开启(开启熔断)
     * 4.当开启的时候，在休眠时间窗口内，所有请求都不会进行转发，而是直接调用降级fallback，通过断路器，实现了自动地发现错误并将降级逻辑切换为主逻辑，减少响应延迟的效果。
     * 5.一段时间后（过了休眠时间窗，默认是5秒），这个时候断路器是半开状态，会让其中一个请求进行转发(释放一次请求到原来的主逻辑上),如果此次请求正常返回，那么断路器将闭合，主逻辑恢复; 若失败，断路器继续进入打开状态, 休眠时间窗重新计时, 也就是重复4和5。
     *
     * 在10秒窗口期中10次请求有6次是请求失败的,就会触发熔断，熔断后正确的请求也不能立刻被响应
     * 异常请求：http://localhost:8001/payment/hystrix/circuit/-1
     * 正常请求：http://localhost:8001/payment/hystrix/circuit/1
     *
     * @param id
     * @return
     */
    @HystrixCommand(
            fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),// 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")// 失败率达到多少后跳闸
    }
    )
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("*****id不能是负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功,流水号:" + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id 不能负数,请稍后重试,o(╥﹏╥)o id:" + id;
    }
}

package com.xzq.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ribbon就是一个软负载均衡的客户端组件，它可以和其他所需请求的客户端结合使用，和Eureka结合只是其中的一个实例。
 *
 * Ribbon在工作时分成两步：
 * 第一步先选择EurekaServer ,它优先选择在同一个区域内负载较少的server。
 * 第二步再根据用户指定的策略，在从server取到的服务注册列表中选择一个地址。
 *
 * spring-cloud-starter-netflix-eureka-client 内部集成了 spring-cloud-starter-ribbon
 *
 * 使用方式1：@LoadBalance + RestTemplate, 赋予RestTemplate 轮询负载均衡的能力： rest接口第几次请求数 % 服务器集群总数量 = 实际调用服务器位置下标
 * @Bean
 * @LoadBalanced
 * public RestTemplate getRestTemplate(){
 *     return  new RestTemplate() ;
 * }
 *
 *
 *使用方式2：指定负载均衡的方式: MySelfRule类
 * step1: 自定义负载均衡算法
 * step2：主启动类指定服务极其负载均衡类
 * : @RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MySelfRule.class)
 *
 * Ribbon 核心组件IRule : com.netflix.loadbalancer.IRule
 * 其中 Ribbon 提供了多种策略：比如轮询、随机和根据响应时间加权。
 * RoundRobinRule:	轮询
 * RandomRule:	随机
 * RetryRule:	先按照RoundRobinRule的策略获取服务，如果获取服务失败则在指定时间内会进行重试，获取可用的服务
 * WeightedResponseTimeRule:	对RoundRobinRule 的拓展，响应速度越快的实例选择权重越大，越容易被选择
 * BestAvailableRule:	会先过滤掉由于多次访问故障而处断路器跳闸状态的服务，然后选择一个并发量最小的服务
 * AvailabilityFilteringRule:	先过滤掉故障实例，在选择并发较小的实例
 * ZoneAvoidanceRule:	默认规则，符合判断Server 所在区域的性能和 Server 的可用性选择服务器
 *
 *测试发现：如果两种方式同时存在，方式2会覆盖方式1
 **/
@Configuration//不能让springboot扫描, 主启动类要添加@RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MySelfRule.class)
public class MySelfRule {

    @Bean
    public IRule myRule() {
        return new RandomRule();
    }
}

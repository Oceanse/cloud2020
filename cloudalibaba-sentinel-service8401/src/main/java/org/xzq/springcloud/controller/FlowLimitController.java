package org.xzq.springcloud.controller;

import cn.hutool.log.LogFactory;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class FlowLimitController {
   public static final Logger LOGGER= LogManager.getLogger(FlowLimitController.class);


    /**
     * 这个接口也可以通过网关访问：http://localhost:9527/testA
     * 也可以直接访问： http://localhost:8401/testA
     * 通过sentinel的授权，也就是对该接口进行设置，只对特定的白名单才能访问，然后可以把网关放入白名单，那么
     * 我们就只能通过网关访问该接口，而不能直接访问该接口
     *
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/testA")
    public String testA() throws InterruptedException {
        //double price=10/0;
        LOGGER.info(Thread.currentThread().getName()+"\t"+"...testA");
        return "------testA";
    }


    /**
     * http://localhost:8401/testB
     * @return
     */
    @GetMapping("/testB")
    public String testB() throws InterruptedException {
       // Thread.sleep(1000);
        LOGGER.info(Thread.currentThread().getName()+"\t"+"...testB");
        return "------testB";
    }


    /**
     * 热点限流
     * http://localhost:8401/testC?p1=1
     * @return
     */
    @GetMapping("/testC")
    @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey")
    public String testC(@RequestParam(required = false) String p1,@RequestParam(required = false) String p2) throws InterruptedException {
        LOGGER.info("p1={}, p2={}",p1,p2);
        return p1+" "+p2;
    }

    /**
     * 兜底方法
     * @param p1
     * @param p2
     * @param exception
     * @return
     */
    public String deal_testHotKey (String p1, String p2, BlockException exception) {
        return "------deal_testHotKey,o(╥﹏╥)o";  //sentinel系统默认的提示：Blocked by Sentinel (flow limiting)
    }
}

package com.xzq.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * https://baijiahao.baidu.com/s?id=1710054576499720883&wfr=spider&for=pc
 * IntelliJ IDEA中的lombok插件+开启注解+引入依赖 仍然不生效，故暂时不用lombok
 * @ClassName: Payment
 * @description: 订单实体类
 * @create: 2020/3/5 17:53
 **/
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class Payment implements Serializable {
    private Long id;
    private String serial;

    public Payment() {
    }

    public Payment(Long id, String serial) {
        this.id = id;
        this.serial = serial;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}

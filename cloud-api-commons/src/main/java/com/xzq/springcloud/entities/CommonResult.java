package com.xzq.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://baijiahao.baidu.com/s?id=1710054576499720883&wfr=spider&for=pc
 * IntelliJ IDEA中的lombok插件+开启注解+引入依赖 仍然不生效，故暂时不用lombok
 * @ClassName: CommonResult
 * @description: 通用返回结果
 * @author: XZQ
 * @create: 2020/3/5 17:56
 **/
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data;

    public CommonResult() {
    }

    public CommonResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

        public CommonResult(Integer code, String message) {
        this(code, message, null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

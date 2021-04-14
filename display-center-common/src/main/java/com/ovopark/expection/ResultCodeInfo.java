package com.ovopark.expection;

import java.io.Serializable;

/**
 * @Classname Validation
 * @Description 自定义异常模板类
 * @Date 2020/7/9 8:10 下午
 * @Created by liuhao
 */
public class ResultCodeInfo implements Serializable {

    private Integer code;

    private String message;

    private String desc;

    public ResultCodeInfo(){}

    public ResultCodeInfo(Integer code, String message, String desc){
        this.code = code;
        this.message = message;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}


package com.ovopark.model.req;

import java.io.Serializable;

/**
 * @Classname InspectionTagDeleteReq
 * @Description TODO
 * @Date 2021/5/11 下午3:15
 * @Created by liuhao
 */
public class InspectionTagDeleteReq implements Serializable {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

package com.ovopark.model.req;

import java.io.Serializable;

/**
 * @Classname InspectionPlanTaskDeleteReq
 * @Description TODO
 * @Date 2021/4/22 上午10:47
 * @Created by liuhao
 */
public class InspectionPlanTaskDeleteReq implements Serializable {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

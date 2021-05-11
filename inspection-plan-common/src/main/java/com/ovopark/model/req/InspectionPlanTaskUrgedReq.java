package com.ovopark.model.req;

import java.io.Serializable;

/**
 * @Classname InspectionPlanTaskUrgedReq
 * @Description TODO
 * @Date 2021/4/22 上午10:47
 * @Created by liuhao
 */
public class InspectionPlanTaskUrgedReq implements Serializable {

    private Integer id;

    private Integer tokenType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTokenType() {
        return tokenType;
    }

    public void setTokenType(Integer tokenType) {
        this.tokenType = tokenType;
    }
}

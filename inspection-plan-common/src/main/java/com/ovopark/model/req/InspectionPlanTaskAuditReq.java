package com.ovopark.model.req;

import java.io.Serializable;

/**
 * @Classname InspectionPlanTaskAuditReq
 * @Description TODO
 * @Date 2021/4/22 上午10:47
 * @Created by liuhao
 */
public class InspectionPlanTaskAuditReq implements Serializable {

    private Integer id;

    private String  reason="";

    private Integer status;

    private Integer tokenType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
            this.reason = reason;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getTokenType() {
        return tokenType;
    }

    public void setTokenType(Integer tokenType) {
        this.tokenType = tokenType;
    }
}

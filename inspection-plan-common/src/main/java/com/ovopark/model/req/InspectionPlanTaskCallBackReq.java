package com.ovopark.model.req;

import java.io.Serializable;

/**
 * @Classname InspectionPlanTaskAuditReq
 * @Description TODO
 * @Date 2021/4/22 上午10:47
 * @Created by liuhao
 */
public class InspectionPlanTaskCallBackReq implements Serializable {

    private Integer taskId;

    private Integer expandId;

    private String deptName;

    private Integer tokenType;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getExpandId() {
        return expandId;
    }

    public void setExpandId(Integer expandId) {
        this.expandId = expandId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getTokenType() {
        return tokenType;
    }

    public void setTokenType(Integer tokenType) {
        this.tokenType = tokenType;
    }
}

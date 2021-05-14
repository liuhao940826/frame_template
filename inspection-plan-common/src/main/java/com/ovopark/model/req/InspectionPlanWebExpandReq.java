package com.ovopark.model.req;

import java.io.Serializable;

/**
 * @Classname InspectionPlanWebExpandReq
 * @Description TODO
 * @Date 2021/5/14 20:24
 * @Created by liuhao
 */
public class InspectionPlanWebExpandReq implements Serializable {

    private Integer taskId;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
}

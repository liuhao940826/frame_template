package com.ovopark.model.req;

import java.io.Serializable;

/**
 * @Classname InspectionPlanTaskAppLogListReq
 * @Description TODO
 * @Date 2021/5/11 下午8:01
 * @Created by liuhao
 */
public class InspectionPlanTaskAppLogListReq implements Serializable {


    private Integer taskId;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
}

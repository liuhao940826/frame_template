package com.ovopark.model.req;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname DisplayCenterTaskAddReq
 * @Description TODO
 * @Date 2021/4/22 上午10:47
 * @Created by liuhao
 */
public class DisplayCenterTaskAuditExpandOuterReq implements Serializable {

    private List<DisplayCenterTaskAuditExpandReq> list = new ArrayList<>();

    private Integer taskId;

    public List<DisplayCenterTaskAuditExpandReq> getList() {
        return list;
    }

    public void setList(List<DisplayCenterTaskAuditExpandReq> list) {
        this.list = list;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

}

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
public class DisplayCenterTaskAddExpandOuterReq implements Serializable {

    private List<DisplayCenterTaskAddExpandReq> list = new ArrayList<>();

    private Integer taskId;

    public List<DisplayCenterTaskAddExpandReq> getList() {
        return list;
    }

    public void setList(List<DisplayCenterTaskAddExpandReq> list) {
        this.list = list;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

}

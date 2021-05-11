package com.ovopark.model.req;

import java.io.Serializable;

/**
 * @Classname InspectionTagListReq
 * @Description TODO
 * @Date 2021/5/11 下午3:15
 * @Created by liuhao
 */
public class InspectionTagListReq implements Serializable {

    private Integer groupId;


    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}

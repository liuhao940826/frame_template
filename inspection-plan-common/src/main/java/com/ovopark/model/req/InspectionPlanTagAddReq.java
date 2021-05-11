package com.ovopark.model.req;

import java.io.Serializable;

/**
 * @Classname InspectionPlanTagAddReq
 * @Description TODO
 * @Date 2021/5/8 下午3:27
 * @Created by liuhao
 */
public class InspectionPlanTagAddReq implements Serializable {


    private Integer tagId;

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
}

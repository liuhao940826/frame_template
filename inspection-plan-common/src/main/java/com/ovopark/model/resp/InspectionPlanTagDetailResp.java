package com.ovopark.model.resp;

import java.io.Serializable;

/**
 * @Classname InspectionPlanTagDetailResp
 * @Description TODO
 * @Date 2021/5/11 下午8:03
 * @Created by liuhao
 */
public class InspectionPlanTagDetailResp implements Serializable {

    private Integer tagId;

    private String  tagName;

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public InspectionPlanTagDetailResp() {
    }

    public InspectionPlanTagDetailResp(Integer tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }
}

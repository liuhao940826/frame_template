package com.ovopark.model.req;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname InspectionPlanExpandAddReq
 * @Description TODO
 * @Date 2021/5/8 下午3:25
 * @Created by liuhao
 */
public class InspectionPlanExpandAddReq implements Serializable {

    private Integer deptId;

    private List<InspectionPlanTagAddReq> tagList;

    private String description;

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public List<InspectionPlanTagAddReq> getTagList() {
        return tagList;
    }

    public void setTagList(List<InspectionPlanTagAddReq> tagList) {
        this.tagList = tagList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

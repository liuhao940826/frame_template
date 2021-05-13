package com.ovopark.model.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Classname InspectionPlanTaskDetailResp
 * @Description TODO
 * @Date 2021/5/11 下午8:01
 * @Created by liuhao
 */
public class InspectionPlanTaskAppListResp implements Serializable {

    private Integer id;

    private String name;

    private Date startTime;

    private Date endTime;

    private String remark;

    private Integer status;

    private Integer completeExpandCount=0;

    private Integer totalExpandCount=0;

    private List<InspectionPlanTagDetailResp> tagList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public List<InspectionPlanTagDetailResp> getTagList() {
        return tagList;
    }

    public void setTagList(List<InspectionPlanTagDetailResp> tagList) {
        this.tagList = tagList;
    }

    public Integer getCompleteExpandCount() {
        return completeExpandCount;
    }

    public void setCompleteExpandCount(Integer completeExpandCount) {
        this.completeExpandCount = completeExpandCount;
    }

    public Integer getTotalExpandCount() {
        return totalExpandCount;
    }

    public void setTotalExpandCount(Integer totalExpandCount) {
        this.totalExpandCount = totalExpandCount;
    }
}

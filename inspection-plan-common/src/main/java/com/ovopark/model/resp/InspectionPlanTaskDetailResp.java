package com.ovopark.model.resp;

import com.ovopark.model.req.InspectionPlanExpandAddReq;

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
public class InspectionPlanTaskDetailResp implements Serializable {

    private Integer id;

    private String name;

    private Integer auditId;

    private String auditName = "";

    private Date startTime;

    private Date endTime;

    private String remark;

    private Integer status;

    private List<InspectionPlanExpandDetailResp> inspectionExpandList =  new ArrayList<>();

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

    public Integer getAuditId() {
        return auditId;
    }

    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
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

    public List<InspectionPlanExpandDetailResp> getInspectionExpandList() {
        return inspectionExpandList;
    }

    public void setInspectionExpandList(List<InspectionPlanExpandDetailResp> inspectionExpandList) {
        this.inspectionExpandList = inspectionExpandList;
    }
}
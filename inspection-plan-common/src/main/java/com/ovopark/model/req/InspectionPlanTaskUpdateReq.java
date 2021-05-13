package com.ovopark.model.req;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Classname InspectionPlanTaskUpdateReq
 * @Description TODO
 * @Date 2021/4/22 上午10:47
 * @Created by liuhao
 */
public class InspectionPlanTaskUpdateReq implements Serializable {

    private Integer id;

    private String name;

    private Integer auditId;

    private String auditName = "";

    private Date startTime;

    private Date endTime;

    private String remark;

    private List<InspectionPlanExpandUpdateReq> inspectionExpandList =  new ArrayList<>();

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
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

    public List<InspectionPlanExpandUpdateReq> getInspectionExpandList() {
        return inspectionExpandList;
    }

    public void setInspectionExpandList(List<InspectionPlanExpandUpdateReq> inspectionExpandList) {
        this.inspectionExpandList = inspectionExpandList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

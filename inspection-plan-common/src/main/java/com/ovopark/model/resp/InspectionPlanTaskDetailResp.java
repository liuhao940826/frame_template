package com.ovopark.model.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ovopark.model.req.InspectionPlanExpandAddReq;
import org.springframework.format.annotation.DateTimeFormat;

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

    private Integer operatorId;

    private String operatorName;

    private Integer auditId;

    private String auditName = "";
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private Integer updateTimes;

    private String remark;

    private Integer status;

    private Integer isOperator=0;

    private Integer isAudit=0;

    private Integer isUrged=0;

    private List<InspectionPlanExpandDetailResp> inspectionExpandList =  new ArrayList<>();

    private String inspectionResult;

    private List<InspectionPlanTaskAuditReasonResp> reason = new ArrayList<>();

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

    public Integer getIsOperator() {
        return isOperator;
    }

    public void setIsOperator(Integer isOperator) {
        this.isOperator = isOperator;
    }


    public Integer getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(Integer isAudit) {
        this.isAudit = isAudit;
    }

    public Integer getUpdateTimes() {
        return updateTimes;
    }

    public void setUpdateTimes(Integer updateTimes) {
        this.updateTimes = updateTimes;
    }

    public Integer getIsUrged() {
        return isUrged;
    }

    public void setIsUrged(Integer isUrged) {
        this.isUrged = isUrged;
    }

    public String getInspectionResult() {
        return inspectionResult;
    }

    public void setInspectionResult(String inspectionResult) {
        this.inspectionResult = inspectionResult;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }


    public List<InspectionPlanTaskAuditReasonResp> getReason() {
        return reason;
    }

    public void setReason(List<InspectionPlanTaskAuditReasonResp> reason) {
        this.reason = reason;
    }
}

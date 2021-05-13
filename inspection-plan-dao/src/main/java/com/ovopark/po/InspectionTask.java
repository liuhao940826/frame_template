package com.ovopark.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Classname InspectionTask
 * @Description TODO
 * @Date 2021/4/22 上午9:32
 * @Created by liuhao
 */
public class InspectionTask implements Serializable {

    private Integer id;

    private String name;

    private Integer groupId;

    private Integer operatorId;

    private String operatorName;

    private Integer auditId;

    private String auditName;

    private Date auditTime;

    private Integer jobId;

    private Integer status;

    private Integer completeExpandCount;

    private Integer totalExpandCount;

    private BigDecimal completePercent;

    private Date startTime;

    private Date endTime;

    private Integer updateTimes;

    private String  remark;

    private Integer createId;

    private Date createTime;

    private Integer updateId;

    private Date updateTime;

    private Integer isDelete;

    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public InspectionTask setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
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

    public Integer getAuditId() {
        return auditId;
    }

    public InspectionTask setAuditId(Integer auditId) {
        this.auditId = auditId;
        return this;
    }

    public String getAuditName() {
        return auditName;
    }

    public InspectionTask setAuditName(String auditName) {
        this.auditName = auditName;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public InspectionTask setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getCompleteExpandCount() {
        return completeExpandCount;
    }

    public InspectionTask setCompleteExpandCount(Integer completeExpandCount) {
        this.completeExpandCount = completeExpandCount;
        return this;
    }

    public Integer getTotalExpandCount() {
        return totalExpandCount;
    }

    public InspectionTask setTotalExpandCount(Integer totalExpandCount) {
        this.totalExpandCount = totalExpandCount;
        return this;
    }

    public BigDecimal getCompletePercent() {
        return completePercent;
    }

    public InspectionTask setCompletePercent(BigDecimal completePercent) {
        this.completePercent = completePercent;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public InspectionTask setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Date getEndTime() {
        return endTime;
    }

    public InspectionTask setEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public Integer getUpdateTimes() {
        return updateTimes;
    }

    public InspectionTask setUpdateTimes(Integer updateTimes) {
        this.updateTimes = updateTimes;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public InspectionTask setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }




}

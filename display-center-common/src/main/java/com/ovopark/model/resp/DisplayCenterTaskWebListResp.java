package com.ovopark.model.resp;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname DisplayCenterTaskAddReq
 * @Description TODO
 * @Date 2021/4/22 上午10:47
 * @Created by liuhao
 */
public class DisplayCenterTaskWebListResp implements Serializable {

    private String name;

    private Integer deptId;

    private String deptName;

    private Integer status;

    private Integer ItemNum;

    private  String scorePercent;

    private Date createTime;

    private Integer isAudit=0;

    private Integer operatorId;

    private Integer auditId;

    private String auditName;

    private String operatorName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(Integer isAudit) {
        this.isAudit = isAudit;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getAuditId() {
        return auditId;
    }

    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    public Integer getItemNum() {
        return ItemNum;
    }

    public void setItemNum(Integer itemNum) {
        ItemNum = itemNum;
    }

    public String getScorePercent() {
        return scorePercent;
    }

    public void setScorePercent(String scorePercent) {
        this.scorePercent = scorePercent;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}

package com.ovopark.model.req;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname InspectionPlanTaskWebListReq
 * @Description TODO
 * @Date 2021/5/11 下午8:01
 * @Created by liuhao
 */
public class InspectionPlanTaskWebListReq implements Serializable {

    private Integer pageNo;

    private Integer pageSize;

    private String name;

    private String operatorName;

    private Integer status;

    private String auditName;

    private String completePercentExpression;

    private BigDecimal completePercent;

    private String startTime;

    private String endTime;

    private List<Integer> tagIdList = new ArrayList<>();

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    public String getCompletePercentExpression() {
        return completePercentExpression;
    }

    public void setCompletePercentExpression(String completePercentExpression) {
        this.completePercentExpression = completePercentExpression;
    }

    public BigDecimal getCompletePercent() {
        return completePercent;
    }


    public void setCompletePercent(BigDecimal completePercent) {
        this.completePercent = completePercent;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<Integer> getTagIdList() {
        return tagIdList;
    }

    public void setTagIdList(List<Integer> tagIdList) {
        this.tagIdList = tagIdList;
    }
}
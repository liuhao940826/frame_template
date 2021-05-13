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

    /**
     * 保留的明细id 不能懂的放在保留的里面 其他的全部放在改变得了里面
     */
    private List<Integer> retainList = new ArrayList<>();

    private List<InspectionPlanExpandAddReq> inspectionExpandList =  new ArrayList<>();

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

    public List<InspectionPlanExpandAddReq> getInspectionExpandList() {
        return inspectionExpandList;
    }

    public void setInspectionExpandList(List<InspectionPlanExpandAddReq> inspectionExpandList) {
        this.inspectionExpandList = inspectionExpandList;
    }

    public List<Integer> getRetainList() {
        return retainList;
    }

    public void setRetainList(List<Integer> retainList) {
        this.retainList = retainList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

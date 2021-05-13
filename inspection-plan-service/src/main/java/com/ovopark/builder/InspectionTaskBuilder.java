package com.ovopark.builder;

import com.ovopark.po.InspectionTask;

import java.util.Date;

/**
 * @Classname InspectionTaskBuilder
 * @Description TODO
 * @Date 2021/5/8 下午4:15
 * @Created by liuhao
 */
public class InspectionTaskBuilder {

    private InspectionTask task = new InspectionTask();

    public InspectionTaskBuilder id(Integer  id) {
        task.setId(id);
        return this;
    }

    public InspectionTaskBuilder name(String  name) {
        task.setName(name);
        return this;
    }

    public InspectionTaskBuilder groupId(Integer  groupId) {
        task.setGroupId(groupId);
        return this;
    }

    public InspectionTaskBuilder operatorId(Integer operatorId) {
        task.setOperatorId(operatorId);
        return this;
    }
    public InspectionTaskBuilder operatorName(String  operatorName) {
        task.setOperatorName(operatorName);
        return this;
    }
    public InspectionTaskBuilder auditId(Integer  auditId) {
        task.setAuditId(auditId);
        return this;
    }
    public InspectionTaskBuilder auditName(String  auditName) {
        task.setAuditName(auditName);
        return this;
    }
    public InspectionTaskBuilder status(Integer  status) {
        task.setStatus(status);
        return this;
    }
    public InspectionTaskBuilder startTime(Date startTime) {
        task.setStartTime(startTime);
        return this;
    }
    public InspectionTaskBuilder endTime(Date  endTime) {
        task.setEndTime(endTime);
        return this;
    }
    public InspectionTaskBuilder remark(String  remark) {
        task.setRemark(remark);
        return this;
    }

    // 构建对象
    public InspectionTask build() {
        return task;
    }

}

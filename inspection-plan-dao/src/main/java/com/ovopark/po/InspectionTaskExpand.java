package com.ovopark.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Classname InspectionTaskExpand
 * @Description TODO
 * @Date 2021/4/22 上午9:40
 * @Created by liuhao
 */
public class InspectionTaskExpand implements Serializable {

    private Integer id;

    private Integer groupId;

    private Integer taskId;

    private Integer deptId;

    private String  description;

    private Integer operatorId;

    private Integer status;

    private Integer createId;

    private Date createTime;

    private Integer updateId;

    private Date updateTime;

    private Integer isDelete;

    public Integer getId() {
        return id;
    }

    public InspectionTaskExpand setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public InspectionTaskExpand setGroupId(Integer groupId) {
        this.groupId = groupId;  return this;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public InspectionTaskExpand setTaskId(Integer taskId) {
        this.taskId = taskId;  return this;
    }


    public Integer getStatus() {
        return status;
    }

    public InspectionTaskExpand setStatus(Integer status) {
        this.status = status;  return this;
    }

    public Integer getCreateId() {
        return createId;
    }

    public InspectionTaskExpand setCreateId(Integer createId) {
        this.createId = createId;  return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public InspectionTaskExpand setCreateTime(Date createTime) {
        this.createTime = createTime;  return this;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public InspectionTaskExpand setUpdateId(Integer updateId) {
        this.updateId = updateId;  return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public InspectionTaskExpand setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;  return this;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public InspectionTaskExpand setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;  return this;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }
}

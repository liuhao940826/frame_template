package com.ovopark.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Classname DisplayCenterExpand
 * @Description TODO
 * @Date 2021/4/22 上午9:40
 * @Created by liuhao
 */
public class DisplayCenterExpand implements Serializable {

    private Integer id;

    private Integer groupId;

    private Integer taskId;

    private String previousUrl;

    private String afterUrl;

    private String title;

    private BigDecimal actualScore;

    private BigDecimal totalScore;

    private Integer status;

    private Integer createId;

    private Date createTime;

    private Integer updateId;

    private Date updateTime;

    private Integer isDelete;

    public Integer getId() {
        return id;
    }

    public DisplayCenterExpand setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public DisplayCenterExpand setGroupId(Integer groupId) {
        this.groupId = groupId;  return this;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public DisplayCenterExpand setTaskId(Integer taskId) {
        this.taskId = taskId;  return this;
    }

    public String getPreviousUrl() {
        return previousUrl;
    }

    public DisplayCenterExpand setPreviousUrl(String previousUrl) {
        this.previousUrl = previousUrl;  return this;
    }

    public String getAfterUrl() {
        return afterUrl;
    }

    public DisplayCenterExpand setAfterUrl(String afterUrl) {
        this.afterUrl = afterUrl;  return this;
    }

    public String getTitle() {
        return title;
    }

    public DisplayCenterExpand setTitle(String title) {
        this.title = title;  return this;
    }

    public BigDecimal getActualScore() {
        return actualScore;
    }

    public DisplayCenterExpand setActualScore(BigDecimal actualScore) {
        this.actualScore = actualScore;  return this;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public DisplayCenterExpand setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;  return this;
    }

    public Integer getStatus() {
        return status;
    }

    public DisplayCenterExpand setStatus(Integer status) {
        this.status = status;  return this;
    }

    public Integer getCreateId() {
        return createId;
    }

    public DisplayCenterExpand setCreateId(Integer createId) {
        this.createId = createId;  return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public DisplayCenterExpand setCreateTime(Date createTime) {
        this.createTime = createTime;  return this;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public DisplayCenterExpand setUpdateId(Integer updateId) {
        this.updateId = updateId;  return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public DisplayCenterExpand setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;  return this;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public DisplayCenterExpand setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;  return this;
    }
}

package com.ovopark.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname InspectionOperatorLog
 * @Description TODO
 * @Date 2021/5/15 16:14
 * @Created by liuhao
 */
public class InspectionOperatorLog implements Serializable {

    private Integer id;

    private Integer groupId;

    private Integer taskId;

    private Integer userId;

    private String  userName;

    private String content;

    private Integer createId;

    private Date createTime;

    private Integer updateId;

    private Date updateTime;

    private Integer isDelete;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public InspectionOperatorLog(Integer groupId, Integer taskId, Integer userId, String userName,String content) {
        this.groupId = groupId;
        this.taskId = taskId;
        this.userId = userId;
        this.userName =userName;
        this.content = content;
    }

    public InspectionOperatorLog() {
    }
}

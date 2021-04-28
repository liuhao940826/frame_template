package com.ovopark.po;


import java.io.Serializable;
import java.util.Date;

public class TaskMessage extends Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4209242003980578535L;
	
	private String description;

	private String taskName;

	private Date startTime;//yyyy-MM-dd HH:mm:ss （开始时间的日期不能小于今天，时分秒可以比现在小）

	private Date endTime;//yyyy-MM-dd HH:mm:ss (结束时间，不能小于当前时间)

    private Integer isDelete;

    private Integer isPeriod;//是否是循环任务

	private Integer updateTaskId;//关联的任务id

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
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

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

	public Integer getIsPeriod() {
		return isPeriod;
	}

	public void setIsPeriod(Integer isPeriod) {
		this.isPeriod = isPeriod;
	}

	public Integer getUpdateTaskId() {
		return updateTaskId;
	}

	public void setUpdateTaskId(Integer updateTaskId) {
		this.updateTaskId = updateTaskId;
	}
}

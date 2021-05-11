package com.ovopark.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname InspectionCategory
 * @Description TODO
 * @Date 2021/5/10 下午2:59
 * @Created by liuhao
 */
public class InspectionCategory implements Serializable {

    private Integer id;

    private Integer groupId;

    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public InspectionCategory(String name,Integer groupId) {
        this.name = name;
        this.groupId = groupId;
    }

    public InspectionCategory(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public InspectionCategory() {
    }
}

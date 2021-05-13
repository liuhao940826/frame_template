package com.ovopark.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname InspectionTag
 * @Description TODO
 * @Date 2021/5/10 下午2:59
 * @Created by liuhao
 */
public class InspectionTag implements Serializable {

    private Integer id;

    private Integer groupId;

    private Integer categoryId;

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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


    public InspectionTag() {
    }

    public InspectionTag(Integer groupId, Integer categoryId, String name) {
        this.groupId = groupId;
        this.categoryId = categoryId;
        this.name = name;
    }

    public InspectionTag(Integer id, Integer groupId, Integer categoryId, String name) {
        this.id = id;
        this.groupId = groupId;
        this.categoryId = categoryId;
        this.name = name;
    }

    public InspectionTag(Integer id) {
        this.id = id;
    }
}

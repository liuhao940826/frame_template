package com.ovopark.model.resp;

/**
 * @Classname InspectionTagListResp
 * @Description TODO
 * @Date 2021/5/11 下午4:15
 * @Created by liuhao
 */
public class InspectionTagListResp {

    private Integer id;

    private Integer groupId;

    private String  name;

    private Integer categoryId;

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}

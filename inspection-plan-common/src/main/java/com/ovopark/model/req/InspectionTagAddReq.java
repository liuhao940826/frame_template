package com.ovopark.model.req;

import java.io.Serializable;

/**
 * @Classname InspectionCategoryAddReq
 * @Description TODO
 * @Date 2021/5/11 下午3:15
 * @Created by liuhao
 */
public class InspectionTagAddReq implements Serializable {

    private String name;

    private Integer categoryId;

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

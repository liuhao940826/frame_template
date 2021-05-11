package com.ovopark.model.req;

import java.io.Serializable;

/**
 * @Classname InspectionCategoryUpdateReq
 * @Description TODO
 * @Date 2021/5/11 下午3:15
 * @Created by liuhao
 */
public class InspectionCategoryUpdateReq implements Serializable {

    private Integer id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

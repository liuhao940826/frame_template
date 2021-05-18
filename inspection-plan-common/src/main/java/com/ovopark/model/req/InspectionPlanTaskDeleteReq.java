package com.ovopark.model.req;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname InspectionPlanTaskDeleteReq
 * @Description TODO
 * @Date 2021/4/22 上午10:47
 * @Created by liuhao
 */
public class InspectionPlanTaskDeleteReq implements Serializable {

    private Integer id;

    private List<Integer> idList;

    private Integer tokenType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

    public Integer getTokenType() {
        return tokenType;
    }

    public void setTokenType(Integer tokenType) {
        this.tokenType = tokenType;
    }
}

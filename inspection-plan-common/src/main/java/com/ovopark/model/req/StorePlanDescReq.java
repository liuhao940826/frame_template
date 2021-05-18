package com.ovopark.model.req;

import java.io.Serializable;

/**
 * @Classname StorePlanDescReq
 * @Description TODO
 * @Date 2020/10/30 16:22
 * @Created by liuhao
 */
public class StorePlanDescReq implements Serializable {

    private Integer descColumn;

    private Integer descOrder;


    public Integer getDescColumn() {
        return descColumn;
    }

    public void setDescColumn(Integer descColumn) {
        this.descColumn = descColumn;
    }

    public Integer getDescOrder() {
        return descOrder;
    }

    public void setDescOrder(Integer descOrder) {
        this.descOrder = descOrder;
    }
}

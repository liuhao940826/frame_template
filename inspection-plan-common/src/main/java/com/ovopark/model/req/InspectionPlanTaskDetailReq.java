package com.ovopark.model.req;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Classname InspectionPlanTaskDetailReq
 * @Description TODO
 * @Date 2021/4/22 上午10:47
 * @Created by liuhao
 */
public class InspectionPlanTaskDetailReq implements Serializable {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

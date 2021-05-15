package com.ovopark.model.req;

import java.io.Serializable;

/**
 * @Classname InspectionPlanWebExpandReq
 * @Description TODO
 * @Date 2021/5/14 20:24
 * @Created by liuhao
 */
public class InspectionPlanAppExpandReq implements Serializable {

    private Integer pageNo=1;

    private Integer pageSize=20;

    private Integer status=0;

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

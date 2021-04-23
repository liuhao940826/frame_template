package com.ovopark.model.req;

import java.io.Serializable;

/**
 * @Classname DisplayCenterTaskAddReq
 * @Description TODO
 * @Date 2021/4/22 上午10:47
 * @Created by liuhao
 */
public class DisplayCenterTaskAppListReq implements Serializable {

    private Integer pageNo=1;

    private Integer pageSize=20;

    private Integer releateType;

    public Integer getReleateType() {
        return releateType;
    }

    public void setReleateType(Integer releateType) {
        this.releateType = releateType;
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
}

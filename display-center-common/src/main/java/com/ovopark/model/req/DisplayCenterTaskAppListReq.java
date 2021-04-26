package com.ovopark.model.req;

import java.io.Serializable;
import java.util.Date;

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
    //门店名字
    private String  deptName;
    //名字
    private String  name;
    //审核时间
    private Date auditTime;

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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }
}

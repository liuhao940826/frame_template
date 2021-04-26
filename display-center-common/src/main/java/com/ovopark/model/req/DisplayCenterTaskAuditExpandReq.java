package com.ovopark.model.req;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Classname DisplayCenterTaskAuditExpandReq
 * @Description TODO
 * @Date 2021/4/25 下午3:03
 * @Created by liuhao
 */
public class DisplayCenterTaskAuditExpandReq implements Serializable {

    private Integer id;

    private BigDecimal actualScore;

    private Integer status;

    private Date auditTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getActualScore() {
        return actualScore;
    }

    public void setActualScore(BigDecimal actualScore) {
        this.actualScore = actualScore;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }
}

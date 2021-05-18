package com.ovopark.model.resp;

import java.io.Serializable;

/**
 * @Classname InspectionPlanTaskExpandListResp
 * @Description TODO
 * @Date 2021/5/11 下午8:01
 * @Created by liuhao
 */
public class InspectionPlanTaskExpandListCountResp implements Serializable {

    private Integer waitCount = 0;

    private Integer alreadyCount =0;

    public Integer getWaitCount() {
        return waitCount;
    }

    public void setWaitCount(Integer waitCount) {

        if(waitCount!=null){
            this.waitCount = waitCount;
        }

    }

    public Integer getAlreadyCount() {
        return alreadyCount;
    }

    public void setAlreadyCount(Integer alreadyCount) {
        if(alreadyCount!=null) {
            this.alreadyCount = alreadyCount;
        }
    }

    public InspectionPlanTaskExpandListCountResp() {
    }

    public InspectionPlanTaskExpandListCountResp(Integer waitCount, Integer alreadyCount) {
        this.waitCount = waitCount;
        this.alreadyCount = alreadyCount;
    }
}
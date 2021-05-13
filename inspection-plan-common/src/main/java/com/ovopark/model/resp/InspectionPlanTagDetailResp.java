package com.ovopark.model.resp;

import java.io.Serializable;

/**
 * @Classname InspectionPlanTagDetailResp
 * @Description TODO
 * @Date 2021/5/11 下午8:03
 * @Created by liuhao
 */
public class InspectionPlanTagDetailResp implements Serializable {

    private Integer tagId;

    private String  tagName;

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public InspectionPlanTagDetailResp() {
    }

    public InspectionPlanTagDetailResp(Integer tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

    @Override
    public int hashCode() {
        return this.tagId.hashCode()*this.tagName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if(obj==null){
            return false;
        }

        if(this==obj){
            return  true;
        }

        /** 判断对象类型是否是User **/
        if (obj instanceof InspectionPlanTagDetailResp) {
            InspectionPlanTagDetailResp resp = (InspectionPlanTagDetailResp) obj;
            /** 比较每个属性的值一致时才返回true **/
            /** 有几个对象就要比较几个属性 **/
            if (resp.tagId.equals(this.tagId)) {
                return true;
            }
        }

        return false;
    }


}

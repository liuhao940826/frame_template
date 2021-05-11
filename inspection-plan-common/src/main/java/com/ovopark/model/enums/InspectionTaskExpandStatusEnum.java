package com.ovopark.model.enums;



public enum InspectionTaskExpandStatusEnum {

    WAIT(0,"未执行"),
    PASS(1,"通过");

    private Integer code;
    private String desc;

    InspectionTaskExpandStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


}

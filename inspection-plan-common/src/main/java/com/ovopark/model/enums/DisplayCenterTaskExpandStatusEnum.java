package com.ovopark.model.enums;



public enum DisplayCenterTaskExpandStatusEnum {

    WAIT(0,"未执行"),
    PASS(1,"通过"),
    REFUSE(2,"驳回"),
    AUDIT(3,"审核中");

    private Integer code;
    private String desc;

    DisplayCenterTaskExpandStatusEnum(Integer code, String desc) {
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

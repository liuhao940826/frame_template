package com.ovopark.model.enums;


public enum StorePlanOrderWebColumnEnum {

    WEB_DEFAULT(0,"web端默认排序","create_time,id desc"),
    START_TIME(1,"开始时间","start_time"),
    END_TIME(2,"截止时间","end_time"),
    COMPLETE_PERCENT(3,"PERCENT","complete_percent"),
    ID(4,"ID","id");



    private Integer code;
    private String desc;
    private String expression;

    StorePlanOrderWebColumnEnum(Integer code, String desc, String expression) {
        this.code = code;
        this.desc = desc;
        this.expression =expression;
    }


    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getExpression() {
        return expression;
    }

    public static StorePlanOrderWebColumnEnum formatOrNull(Integer code) {
        if (null == code) {
            return null;
        }
        StorePlanOrderWebColumnEnum[] enums = values();
        for (StorePlanOrderWebColumnEnum enu : enums) {
            if (enu.getCode().equals(code)) {
                return enu;
            }
        }

        return null;
    }


    public static StorePlanOrderWebColumnEnum format(Integer code) {
        StorePlanOrderWebColumnEnum se = formatOrNull(code);
        return null == se ? WEB_DEFAULT : se;
    }

}

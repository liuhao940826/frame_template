package com.ovopark.model.enums;



public enum DisplayCenterTaskStatusEnum {

    DEFAULT(0,"默认"),
    WAIT(1,"待执行"),
    AUDIT(2,"待审核"),
    PASS(3,"通过"),
    AUTO_PASS(4,"自动通过");

    private Integer code;
    private String desc;

    DisplayCenterTaskStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static String formatDesc(Integer code) {
        DisplayCenterTaskStatusEnum se = formatOrNull(code);
        return null == se ? "" : se.getDesc();
    }

    public static DisplayCenterTaskStatusEnum formatOrNull(Integer code) {
        if (null == code) {
            return null;
        }
        DisplayCenterTaskStatusEnum[] enums = values();
        for (DisplayCenterTaskStatusEnum enu : enums) {
            if (enu.getCode().equals(code)) {
                return enu;
            }
        }

        return null;
    }


    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


}

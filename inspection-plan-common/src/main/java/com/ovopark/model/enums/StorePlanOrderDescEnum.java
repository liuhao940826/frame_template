package com.ovopark.model.enums;


public enum StorePlanOrderDescEnum {

    ASC(1," ASC"),
    DESC(2," DESC"),
  ;

    private Integer code;
    private String desc;

    StorePlanOrderDescEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static StorePlanOrderDescEnum formatOrNull(Integer code) {
        if (null == code) {
            return null;
        }
        StorePlanOrderDescEnum[] enums = values();
        for (StorePlanOrderDescEnum enu : enums) {
            if (enu.getCode().equals(code)) {
                return enu;
            }
        }

        return null;
    }


    public static StorePlanOrderDescEnum format(Integer code) {
        StorePlanOrderDescEnum se = formatOrNull(code);
        return null == se ? ASC : se;
    }

}

package com.ovopark.model.enums;


public enum DisplayCenterRelateTypeEnum {

    EXECUTE(1,"我执行的"),
    AUDIT(2,"我审核的");



    private Integer code;
    private String desc;

    DisplayCenterRelateTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static DisplayCenterRelateTypeEnum formatOrNull(Integer code) {
        if (null == code) {
            return null;
        }
        DisplayCenterRelateTypeEnum[] enums = values();
        for (DisplayCenterRelateTypeEnum enu : enums) {
            if (enu.getCode().equals(code)) {
                return enu;
            }
        }

        return null;
    }

}

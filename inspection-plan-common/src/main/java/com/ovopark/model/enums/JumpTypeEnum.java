package com.ovopark.model.enums;


public enum JumpTypeEnum {

    APP(0,"任务结果页面"),
    AUDIT(1,"审核页面"),
    LIST(2,"列表页"),
    DETAIL(3,"详情页"),
    ;

    private Integer code;
    private String desc;

    JumpTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static JumpTypeEnum formatOrNull(Integer code) {
        if (null == code) {
            return null;
        }
        JumpTypeEnum[] enums = values();
        for (JumpTypeEnum enu : enums) {
            if (enu.getCode().equals(code)) {
                return enu;
            }
        }

        return null;
    }


    public static JumpTypeEnum format(Integer code) {
        JumpTypeEnum se = formatOrNull(code);
        return null == se ? APP : se;
    }

}

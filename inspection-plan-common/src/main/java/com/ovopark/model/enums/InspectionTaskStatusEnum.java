package com.ovopark.model.enums;

public enum InspectionTaskStatusEnum {
    // 1:待审核 2:驳回 3审核通过 4 巡店中 5 已完成 6 已过期
    DEFAULT(0,"默认"),
    AUDIT(1,"待审核"),
    REFUSE(2,"被驳回"),
    PASS(3,"审核通过"),
    INSPECT(4,"巡店中"),
    FINISH(5,"已完成"),
    EXPIRE(5,"已过期")
    ;

    private Integer code;
    private String desc;

    InspectionTaskStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static InspectionTaskStatusEnum formatOrNull(Integer code) {
        if (null == code) {
            return null;
        }
        InspectionTaskStatusEnum[] enums = values();
        for (InspectionTaskStatusEnum enu : enums) {
            if (enu.getCode().equals(code)) {
                return enu;
            }
        }

        return null;
    }


    public static InspectionTaskStatusEnum format(Integer code) {
        InspectionTaskStatusEnum se = formatOrNull(code);
        return null == se ? DEFAULT : se;
    }

}

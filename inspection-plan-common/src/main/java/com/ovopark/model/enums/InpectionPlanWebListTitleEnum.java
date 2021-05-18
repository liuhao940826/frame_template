package com.ovopark.model.enums;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum InpectionPlanWebListTitleEnum {

    NAME(0,"任务名称"),
    OPERATOR(1,"执行人"),
    TAG(2,"问题标签"),
    DEPT_NUM(3,"门店数量"),
    START_TIME(4,"开始时间"),
    END_TIME(5,"结束时间"),
    AUDIT(6,"审核人"),
    STATUS(7,"状态"),
    PERCENT(8,"完成进度"),
    REMARK(9,"备注"),
//    LOG(10,"历史记录"),
    ;

    private Integer code;
    private String desc;

    InpectionPlanWebListTitleEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static InpectionPlanWebListTitleEnum formatOrNull(Integer code) {
        if (null == code) {
            return null;
        }
        InpectionPlanWebListTitleEnum[] enums = values();
        for (InpectionPlanWebListTitleEnum enu : enums) {
            if (enu.getCode().equals(code)) {
                return enu;
            }
        }

        return null;
    }


    public static InpectionPlanWebListTitleEnum format(Integer code) {
        InpectionPlanWebListTitleEnum se = formatOrNull(code);
        return null == se ? null : se;
    }


    public static List<String> getTitle(){
        InpectionPlanWebListTitleEnum[] enums = values();
        List<InpectionPlanWebListTitleEnum> list = Arrays.asList(enums);

        List<String> sortTitle = list.stream().sorted(Comparator.comparing(InpectionPlanWebListTitleEnum::getCode))
                .map(InpectionPlanWebListTitleEnum::getDesc).collect(Collectors.toList());

        return sortTitle;
    }



}

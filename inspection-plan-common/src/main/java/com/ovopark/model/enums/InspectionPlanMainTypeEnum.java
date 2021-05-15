package com.ovopark.model.enums;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum InspectionPlanMainTypeEnum {

    DISPLAY(0,"陈列中心"),
    INSPECTION(1,"巡检计划"),
    ;

    private Integer code;
    private String desc;

    InspectionPlanMainTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static InspectionPlanMainTypeEnum formatOrNull(Integer code) {
        if (null == code) {
            return null;
        }
        InspectionPlanMainTypeEnum[] enums = values();
        for (InspectionPlanMainTypeEnum enu : enums) {
            if (enu.getCode().equals(code)) {
                return enu;
            }
        }

        return null;
    }


    public static InspectionPlanMainTypeEnum format(Integer code) {
        InspectionPlanMainTypeEnum se = formatOrNull(code);
        return null == se ? DISPLAY : se;
    }


    public static List<String> getTitle(){
        InspectionPlanMainTypeEnum[] enums = values();
        List<InspectionPlanMainTypeEnum> list = Arrays.asList(enums);

        List<String> sortTitle = list.stream().sorted(Comparator.comparing(InspectionPlanMainTypeEnum::getCode))
                .map(InspectionPlanMainTypeEnum::getDesc).collect(Collectors.toList());

        return sortTitle;
    }



}

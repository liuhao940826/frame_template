package com.ovopark.model.enums;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum DisplayMainTypeEnum {

    DISPLAY(0,"陈列中心"),
    INSPECTION(1,"巡检计划"),
    ;

    private Integer code;
    private String desc;

    DisplayMainTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static DisplayMainTypeEnum formatOrNull(Integer code) {
        if (null == code) {
            return null;
        }
        DisplayMainTypeEnum[] enums = values();
        for (DisplayMainTypeEnum enu : enums) {
            if (enu.getCode().equals(code)) {
                return enu;
            }
        }

        return null;
    }


    public static DisplayMainTypeEnum format(Integer code) {
        DisplayMainTypeEnum se = formatOrNull(code);
        return null == se ? DISPLAY : se;
    }


    public static List<String> getTitle(){
        DisplayMainTypeEnum[] enums = values();
        List<DisplayMainTypeEnum> list = Arrays.asList(enums);

        List<String> sortTitle = list.stream().sorted(Comparator.comparing(DisplayMainTypeEnum::getCode))
                .map(DisplayMainTypeEnum::getDesc).collect(Collectors.toList());

        return sortTitle;
    }



}

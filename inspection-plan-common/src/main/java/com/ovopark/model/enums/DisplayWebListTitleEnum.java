package com.ovopark.model.enums;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum DisplayWebListTitleEnum {

    NAME(0,"计划名称"),
    DEPTNAME(1,"门店名称"),
    STATUS(2,"任务状态"),
    ITEMNUM(3,"检查项"),
    SCORE(4,"得分/总分"),
    PERCENT(5,"得分率"),
    OPERATOR(6,"执行人"),
    AUDIT(7,"审核人"),
    CREATEDATE(8,"创建时间")
    ;

    private Integer code;
    private String desc;

    DisplayWebListTitleEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static DisplayWebListTitleEnum formatOrNull(Integer code) {
        if (null == code) {
            return null;
        }
        DisplayWebListTitleEnum[] enums = values();
        for (DisplayWebListTitleEnum enu : enums) {
            if (enu.getCode().equals(code)) {
                return enu;
            }
        }

        return null;
    }


    public static DisplayWebListTitleEnum format(Integer code) {
        DisplayWebListTitleEnum se = formatOrNull(code);
        return null == se ? null : se;
    }


    public static List<String> getTitle(){
        DisplayWebListTitleEnum[] enums = values();
        List<DisplayWebListTitleEnum> list = Arrays.asList(enums);

        List<String> sortTitle = list.stream().sorted(Comparator.comparing(DisplayWebListTitleEnum::getCode))
                .map(DisplayWebListTitleEnum::getDesc).collect(Collectors.toList());

        return sortTitle;
    }



}

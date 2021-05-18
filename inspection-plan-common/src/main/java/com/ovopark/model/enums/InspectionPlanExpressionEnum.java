package com.ovopark.model.enums;

public enum InspectionPlanExpressionEnum {
    DEFAULT("",""),
    GTE(">=",">="),
    EQUALS("=","="),
    GT(">",">"),
    LT("<","<"),
    LTE("<=","<="),
    NOTEQUALS("≠","!=")
    ;

    private String str;
    private String expression;

    InspectionPlanExpressionEnum(String str, String expression) {
        this.str = str;
        this.expression = expression;
    }

    public String getStr() {
        return str;
    }

    public String getExpression() {
        return expression;
    }

    public static InspectionPlanExpressionEnum formatOrNull(String str) {
        if (null == str) {
            return DEFAULT;
        }
        InspectionPlanExpressionEnum[] enums = values();
        for (InspectionPlanExpressionEnum enu : enums) {
            if (enu.getStr().equals(str)) {
                return enu;
            }
        }

        return DEFAULT;
    }


    public static InspectionPlanExpressionEnum format(String str) {
        InspectionPlanExpressionEnum se = formatOrNull(str);
        return null == se ? DEFAULT : se;
    }

}

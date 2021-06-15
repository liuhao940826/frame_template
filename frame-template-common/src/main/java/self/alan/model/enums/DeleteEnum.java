package self.alan.model.enums;

public enum DeleteEnum {

    NOT_DELETED(0,"未删除"),
    DELETED(1,"已删除"),
    ;

    private Integer code;
    private String desc;

    DeleteEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static DeleteEnum formatOrNull(Integer code) {
        if (null == code) {
            return null;
        }
        DeleteEnum[] enums = values();
        for (DeleteEnum enu : enums) {
            if (enu.getCode().equals(code)) {
                return enu;
            }
        }

        return null;
    }


    public static DeleteEnum format(Integer code) {
        DeleteEnum se = formatOrNull(code);
        return null == se ? NOT_DELETED : se;
    }

}

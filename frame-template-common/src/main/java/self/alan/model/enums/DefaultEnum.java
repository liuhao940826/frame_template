package self.alan.model.enums;

public enum DefaultEnum {

    DEFAULT_FALSE(0,"默认否标记"),
    DEFAULT_TRUE(1,"默认是标记"),
    ;

    private Integer code;
    private String desc;

    DefaultEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static DefaultEnum formatOrNull(Integer code) {
        if (null == code) {
            return null;
        }
        DefaultEnum[] enums = values();
        for (DefaultEnum enu : enums) {
            if (enu.getCode().equals(code)) {
                return enu;
            }
        }

        return null;
    }


    public static DefaultEnum format(Integer code) {
        DefaultEnum se = formatOrNull(code);
        return null == se ? DEFAULT_FALSE : se;
    }

}

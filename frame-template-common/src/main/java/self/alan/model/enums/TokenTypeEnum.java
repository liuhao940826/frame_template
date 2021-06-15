package self.alan.model.enums;


public enum TokenTypeEnum {

    TOKEN(0,"token"),
    OVO(1,"Ovo-Authorization"),
    AUTHORIZATION(2,"authorization"),
    TICKET(3,"ticket"),
    AUTHENTICATOR(4,"authenticator"),
    ;

    private Integer code;
    private String desc;

    TokenTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static TokenTypeEnum formatOrNull(Integer code) {
        if (null == code) {
            return null;
        }
        TokenTypeEnum[] enums = values();
        for (TokenTypeEnum enu : enums) {
            if (enu.getCode().equals(code)) {
                return enu;
            }
        }

        return null;
    }


    public static TokenTypeEnum format(Integer code) {
        TokenTypeEnum se = formatOrNull(code);
        return null == se ? TOKEN : se;
    }

}

package com.ovopark.constants;

import java.math.BigDecimal;

/**
 * @author
 * @version 1.0
 * @description 描述
 * @create 2019-11-25 14:24
 */
public final class CommonConstants {

    public static final String UTF8 = "UTF-8";

    public static final String HEADER_ACCEPT = "application/json";
    public static final String HEADER_CONTENT_TYPE = "application/json;charset=utf-8";

    public static final Long MILLISECOND_UNIT = 1000L;

    public final static String NULL = "NULL";

    /**
     * 获取用户信息的请求路径
     */
    public static final String PARSE_TOKEN_URL = "/ovopark-sso/token/parseToken";
    /**
     * 版本号
     */
    public static final Integer DEFAULT_VERSION = 1;

    /**
     * 每一项的底数
     */
    public static final BigDecimal EACH_SCORE = new BigDecimal(5).setScale(2,BigDecimal.ROUND_HALF_DOWN);

    public static Integer TWO_WEEKS_DAYS= 14;

}

package self.alan.constants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @version 1.0
 * @description 描述
 * @create 2019-11-25 14:24
 */
public final class CommonConstants {

    public final static String NULL = "NULL";

    public static final Integer UPDATE_TIMES =  3;

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

    public static Integer ZERO= 0;

    public static String TASK_EXCEL_FILE_NAME ="陈列中心-任务报表";




    public static String INSPECTION_RESULT ="本次任务共计划检查%s家门店，已完成%s家，未完成%s家";

    public static String OK ="ok";

}

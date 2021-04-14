package com.ovopark.constants;

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

    public final static String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public final static Integer TRUE = 1;
    public final static Integer FALSE = 2;

    //数据源
    public final static String DATASOURCE_CHANNEL = "crm-ds";

    public final static String DATASOURCE_JEECG = "master";

    //base64加密盐
    public final static String BASE64_ENCODE_SALT = "X1myg4c9niY3idPf";

    //最大pageSize限制
    public final static int MAX_PAGE_SIZE_LIMIT = 200;

    //导入数据数量最大限制
    public final static int IMPORT_DATA_MAX_SIZE = 1000;

    //对象属性：隐藏敏感信息字段
    public final static String SHOW_SENSITIVE_INFO_CODE = "showSensitiveInfo";
    public final static String SHOW_SENSITIVE_INFO_NAME = "显示敏感信息";

    //导入数据动态属性
    public final static String CHANNEL_DYNAMIC_OBJECT_CODE = "IMPORTOBJECT_DYNAMIC";

    //crm动态属性
    public final static String CRM_DYNAMIC_OBJECT_CODE = "CRM_OBJECT_DYNAMIC";

    //管理员角色code
    public final static String ADMIN_ROLE_CODE = "admin";

    //采集任务最大重试次数
    public final static Integer COLLECT_TASK_RETRY_TIME = 3;

    public final static Integer Ten_Million = 10000000;

    public final static Integer Ten_ThousandE = 10000;

    public final static Integer LIMIT_SIZE =5;


}

package com.ovopark.constants;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class CommonHttpConstants {

    public static Map<String,String> commonHeaderMap = new HashMap<String,String>();
    public static Map<String,String> commonParamMap = new HashMap<String,String>();

    public static final String HEADER_CONTENT_TYPE = "application/json;charset=utf-8";
    public static final String Content_Type = "Content-Type";


    //静态代码块去赋值预热的参数 参数写在这个前面 不一定会去new类 用静态代码块去写
    static {
        //可以封装字的参数到这个里面
        commonHeaderMap.put(Content_Type,HEADER_CONTENT_TYPE);
//        commonParamMap.put()
    }

}

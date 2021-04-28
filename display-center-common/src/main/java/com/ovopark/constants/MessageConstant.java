package com.ovopark.constants;


/**
 * @Classname Validation
 * @Description 自定义异常 静态枚举类
 * @Date 2020/7/9 8:10 下午
 * @Created by liuhao
 */
public class MessageConstant {

    public static final String DISPLAY_CENTER = "陈列中心";


    public static final String DISPLAY_CENTER_CATEGORY="DISPLAY_CENTER_CATEGORY";

    public static final String CLIENT_IOS = "iOS";

    public static final String CLIENT_ANDROID = "Android";

    /**
     * IM收到系统消息
     */
    public static final int TYPE_MSG_RECEIVE = 1000;

    public static int MASSAGE_TYPE_NOTIFY = 1;//通知（特定用户）


    //登录code和设备那边统一
    public static String UN_QUALIFIED ="您创建的陈列巡检任务，存在不合格项，点击查看详情'";







    public MessageConstant() {
    }
}

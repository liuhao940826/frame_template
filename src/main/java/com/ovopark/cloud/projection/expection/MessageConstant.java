package com.ovopark.cloud.projection.expection;


/**
 * @Classname Validation
 * @Description 自定义异常 静态枚举类
 * @Date 2020/7/9 8:10 下午
 * @Created by liuhao
 */
public class MessageConstant {

    //登录code和设备那边统一
    public static String FINISH_MESSAGE ="执行人%s,完成了%s门店的陈列任务'%s'";

    public static String START_MESSAGE ="您有一条%s任务，'%s'请查看";

    public static String FINISH_TOTAL_MESSAGE ="您创建的%s任务'%s'已经全部完成，请查看";

    //任务名称-门店名称-执行人 时分秒 时分秒是根据创建时间来战士的
    public static String PICTURES_CENTER_ALIAS_NAME="'%s'-%s-%s";

    public static String ADD_COPY_MESSAGE ="有一条'%s'任务抄送给您";

    public static String AUDIT_FAIL="您的审核未通过，请重新提交";

    public static String DELETE_MESSAGE ="%s:'%s'已删除";

    public static String REVIEW_MESSAGE = "进行中的%s任务%s已完成，请审批";

    public static String REVIEW_MESSAGE_PASS = "您执行的%s任务,%s审核%s,请查看。";



    public MessageConstant() {
    }
}

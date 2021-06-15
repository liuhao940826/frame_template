package self.alan.constants;


/**
 * @Classname Validation
 * @Description 自定义异常 静态枚举类
 * @Date 2020/7/9 8:10 下午
 * @Created by liuhao
 */
public class MessageConstant {

    public static final String INSPECTION_PLAN= "巡检计划";


    /**
     * 计划巡店消息大类
     */
    public static final String TYPE_STORE_PLAN_SYSTEM="TYPE_STORE_PLAN_SYSTEM";

    public static final String INSPECTION_PLAN_CATEGORY="INSPECTION_PLAN_CATEGORY";

    public static final String CLIENT_IOS = "iOS";

    public static final String CLIENT_ANDROID = "Android";

    /**
     * IM收到系统消息
     */
    public static final int TYPE_MSG_RECEIVE = 1000;
    /**
     *极光推送陈列中心Type
     */
    public static int DISPLAY_CENTER_JPUSH_TYPE =65;
    /**
     *极光推送巡检Type 说是要放在计划巡店里面所以改成46 本来定的是66
     */
    public static int INSPECTION_JPUSH_TYPE =46;


    //登录code和设备那边统一
    public static String UN_QUALIFIED ="您创建的陈列巡检任务，存在不合格项，点击查看详情'";


    //巡检集合消息文案
    public static String URGED_AUDIT ="%s提醒您审批他的“%s”巡店计划";


    public static String ADD_MESSAGE ="%s发起的“%s”巡店计划，需要您进行审核";

    public static String REFUSE_MESSAGE ="您的“%s”巡店计划被驳回，请修改后再次提交。";

    public static String PASS_MESSAGE ="您的“%s”巡店计划审核通过";


    public static String DELETE_MESSAGE ="%s删除了“%s”巡店计划";

    public static String COMPELETE_MESSAGE ="“%s”巡店计划已完成，请查看检查结果";

    public MessageConstant() {
    }
}

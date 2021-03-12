package com.ovopark.cloud.projection.expection;


/**
 * @Classname Validation
 * @Description 自定义异常 静态枚举类
 * @Date 2020/7/9 8:10 下午
 * @Created by liuhao
 */
public class ResultCode {

    //登录code和设备那边统一
    public static ResultCodeInfo TOKEN_ERROR = new ResultCodeInfo(9990001, "TOKEN_ERROR", "token错误");
    public static ResultCodeInfo RESULT_INVALID_TOKEN = new ResultCodeInfo(9990002, "INVALID_TOKEN", "登录失效");

    public static ResultCodeInfo OK = new ResultCodeInfo(0, "OK", "OK");
    public static ResultCodeInfo SUCCESS = new ResultCodeInfo(200, "SUCCESS", "OK");

    public static ResultCodeInfo FORBIDDEN_ACCESS = new ResultCodeInfo(403, "FORBIDDEN_ACCESS", "无权访问");
    public static ResultCodeInfo PATH_NOT_FOUND = new ResultCodeInfo(404, "PATH_NOT_FOUND", "请求地址不存在");
    public static ResultCodeInfo PARAM_ERROR = new ResultCodeInfo(501, "PARAM_ERROR", "请求参数错误");
    public static ResultCodeInfo INTERNAL_SERVER_ERROR = new ResultCodeInfo(500, "INTERNAL_SERVER_ERROR", "服务器异常");
    public static ResultCodeInfo SYS_ERROR_MSG = new ResultCodeInfo(996, "SYS_ERROR_MSG", "系统异常, %s ");
    public static ResultCodeInfo FAILURE = new ResultCodeInfo(997, "FAILURE", "业务失败");
    public static ResultCodeInfo SYS_ERROR = new ResultCodeInfo(998, "SYS_ERROR", "系统异常");
    public static ResultCodeInfo UNKNOWN_ERROR = new ResultCodeInfo(999, "UNKNOWN_ERROR", "未知错误");
    public static ResultCodeInfo SOCKET_TIMEOUT_ERROR = new ResultCodeInfo(1000, "SOCKET_TIMEOUT_ERROR", "网络超时请稍后再试");
    // 系统错误

    public static ResultCodeInfo PARAM_ERROR_NAME = new ResultCodeInfo(100002, "PARAM_ERROR_NAME", " %s 请求参数错误");
    public static ResultCodeInfo DB_OPERATION_ERROR = new ResultCodeInfo(100003, "DB_OPERATION_ERROR", " DB操作失败");
    public static ResultCodeInfo UPLOAD_FILE_ERROR = new ResultCodeInfo(100004, "UPLOAD_FILE_ERROR", "上传文件失败");
    public static ResultCodeInfo APPLY_ID_FAIL = new ResultCodeInfo(100005, "APPLY_ID_FAIL", "申请id失败");
    public static ResultCodeInfo ADMIN_NOT_EXIST = new ResultCodeInfo(100006, "ADMIN_NOT_EXIST", "用户不存在");
    public static ResultCodeInfo REMOTE_INVOVATION_ERROR = new ResultCodeInfo(100007, "REMOTE_INVOVATION_ERROR", "远程调用失败");
    public static ResultCodeInfo NO_CORRESPONDING_ENTITY = new ResultCodeInfo(100008, "NO_CORRESPONDING_ENTITY", "无对应实体");
    public static ResultCodeInfo UPDATA_SUCCESS = new ResultCodeInfo(200, "UPDATA_SUCCESS", "更新成功");
    public static ResultCodeInfo UPDATA_FAIL = new ResultCodeInfo(100010, "UPDATA_FAIL", "更新失败");
    public static ResultCodeInfo PARAM_SHOULD_NOT_BE_NULL = new ResultCodeInfo(100009, "PARAM_SHOULD_NOT_BE_NULL", " %s不得为空");


    //巡店的任务
    public static  ResultCodeInfo TEMPLATE_DELETE_FAIL = new ResultCodeInfo(200001, "TEMPLATE_DELETE_FAIL", "巡店模板高级配置删除失败");
    public static  ResultCodeInfo STORE_PLAN_UPDATE_PARAM_EMPTY = new ResultCodeInfo(200002, "STORE_PLAN_UPDATE_PARAM_EMPTY", "巡店任务更改参数为空");
    public static  ResultCodeInfo STORE_PLAN_EMPTY = new ResultCodeInfo(200003, "STORE_PLAN_EMPTY", "计划巡店任务不存在");
    public static  ResultCodeInfo STORE_PLAN_TPYE_NOT_READ = new ResultCodeInfo(200004, "STORE_PLAN_TPYE_NOT_READ", "计划巡店任务任务类型非阅读任务");
    public static  ResultCodeInfo STORE_PLAN_JOB = new ResultCodeInfo(200005, "STORE_PLAN_TPYE_NOT_READ", "计划巡店任务任务定时任务失败");
    public static  ResultCodeInfo STORE_PLAN_PRIVILEGEDEPT_INTERSECTION_EMPTY = new ResultCodeInfo(200006, "STORE_PLAN_PRIVILEGEDEPT_INTERSECTION_EMPTY", "计划巡店任务门店用户权限交集为空");
    //企业配置
    public static  ResultCodeInfo ENTERPRISE_CONFIG_EMPTY = new ResultCodeInfo(300001, "ENTERPRISE_CONFIG_EMPTY", "root企业配置不存在");




    public ResultCode() {
    }
}

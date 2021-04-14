package com.ovopark.expection;


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





    public ResultCode() {
    }
}

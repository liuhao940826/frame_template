package self.alan.model.resp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.HashMap;

public class JsonResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RESULT_SUCCESS = "ok";
	public static final String RESULT_FAILED = "FAILED";
	public static final String RESULT_INVALID_TOKEN = "INVALID_TOKEN";
	public static final String RESULT_INVALID_FILE_FORMAT = "INVALID_FILE_FORMAT";
	public static final String RESULT_INVALID_MAC = "INVALID_MAC";
	public static final String RESULT_INVALID_EMAIL_FORMAT = "INVALID_EMAIL_FORMAT";
	public static final String RESULT_INVALID_EMAIL = "INVALID_EMAIL";
	public static final String RESULT_INVALID_PHONE_FORMAT = "INVALID_PHONE_FORMAT";
	public static final String RESULT_DEVICE_OFFLINE = "DEVICE_OFFLINE";
	public static final String RESULT_INVALID_PARENT = "INVALID_PARENT";
	public static final String RESULT_DEVICE_REGISTERED = "DEVICE_REGISTERED";
	public static final String RESULT_NO_DATA = "NO_DATA";
	public static final String RESULT_NO_PHONE = "NO_PHONE";
	public static final String RESULT_EXCEPTION = "EXCEPTION";
	public static final String RESULT_INVALID_PARAMETER = "INVALID_PARAMETER";
	public static final String RESULT_NO_PERMISSION = "NO_PERMISSION";
	public static final String RESULT_USERNAME_REGISTERED = "USERNAME_REGISTERED";
	public static final String RESULT_PHONE_REGISTERED = "PHONE_REGISTERED";
	public static final String RESULT_PHONE_NULL = "PHONE_NULL";
	public static final String RESULT_UNKNOWN_EMAIL = "UNKNOWN_EMAIL";
	public static final String RESULT_PASSWD_ERROR = "PASSWD_ERROR";
	public static final String RESULT_TOKEN_EXPIRED = "TOKEN_EXPIRED";
	public static final String RESULT_USER_EXPIRED = "USER_EXPIRED";
	public static final String RESULT_CHANNEL_FULL = "CHANNEL_FULL";
	public static final String RESULT_INPUT_FAILED = "INPUT_FAILED";
	public static final String RESULT_NOT_SUPPORTED = "NOT_SUPPORTED";
	public static final String RESULT_DUPLICATE = "DUPLICATE";
	public static final String RESULT_EXPIRE = "EXPIRE";
	public static final String RESULT_DEVICE_INITIALIZED = "DEVICE_INITIALIZED";
	public static final String RESULT_NEVER_FIRED = "NEVER_FIRED";
	public static final String RESULT_DUPLICATE_SUBMIT = "DUPLICATE_SUBMIT";
	public static final String RESULT_BALANCE_INSUFFICIENT = "RESULT_BALANCE_INSUFFICIENT";
	public static final String RESULT_CODE_OVERDUE = "CODE_OVERDUE";
	public static final String RESULT_USER_OVERDUE = "USER_OVERDUE";
	public static final String RESULT_USER_DEL = "USER_DEL";
	public static final String RESULT_DEPT_DEL = "DEPT_DEL";
	public static final String RESULT_CODE_ERROR = "CODE_ERROR";
	public static final String RESULT_INVALID_DEVICE_LOGIN = "INVALID_DEVICE_LOGIN";
	public static final String RESULT_EXCEED_LIMIT = "EXCEED_LIMIT";
	public static final String RESULT_ILLEGAL_DATA = "ILLEGAL_DATA";
	public static final String RESULT_HAND_CAPTURE_SCENE = "HAND_CAPTURE_SCENE";
	public static final String RESULT_DELETED_SCENE = "DELETED_SCENE";
	public static final String RESULT_DISABLED_SCENE = "DISABLED_SCENE";
	public static final String RESULT_DEVICE_BUSY = "DEVICE_BUSY";
	public static final String RESULT_EXIST_DATA = "EXIST_DATA";
	public static final String RESULT_PHONE_NUMBER_ALREADY_EXIST = "PHONE_NUMBER_ALREADY_EXIST";
	public static final String RESULT_ID_NUMBER_ALREADY_EXIST = "ID_NUMBER_ALREADY_EXIST";
	public static final String RESULT_PHONE_AND_ID_NUMBER_ALREADY_EXIST = "PHONE_AND_ID_NUMBER_ALREADY_EXIST";
	public static final String RESULT_FACE_RECOGNIZE_FAILED = "FACE_RECOGNIZE_FAILED";
	public static final String RESULT_SIGN_CHECK_FAILED = "SIGN_CHECK_FAILED";
	
	public static final String RESULT_NO_AMUSEMENT_ID = "NO_AMUSEMENT_ID";
	public static final String RESULT_NO_SUBJECT = "MAIL_NO_SUBJECT";
	public static final String RESULT_NO_FROM = "MAIL_NO_FROM";
	public static final String RESULT_NO_TO = "MAIL_NO_TO";
	public static final String RESULT_CREATE_FILE_FAILED = "CREATE_FILE_FAILED";
	public static final String RESULT_ALREADY_EXIST = "ALREADY_EXIST";
	public static final String RESULT_GET_ACCESS_FAILED = "GET_ACCESS_FAILED";
	public static final String RESULT_INVALID_PRIVILEGE = "INVALID_PRIVILEGE";
	public static final String RESULT_ALL_CLOSE = "RESULT_ALL_CLOSE";
	//名称已经存在
	public static final String ACTIVITY_NAME_EXISTING = "ACTIVITY_NAME_EXISTING";
	//推荐上限
	public static final String RECOMMEND_EXISTING = "RECOMMEND_EXISTING";
	
	public static final String RESULT_INVALID_SHOPID_OR_MACHINEID = "INVALID_SHOPID_OR_MACHINEID";
	
	//不是超级管理员
	public static final String RESULT_NOT_ADMINISTRATOR = "NOT_ADMINISTRATOR";
	
	//faceSetDetail表插入数据失败
	public static final String RESULT_INSERT_FACESET_DETAIL_IN_DB_FAILED = "INSERT_FACESET_DETAIL_IN_DB_FAILED";
	//无效的会员类型
	public static final String RESULT_INVALID_REGTYPE = "INVALID_REGTYPE";	
	//超过一个人脸
	public static final String RESULT_MORE_THAN_ONE_FACE = "MORE_THAN_ONE_FACE";	
	//不是人脸
	public static final String RESULT_IS_NOT_FACE = "IS_NOT_FACE";	
	//创建人脸集合失败
	public static final String RESULT_CREATE_FACE_SET_FAIL = "CREATE_FACE_SET_FAIL";
	//添加人脸到人脸集合失败
	public static final String RESULT_ADD_FACE_IN_FACESET_FAIL = "ADD_FACE_IN_FACESET_FAIL";
	//无匹配的人脸
	public static final String RESULT_NO_MATCHED_FACE = "NO_MATCHED_FACE";
	//未知错误
	public static final String RESULT_UNKNOWN_ERROR = "UNKNOWN_ERROR";
	//Bean转换错误
	public static final String RESULT_BEAN_CHANGE_ERROR = "BEAN_CHANGE_ERROR";
	//该人脸已经注册
	public static final String RESULT_FACE_HAS_BEEN_REGIST = "FACE_HAS_BEEN_REGIST";
	//search失败
	public static final String RESULT_FACE_SEARCH_FAIL = "FACE_SEARCH_FAIL";
	//人脸不符合入库要求
	public static final String RESULT_FACE_LOW_QUALITY = "FACE_LOW_QUALITY";
	//人脸超并发量
	public static final String RESULT_CONCURRENCY_LIMIT_EXCEEDED = "CONCURRENCY_LIMIT_EXCEEDED";
	//数据源获取失败
	public static final String RESULT_DATA_SRC_GET_ERROR = "DATA_SRC_GET_ERROR";
	//删除人脸失败
	public static final String RESULT_FACE_DELETE_ERROR = "FACE_DELETE_ERROR";
	//人脸嘴部被遮挡
	public static final String RESULT_FACE_MOUTH_OCCLUSION = "FACE_MOUTH_OCCLUSION";
	//人脸左眼被遮挡
	public static final String RESULT_FACE_LEFT_EYE_OCCLUSION = "FACE_LEFT_EYE_OCCLUSION";
	//人脸右眼被遮挡
	public static final String RESULT_FACE_RIGHT_EYE_OCCLUSION = "FACE_RIGHT_EYE_OCCLUSION";
	//人脸左脸颊被遮挡
	public static final String RESULT_FACE_LEFT_CHEEK_OCCLUSION = "FACE_LEFT_CHEEK_OCCLUSION";
	//人脸右脸颊被遮挡
	public static final String RESULT_FACE_RIGHT_CHEEK_OCCLUSION = "FACE_RIGHT_CHEEK_OCCLUSION";
	//人脸太模糊
	public static final String RESULT_FACE_BLUR = "FACE_BLUR";
	//人脸太黑
	public static final String RESULT_FACE_DARK = "FACE_DARK";
	//没有合格的人脸照片
	public static final String RESULT_NO_FACE_PICTURE = "NO_FACE_PICTURE";
	
	//oauth 无效accessToken
	public static final String RESULT_INVALID_ACCESS_TOKEN = "INVALID_ACCESS_TOKEN";

	//任务开始时间 >= 结束时间
	public static final String RESULT_INVALID_TIME_PARAMETER = "INVALID_TIME_PARAMETER";
	
	//任务结束时间 <= 现在
	public static final String RESULT_INVALID_TIME_END_BEFORE_NOW = "INVALID_TIME_END_BEFORE_NOW";
	
	//结束时间 - 开始时间 > 366天
	public static final String RESULT_INVALID_TIME_MORE_THAN_ONE_YEAR = "INVALID_TIME_MORE_THAN_ONE_YEAR";
	
	//属性已经存在
	public static final String RESULT_ATTRIBUTE_ALREADY_EXIST = "ATTRIBUTE_ALREADY_EXIST";
	
	//属性名为空
	public static final String RESULT_ATTRIBUTE_NAME_EMPTY = "ATTRIBUTE_NAME_EMPTY";
	
	//选项已经存在
	public static final String RESULT_OPTION_ALREADY_EXIST = "OPTION_ALREADY_EXIST";
	
	//选项名为空
	public static final String RESULT_OPTION_NAME_EMPTY = "OPTION_NAME_EMPTY";
	
	//门店未设置目标
	public static final String RESULT_EMPTY_DEPARTMENT_GOAL = "EMPTY_DEPARTMENT_GOAL";

	//没有配置客服
	public static final String RESULT_EMPTY_SERVICE = "EMPTY_SERVICE";
	
	//总目标！=目标之和
	public static final String RESULT_GOAL_NOT_FULL= "GOAL_NOTFULL";
	//名称重复
	public static final String NAME_EXISTING="NAME_EXISTING";
	//没有评论人
	public static final String RESULT_EMPTY_COMMENTER="EMPTY_COMMENTER";
	//商品价格不一致
	public static final String TOTAL_GOLD_FAILED="TOTAL_GOLD_FAILED";
	//chanllenge为空
	public static final String RESULT_CHANLLENGE_NULL="RESULT_CHANLLENGE_NULL";
	//geetest验证失败
	public static final String RESULT_GEETEST_CHECK_FAILIURE="RESULT_GEETEST_CHECK_FAILIURE";
	//challenge在redis中不存在
	public static final String RESULT_INVALID_CHANLLENGE="RESULT_INVALID_CHANLLENGE";
	//redis查詢異常
	public static final String RESULT_QUERRY_REDIS_ERROR="RESULT_QUERRY_REDIS_ERROR";
	//题目已经被做，不能修改
	public static final String QUESTION_IS_USE="QUESTION_IS_USE";





	public static final String NOT_IN_THIS_PROJECT="NOT_IN_THIS_PROJECT";

	//金币规则不符
	public static final String GOLD_RULE_FAILED="GOLD_RULE_FAILED";
	//金币规则不符
	public static final String GOLD_RULE_MAX_FAILED="GOLD_RULE_MAX_FAILED";

	public JsonResult(){}
	
	public JsonResult(String result, HashMap<String, Object> data, Boolean isError){
		this.result = result;
		this.data = data;
		this.isError = isError;
	}
	
	public static JsonResult newInstance(String result,Object data,Boolean isError){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("data", data);
		return new JsonResult(result,map,isError);
	}
	
	/**
	 * 该用户不属于任何企业
	 */
	public static final String RESULT_NOT_BELONG_TO_ANY_COMPANY = "NOT_BELONG_TO_ANY_COMPANY";
	/**
	 * 未登录
	 */
	public static final String RESULT_NOT_LOGIN = "LOGIN_OUT";
	/**
	 * 巡店任务不存在
	 */
	public static final String RESULT_TASK_NOT_EXIST = "TASK_NOT_EXIST";
	
	private String result;
	private HashMap<String, Object> data = new HashMap<String, Object>();
	private Boolean isError;
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public HashMap<String, Object> getData() {
		return data;
	}
	
	public void setData(HashMap<String, Object> data) {
		this.data = data;
	}

	public Boolean getIsError() {
		return isError;
	}

	public void setIsError(Boolean isError) {
		this.isError = isError;
	}

	public static String success(){
		return JSONObject.toJSONString(newInstance(JsonResult.RESULT_SUCCESS,null,false));
	}
	
	public static String success(Object data){
		return JSONObject.toJSONString(newInstance(JsonResult.RESULT_SUCCESS,data,false),SerializerFeature.DisableCircularReferenceDetect);
	}

	/**
	 * 返回的json字符串中 包含值为 null 的字段
	 * @param data
	 * @return
	 */
	public static String successMapNullValue(Object data){
		return JSONObject.toJSONString(newInstance(JsonResult.RESULT_SUCCESS,data,false),SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue);
	}
	
	public static String paramError(){
		return JSONObject.toJSONString(newInstance(JsonResult.RESULT_INVALID_PARAMETER,null,true));
	}

	public static String error(){
		return JSONObject.toJSONString(newInstance(JsonResult.RESULT_FAILED,null,true));
	}
	
	public static String invalidToken(){
		return JSONObject.toJSONString(newInstance(JsonResult.RESULT_INVALID_TOKEN,null,true));
	}
	
	public static String invalidAccessToken(){
		return JSONObject.toJSONString(newInstance(JsonResult.RESULT_INVALID_ACCESS_TOKEN,null,true));
	}
	
	public static String invalidPrivilege(){
		return JSONObject.toJSONString(newInstance(JsonResult.RESULT_INVALID_PRIVILEGE,null,true));
	}
	
	public static String error(String errorCode,Object data){
		if(StringUtils.isEmpty(errorCode)){
			errorCode = JsonResult.RESULT_FAILED;
		}
		return JSONObject.toJSONString(newInstance(errorCode,data,true));
	}
	public static String success(String successCode,Object data){
		if(StringUtils.isEmpty(successCode)){
			successCode = JsonResult.RESULT_SUCCESS;
		}
		return JSONObject.toJSONString(newInstance(RESULT_SUCCESS,data,false));
	}

	
	public static JsonResult jrparamError(){
		return newInstance(JsonResult.RESULT_INVALID_PARAMETER,null,true);
	}	

	public static JsonResult jrSuccess(){
		return newInstance(JsonResult.RESULT_SUCCESS,null,false);
	}
	
	public static JsonResult jrSuccess(Object data){
		return newInstance(JsonResult.RESULT_SUCCESS,data,false);
	}

	public static JsonResult jrError(){
		return newInstance(JsonResult.RESULT_FAILED,null,true);
	}
	
	public static JsonResult jrError(String errorCode,Object data){
		if(StringUtils.isEmpty(errorCode)){
			errorCode = JsonResult.RESULT_FAILED;
		}
		return newInstance(errorCode,data,true);
	}
	
	public static void main(String[] args) {
		JsonResult t = new JsonResult();
		t.setIsError(false);
		t.setResult(null);
		System.out.println(JSON.toJSONString(t));
		System.out.println(success(t));
	}
}

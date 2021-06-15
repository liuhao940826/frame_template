package self.alan.model.resp;

import self.alan.expection.ResultCodeInfo;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.HashMap;

public class JsonNewResult<T> implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final String RESULT_SUCCESS = "ok";
	public static final String RESULT_FAILED = "FAILED";
	public static final String RESULT_INVALID_TOKEN = "INVALID_TOKEN";
	public static final String RESULT_NO_DATA = "NO_DATA";
	public static final String RESULT_EXCEPTION = "EXCEPTION";
	public static final String RESULT_INVALID_PARAMETER = "INVALID_PARAMETER";
	public static final String RESULT_INVALID_PRIVILEGE = "INVALID_PRIVILEGE";

	//oauth 无效accessToken
	public static final String RESULT_INVALID_ACCESS_TOKEN = "INVALID_ACCESS_TOKEN";

	public static final String NOT_IN_THIS_PROJECT="NOT_IN_THIS_PROJECT";


	public JsonNewResult() {
	}

	public JsonNewResult(String result, HashMap<String, T> data, Boolean isError){
		this.result = result;
		this.data = data;
		this.isError = isError;
	}

	public JsonNewResult(String result, String code, HashMap<String, T> data, Boolean isError) {
		this.result = result;
		this.code = code;
		this.data = data;
		this.isError = isError;
	}

	public static <T> JsonNewResult<T> newInstance(String result, T data, Boolean isError){
		HashMap<String, T> map = new HashMap<String, T>();
		map.put("data", data);
		return new JsonNewResult(result,map,isError);
	}

	public static <T> JsonNewResult<T> newInstance(Integer code ,String result, T data, Boolean isError){
		HashMap<String, T> map = new HashMap<String, T>();
		map.put("data", data);
		return new JsonNewResult(result,map,isError);
	}

	private String result;
	private String code;
	private HashMap<String, T> data = new HashMap<String, T>();
	private Boolean isError;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public HashMap<String, T> getData() {
		return data;
	}
	
	public void setData(HashMap<String, T> data) {
		this.data = data;
	}

	public Boolean getIsError() {
		return isError;
	}

	public void setIsError(Boolean isError) {
		this.isError = isError;
	}

	public static <T> JsonNewResult<T> success(){
		return newInstance(JsonNewResult.RESULT_SUCCESS,null,false);
	}
	
	public static <T> JsonNewResult<T> success(T data){
		return newInstance(JsonNewResult.RESULT_SUCCESS,data,false);
	}

	/**
	 * 返回的json字符串中 包含值为 null 的字段
	 * @param data
	 * @return
	 */
	public static <T> JsonNewResult<T> successMapNullValue(T data){
		return newInstance(JsonNewResult.RESULT_SUCCESS,data,false);
	}
	
	public static <T> JsonNewResult<T> paramError(){
		return newInstance(JsonNewResult.RESULT_INVALID_PARAMETER,null,true);
	}

	public static <T> JsonNewResult<T> error(){
		return newInstance(JsonNewResult.RESULT_FAILED,null,true);
	}
	
	public static <T> JsonNewResult<T> invalidToken(){
		return newInstance(JsonNewResult.RESULT_INVALID_TOKEN,null,true);
	}
	
	public static <T> JsonNewResult<T> invalidAccessToken(){
		return newInstance(JsonNewResult.RESULT_INVALID_ACCESS_TOKEN,null,true);
	}
	
	public static <T> JsonNewResult<T> invalidPrivilege(){
		return newInstance(JsonNewResult.RESULT_INVALID_PRIVILEGE,null,true);
	}
	
	public static <T> JsonNewResult<T> error(String errorCode, T data){
		if(StringUtils.isEmpty(errorCode)){
			errorCode = JsonNewResult.RESULT_FAILED;
		}
		return newInstance(errorCode,data,true);
	}

	public static <T> JsonNewResult<T> error(ResultCodeInfo resultCodeInfo){
		return newInstance(resultCodeInfo.getCode(),resultCodeInfo.getDesc(),null,true);
	}


	public static <T> JsonNewResult<T> error(ResultCodeInfo resultCodeInfo,String eStr){
		return newInstance(resultCodeInfo.getCode(),resultCodeInfo.getDesc()+":"+eStr,null,true);
	}


	public static <T> JsonNewResult<T> success(String successCode, T data){
		if(StringUtils.isEmpty(successCode)){
			successCode = JsonNewResult.RESULT_SUCCESS;
		}
		return newInstance(RESULT_SUCCESS,data,false);
	}

	
	public static <T> JsonNewResult<T> jrparamError(){
		return newInstance(JsonNewResult.RESULT_INVALID_PARAMETER,null,true);
	}	

	public static <T> JsonNewResult<T> jrSuccess(){
		return newInstance(JsonNewResult.RESULT_SUCCESS,null,false);
	}
	
	public static <T> JsonNewResult<T> jrSuccess(T data){
		return newInstance(JsonNewResult.RESULT_SUCCESS,data,false);
	}

	public static <T> JsonNewResult<T> jrError(){
		return newInstance(JsonNewResult.RESULT_FAILED,null,true);
	}
	
	public static <T> JsonNewResult<T> jrError(String errorCode, T data){
		if(StringUtils.isEmpty(errorCode)){
			errorCode = JsonNewResult.RESULT_FAILED;
		}
		return newInstance(errorCode,data,true);
	}
	
	public static void main(String[] args) {
		JsonNewResult t = new JsonNewResult();
		t.setIsError(false);
		t.setResult(null);
		System.out.println(JSON.toJSONString(t));
		System.out.println(success(t));
	}
}

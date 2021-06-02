package com.ovopark.utils;

import com.ovopark.constants.CommonHttpConstants;
import com.ovopark.model.enums.TokenTypeEnum;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * @Classname OkHttp3Util
 * @Description TODO
 * @Date 2021/5/13 20:02
 * @Created by liuhao
 */
public class OkHttp3Util {

    private static Logger logger = LoggerFactory.getLogger(OkHttp3Util.class);

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public static final String  SCHEME_HTTP ="http";

    public static final String  SCHEME_HTTPS ="https";

    /**
     *
     * @param url
     * @param token
     * @param tokenType 根据类型去获取token的Key
     * @param scheme http/https
     */
    public static String doGet(String url, Map<String,String> param,String token,Integer tokenType,String scheme) {

        if(scheme==null){
            scheme = SCHEME_HTTP;
        }

        //创建OkHttpClient请求对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new CommonParamInterceptor())
                .build();

        HttpUrl.Builder httpBuild = new HttpUrl.Builder()
                .scheme(scheme)
                .host(url);
        for (String key : param.keySet()) {
            httpBuild.addQueryParameter(key,param.get(key));
        }

        HttpUrl httpUrl = httpBuild.build();

        //创建Request
        Request.Builder builder = new Request.Builder();

        TokenTypeEnum tokenTypeEnum = TokenTypeEnum.format(tokenType);
        Request request = builder.url(httpUrl)
                .header(tokenTypeEnum.getDesc(), token)
                .build();

        //得到Call对象
        Call call = okHttpClient.newCall(request);


        try (Response response = call.execute()){
            String result = response.body().string();
            logger.info("url:"+url+"的返回参数:"+result);
            return result;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 回调的逻辑 回调方法在callBack里面
     * @param url
     * @param params
     * @param callback
     * @param token
     * @param tokenType
     */
    public static void doPostCallBack(String url, Map<String, Object> params, Callback callback, String token, Integer tokenType) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor())
                .build();
        //创建请求
        RequestBody body = RequestBody.create(JSON, com.alibaba.fastjson.JSON.toJSONString(params));

        TokenTypeEnum tokenTypeEnum = TokenTypeEnum.format(tokenType);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header(tokenTypeEnum.getDesc(), token)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * post请求
     * @param url
     * @param params
     * @param token
     * @param tokenType
     * @return
     */
    public static String doPost(String url, Map<String, Object> params,  String token, Integer tokenType) {
        //添加拦截器
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor())
                .build();
        //创建请求
        RequestBody body = RequestBody.create(JSON, com.alibaba.fastjson.JSON.toJSONString(params));

        TokenTypeEnum tokenTypeEnum = TokenTypeEnum.format(tokenType);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header(tokenTypeEnum.getDesc(), token)
                .build();

        //这么写可以获取返回结果
        try (Response response = okHttpClient.newCall(request).execute()) {
            String result = response.body().string();
            logger.info("url:"+url+"的返回参数:"+result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 公共参数拦截器
     */
    public static class LoggerInterceptor implements Interceptor {
        //拦截器处理
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long startTime = System.nanoTime();
            logger.info(String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);
            long endTime = System.nanoTime();
            logger.info(String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (endTime - startTime) / 1e6d, response.headers()));

            return response;
        }
    }

    public  static class CommonParamInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request oldRequest = chain.request();
            Response response = null;
            //添加请求头
            Request addParamRequest = addHeader(oldRequest);
            //添加参数
            addParamRequest = addParam(oldRequest);
            response = chain.proceed(addParamRequest);
            return response;
        }

        /**
         * 添加公共参数
         *
         * @param oldRequest
         * @return
         */
        private Request addParam(Request oldRequest) {
            //这边自定义更改对应的参数
            HttpUrl.Builder builder = null;

            HttpUrl.Builder httpUrlBuilder = oldRequest.url().newBuilder();
            //公共的参数
            Map<String, String> commonParamMap = CommonHttpConstants.commonParamMap;

            for (String key : commonParamMap.keySet()) {
                httpUrlBuilder.addQueryParameter(key, commonParamMap.get(key));
            }
            HttpUrl build = httpUrlBuilder.build();
            //创建新的请求
            Request newRequest = oldRequest.newBuilder()
                    .method(oldRequest.method(), oldRequest.body())
                    .url(build)
                    .build();

            return newRequest;
        }

        /**
         * 添加请求头
         *
         * @param oldRequest
         * @return
         */
        public Request addHeader(Request oldRequest) {
            //可以自定义的map 遍历
            Request.Builder builder = oldRequest.newBuilder();
            //公共的参数
            Map<String, String> commonHeaderMap = CommonHttpConstants.commonHeaderMap;

            for (String key : commonHeaderMap.keySet()) {
                builder.addHeader(key, commonHeaderMap.get(key));
            }

            return builder.build();
        }
    }


}

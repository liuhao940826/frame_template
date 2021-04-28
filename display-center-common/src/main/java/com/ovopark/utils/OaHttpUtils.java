package com.ovopark.utils;

import com.alibaba.fastjson.JSON;
import com.ovopark.model.enums.TokenTypeEnum;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Classname OaHttpUtils
 * @Description TODO
 * @Date 2021/3/19 上午9:48
 * @Created by liuhao
 */
public class OaHttpUtils {

    private static Logger logger = LoggerFactory.getLogger(OaHttpUtils.class);

    public static String sendJsonToOtherServerDefaultWithOutToken(String url, Map<String,Object> map, String token, Integer tokenType) {

        logger.info("调用的url地址"+url+"参数"+ JSON.toJSONString(map));

        HttpClient client = new HttpClient();//创建httpClient对象
        PostMethod post = new PostMethod(url);

        if(map != null && map.size() > 0) {

            NameValuePair[] nameValuePair = new NameValuePair[map.size()];
            int i = 0;
            for (String key : map.keySet()) {
                NameValuePair nameValuePairTmp = new NameValuePair();
                nameValuePair[i] = nameValuePairTmp;
                nameValuePair[i].setName(key);
                nameValuePair[i].setValue(JSON.toJSONString(map.get(key)));
                i++;
            }

            post.setRequestBody(nameValuePair);
        }

        TokenTypeEnum tokenTypeEnum = TokenTypeEnum.format(tokenType);

        post.setRequestHeader(tokenTypeEnum.getDesc(),token);

        post.getParams().setParameter(
                HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        HttpConnectionManagerParams managerParams = client
                .getHttpConnectionManager().getParams();
        // 设置连接超时时间(单位毫秒)
        managerParams.setConnectionTimeout(60000);
        // 设置读数据超时时间(单位毫秒)
        managerParams.setSoTimeout(60000);

        try {
            int code=client.executeMethod(post);//发送数据
            if (code==200) {
                return post.getResponseBodyAsString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            post.releaseConnection();//关闭连接
        }

        return null;

    }




}

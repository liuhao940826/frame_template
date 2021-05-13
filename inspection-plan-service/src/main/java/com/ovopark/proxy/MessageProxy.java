package com.ovopark.proxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ovopark.constants.CommonConstants;
import com.ovopark.constants.MessageConstant;
import com.ovopark.context.HttpContext;
import com.ovopark.expection.ResultCode;
import com.ovopark.expection.SysErrorException;
import com.ovopark.model.enums.DisplayMainTypeEnum;
import com.ovopark.model.resp.JsonNewResult;
import com.ovopark.model.resp.JsonResult;
import com.ovopark.po.Messages;
import com.ovopark.po.TaskMessage;
import com.ovopark.service.MessageService;
import com.ovopark.utils.DateUtils;
import com.ovopark.utils.HttpUtils;
import com.ovopark.utils.OkHttp3Util;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Classname XxlJobProxy
 * @Description TODO
 * @Date 2021/4/25 下午4:05
 * @Created by liuhao
 */
@Service
public class MessageProxy {


    private static Logger logger = LoggerFactory.getLogger(MessageProxy.class);


    @Value("${websocket.url}")
    private String webSocketServerUrl;

    @Value("${jpush.url}")
    private String jpushServerUrl;

    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    @Autowired
    MessageService messageService;

    /**
     * 极光推送
     *
     * @param userId
     * @param title
     * @param message
     */
    public void sendWebSocketAndJpush(Integer targetUserId, Integer userId, String title, String message, Integer groupId,
                                       Integer type, Integer relatedId, DisplayMainTypeEnum mainType,
                                       Integer tokenType) {
        JpushAndWebSocket(targetUserId, title, message, type, relatedId, mainType.getDesc(),tokenType);
        sendWebSocket(message, userId, targetUserId, groupId, MessageConstant.TYPE_MSG_RECEIVE, type, relatedId, mainType.getDesc());

    }

    /**
     * 极光推送
     *
     * @param userId
     * @param title
     * @param message
     */
    private void JpushAndWebSocket(Integer userId, String title, String message, Integer type, Integer relatedId, String objectType, Integer tokenType) {

        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("userIdList", Arrays.asList(userId));
        map.put("messageType", type);

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("taskId", relatedId);
        jsonMap.put("objectType", objectType);
        map.put("taskJson", jsonMap);
        map.put("reason", message);
        logger.info("极光推送请求参数数据:" + JSON.toJSONString(map) + "tokenType" + tokenType + "token值" + HttpContext.getContextInfoToken());
//        String result = OkHttpUtils.sendPostToOtherServerWithAuth(jpushServerUrl, map, HttpContext.getContextInfoToken(),tokenType);
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                logger.info("极光推送 onFailure: " + e.getMessage());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                if (body != null) {
                    logger.info("极光推送Success 返回结果:" + response.body().string());
                    body.close();
                }
            }
        };

        OkHttp3Util.doPostCallBack(jpushServerUrl, map, callback , HttpContext.getContextInfoToken(), tokenType);

    }


    /**
     * websocket
     *
     * @param content
     * @param userId
     * @param targetUserId
     * @param enterpriseId
     * @param messageType
     */
    private void sendWebSocket(String content, Integer userId, Integer targetUserId, Integer enterpriseId, Integer messageType, Integer type, Integer relatedId, String objectType) {
        Date now = new Date();
        String nowStr = DateUtils.format(now);
//        String objectType = ConstantsUtil.WebSocket.STORE_PLAN_TYPE;
        String category = MessageConstant.INSPECTION_PLAN_CATEGORY;
        //webSocket
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("type", type);
        if (relatedId != null) {
            param.put("taskId", relatedId);
        }

        //保存到数据库
        Messages msg = new Messages();
        msg.setContent(content);
        msg.setCreatetime(now);
        msg.setEnterpriseId(enterpriseId);
        msg.setSrcUserId(userId);
        msg.setTargetUserId(targetUserId);
        msg.setSubId(relatedId);
        msg.setObjectType(objectType);
        msg.setCategory(category);
        msg.setDescription(JSONObject.toJSONString(param));
        msg.setStatus(new Byte("0"));
        msg.setOptionState(new Byte("0"));
        long id = messageService.insertMessages(msg);

        TaskMessage message = new TaskMessage();
        message.setContent(content);
        message.setCreateTime(nowStr);
        message.setEnterpriseId(enterpriseId);
        message.setSrcUserId(userId);
        message.setTargetUserId(targetUserId);
        message.setSubId(relatedId);
        message.setObjectType(objectType);
        message.setDescription(JSONObject.toJSONString(param));
        message.setId(id);
        message.setStatus(0);
        message.setCategory(category);

        Map<String, String> map = new HashMap<>();
        map.put("userId", targetUserId + "");
        map.put("clients", MessageConstant.CLIENT_ANDROID + "," + MessageConstant.CLIENT_IOS);
        map.put("message", JSONObject.toJSONString(message));
        map.put("messageType", String.valueOf(messageType));
        logger.info("webSocket请求参数"+JSON.toJSONString(map));
        String result = HttpUtils.sendJsonToOtherServerDefault(webSocketServerUrl, map);
        logger.info("webSocket返回结果"+result);
        JsonNewResult jsonNewResult = JSON.parseObject(result, JsonNewResult.class);

        if(jsonNewResult==null || jsonNewResult.getIsError()){
            throw new SysErrorException(ResultCode.WEBSOECK_ERROR);
        }


    }


}

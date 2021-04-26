package com.ovopark.proxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ovopark.expection.ResultCode;
import com.ovopark.expection.SysErrorException;
import com.ovopark.model.proxy.XxlJobInfoBo;
import com.ovopark.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    @Value("${shopjob.url}")
    private String xxljobUrl;

    @Value("${shopjob.executorId}")
    private String executorId;

    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    /**
     * 极光推送
     *
     * @param userId
     * @param title
     * @param message
     */


}

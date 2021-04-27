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

import java.util.Map;

/**
 * @Classname XxlJobProxy
 * @Description TODO
 * @Date 2021/4/25 下午4:05
 * @Created by liuhao
 */
@Service
public class XxlJobProxy {

    private static Logger logger = LoggerFactory.getLogger(XxlJobProxy.class);

    @Value("${shopjob.url}")
    private String xxljobUrl;

    @Value("${shopjob.executorId}")
    private String executorId;

    /**
     *
     * @param jobType 执行器名字
     * @param parameter 参数
     * @param cron 表达式
     * @param userId 用户di
     * @param name 名字
     * @return
     */
    public Integer sendCronToJob(String jobType, String parameter, String cron, Integer userId, String name) {
        //这个可以共用
        String url = xxljobUrl + "/jobinfo/addByShopwebSupport";
        XxlJobInfoBo job = new XxlJobInfoBo();
        job.setJobGroup(Integer.parseInt(executorId));
        job.setJobDesc(name);
        job.setExecutorRouteStrategy("ROUND");
        job.setJobCron(cron);
        job.setGlueType("BEAN");
        job.setExecutorHandler(jobType);
        job.setExecutorBlockStrategy("SERIAL_EXECUTION");
        job.setExecutorTimeout(0);
        job.setExecutorFailRetryCount(1);
        job.setAuthor(userId.toString());
        job.setAlarmEmail("huangsihuai@ovopark.com");
        job.setExecutorParam(parameter);
        job.setGlueRemark("陈列中心");

        logger.info("添加陈列中心 请求参数:" + JSON.toJSONString(job));
        String result = HttpUtils.sendPost(url, null,JSON.toJSONString(job));

        logger.info("调用定时任务返回结果:" + result);
        Map<String, Object> maps = JSONObject.parseObject(result);

        if (maps == null || maps.get("content") == null) {
            throw new SysErrorException(ResultCode.XXL_JOB_ERROR);
        }

        int jobId = Integer.parseInt(maps.get("content").toString());

        return jobId;
    }


    public String getXxljobUrl() {
        return xxljobUrl;
    }

    public void setXxljobUrl(String xxljobUrl) {
        this.xxljobUrl = xxljobUrl;
    }
}

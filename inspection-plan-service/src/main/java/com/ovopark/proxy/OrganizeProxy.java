package com.ovopark.proxy;

import com.alibaba.fastjson.JSON;
import com.ovopark.api.DepartmentApi;
import com.ovopark.context.HttpContext;
import com.ovopark.expection.ResultCode;
import com.ovopark.expection.SysErrorException;
import com.ovopark.model.resp.organize.OrganzieUserResp;
import com.ovopark.pojo.BaseResult;
import com.ovopark.pojo.IdAndNameVo;
import com.ovopark.utils.OkHttp3Util;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Classname OrganizeProxy
 * @Description TODO
 * @Date 2021/5/12 上午12:31
 * @Created by liuhao
 */
@Service
public class OrganizeProxy {

    private static Logger logger = LoggerFactory.getLogger(OrganizeProxy.class);


    @Value("${organize.url}")
    private String organizeUrl;


    public List<Integer> getOrganzieUserIdList(List<Integer> organizeIdList,Integer groupId,Integer tokenType){

        Map<String, Object> map = new HashMap<>();
        map.put("groupId",groupId);
        map.put("organizedIds",organizeIdList);

        logger.info("请求组织架构:" + JSON.toJSONString(map) + "tokenType" + tokenType + "token值" + HttpContext.getContextInfoToken());
//        String result = OkHttpUtils.sendPostToOtherServerWithAuth(jpushServerUrl, map, HttpContext.getContextInfoToken(),tokenType);

        String result = OkHttp3Util.doPost(organizeUrl, map, HttpContext.getContextInfoToken(), tokenType);

        if(result==null){
            throw new SysErrorException(ResultCode.ORGANZIE_PROXY_ERROR);
        }

        OrganzieUserResp organzieUserResp = JSON.parseObject(result, OrganzieUserResp.class);

        if(organzieUserResp==null || organzieUserResp.isError()){
            throw new SysErrorException(ResultCode.ORGANZIE_PROXY_ERROR);
        }

        List<Integer> userIdList = organzieUserResp.getData();

        if(CollectionUtils.isEmpty(userIdList)){
            return  new ArrayList<>();
        }

        return userIdList;
    }

}

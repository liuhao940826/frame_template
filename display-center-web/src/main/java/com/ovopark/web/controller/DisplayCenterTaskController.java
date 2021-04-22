package com.ovopark.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovopark.annotation.AutoCache;
import com.ovopark.annotation.DelCache;
import com.ovopark.constants.CacheKeyConsts;
import com.ovopark.context.HttpContext;
import com.ovopark.mapper.TestMapper;
import com.ovopark.model.req.DisplayCenterTaskAddReq;
import com.ovopark.model.resp.JsonNewResult;
import com.ovopark.po.Depart;
import com.ovopark.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Classname DisplayCenterTaskController
 * @Description TODO
 * @Date 2021/3/12 下午5:19
 * @Created by liuhao
 */
@Controller
@RequestMapping("/task")
public class DisplayCenterTaskController {

    private static Logger log = LoggerFactory.getLogger(DisplayCenterTaskController.class);

    @RequestMapping(value="/add")
    @ResponseBody
    public JsonNewResult<Void> testRedisTemplate(@RequestBody  DisplayCenterTaskAddReq req) {


        return null;
    }



}

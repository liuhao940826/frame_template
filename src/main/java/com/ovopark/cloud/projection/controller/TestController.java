package com.ovopark.cloud.projection.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovopark.cloud.projection.mapper.TestMapper;
import com.ovopark.cloud.projection.model.po.Depart;
import com.ovopark.cloud.projection.model.resp.JsonNewResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2021/3/12 下午5:19
 * @Created by liuhao
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private static Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    TestMapper testMapper;

    /**
     * 删除草稿箱的任务
     * @return
     */
    @RequestMapping(value="/test")
    @ResponseBody
    public JsonNewResult<?> test(@RequestParam("id")Integer id){

        log.trace("======trace");
        log.debug("======debug");
        log.info("======info");
        log.warn("======warn");
        log.error("======error");


        PageInfo<Depart> pageInfo = null;

        PageHelper.startPage(1,2);

        List<Depart> test = testMapper.test();
        pageInfo = new PageInfo<Depart>(test);

        return JsonNewResult.success(pageInfo);
    }

}

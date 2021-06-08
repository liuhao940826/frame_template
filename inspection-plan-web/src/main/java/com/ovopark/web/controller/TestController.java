package com.ovopark.web.controller;

import com.ovopark.model.req.TestEsReq;
import com.ovopark.model.resp.JsonNewResult;
import com.ovopark.utils.EsHighClientUtls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2021/6/8 下午1:55
 * @Created by liuhao
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private EsHighClientUtls esUtils;


    @RequestMapping(value="/testEs")
    @ResponseBody
    public JsonNewResult<String> testEs(@RequestBody TestEsReq req){

        try {
//            esUtils.createIndex(req.getIndex(), req.getType(), null, req.getJsonStr());


            esUtils.addDocByJson(req.getIndex(),req.getType(),null,req.getJsonStr());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return JsonNewResult.success();
    }

}

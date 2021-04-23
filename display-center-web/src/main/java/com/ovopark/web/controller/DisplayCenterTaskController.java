package com.ovopark.web.controller;

import com.ovopark.context.HttpContext;
import com.ovopark.expection.ResultCode;
import com.ovopark.expection.Validation;
import com.ovopark.mapper.TestMapper;
import com.ovopark.model.login.Users;
import com.ovopark.model.page.Page;
import com.ovopark.model.req.DisplayCenterTaskAddExpandOuterReq;
import com.ovopark.model.req.DisplayCenterTaskAddReq;
import com.ovopark.model.req.DisplayCenterTaskAppListReq;
import com.ovopark.model.resp.DisplayCenterTaskAppListResp;
import com.ovopark.model.resp.JsonNewResult;
import com.ovopark.po.Depart;
import com.ovopark.service.DisplayCenterTaskService;
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

    private static Logger logger = LoggerFactory.getLogger(DisplayCenterTaskController.class);

    @Autowired
    DisplayCenterTaskService displayCenterTaskService;


    @RequestMapping(value="/app/list")
    @ResponseBody
    public JsonNewResult<Page<DisplayCenterTaskAppListResp>> appList(@RequestBody DisplayCenterTaskAppListReq req){

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .isValidThrowException();

        return displayCenterTaskService.appList(req,user);
    }



    @RequestMapping(value="/add")
    @ResponseBody
    public JsonNewResult<Void> add(@RequestBody  DisplayCenterTaskAddReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getOperatorId()==null  , ResultCode.PARAM_ERROR_NAME,"operatorId")
                .addError(req.getAuditId()==null  , ResultCode.PARAM_ERROR_NAME,"auditId")
                .addError(req.getDeptId()==null  , ResultCode.PARAM_ERROR_NAME,"deptId")
                .isValidThrowException();

        return displayCenterTaskService.add(req,user);

    }


    @RequestMapping(value="/addExpland")
    @ResponseBody
    public JsonNewResult<Void> addExpland(@RequestBody DisplayCenterTaskAddExpandOuterReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getTaskId()==null  , ResultCode.PARAM_ERROR_NAME,"taskId")
                .isValidThrowException();

        return displayCenterTaskService.addExpland(req,user);

    }




}

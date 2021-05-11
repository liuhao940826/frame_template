package com.ovopark.web.controller;

import com.ovopark.context.HttpContext;
import com.ovopark.expection.ResultCode;
import com.ovopark.expection.Validation;
import com.ovopark.model.login.Users;
import com.ovopark.model.req.DisplayCenterTaskAddReq;
import com.ovopark.model.req.InspectionPlanTaskAddReq;
import com.ovopark.model.resp.JsonNewResult;
import com.ovopark.service.InspectionTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Classname InspectionTaskController
 * @Description TODO
 * @Date 2021/5/7 下午4:11
 * @Created by liuhao
 */
@Controller
@RequestMapping("/task")
public class InspectionTaskController {

    private static Logger logger = LoggerFactory.getLogger(InspectionTaskController.class);

    @Autowired
    InspectionTaskService inspectionTaskService;


    @RequestMapping(value="/add")
    @ResponseBody
    public JsonNewResult<Void> add(@RequestBody InspectionPlanTaskAddReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getAuditId()==null  , ResultCode.PARAM_ERROR_NAME,"auditId")
                .addError(CollectionUtils.isEmpty(req.getInspectionExpandList()),ResultCode.PARAM_ERROR_NAME,"inspectionExpandList")
                .isValidThrowException();

        return inspectionTaskService.add(req,user);
    }



    @RequestMapping(value="/expire")
    @ResponseBody
    public JsonNewResult<Void> expire(@RequestBody InspectionPlanTaskAddReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getAuditId()==null  , ResultCode.PARAM_ERROR_NAME,"auditId")
                .addError(CollectionUtils.isEmpty(req.getInspectionExpandList()),ResultCode.PARAM_ERROR_NAME,"inspectionExpandList")
                .isValidThrowException();

        return inspectionTaskService.add(req,user);
    }



}

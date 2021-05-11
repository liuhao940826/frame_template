package com.ovopark.web.controller;

import com.ovopark.context.HttpContext;
import com.ovopark.expection.ResultCode;
import com.ovopark.expection.Validation;
import com.ovopark.model.login.Users;
import com.ovopark.model.req.InspectionCategoryAddReq;
import com.ovopark.model.req.InspectionCategoryDeleteReq;
import com.ovopark.model.req.InspectionCategoryUpdateReq;
import com.ovopark.model.req.InspectionPlanTaskAddReq;
import com.ovopark.model.resp.JsonNewResult;
import com.ovopark.service.InspectionCategoryService;
import com.ovopark.service.InspectionTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
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
@RequestMapping("/category")
public class InspectionCategoryController {

    private static Logger logger = LoggerFactory.getLogger(InspectionCategoryController.class);

    @Autowired
    InspectionCategoryService inspectionCategoryService;


    @RequestMapping(value="/add")
    @ResponseBody
    public JsonNewResult<Void> add(@RequestBody InspectionCategoryAddReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(StringUtils.isEmpty(req.getName()), ResultCode.PARAM_ERROR_NAME,"name")
                .isValidThrowException();

        return inspectionCategoryService.add(req,user);
    }


    @RequestMapping(value="/update")
    @ResponseBody
    public JsonNewResult<Void> update(@RequestBody InspectionCategoryUpdateReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(StringUtils.isEmpty(req.getName()), ResultCode.PARAM_ERROR_NAME,"name")
                .addError(req.getId()==null, ResultCode.PARAM_ERROR_NAME,"id")
                .isValidThrowException();

        return inspectionCategoryService.update(req,user);
    }

    @RequestMapping(value="/delete")
    @ResponseBody
    public JsonNewResult<Void> delete(@RequestBody InspectionCategoryDeleteReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getId()==null, ResultCode.PARAM_ERROR_NAME,"id")
                .isValidThrowException();

        return inspectionCategoryService.delete(req,user);
    }



}

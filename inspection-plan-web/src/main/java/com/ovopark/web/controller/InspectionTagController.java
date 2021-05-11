package com.ovopark.web.controller;

import com.ovopark.context.HttpContext;
import com.ovopark.expection.ResultCode;
import com.ovopark.expection.Validation;
import com.ovopark.model.login.Users;
import com.ovopark.model.req.*;
import com.ovopark.model.resp.InspectionTagCategoryListResp;
import com.ovopark.model.resp.JsonNewResult;
import com.ovopark.service.InspectionTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Classname InspectionTaskController
 * @Description TODO
 * @Date 2021/5/7 下午4:11
 * @Created by liuhao
 */
@Controller
@RequestMapping("/tag")
public class InspectionTagController {

    private static Logger logger = LoggerFactory.getLogger(InspectionTagController.class);

    @Autowired
    InspectionTagService inspectionTagService;


    @RequestMapping(value="/add")
    @ResponseBody
    public JsonNewResult<Void> add(@RequestBody InspectionTagAddReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getCategoryId()==null  , ResultCode.PARAM_ERROR_NAME,"categoryId")
                .addError(StringUtils.isEmpty(req.getName()), ResultCode.PARAM_ERROR_NAME,"name")
                .isValidThrowException();

        return inspectionTagService.add(req,user);
    }


    @RequestMapping(value="/update")
    @ResponseBody
    public JsonNewResult<Void> update(@RequestBody InspectionTagUpdateReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getCategoryId()==null  , ResultCode.PARAM_ERROR_NAME,"categoryId")
                .addError(StringUtils.isEmpty(req.getName()), ResultCode.PARAM_ERROR_NAME,"name")
                .addError(req.getId()==null, ResultCode.PARAM_ERROR_NAME,"id")
                .isValidThrowException();

        return inspectionTagService.update(req,user);
    }


    @RequestMapping(value="/delete")
    @ResponseBody
    public JsonNewResult<Void> delete(@RequestBody InspectionTagDeleteReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getId()==null, ResultCode.PARAM_ERROR_NAME,"id")
                .isValidThrowException();

        return inspectionTagService.delete(req,user);
    }


    @RequestMapping(value="/list")
    @ResponseBody
    public JsonNewResult<List<InspectionTagCategoryListResp>> list(@RequestBody InspectionTagListReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getGroupId()==null, ResultCode.PARAM_ERROR_NAME,"groupId")
                .isValidThrowException();

        return inspectionTagService.list(req,user);
    }


}

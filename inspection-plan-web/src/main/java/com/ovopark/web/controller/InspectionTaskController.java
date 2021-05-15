package com.ovopark.web.controller;

import com.ovopark.context.HttpContext;
import com.ovopark.expection.ResultCode;
import com.ovopark.expection.Validation;
import com.ovopark.model.login.Users;
import com.ovopark.model.page.Page;
import com.ovopark.model.req.*;
import com.ovopark.model.resp.*;
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


    @RequestMapping(value="/app/list")
    @ResponseBody
    public JsonNewResult<Page<InspectionPlanTaskAppListResp>> appList(@RequestBody InspectionPlanTaskAppListReq req){

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .isValidThrowException();

        return inspectionTaskService.appList(req,user);
    }

    @RequestMapping(value="/web/list")
    @ResponseBody
    public JsonNewResult<Page<InspectionPlanTaskWebListResp>> webList(@RequestBody InspectionPlanTaskWebListReq req){

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .isValidThrowException();

        return inspectionTaskService.webList(req,user);
    }





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

    @RequestMapping(value="/detail")
    @ResponseBody
    public JsonNewResult<InspectionPlanTaskDetailResp> detail(@RequestBody InspectionPlanTaskDetailReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getId()==null  , ResultCode.PARAM_ERROR_NAME,"id")
                .isValidThrowException();

        return inspectionTaskService.detail(req,user);
    }


    @RequestMapping(value="/expandList")
    @ResponseBody
    public JsonNewResult<Page<InspectionPlanTaskExpandListResp>> expandList(@RequestBody InspectionPlanTaskDetailReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getId()==null  , ResultCode.PARAM_ERROR_NAME,"id")
                .isValidThrowException();

        return inspectionTaskService.expandList(req,user);
    }




    @RequestMapping(value="/udpate")
    @ResponseBody
    public JsonNewResult<Void> udpate(@RequestBody InspectionPlanTaskUpdateReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getId()==null  , ResultCode.PARAM_ERROR_NAME,"id")
                .isValidThrowException();

        return inspectionTaskService.udpate(req,user);
    }



    @RequestMapping(value="/delete")
    @ResponseBody
    public JsonNewResult<Void> delete(@RequestBody InspectionPlanTaskDeleteReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getId()==null  , ResultCode.PARAM_ERROR_NAME,"id")
                .isValidThrowException();

        return inspectionTaskService.delete(req,user);
    }

    @RequestMapping(value="/urged")
    @ResponseBody
    public JsonNewResult<Void> urged(@RequestBody InspectionPlanTaskUrgedReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getId()==null  , ResultCode.PARAM_ERROR_NAME,"id")
                .isValidThrowException();

        return inspectionTaskService.urged(req,user);
    }

    @RequestMapping(value="/audit")
    @ResponseBody
    public JsonNewResult<Void> audit(@RequestBody InspectionPlanTaskAuditReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getId()==null  , ResultCode.PARAM_ERROR_NAME,"id")
                .isValidThrowException();

        return inspectionTaskService.audit(req,user);
    }



    @RequestMapping(value="/expire")
    @ResponseBody
    public JsonNewResult<Void> expire(@RequestBody InspectionPlanTaskExpireReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
//                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getId()==null  , ResultCode.PARAM_ERROR_NAME,"id")
                .isValidThrowException();

        return inspectionTaskService.expire(req,user);
    }

    /**
     * 可能需要加白名单的机制
     * @param req
     * @return
     */
    @RequestMapping(value="/callBack")
    @ResponseBody
    public JsonNewResult<Void> callBack(@RequestBody InspectionPlanTaskCallBackReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getTaskId()==null  , ResultCode.PARAM_ERROR_NAME,"taskId")
                .addError(req.getExpandId()==null  , ResultCode.PARAM_ERROR_NAME,"expandId")
                .isValidThrowException();

        return inspectionTaskService.callBack(req,user);
    }


    @RequestMapping(value="/web/expandList")
    @ResponseBody
    public JsonNewResult<InspectionPlanTaskWebExpandListResp> webExpandList(@RequestBody InspectionPlanWebExpandReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getTaskId()==null  , ResultCode.PARAM_ERROR_NAME,"taskId")
                .isValidThrowException();

        return inspectionTaskService.webExpandList(req,user);
    }







}

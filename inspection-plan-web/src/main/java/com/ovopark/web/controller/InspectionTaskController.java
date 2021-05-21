package com.ovopark.web.controller;

import com.ovopark.constants.CommonConstants;
import com.ovopark.context.HttpContext;
import com.ovopark.expection.ResultCode;
import com.ovopark.expection.Validation;
import com.ovopark.model.enums.InpectionPlanWebListTitleEnum;
import com.ovopark.model.enums.InspectionTaskStatusEnum;
import com.ovopark.model.login.Users;
import com.ovopark.model.page.Page;
import com.ovopark.model.req.*;
import com.ovopark.model.resp.*;
import com.ovopark.service.InspectionTaskService;
import com.ovopark.utils.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


    @RequestMapping(value="/app/log/list")
    @ResponseBody
    public JsonNewResult<List<InspectionPlanTaskAppLogListResp>> appLog(@RequestBody InspectionPlanTaskAppLogListReq req){

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .isValidThrowException();

        return inspectionTaskService.appLogList(req,user);
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


    @RequestMapping(value="/web/export/list")
    @ResponseBody
    public JsonNewResult<?> exportWebList(HttpServletResponse response, @RequestBody InspectionPlanTaskWebListReq req){

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .isValidThrowException();

        JsonNewResult<Page<InspectionPlanTaskWebListResp>> pageJsonNewResult = inspectionTaskService.webList(req, user);

        Page<InspectionPlanTaskWebListResp> page = pageJsonNewResult.getData().get("data");

        List<String> title = InpectionPlanWebListTitleEnum.getTitle();

        List<List<String>> dataList =assembleRowList(page.getContent());

        JsonNewResult result = new JsonNewResult();

        boolean flag =false;
        try {
            flag = ExcelUtils.createWorkBook(response,  null, CommonConstants.TASK_EXCEL_FILE_NAME, title, dataList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(flag){
            result.setIsError(false);
            result.setResult(JsonNewResult.RESULT_SUCCESS);
            return result;
        }

        result.setIsError(true);
        result.setResult(JsonNewResult.RESULT_EXCEPTION);

        return result;



    }

    private List<List<String>> assembleRowList(List<InspectionPlanTaskWebListResp> list) {

        List<List<String>> dataList = new ArrayList();

        for (InspectionPlanTaskWebListResp resp : list) {
            //行数据
            List<String> rowData = new ArrayList<>();
            //名字
            rowData.add(resp.getName());
            //操作人
            rowData.add(resp.getOperatorName());
            //标签名字
            List<String> tagNameList = new ArrayList<>();

            for (InspectionPlanTagDetailResp inspectionPlanTagDetailResp : resp.getTagList()) {
                tagNameList.add(inspectionPlanTagDetailResp.getTagName());
            }
            //标签名字
            rowData.add(StringUtils.join(tagNameList,","));

            //门店数量
            rowData.add(String.valueOf(resp.getDeptNum()));
            //开始时间
            rowData.add(resp.getStartTimeStr());
            //结束时间
            rowData.add(resp.getEndTimeStr());
            //审核人
            rowData.add(resp.getAuditName());

            InspectionTaskStatusEnum statusEnum = InspectionTaskStatusEnum.format(resp.getStatus());
            //状态
            rowData.add(statusEnum==null?"":statusEnum.getDesc());
            //完成度
            rowData.add(resp.getCompletePercent().toString());
            //备注
            rowData.add(resp.getRemark());
            //历史记录
//            List<String> logContentList = new ArrayList<>();
//
//            for (InspectionPlanTaskAppLogListResp logListResp : resp.getLogList()) {
//                logContentList.add(logListResp.getContent());
//            }
//
//            String logContentStr = String.join("\n", logContentList);
//
//            rowData.add(logContentStr);

            dataList.add(rowData);
        }
        return dataList;
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
    public JsonNewResult<Page<InspectionPlanTaskExpandListResp>> expandList(@RequestBody InspectionPlanAppExpandReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getId()==null  , ResultCode.PARAM_ERROR_NAME,"id")
                .isValidThrowException();

        return inspectionTaskService.expandList(req,user);
    }

    @RequestMapping(value="/expandList/count")
    @ResponseBody
    public JsonNewResult<InspectionPlanTaskExpandListCountResp> expandCountList(@RequestBody InspectionPlanAppExpandReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getId()==null  , ResultCode.PARAM_ERROR_NAME,"id")
                .isValidThrowException();

        return inspectionTaskService.expandCountList(req,user);
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



    @RequestMapping(value="/batchDelete")
    @ResponseBody
    public JsonNewResult<Void> batchDelete(@RequestBody InspectionPlanTaskDeleteReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(CollectionUtils.isEmpty(req.getIdList())  , ResultCode.PARAM_ERROR_NAME,"idList")
                .isValidThrowException();

        return inspectionTaskService.batchDelete(req,user);
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

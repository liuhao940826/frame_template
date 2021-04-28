package com.ovopark.web.controller;

import com.ovopark.constants.CommonConstants;
import com.ovopark.constants.StringKeyConstants;
import com.ovopark.context.HttpContext;
import com.ovopark.expection.ResultCode;
import com.ovopark.expection.Validation;
import com.ovopark.model.enums.DisplayCenterTaskStatusEnum;
import com.ovopark.model.enums.DisplayWebListTitleEnum;
import com.ovopark.model.login.Users;
import com.ovopark.model.page.Page;
import com.ovopark.model.req.*;
import com.ovopark.model.resp.DisplayCenterTaskAppListResp;
import com.ovopark.model.resp.DisplayCenterTaskDetailResp;
import com.ovopark.model.resp.DisplayCenterTaskWebListResp;
import com.ovopark.model.resp.JsonNewResult;
import com.ovopark.service.DisplayCenterTaskService;
import com.ovopark.utils.DateUtils;
import com.ovopark.utils.ExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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

    @Value("${spring.profiles.active}")
    private String env;


    @RequestMapping(value="/env")
    @ResponseBody
    public JsonNewResult<Void> testEnv(@RequestBody DisplayCenterTaskDetailReq req){

        System.out.println("env:"+env);

        return JsonNewResult.success();
    }


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
                .addError(req.getAuditId()==null  , ResultCode.PARAM_ERROR_NAME,"auditId")
                .addError(req.getDeptId()==null  , ResultCode.PARAM_ERROR_NAME,"deptId")
                .isValidThrowException();

        return displayCenterTaskService.add(req,user);

    }


    @RequestMapping(value="/carryOut")
    @ResponseBody
    public JsonNewResult<Void> carryOut(@RequestBody DisplayCenterTaskAddExpandOuterReq req) {

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getTaskId()==null  , ResultCode.PARAM_ERROR_NAME,"taskId")
                .isValidThrowException();

        return displayCenterTaskService.carryOut(req,user);

    }

    @RequestMapping(value="/audit")
    @ResponseBody
    public JsonNewResult<Void> audit(@RequestBody DisplayCenterTaskAuditExpandOuterReq req){

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getTaskId()==null  , ResultCode.PARAM_ERROR_NAME,"taskId")
                .isValidThrowException();

        return displayCenterTaskService.audit(req,user);

    }


    @RequestMapping(value="/detail")
    @ResponseBody
    public JsonNewResult<DisplayCenterTaskDetailResp> detail(@RequestBody DisplayCenterTaskDetailReq req){

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .addError(req.getTaskId()==null  , ResultCode.PARAM_ERROR_NAME,"taskId")
                .isValidThrowException();

        return displayCenterTaskService.detail(req,user);

    }


    @RequestMapping(value="/web/list")
    @ResponseBody
    public JsonNewResult<Page<DisplayCenterTaskWebListResp>> webList(@RequestBody DisplayCenterTaskWebListReq req){

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .isValidThrowException();

        return displayCenterTaskService.DisplayCenterTaskWebList(req,user);

    }

    @RequestMapping(value="/web/export/list")
    @ResponseBody
    public JsonNewResult<?> exportWebList(HttpServletResponse response, @RequestBody DisplayCenterTaskWebListReq req){

        Users user = HttpContext.getContextInfoUser();

        Validation.newValidation()
                .addError(null == user, ResultCode.RESULT_INVALID_TOKEN)
                .isValidThrowException();

        JsonNewResult<Page<DisplayCenterTaskWebListResp>> pageJsonNewResult = displayCenterTaskService.DisplayCenterTaskWebList(req, user);

        Page<DisplayCenterTaskWebListResp> page = pageJsonNewResult.getData().get("data");

        List<String> title = DisplayWebListTitleEnum.getTitle();

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

    private List<List<String>> assembleRowList(List<DisplayCenterTaskWebListResp> list) {

        List<List<String>> dataList = new ArrayList();


        for (DisplayCenterTaskWebListResp resp : list) {
            List<String> rowData = new ArrayList<>();
            //名字
            rowData.add(resp.getName());
            //门店名字
            rowData.add(resp.getDeptName());
            //状态
            rowData.add(DisplayCenterTaskStatusEnum.formatDesc(resp.getStatus()));
            //检查项
            rowData.add(String.valueOf(resp.getItemNum()));
            //得分
            rowData.add(String.format(StringKeyConstants.SCORE,resp.getActualScore(),resp.getTotalScore()));
            //得分率
            rowData.add(resp.getScorePercent());
            //执行人
            rowData.add(resp.getOperatorName());
            //审核人
            rowData.add(resp.getAuditName());
            //创建时间
            rowData.add(DateUtils.getDateStr(resp.getCreateTime(),DateUtils.FORMAT_LONG_SLASH));

            dataList.add(rowData);
        }

        return dataList;
    }


    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }
}

package com.ovopark.service.Impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.ovopark.constants.CommonConstants;
import com.ovopark.constants.MessageConstant;
import com.ovopark.constants.ProxyConstants;
import com.ovopark.expection.ResultCode;
import com.ovopark.expection.SysErrorException;
import com.ovopark.mapper.DisplayCenterExpandMapper;
import com.ovopark.mapper.DisplayCenterTaskMapper;
import com.ovopark.model.base.EntityBase;
import com.ovopark.model.enums.DefaultEnum;
import com.ovopark.model.enums.DisplayCenterTaskExpandStatusEnum;
import com.ovopark.model.enums.DisplayCenterTaskStatusEnum;
import com.ovopark.model.enums.DisplayMainTypeEnum;
import com.ovopark.model.login.Users;
import com.ovopark.model.page.Page;
import com.ovopark.model.req.*;
import com.ovopark.model.resp.*;
import com.ovopark.po.DisplayCenterExpand;
import com.ovopark.po.DisplayCenterTask;
import com.ovopark.proxy.MessageProxy;
import com.ovopark.proxy.XxlJobProxy;
import com.ovopark.service.DisplayCenterTaskService;
import com.ovopark.utils.BigDecimalUtils;
import com.ovopark.utils.ClazzConverterUtils;
import com.ovopark.utils.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Classname DisplayCenterTaskServiceImpl
 * @Description TODO
 * @Date 2021/4/23 上午11:07
 * @Created by liuhao
 */

@Service
public class DisplayCenterTaskServiceImpl implements DisplayCenterTaskService {

    @Autowired
    DisplayCenterTaskMapper displayCenterTaskMapper;

    @Autowired
    DisplayCenterExpandMapper displayCenterExpandMapper;

    @Autowired
    XxlJobProxy xxlJobProxy;

    @Autowired
    MessageProxy messageProxy;


    @Override
    public JsonNewResult<Page<DisplayCenterTaskAppListResp>> appList(DisplayCenterTaskAppListReq req, Users user) {

        Page<DisplayCenterTask> pageTemp = new Page<DisplayCenterTask>();
        pageTemp.setPageNumber(req.getPageNo());
        pageTemp.setPageSize(req.getPageSize());

        //我审核
        List<DisplayCenterTask> list = displayCenterTaskMapper.queryAppListByRelateTypeByPage(pageTemp, req.getReleateType(), user.getId(), user.getGroupId(),req.getDeptName(),req.getName(),req.getStartTime(),req.getEndTime());

        //转换集合
        List<DisplayCenterTaskAppListResp> result = ClazzConverterUtils.converterClass(list, DisplayCenterTaskAppListResp.class);
        //处理标记
        for (DisplayCenterTaskAppListResp resp : result) {

            if (resp.getOperatorId().equals(user.getId()) && DisplayCenterTaskStatusEnum.WAIT.getCode().equals(resp.getStatus())){
                resp.setIsOperator(DefaultEnum.DEFAULT_TRUE.getCode());
            }

            if (resp.getAuditId().equals(user.getId()) && DisplayCenterTaskStatusEnum.AUDIT.getCode().equals(resp.getStatus())){
                resp.setIsAudit(DefaultEnum.DEFAULT_TRUE.getCode());
            }
        }


        Page<DisplayCenterTaskAppListResp> pageResult = new Page<DisplayCenterTaskAppListResp>();

        BeanUtils.copyProperties(pageTemp, pageResult);

        pageResult.setContent(result);

        return JsonNewResult.success(pageResult);
    }


    @Override
    public JsonNewResult<Page<DisplayCenterTaskWebListResp>> DisplayCenterTaskWebList(DisplayCenterTaskWebListReq req, Users user) {

        Page<DisplayCenterTask> pageTemp = new Page<DisplayCenterTask>();
        pageTemp.setPageNumber(req.getPageNo());
        pageTemp.setPageSize(req.getPageSize());

        List<DisplayCenterTask> list = displayCenterTaskMapper.queryWebListByPage(pageTemp,user.getGroupId(),req.getName(),
                req.getDeptName(),req.getStatus(),req.getOperatorName(),req.getAuditName(),req.getStartTime(),req.getEndTime());

        if(CollectionUtils.isEmpty(list)){
            return JsonNewResult.success(new Page<DisplayCenterTaskWebListResp>(req.getPageNo(),req.getPageSize()));
        }
        //id的集合
        List<Integer> taskIdList = list.stream().map(DisplayCenterTask::getId).collect(Collectors.toList());
        //明细
        List<DisplayCenterExpand> expandList = displayCenterExpandMapper.queryExpandByTaskIdList(taskIdList);
        //明细map
        Map<Integer,List<DisplayCenterExpand>> expandMap =new HashMap<>();

        if (!CollectionUtils.isEmpty(expandList)) {
            expandMap = expandList.stream().collect(Collectors.toMap(DisplayCenterExpand::getTaskId, value -> Lists.newArrayList(value),
                    (List<DisplayCenterExpand> newValueList, List<DisplayCenterExpand> oldValueList) -> { oldValueList.addAll(newValueList);
                        return oldValueList;
                    }));
        }

        //处理结果
        List<DisplayCenterTaskWebListResp> result = ClazzConverterUtils.converterClass(list, DisplayCenterTaskWebListResp.class);

        for (DisplayCenterTaskWebListResp resp : result) {

            BigDecimal actualScore = resp.getActualScore();
            BigDecimal totalScore = resp.getTotalScore();

            String percent = BigDecimalUtils.calculateBigDecimalPercentStr(actualScore, totalScore, BigDecimalUtils.DEFAULT_SCALE_FOUR, BigDecimal.ROUND_HALF_UP);
            resp.setScorePercent(percent);
            //检查项
            List<DisplayCenterExpand> taskExpandList = expandMap.get(resp.getId());

            if(CollectionUtils.isEmpty(taskExpandList)){
                resp.setItemNum(CommonConstants.ZERO);
            }else {
                resp.setItemNum(taskExpandList.size());
            }

            if (resp.getAuditId().equals(user.getId()) && DisplayCenterTaskStatusEnum.AUDIT.getCode().equals(resp.getStatus())){
                resp.setIsAudit(DefaultEnum.DEFAULT_TRUE.getCode());
            }
        }

        Page<DisplayCenterTaskWebListResp> pageResult = new Page<DisplayCenterTaskWebListResp>();

        BeanUtils.copyProperties(pageTemp, pageResult);

        pageResult.setContent(result);

        return JsonNewResult.success(pageResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonNewResult<Void> add(DisplayCenterTaskAddReq req, Users user) {
        //copy传递值
        DisplayCenterTask po = ClazzConverterUtils.converterClass(req, DisplayCenterTask.class);
        //赋值属性
        assembleDisplayCenterTask(po, user);

        displayCenterTaskMapper.insertSelective(po);

        return JsonNewResult.success();
    }

    /**
     * 赋值公共字段
     *
     * @param po
     */
    private void assembleDisplayCenterTask(DisplayCenterTask po, Users user) {

        po.setStatus(DisplayCenterTaskStatusEnum.WAIT.getCode());
        po.setGroupId(user.getGroupId());
        po.setOperatorId(user.getId());
        po.setOperatorName(user.getShowName());
        //赋值公共属性
        EntityBase.setCreateParams(po, user);
    }

    /**
     * 添加执行的明细内容
     *
     * @param req
     * @param user
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonNewResult<Void> carryOut(DisplayCenterTaskAddExpandOuterReq req, Users user) {

        Integer taskId = req.getTaskId();

        DisplayCenterTask task  =displayCenterTaskMapper.selectByPrimaryKey(req.getTaskId());
        //任务不存在
        if(task == null){
            throw  new SysErrorException(ResultCode.DISPLAY_TASK_NULL);
        }
        //状态不是待执行
        if(!DisplayCenterTaskStatusEnum.WAIT.getCode().equals(task.getStatus()) ){
            throw  new SysErrorException(ResultCode.DISPLAY_TASK_STATUS_UNWAIT);
        }
        //非执行人
        if(!user.getId().equals(task.getOperatorId())){
            throw  new SysErrorException(ResultCode.DISPLAY_TASK_NON_OPERATOR);
        }

        List<DisplayCenterTaskAddExpandReq> list = req.getList();

        List<DisplayCenterExpand> insertList = new ArrayList<>();
        //总分长度乘以每项分数
        BigDecimal totalScore  = CommonConstants.EACH_SCORE.multiply(new BigDecimal(list.size()));

        for (DisplayCenterTaskAddExpandReq expandReq : list) {
            //添加明细

            DisplayCenterExpand displayCenterExpand = new DisplayCenterExpand().setTaskId(taskId).setTitle(expandReq.getTitle()).setPreviousUrl(expandReq.getPreviousUrl())
                    .setAfterUrl(expandReq.getAfterUrl()).setGroupId(task.getGroupId()).setTotalScore(totalScore).setStatus(DisplayCenterTaskExpandStatusEnum.WAIT.getCode());

            //赋值公共属性
            EntityBase.setCreateParams(displayCenterExpand, user);

            insertList.add(displayCenterExpand);

        }
        //批量插入明细内容
        displayCenterExpandMapper.batchSaveDisplayCenterExpand(insertList);
        //更新任务状态
        displayCenterTaskMapper.updateTaskStautsById(taskId,DisplayCenterTaskStatusEnum.AUDIT.getCode(),new Date(),null,null,totalScore);

        //处理自动通过逻辑
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", task.getId());
        //cron表达式 14天后自动通过
        String cron = DateUtils.dateToCron(DateUtils.addDays(task.getCreateTime(), CommonConstants.TWO_WEEKS_DAYS));

        Integer jobId = xxlJobProxy.sendCronToJob(ProxyConstants.XXL_JOB_HANDLE_TYPE_DISPLAY_TASK, JSON.toJSONString(param), cron, user.getId(), String.format(ProxyConstants.REMARK, task.getName()));

        displayCenterTaskMapper.updateJobId(jobId,task.getId());

        return JsonNewResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonNewResult<Void> audit(DisplayCenterTaskAuditExpandOuterReq req, Users user) {

        Integer taskId = req.getTaskId();

        DisplayCenterTask task  =displayCenterTaskMapper.selectByPrimaryKey(req.getTaskId());
        //任务不存在
        if(task == null){
            throw  new SysErrorException(ResultCode.DISPLAY_TASK_NULL);
        }
        //状态不是待审核
        if(!DisplayCenterTaskStatusEnum.AUDIT.getCode().equals(task.getStatus()) ){
            throw  new SysErrorException(ResultCode.DISPLAY_TASK_STATUS_UNAUDIT);
        }

        //非审核人
        if(!user.getId().equals(task.getAuditId())){
            throw  new SysErrorException(ResultCode.DISPLAY_TASK_NON_AUDITOR);
        }
        //审核时间
        Date auditTime = new Date();

        List<DisplayCenterTaskAuditExpandReq> list = req.getList();

        List<DisplayCenterExpand> auditList = new ArrayList<>();

        Boolean isMessageFlag = false;
        BigDecimal actualScore = BigDecimal.ZERO;

        for (DisplayCenterTaskAuditExpandReq expandReq : list) {

            DisplayCenterExpand displayCenterExpand = new DisplayCenterExpand().setId(expandReq.getId()).setActualScore(expandReq.getActualScore()).setStatus(expandReq.getStatus());
            //计算实际得分
            actualScore=  actualScore.add(expandReq.getActualScore());

            auditList.add(displayCenterExpand);

            if(DisplayCenterTaskExpandStatusEnum.REFUSE.getCode().equals(expandReq.getStatus())){
                isMessageFlag= true;
            }

        }

        displayCenterExpandMapper.batchUpdateActualScoreAndStatus(auditList);
        //任务变成通过
        displayCenterTaskMapper.updateTaskStautsById(taskId,DisplayCenterTaskStatusEnum.PASS.getCode(),null,auditTime,actualScore,null);

        if(isMessageFlag){
            //发送消息
            messageProxy.sendWebSocketAndJpush(task.getId(),user.getId(), MessageConstant.DISPLAY_CENTER,MessageConstant.UN_QUALIFIED,user.getGroupId(),
                    MessageConstant.DISPLAY_CENTER_JPUSH_TYPE,task.getId(), DisplayMainTypeEnum.DISPLAY,req.getTokenType());

        }

        return JsonNewResult.success();
    }

    /**
     * 详情
     * @param req
     * @param user
     * @return
     */
    @Override
    public JsonNewResult<DisplayCenterTaskDetailResp> detail(DisplayCenterTaskDetailReq req, Users user) {

        Integer taskId = req.getTaskId();

        DisplayCenterTask task  =displayCenterTaskMapper.selectByPrimaryKey(req.getTaskId());
        //任务不存在
        if(task == null){
            throw  new SysErrorException(ResultCode.DISPLAY_TASK_NULL);
        }

        List<DisplayCenterExpand> list =displayCenterExpandMapper.getDisplayExpandByTaskId(taskId);

        DisplayCenterTaskDetailResp resp = ClazzConverterUtils.converterClass(task, DisplayCenterTaskDetailResp.class);

        List<DisplayCenterTaskDetailExpandResp> expandRespList = ClazzConverterUtils.converterClass(list, DisplayCenterTaskDetailExpandResp.class);

        //检查项数量
        if(!CollectionUtils.isEmpty(list)){
            resp.setItemNum(list.size());
        }else {
            resp.setItemNum(0);
        }

        //合格数量
        Integer qualifiedNum = 0;
        //不合格数量
        Integer unQualifiedNum = 0;

        for (DisplayCenterExpand displayCenterExpand : list) {
            if(DisplayCenterTaskExpandStatusEnum.PASS.getCode().equals(displayCenterExpand.getStatus())){
                qualifiedNum+=1;
            }

            if(DisplayCenterTaskExpandStatusEnum.REFUSE.getCode().equals(displayCenterExpand.getStatus())){
                unQualifiedNum+=1;
            }
        }

        resp.setQualifiedNum(qualifiedNum);
        resp.setUnQualifiedNum(unQualifiedNum);
        resp.setList(expandRespList);

        return JsonNewResult.success(resp);
    }
}

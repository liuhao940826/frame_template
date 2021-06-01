package com.ovopark.service.Impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.ovopark.builder.InspectionTaskBuilder;
import com.ovopark.constants.CommonConstants;
import com.ovopark.constants.LogConstant;
import com.ovopark.constants.MessageConstant;
import com.ovopark.constants.ProxyConstants;
import com.ovopark.expection.ResultCode;
import com.ovopark.expection.SysErrorException;
import com.ovopark.mapper.*;
import com.ovopark.model.base.EntityBase;
import com.ovopark.model.enums.*;
import com.ovopark.model.login.Users;
import com.ovopark.model.page.Page;
import com.ovopark.model.req.*;
import com.ovopark.model.resp.*;
import com.ovopark.po.*;
import com.ovopark.proxy.DepartProxy;
import com.ovopark.proxy.MessageProxy;
import com.ovopark.proxy.OrganizeProxy;
import com.ovopark.proxy.XxlJobProxy;
import com.ovopark.service.InspectionTaskService;
import com.ovopark.utils.BigDecimalUtils;
import com.ovopark.utils.ClazzConverterUtils;
import com.ovopark.utils.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Classname InspectionTaskImpl
 * @Description TODO
 * @Date 2021/5/8 下午3:43
 * @Created by liuhao
 */
@Service
public class InspectionTaskServiceImpl implements InspectionTaskService {

    @Autowired
    InspectionTaskMapper inspectionTaskMapper;

    @Autowired
    InspectionTaskExpandMapper inspectionTaskExpandMapper;

    @Autowired
    InspectionDeptTagMapper inspectionDeptTagMapper;

    @Autowired
    InspectionTagMapper inspectionTagMapper;

    @Autowired
    InspectionAuditReasonMapper inspectionAuditReasonMapper;


    @Autowired
    InspectionOperatorLogMapper inspectionOperatorLogMapper;


    @Autowired
    XxlJobProxy xxlJobProxy;

    @Autowired
    DepartProxy departProxy;

    @Autowired
    MessageProxy messageProxy;

    @Autowired
    OrganizeProxy organizeProxy;


    /**
     *
     * @param req
     * @param user
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonNewResult<Void> add(InspectionPlanTaskAddReq req, Users user) {

        //构建builder
        InspectionTaskBuilder builder = new InspectionTaskBuilder();
        //企业id
        Integer groupId = user.getGroupId();

        //处理明细
        List<InspectionPlanExpandAddReq> inspectionExpandList = req.getInspectionExpandList();

        //构建任务
        InspectionTask task = builder.groupId(groupId).name(req.getName()).operatorId(user.getId()).operatorName(user.getShowName()).auditId(req.getAuditId())
                .auditName(req.getAuditName()).status(InspectionTaskStatusEnum.AUDIT.getCode()).completeExpandCount(0).totalExpandCount(inspectionExpandList.size())
                .startTime(req.getStartTime()).endTime(req.getEndTime()).remark(req.getRemark()).build();

        //赋值公共属性
        EntityBase.setCreateParams(task, user);

        inspectionTaskMapper.insertSelective(task);

        //明细列表
        List<InspectionTaskExpand> expandList = new ArrayList<InspectionTaskExpand>();
        //标签关系列表
        List<InspectionDeptTag> targetTagList = new ArrayList<InspectionDeptTag>();

        for (InspectionPlanExpandAddReq expandReq : inspectionExpandList) {
            //处理明细
            assembleInspectionTaskExpand(expandReq,task.getId(),user,expandList,targetTagList);

        }
        if(!CollectionUtils.isEmpty(expandList)) {
            //明细表保存数据
            inspectionTaskExpandMapper.batchSaveInspectionTaskExpand(expandList);

        }
        //明细标签关联表保存数据

        if(!CollectionUtils.isEmpty(targetTagList)){
            inspectionDeptTagMapper.batchSaveInspectionDeptTag(targetTagList);
        }

        Date endTime = task.getEndTime();
        //cron表达式 截止日期
        String cron = DateUtil.dateToCron(endTime);

        //处理过期
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", task.getId());

        //截止时间回调处理
        Integer jobId = xxlJobProxy.sendCronToJob(ProxyConstants.XXL_JOB_HANDLE_TYPE_INSPECTION_PLAN, JSON.toJSONString(param), cron, user.getId(), String.format(ProxyConstants.REMARK, task.getName()));

        //发送消息
        messageProxy.sendWebSocketAndJpush(task.getAuditId(),user.getId(), MessageConstant.INSPECTION_PLAN_CATEGORY,String.format(MessageConstant.ADD_MESSAGE,user.getShowName(),task.getName()),user.getGroupId(),
                MessageConstant.INSPECTION_JPUSH_TYPE,task.getId(), InspectionPlanMainTypeEnum.INSPECTION,req.getTokenType(),JumpTypeEnum.AUDIT.getCode());


        //更新定时任务id
        inspectionTaskMapper.updateJobIdById(jobId,task.getId());
        //操作日志
        insertLog(user, task.getId(), LogConstant.CREATE,user.getShowName());

        return JsonNewResult.success();

    }

    /**
     * 更新
     * @param req
     * @param user
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonNewResult<Void> udpate(InspectionPlanTaskUpdateReq req, Users user) {
        //获取
        InspectionTask orgTask = inspectionTaskMapper.selectbyPrimaryId(req.getId());

        if (orgTask == null) {
            throw new SysErrorException(ResultCode.INSPECTION_PLAN_TASK_NULL);
        }

        if(orgTask.getUpdateTimes()> CommonConstants.UPDATE_TIMES){
            throw  new SysErrorException(ResultCode.INSPECTION_PLAN_TASK_UPDATE_TIMES_OVER);
        }

        //获取原本的明细, 用来做门店踢出
        List<InspectionTaskExpand> orgExpandList = inspectionTaskExpandMapper.selectExpandListByTaskId(orgTask.getId(), user.getGroupId());
        //明细门店map<expandId,DeptId>
        Map<Integer, Integer> expandIdDeptMap = orgExpandList.stream().collect(Collectors.toMap(InspectionTaskExpand::getId, InspectionTaskExpand::getDeptId, (e1, e2) -> e2));

        //保留的门店id
        List<Integer> retainDeptList = new ArrayList<>();
        //剔除掉保留id的门店
        for (Integer expandId : req.getRetainList()) {
            Integer retainDeptId = expandIdDeptMap.get(expandId);
            if(retainDeptId!=null){
                //需要保留的门店id
                retainDeptList.add(retainDeptId);
            }
        }

        //按照页面的明细新增
        List<InspectionPlanExpandAddReq> inspectionExpandList = req.getInspectionExpandList();
        //新的明细列表
        List<InspectionTaskExpand> expandList = new ArrayList<InspectionTaskExpand>();
        //标签关系列表
        List<InspectionDeptTag> targetTagList = new ArrayList<InspectionDeptTag>();

        for (InspectionPlanExpandAddReq expandReq : inspectionExpandList) {
            //处理明细
            assembleInspectionTaskExpand(expandReq,orgTask.getId(),user,expandList,targetTagList);
        }

        //完成数量
        Integer completeExpandCount =retainDeptList.size();
        //总数量
        Integer totalExpandCount = expandList.size()+retainDeptList.size();

        //判断戒指时间处理对应的定时任务逻辑
        if(!orgTask.getEndTime().equals(req.getEndTime())){
            //停止原本任务
            xxlJobProxy.stopJob(orgTask.getJobId());

            String cron = DateUtil.dateToCron(req.getEndTime());
            //处理过期
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("id", orgTask.getId());
            //任务id
            Integer jobId = xxlJobProxy.sendCronToJob(ProxyConstants.XXL_JOB_HANDLE_TYPE_INSPECTION_PLAN, JSON.toJSONString(param), cron, user.getId(), String.format(ProxyConstants.REMARK, orgTask.getName()));
            //赋值新的jobId
            orgTask.setJobId(jobId);
        }


        //处理百分比
        BigDecimal percent = BigDecimalUtils.calculatePercent(orgTask.getCompleteExpandCount(), totalExpandCount,BigDecimalUtils.DEFAULT_SCALE_FOUR, BigDecimal.ROUND_HALF_UP);

        //状态回到待审核
        orgTask.setUpdateTimes(orgTask.getUpdateTimes()+1).setName(req.getName()).setAuditId(req.getAuditId()).setAuditName(req.getAuditName())
                .setTotalExpandCount(totalExpandCount).setCompletePercent(percent)
                .setStartTime(req.getStartTime()).setEndTime(req.getEndTime()).setRemark(req.getRemark())
                .setStatus(InspectionTaskStatusEnum.AUDIT.getCode());

        //赋值公共属性
        EntityBase.setUpdateParams(orgTask, user);
        //更新主表 包含了jobId
        int isOk  = inspectionTaskMapper.updateByPrimaryId(orgTask);

        //全量删除这个任务的从表和标签表的东西
        inspectionTaskExpandMapper.deleteByTaskIdWithOutExpandList(orgTask.getId(),req.getRetainList());

        inspectionDeptTagMapper.deleteByTaskIdWithOutDeptIdList(orgTask.getId(),retainDeptList);


        if(!CollectionUtils.isEmpty(expandList)){
            //明细表保存数据
            inspectionTaskExpandMapper.batchSaveInspectionTaskExpand(expandList);
        }
        if(!CollectionUtils.isEmpty(targetTagList)) {
            //明细标签关联表保存数据
            inspectionDeptTagMapper.batchSaveInspectionDeptTag(targetTagList);
        }

        //操作日志
        insertLog( user, orgTask.getId(), LogConstant.UPDATE,user.getShowName());

        return JsonNewResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonNewResult<Void> audit(InspectionPlanTaskAuditReq req, Users user) {

        //获取
        InspectionTask orgTask = inspectionTaskMapper.selectbyPrimaryId(req.getId());

        if (orgTask == null) {
            throw new SysErrorException(ResultCode.INSPECTION_PLAN_TASK_NULL);
        }

        if(!InspectionTaskStatusEnum.AUDIT.getCode().equals(orgTask.getStatus())){
            throw new SysErrorException(ResultCode.INSPECTION_PLAN_TASK_WAIT_STATUS);
        }


        Integer status = req.getStatus();

        Date audtiTime = new Date();
        //审核通过
        inspectionTaskMapper.updateStatusById(status,req.getId(),audtiTime);
        InspectionAuditReason inspectionAuditReason = new InspectionAuditReason(user.getGroupId(),orgTask.getId(),status,req.getReason());
        //赋值公共属性
        EntityBase.setCreateParams(inspectionAuditReason, user);

        inspectionAuditReasonMapper.insert(inspectionAuditReason);

        if(InspectionTaskStatusEnum.PASS.getCode().equals(status)){
            //操作日志 通过
            insertLog(user, orgTask.getId(), LogConstant.PASS,user.getShowName());

            //发送消息
            messageProxy.sendWebSocketAndJpush(orgTask.getOperatorId(),user.getId(), MessageConstant.INSPECTION_PLAN_CATEGORY,String.format(MessageConstant.PASS_MESSAGE,orgTask.getName()),user.getGroupId(),
                    MessageConstant.INSPECTION_JPUSH_TYPE,orgTask.getId(), InspectionPlanMainTypeEnum.INSPECTION,req.getTokenType(),JumpTypeEnum.DETAIL.getCode());

        }else if(InspectionTaskStatusEnum.REFUSE.getCode().equals(status)) {
            //操作日志 驳回
            insertLog(user, orgTask.getId(), LogConstant.REFUSE,user.getShowName());

            //发送消息
            messageProxy.sendWebSocketAndJpush(orgTask.getOperatorId(),user.getId(), MessageConstant.INSPECTION_PLAN_CATEGORY,String.format(MessageConstant.REFUSE_MESSAGE,orgTask.getName()),user.getGroupId(),
                    MessageConstant.INSPECTION_JPUSH_TYPE,orgTask.getId(), InspectionPlanMainTypeEnum.INSPECTION,req.getTokenType(),JumpTypeEnum.DETAIL.getCode());
        }

        return JsonNewResult.success();
    }


    @Override
    public JsonNewResult<Void> expire(InspectionPlanTaskExpireReq req, Users user) {

        Integer id = req.getId();

        InspectionTask inspectionTask = inspectionTaskMapper.selectbyPrimaryId(id);

        inspectionTaskMapper.updateStatusById(InspectionTaskStatusEnum.EXPIRE.getCode(),req.getId(),null);

        return JsonNewResult.success();
    }

    /**
     * 回调
     * @param req
     * @param user
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonNewResult<Void> callBack(InspectionPlanTaskCallBackReq req, Users user) {

        Integer taskId = req.getTaskId();

        Integer expandId = req.getExpandId();

        //获取
        InspectionTask orgTask = inspectionTaskMapper.selectbyPrimaryId(taskId);

        if (orgTask == null) {
            throw new SysErrorException(ResultCode.INSPECTION_PLAN_TASK_NULL);
        }

        Integer completeExpandCount = orgTask.getCompleteExpandCount();

        //明细pass
        int isOk = inspectionTaskExpandMapper.updateStatusById(expandId,InspectionTaskExpandStatusEnum.PASS.getCode());

        completeExpandCount+=1;

        BigDecimal percent = BigDecimalUtils.calculatePercent(completeExpandCount, orgTask.getTotalExpandCount(),BigDecimalUtils.DEFAULT_SCALE_FOUR, BigDecimal.ROUND_HALF_UP);

        //更新 完成数量+1
        inspectionTaskMapper.updateCompleteExpandCount(taskId,completeExpandCount,percent);

        //操作日志
        insertLog(user, taskId, LogConstant.CALLBACK,user.getShowName(),req.getDeptName());

        if(completeExpandCount.equals(orgTask.getTotalExpandCount())){
            //判断任务完成
            int isFinish =inspectionTaskMapper.updateStatusById(InspectionTaskStatusEnum.FINISH.getCode(),taskId,null);
            //操作日志
            insertLog(user, taskId, LogConstant.COMPELATE,orgTask.getName());

            //发送消息
            messageProxy.sendWebSocketAndJpush(orgTask.getAuditId(),user.getId(), MessageConstant.INSPECTION_PLAN_CATEGORY,String.format(MessageConstant.COMPELETE_MESSAGE,orgTask.getName()),user.getGroupId(),
                    MessageConstant.INSPECTION_JPUSH_TYPE,orgTask.getId(), InspectionPlanMainTypeEnum.INSPECTION,req.getTokenType(),JumpTypeEnum.APP.getCode());

        }else if(completeExpandCount<orgTask.getTotalExpandCount()){
            //判断任务完成
            int isOperat =inspectionTaskMapper.updateStatusById(InspectionTaskStatusEnum.INSPECT.getCode(),taskId,null);
        }

        return JsonNewResult.success();
    }

    /**
     * 门店列表
     * @param req
     * @param user
     * @return
     */
    @Override
    public JsonNewResult<Page<InspectionPlanTaskExpandListResp>> expandList(InspectionPlanAppExpandReq req, Users user) {

        Page<InspectionTaskExpand> pageTemp = new Page<InspectionTaskExpand>();
        pageTemp.setPageNumber(req.getPageNo());
        pageTemp.setPageSize(req.getPageSize());

        //我审核
        List<InspectionTaskExpand> list = inspectionTaskExpandMapper.queryExpandListByTaskIdByPage(pageTemp,req.getId(),req.getStatus());


        //<门店id,门店名字>
        Map<Integer, String> deptNameMap = new HashMap<>();
        //<标签id, 标签对象>
        Map<Integer, String> tagMap = new HashMap<>();

        //<门店id, 标签集合>
        Map<Integer, List<InspectionDeptTag>> groupByDeptIdTagMap = new HashMap<>();

        List<InspectionPlanTaskExpandListResp> result = new ArrayList<>();

        if(!CollectionUtils.isEmpty(list)) {
            //转换集合
            result = ClazzConverterUtils.converterClass(list, InspectionPlanTaskExpandListResp.class);
            //门店id集合
            List<Integer> deptIdList = result.stream().map(InspectionPlanTaskExpandListResp::getDeptId).collect(Collectors.toList());
            //<门店id,门店名称>
            deptNameMap = departProxy.getDeptNameMap(deptIdList);
        }

        for (InspectionPlanTaskExpandListResp resp : result) {
            resp.setDeptName(deptNameMap.get(resp.getDeptId()));
        }

        Page<InspectionPlanTaskExpandListResp> pageResult = new Page<InspectionPlanTaskExpandListResp>();

        BeanUtils.copyProperties(pageTemp, pageResult);

        pageResult.setContent(result);

        return JsonNewResult.success(pageResult);
    }


    @Override
    public JsonNewResult<InspectionPlanTaskExpandListCountResp> expandCountList(InspectionPlanAppExpandReq req, Users user) {

        //我审核
        Integer waitCount = inspectionTaskExpandMapper.queryExpandListCountByTaskId(req.getId(),InspectionTaskExpandStatusEnum.WAIT.getCode());

        Integer passCount = inspectionTaskExpandMapper.queryExpandListCountByTaskId(req.getId(),InspectionTaskExpandStatusEnum.PASS.getCode());

        return JsonNewResult.success(new InspectionPlanTaskExpandListCountResp(waitCount,passCount));
    }

    /**
     *
     * @param req
     * @param user
     * @param list
     */
    public void assembleInspectionTaskExpand(InspectionPlanExpandAddReq req,Integer taskId,Users user,List<InspectionTaskExpand> list, List<InspectionDeptTag> targetTagList){

        InspectionTaskExpand expand = new InspectionTaskExpand();
        //企业id
        expand.setGroupId(user.getGroupId());
        //任务id
        expand.setTaskId(taskId);
        //门店id
        expand.setDeptId(req.getDeptId());
        //描述
        expand.setDescription(req.getDescription());
        //操作人
        expand.setOperatorId(user.getId());
        //状态值
        expand.setStatus(InspectionTaskExpandStatusEnum.WAIT.getCode());
        //赋值公共属性
        EntityBase.setCreateParams(expand, user);
        //列表添加
        list.add(expand);
        //处理标签
        assembleInspectionDeptTag(targetTagList, req.getTagList(), taskId, req.getDeptId(), user);

    }


    /**
     * 组装门店标签集合
     */
    public void  assembleInspectionDeptTag(List<InspectionDeptTag> targetTagList, List<InspectionPlanTagAddReq> sourceTagList,Integer taskId,Integer deptId,Users user){

        for (InspectionPlanTagAddReq tagAddReq : sourceTagList) {

            InspectionDeptTag inspectionDeptTag = new InspectionDeptTag();
            //企业id
            inspectionDeptTag.setGroupId(user.getGroupId());
            //任务id
            inspectionDeptTag.setTaskId(taskId);
            //门店id
            inspectionDeptTag.setDeptId(deptId);
            //标签id
            inspectionDeptTag.setTagId(tagAddReq.getTagId());
            //赋值公共属性
            EntityBase.setCreateParams(inspectionDeptTag, user);
            //集合添加
            targetTagList.add(inspectionDeptTag);
        }
    }

    /**
     * app 列表
     * @param req
     * @param user
     * @return
     */
    @Override
    public JsonNewResult<Page<InspectionPlanTaskAppListResp>> appList(InspectionPlanTaskAppListReq req, Users user) {
        //组织id
        Integer groupId = user.getGroupId();

        Page<InspectionTask> pageTemp = new Page<InspectionTask>();
        pageTemp.setPageNumber(req.getPageNo());
        pageTemp.setPageSize(req.getPageSize());

         List<Integer> taskIdList = new ArrayList<>();

        //这是个大变量
        if(!CollectionUtils.isEmpty(req.getTagIdList())){
            taskIdList = inspectionDeptTagMapper.selectTaskIdByGroupIdAndTagIdList(groupId, req.getTagIdList());

            if(CollectionUtils.isEmpty(taskIdList)){
                return JsonNewResult.success(new Page<InspectionPlanTaskAppListResp>());
            }
        }

        //任务
        List<InspectionTask> list = inspectionTaskMapper.queryAppListByRelateTypeByPage(pageTemp, req.getReleateType(), user.getId(), groupId, req.getName(),req.getStatus(), req.getStartTime(), req.getEndTime(), taskIdList);
        //手动gc 释放大变量
        System.gc();

        Page<InspectionPlanTaskAppListResp> pageResult = new Page<InspectionPlanTaskAppListResp>();
        BeanUtils.copyProperties(pageTemp, pageResult);

        //为空返回
        if(CollectionUtils.isEmpty(list)){
            return JsonNewResult.success(pageResult);
        }

        //转换集合
        List<InspectionPlanTaskAppListResp> result = ClazzConverterUtils.converterClass(list, InspectionPlanTaskAppListResp.class);

        //任务id
        List<Integer> resultTaskIdList = result.stream().map(InspectionPlanTaskAppListResp::getId).collect(Collectors.toList());

        //获取查询到的任务和标记的关联
        List<InspectionDeptTag> inspectionDeptTagsList = inspectionDeptTagMapper.selectTagIdListByTaskList(groupId, resultTaskIdList);

        if(CollectionUtils.isEmpty(inspectionDeptTagsList)){

            pageResult.setContent(result);

            return JsonNewResult.success(pageResult);
        }

        //map<任务id,标签id集合>
        Map<Integer, Set<Integer>> taskTagMap = new HashMap<>();
        //遍历处理任务和标签的id集合
        for (InspectionDeptTag inspectionDeptTag : inspectionDeptTagsList) {

            Integer taskId = inspectionDeptTag.getTaskId();
            //没有新增
            if(CollectionUtils.isEmpty(taskTagMap.get(taskId))){
                HashSet<Integer> tagIdSet = new HashSet<>();
                tagIdSet.add(inspectionDeptTag.getTagId());
                taskTagMap.put(taskId,tagIdSet);
            }else {
                //有添加
                Set<Integer> tagIdSet = taskTagMap.get(taskId);
                tagIdSet.add(inspectionDeptTag.getTagId());
            }
        }


        Set<Integer> distinctTagIdSet = inspectionDeptTagsList.stream().map(InspectionDeptTag::getTagId).collect(Collectors.toSet());

        List<Integer> distinctTagIdList = new ArrayList<>(distinctTagIdSet);

        //<标签id, 标签名字>
        Map<Integer, String> tagMap = new HashMap<>();

        List<InspectionTag> tagList = inspectionTagMapper.queryTagByTagIdList(groupId, distinctTagIdList);

        if(!CollectionUtils.isEmpty(tagList)){
            //<标签id, 标签名字>
            tagMap = tagList.stream().collect(Collectors.toMap(InspectionTag::getId, InspectionTag::getName));
        }

        //手动gc
        System.gc();

        for (InspectionPlanTaskAppListResp inspectionPlanTaskAppListResp : result) {

            List<InspectionPlanTagDetailResp> eachTaskTagList = new ArrayList<>();

            Integer taskId = inspectionPlanTaskAppListResp.getId();

            Set<Integer> tagSet = taskTagMap.get(taskId);

            if(!CollectionUtils.isEmpty(tagSet)){
                for (Integer tagId : tagSet) {

                    String tagName = tagMap.get(tagId);

                    eachTaskTagList.add(new InspectionPlanTagDetailResp(tagId, tagName));

                }
            }
            //设置标签集合
            inspectionPlanTaskAppListResp.setTagList(eachTaskTagList);


            inspectionPlanTaskAppListResp.setStartTimeStr(DateUtil.format(inspectionPlanTaskAppListResp.getStartTime(),DateUtil.FORMAT_SHORT));
            inspectionPlanTaskAppListResp.setEndTimeStr(DateUtil.format(inspectionPlanTaskAppListResp.getEndTime(),DateUtil.FORMAT_SHORT));
        }

        pageResult.setContent(result);

        return JsonNewResult.success(pageResult);

    }

    /**
     * web的列表
     * @param req
     * @param user
     * @return
     */
    @Override
    public JsonNewResult<Page<InspectionPlanTaskWebListResp>> webList(InspectionPlanTaskWebListReq req, Users user) {

        //组织id
        Integer groupId = user.getGroupId();

        Page<InspectionTask> pageTemp = new Page<InspectionTask>();
        pageTemp.setPageNumber(req.getPageNo());
        pageTemp.setPageSize(req.getPageSize());

        List<Integer> taskIdList = new ArrayList<>();

        //这是个大变量
        if(!CollectionUtils.isEmpty(req.getTagIdList())){
            taskIdList = inspectionDeptTagMapper.selectTaskIdByGroupIdAndTagIdList(groupId, req.getTagIdList());

            if(CollectionUtils.isEmpty(taskIdList)){
                return JsonNewResult.success(new Page<InspectionPlanTaskWebListResp>());
            }
        }

        //自定义排序处理
        StringBuilder sqlDescBuilder = new StringBuilder();

        if (!CollectionUtils.isEmpty(req.getOrderList())) {

            List<StorePlanDescReq> orderList = req.getOrderList();

            for (int i = 0; i < orderList.size(); i++) {

                StorePlanDescReq descOrder = orderList.get(i);

                if (descOrder.getDescColumn() != null && descOrder.getDescOrder() != null) {

                    String columnExpression = StorePlanOrderWebColumnEnum.format(descOrder.getDescColumn()).getExpression();

                    String orderExpression = StorePlanOrderDescEnum.format(descOrder.getDescOrder()).getDesc();

                    sqlDescBuilder.append(columnExpression).append(orderExpression).append(",");
                }
            }

            sqlDescBuilder.append(StorePlanOrderWebColumnEnum.ID.getExpression()).append(StorePlanOrderDescEnum.DESC.getDesc());
        } else {
            sqlDescBuilder.append(StorePlanOrderWebColumnEnum.WEB_DEFAULT.getExpression());
        }

        String sqlOrderExpression = sqlDescBuilder.toString();

        List<Integer> organizeUserList  =  new ArrayList<>();

        if(!CollectionUtils.isEmpty(req.getOrganizeIdList())){
            organizeUserList= organizeProxy.getOrganzieUserIdList(req.getOrganizeIdList(), user.getGroupId(), req.getTokenType());
        }

        //任务
        List<InspectionTask> list = inspectionTaskMapper.queryWebListByPage(pageTemp,groupId ,req.getName(), req.getOperatorName(), req.getStatus(),
                req.getAuditName(), req.getStartTime(), req.getEndTime(), InspectionPlanExpressionEnum.formatOrNull(req.getCompletePercentExpression()).getExpression(),
                req.getCompletePercent(),taskIdList,organizeUserList,sqlOrderExpression);

        //手动gc 释放大变量
        System.gc();

        Page<InspectionPlanTaskWebListResp> pageResult = new Page<InspectionPlanTaskWebListResp>();
        BeanUtils.copyProperties(pageTemp, pageResult);

        //为空返回
        if(CollectionUtils.isEmpty(list)){
            return JsonNewResult.success(pageResult);
        }

        //转换集合
        List<InspectionPlanTaskWebListResp> result = ClazzConverterUtils.converterClass(list, InspectionPlanTaskWebListResp.class);

        //任务id
        List<Integer> resultTaskIdList = result.stream().map(InspectionPlanTaskWebListResp ::getId).collect(Collectors.toList());

        //明细
        List<InspectionTaskExpand> expandList = inspectionTaskExpandMapper.selectExpandListByTaskIdList(resultTaskIdList, user.getGroupId());

        Map<Integer,List<InspectionTaskExpand>> expandMap  =new  HashMap<Integer,List<InspectionTaskExpand>>();

        if (!CollectionUtils.isEmpty(expandList)) {
            expandMap = expandList.stream().collect(Collectors.toMap(InspectionTaskExpand::getTaskId, value -> Lists.newArrayList(value),
                    (List<InspectionTaskExpand> newValueList, List<InspectionTaskExpand> oldValueList) -> {
                        oldValueList.addAll(newValueList);
                        return oldValueList;
                    }));
        }


        //获取查询到的任务和标记的关联
        List<InspectionDeptTag> inspectionDeptTagsList = inspectionDeptTagMapper.selectTagIdListByTaskList(groupId, resultTaskIdList);

        if(CollectionUtils.isEmpty(inspectionDeptTagsList)){

            pageResult.setContent(result);

            return JsonNewResult.success(pageResult);
        }

        //map<任务id,标签id集合>
        Map<Integer, Set<Integer>> taskTagMap = new HashMap<>();
        //遍历处理任务和标签的id集合
        for (InspectionDeptTag inspectionDeptTag : inspectionDeptTagsList) {

            Integer taskId = inspectionDeptTag.getTaskId();
            //没有新增
            if(CollectionUtils.isEmpty(taskTagMap.get(taskId))){
                HashSet<Integer> tagIdSet = new HashSet<>();
                tagIdSet.add(inspectionDeptTag.getTagId());
                taskTagMap.put(taskId,tagIdSet);
            }else {
                //有添加
                Set<Integer> tagIdSet = taskTagMap.get(taskId);
                tagIdSet.add(inspectionDeptTag.getTagId());
            }
        }
        //获取所有的去重的标签id
        Set<Integer> distinctTagIdSet = inspectionDeptTagsList.stream().map(InspectionDeptTag::getTagId).collect(Collectors.toSet());

        List<Integer> distinctTagIdList = new ArrayList<>(distinctTagIdSet);

        //<标签id, 标签名字>
        Map<Integer, String> tagMap = new HashMap<>();

        List<InspectionTag> tagList = inspectionTagMapper.queryTagByTagIdList(groupId, distinctTagIdList);

        if(!CollectionUtils.isEmpty(tagList)){
            //<标签id, 标签名字>
            tagMap = tagList.stream().collect(Collectors.toMap(InspectionTag::getId, InspectionTag::getName));
        }

        //手动gc
        System.gc();

//        List<InspectionPlanTaskAppLogListResp> logResult = new ArrayList<>();
//
//        Map<Integer, List<InspectionPlanTaskAppLogListResp>> logMap = new HashMap<>();
//
//        List<InspectionOperatorLog>  logList =inspectionOperatorLogMapper.selectLogListByGroupAndTaskIdList(taskIdList,groupId);
//
//        if(!CollectionUtils.isEmpty(logList)){
//            logResult= ClazzConverterUtils.converterClass(logList, InspectionPlanTaskAppLogListResp.class);
//
//            logMap = logResult.stream().collect(Collectors.toMap(InspectionPlanTaskAppLogListResp::getTaskId, value -> Lists.newArrayList(value),
//                    (List<InspectionPlanTaskAppLogListResp> newValueList, List<InspectionPlanTaskAppLogListResp> oldValueList) -> {
//                        oldValueList.addAll(newValueList);
//                        return oldValueList;
//                    }));
//        }


        for (InspectionPlanTaskWebListResp resp : result) {
            //处理时间
            resp.setEndTimeStr(DateUtil.getDateStr(resp.getEndTime(), DateUtil.FORMAT_TIME));
            resp.setStartTimeStr(DateUtil.getDateStr(resp.getEndTime(), DateUtil.FORMAT_TIME));
            //设置门店数量
            List<InspectionTaskExpand> taskExpandList = expandMap.get(resp.getId());
            resp.setDeptNum(CollectionUtils.isEmpty(taskExpandList)?0:taskExpandList.size());

            List<InspectionPlanTagDetailResp> eachTaskTagList = new ArrayList<>();

            Integer taskId = resp.getId();

            Set<Integer> tagSet = taskTagMap.get(taskId);


            if(!CollectionUtils.isEmpty(tagSet)){
                for (Integer tagId : tagSet) {

                    String tagName = tagMap.get(tagId);

                    eachTaskTagList.add(new InspectionPlanTagDetailResp(tagId, tagName));

                }
            }

            //设置标签集合
            resp.setTagList(eachTaskTagList);
            //设置日志
//            resp.setLogList(logMap.get(resp.getId()));
        }

        pageResult.setContent(result);

        return JsonNewResult.success(pageResult);

    }

    @Override
    public JsonNewResult<InspectionPlanTaskDetailResp> detail(InspectionPlanTaskDetailReq req, Users user) {
        //任务id
        Integer taskId = req.getId();

        InspectionTask task = inspectionTaskMapper.selectbyPrimaryId(taskId);

        if (task == null) {
            throw new SysErrorException(ResultCode.INSPECTION_PLAN_TASK_NULL);
        }
        //明细
        List<InspectionTaskExpand> expandList = inspectionTaskExpandMapper.selectExpandListByTaskId(taskId, user.getGroupId());

        InspectionPlanTaskDetailResp resp = ClazzConverterUtils.converterClass(task, InspectionPlanTaskDetailResp.class);
        //可执行的时间判断
        Date now = new Date();

        //判断是否是执行人或者审核人
        if(task.getOperatorId().equals(user.getId())  && Arrays.asList(InspectionTaskStatusEnum.PASS.getCode(),InspectionTaskStatusEnum.INSPECT.getCode()).contains(task.getStatus()) && now.after(task.getStartTime())){
            resp.setIsOperator(DefaultEnum.DEFAULT_TRUE.getCode());
        }
        //执行人 待审核状态 催办
        if(task.getOperatorId().equals(user.getId())  && InspectionTaskStatusEnum.AUDIT.getCode().equals(task.getStatus())){
            resp.setIsUrged(DefaultEnum.DEFAULT_TRUE.getCode());
        }
        //审核人待审核状态审核
        if(task.getAuditId().equals(user.getId()) && InspectionTaskStatusEnum.AUDIT.getCode().equals(task.getStatus())){
            resp.setIsAudit(DefaultEnum.DEFAULT_TRUE.getCode());
        }

        List<InspectionPlanExpandDetailResp> expandRespList = new ArrayList<InspectionPlanExpandDetailResp>();

        //<门店id,门店名字>
        Map<Integer, String> deptNameMap = new HashMap<>();
        //<标签id, 标签对象>
        Map<Integer, String> tagMap = new HashMap<>();

        //<门店id, 标签集合>
        Map<Integer, List<InspectionDeptTag>> groupByDeptIdTagMap = new HashMap<>();

        if(!CollectionUtils.isEmpty(expandList)){
            expandRespList = ClazzConverterUtils.converterClass(expandList, InspectionPlanExpandDetailResp.class);
            //门店id集合
            List<Integer> deptIdList = expandList.stream().map(InspectionTaskExpand::getDeptId).collect(Collectors.toList());
            //<门店id,门店名称>
            deptNameMap = departProxy.getDeptNameMap(deptIdList);
            //门店和标签的关联集合
            List<InspectionDeptTag>  deptTagList = inspectionDeptTagMapper.selectTagIdListByTaskAndDeptList(taskId,user.getGroupId(),deptIdList);

            if(!CollectionUtils.isEmpty(deptTagList)){

                List<Integer> tagIdList = deptTagList.stream().map(InspectionDeptTag::getTagId).collect(Collectors.toList());
                //map集合
                groupByDeptIdTagMap = deptTagList.stream().collect(Collectors.groupingBy(InspectionDeptTag::getDeptId));

                //所有的标签集合
                List<InspectionTag> tagList = inspectionTagMapper.queryTagByTagIdList(user.getGroupId(),tagIdList);
                //<标签id, 标签名字>
                tagMap = tagList.stream().collect(Collectors.toMap(InspectionTag::getId, InspectionTag::getName));

            }

            Integer undoNum = 0;
            Integer alreadyDoNum =0;

            //明细集合
            for (InspectionPlanExpandDetailResp inspectionPlanExpandDetailResp : expandRespList) {

                Integer deptId = inspectionPlanExpandDetailResp.getDeptId();

                if(InspectionTaskExpandStatusEnum.PASS.getCode().equals(inspectionPlanExpandDetailResp.getStatus())){
                    alreadyDoNum+=1;
                }
                if(InspectionTaskExpandStatusEnum.WAIT.getCode().equals(inspectionPlanExpandDetailResp.getStatus())){
                    undoNum+=1;
                }

                //门店名称
                inspectionPlanExpandDetailResp.setDeptName(deptNameMap.get(deptId));
                //获取门店关联的标签集合
                List<InspectionDeptTag> eachDeptTagList = groupByDeptIdTagMap.get(deptId);

                if(!CollectionUtils.isEmpty(eachDeptTagList)){

                    List<InspectionPlanTagDetailResp> tagList = inspectionPlanExpandDetailResp.getTagList();

                    for (InspectionDeptTag inspectionDeptTag : eachDeptTagList) {

                        Integer tagId = inspectionDeptTag.getTagId();

                        InspectionPlanTagDetailResp tagResp = new InspectionPlanTagDetailResp(tagId, tagMap.get(tagId));
                        //tag添加到集合
                        tagList.add(tagResp);
                    }
                }
            }

            String inspectResult = String.format(CommonConstants.INSPECTION_RESULT, expandRespList.size(),alreadyDoNum, undoNum);

            resp.setInspectionResult(inspectResult);

        }
        //设置明细
        resp.setInspectionExpandList(expandRespList);
        //设置返回最新的审核结果
        List<InspectionAuditReason> list  =inspectionAuditReasonMapper.selectAudiReasonListByTaskId(taskId);

        List<InspectionPlanTaskAuditReasonResp> reasonList = new ArrayList<>();

        if(!CollectionUtils.isEmpty(list)){
            reasonList = ClazzConverterUtils.converterClass(list, InspectionPlanTaskAuditReasonResp.class);
        }
        //审核的历史记录
        resp.setReason(reasonList);

        return JsonNewResult.success(resp);

    }


    @Override
    public JsonNewResult<InspectionPlanTaskWebExpandListResp> webExpandList(InspectionPlanWebExpandReq req, Users user) {

        Integer taskId = req.getTaskId();

        //获取
        InspectionTask orgTask = inspectionTaskMapper.selectbyPrimaryId(taskId);

        if (orgTask == null) {
            throw new SysErrorException(ResultCode.INSPECTION_PLAN_TASK_NULL);
        }
        //明细的集合
        List<InspectionTaskExpand> list = inspectionTaskExpandMapper.selectExpandListByTaskId(taskId,user.getGroupId());

        //<标签id, 标签对象>
        Map<Integer, String> tagMap = new HashMap<>();

        if(CollectionUtils.isEmpty(list)){
            return JsonNewResult.success(new InspectionPlanTaskWebExpandListResp());
        }

        List<Integer> deptIdList = list.stream().map(InspectionTaskExpand::getDeptId).collect(Collectors.toList());
        //<门店id, 标签集合>
        Map<Integer, List<InspectionDeptTag>> groupByDeptIdTagMap = new HashMap<>();

        //门店和标签的关联集合
        List<InspectionDeptTag> deptTagList = inspectionDeptTagMapper.selectTagIdListByTaskAndDeptList(taskId,user.getGroupId(),deptIdList);

        if(!CollectionUtils.isEmpty(deptTagList)){

            List<Integer> tagIdList = deptTagList.stream().map(InspectionDeptTag::getTagId).collect(Collectors.toList());
            //map集合
            groupByDeptIdTagMap = deptTagList.stream().collect(Collectors.groupingBy(InspectionDeptTag::getDeptId));

            //所有的标签集合
            List<InspectionTag> tagList = inspectionTagMapper.queryTagByTagIdList(user.getGroupId(),tagIdList);
            //<标签id, 标签名字>
            tagMap = tagList.stream().collect(Collectors.toMap(InspectionTag::getId, InspectionTag::getName));

        }

        //明细id,.
        Map<Integer, InspectionPlanTaskWebExpandListInnerResp>  resultMap = new HashMap();

        //明细集合
        for (InspectionTaskExpand expand : list) {

            InspectionTaskExpandStatusEnum expandStatusEnum = InspectionTaskExpandStatusEnum.formatOrNull(expand.getStatus());
            //明细状态对象
            InspectionPlanTaskWebExpandListInnerResp innerResp = new InspectionPlanTaskWebExpandListInnerResp(expand.getStatus(),expandStatusEnum==null?"":expandStatusEnum.getDesc());

            Integer deptId = expand.getDeptId();
            //获取门店关联的标签集合
            List<InspectionDeptTag> eachDeptTagList = groupByDeptIdTagMap.get(deptId);

            if(!CollectionUtils.isEmpty(eachDeptTagList)){

                for (InspectionDeptTag inspectionDeptTag : eachDeptTagList) {

                    List<String> tagNameList = innerResp.getTagNameList();

                    Integer tagId = inspectionDeptTag.getTagId();
                    //标签名字
                    String tagName = tagMap.get(tagId);

                    tagNameList.add(tagName);
                }
            }

            resultMap.put(expand.getId(),innerResp);
        }

        return JsonNewResult.success(new InspectionPlanTaskWebExpandListResp(resultMap));
    }






    /**
     * 删除
     * @param req
     * @param user
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonNewResult<Void> delete(InspectionPlanTaskDeleteReq req, Users user) {

        Integer taskId = req.getId();

        InspectionTask task = inspectionTaskMapper.selectbyPrimaryId(taskId);

        if(task==null){
            throw new SysErrorException(ResultCode.INSPECTION_PLAN_TASK_NULL);
        }

        Integer jobId = task.getJobId();

        inspectionTaskMapper.deleteByPrimaryId(taskId);

        inspectionTaskExpandMapper.deleteByTaskIdWithOutExpandList(taskId,new ArrayList<>());

        inspectionDeptTagMapper.deleteByTaskIdWithOutDeptIdList(taskId,new ArrayList<>());

        inspectionAuditReasonMapper.deleteByTaskId(taskId);

        //
        Date now = new Date();

        if(now.before(task.getEndTime())){
            //暂留回滚逻辑
            xxlJobProxy.stopJob(jobId);
        }

        //发送消息 审核人
        messageProxy.sendWebSocketAndJpush(task.getAuditId(),user.getId(), MessageConstant.INSPECTION_PLAN_CATEGORY,String.format(MessageConstant.DELETE_MESSAGE,user.getShowName(),task.getName()),user.getGroupId(),
                MessageConstant.INSPECTION_JPUSH_TYPE,task.getId(), InspectionPlanMainTypeEnum.INSPECTION,req.getTokenType(),JumpTypeEnum.LIST.getCode());
        //执行人
        messageProxy.sendWebSocketAndJpush(task.getOperatorId(),user.getId(), MessageConstant.INSPECTION_PLAN_CATEGORY,String.format(MessageConstant.DELETE_MESSAGE,user.getShowName(),task.getName()),user.getGroupId(),
                MessageConstant.INSPECTION_JPUSH_TYPE,task.getId(), InspectionPlanMainTypeEnum.INSPECTION,req.getTokenType(),JumpTypeEnum.LIST.getCode());

        return JsonNewResult.success();

    }


    @Override
    public JsonNewResult<Void> batchDelete(InspectionPlanTaskDeleteReq req, Users user) {

        List<Integer> taskIdList = req.getIdList();

        List<InspectionTask> taskList =inspectionTaskMapper.selectTaskByIdList(taskIdList,user.getGroupId());

        Date now = new Date();
        //任务id
        List<Integer> jobIdList = taskList.stream().filter(t1->now.before(t1.getEndTime())).map(InspectionTask::getJobId).collect(Collectors.toList());

        inspectionTaskMapper.deleteByIdList(taskIdList,user.getGroupId());

        inspectionTaskExpandMapper.deleteByTaskIdListWithOutExpandList(taskIdList,new ArrayList<>(),user.getGroupId());

        inspectionDeptTagMapper.deleteByTaskIdListWithOutDeptIdList(taskIdList,new ArrayList<>(),user.getGroupId());


        for (Integer jobId : jobIdList) {
            //暂留回滚逻辑
            xxlJobProxy.stopJob(jobId);
        }

        return JsonNewResult.success();
    }


    @Override
    public JsonNewResult<List<InspectionPlanTaskAppLogListResp>> appLogList(InspectionPlanTaskAppLogListReq req, Users user) {

        Integer groupId = user.getGroupId();

        Integer taskId = req.getTaskId();

        List<Integer> taskIdList = Arrays.asList(taskId);

        List<InspectionPlanTaskAppLogListResp> result = new ArrayList<>();

        List<InspectionOperatorLog>  logList =inspectionOperatorLogMapper.selectLogListByGroupAndTaskIdList(taskIdList,groupId);

        if(CollectionUtils.isEmpty(logList)){
            return JsonNewResult.success(result);
        }

        result= ClazzConverterUtils.converterClass(logList, InspectionPlanTaskAppLogListResp.class);

        for (InspectionPlanTaskAppLogListResp resp : result) {
            resp.setCreateTimeStr(DateUtil.format(resp.getCreateTime(),DateUtil.FORMAT_SHORT));
        }
        

        return JsonNewResult.success(result);
    }

    /**
     * 催办
     * @param req
     * @param user
     * @return
     */
    @Override
    public JsonNewResult<Void> urged(InspectionPlanTaskUrgedReq req, Users user) {

        Integer taskId = req.getId();

        InspectionTask task = inspectionTaskMapper.selectbyPrimaryId(taskId);

        if(task==null){
            throw new SysErrorException(ResultCode.INSPECTION_PLAN_TASK_NULL);
        }

        //操作日志
        insertLog(user, task.getId(), LogConstant.URGED,user.getShowName());

        //发送消息
        messageProxy.sendWebSocketAndJpush(task.getAuditId(),user.getId(), MessageConstant.INSPECTION_PLAN_CATEGORY,String.format(MessageConstant.URGED_AUDIT,task.getOperatorName(),task.getName()),user.getGroupId(),
                MessageConstant.INSPECTION_JPUSH_TYPE,task.getId(), InspectionPlanMainTypeEnum.INSPECTION,req.getTokenType(),JumpTypeEnum.AUDIT.getCode());

        return JsonNewResult.success();

    }

    public void insertLog(Users user,Integer taskId,String operatorType,String ... param){
        //日志处理
        String content = String.format(operatorType, param);

        InspectionOperatorLog log  = new InspectionOperatorLog(user.getGroupId(),taskId,user.getId(),user.getShowName(),content);

        //赋值公共属性
        EntityBase.setCreateParams(log, user);

        inspectionOperatorLogMapper.insertSelective(log);
    }


    public static void main(String[] args) {

        BigDecimal percent = BigDecimalUtils.calculatePercent(1, 1,BigDecimalUtils.DEFAULT_SCALE_FOUR, BigDecimal.ROUND_HALF_UP);


        System.out.println(percent.intValue());

    }

}

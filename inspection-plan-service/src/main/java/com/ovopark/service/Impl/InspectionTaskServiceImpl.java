package com.ovopark.service.Impl;

import com.alibaba.fastjson.JSON;
import com.ovopark.builder.InspectionTaskBuilder;
import com.ovopark.constants.CommonConstants;
import com.ovopark.constants.MessageConstant;
import com.ovopark.constants.ProxyConstants;
import com.ovopark.expection.ResultCode;
import com.ovopark.expection.SysErrorException;
import com.ovopark.mapper.*;
import com.ovopark.model.base.EntityBase;
import com.ovopark.model.enums.DefaultEnum;
import com.ovopark.model.enums.DisplayMainTypeEnum;
import com.ovopark.model.enums.InspectionTaskExpandStatusEnum;
import com.ovopark.model.enums.InspectionTaskStatusEnum;
import com.ovopark.model.login.Users;
import com.ovopark.model.page.Page;
import com.ovopark.model.req.*;
import com.ovopark.model.resp.*;
import com.ovopark.po.*;
import com.ovopark.proxy.DepartProxy;
import com.ovopark.proxy.MessageProxy;
import com.ovopark.proxy.XxlJobProxy;
import com.ovopark.service.InspectionTaskService;
import com.ovopark.utils.ClazzConverterUtils;
import com.ovopark.utils.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    XxlJobProxy xxlJobProxy;

    @Autowired
    DepartProxy departProxy;

    @Autowired
    MessageProxy messageProxy;


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
        InspectionTask task = builder.groupId(groupId).name(req.getName()).operatorId(user.getId()).operatorName(user.getUserName()).auditId(req.getAuditId())
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

        //明细表保存数据
        inspectionTaskExpandMapper.batchSaveInspectionTaskExpand(expandList);
        //明细标签关联表保存数据
        inspectionDeptTagMapper.batchSaveInspectionDeptTag(targetTagList);

        //cron表达式 截止日期
        String cron = DateUtils.dateToCron(task.getEndTime());

        //处理过期
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", task.getId());

        //截止时间回调处理
        Integer jobId = xxlJobProxy.sendCronToJob(ProxyConstants.XXL_JOB_HANDLE_TYPE_INSPECTION_PLAN, JSON.toJSONString(param), cron, user.getId(), String.format(ProxyConstants.REMARK, task.getName()));

        //更新定时任务id
        inspectionTaskMapper.updateJobIdById(jobId,task.getId());

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

            String cron = DateUtils.dateToCron(req.getEndTime());
            //处理过期
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("id", orgTask.getId());
            //任务id
            Integer jobId = xxlJobProxy.sendCronToJob(ProxyConstants.XXL_JOB_HANDLE_TYPE_INSPECTION_PLAN, JSON.toJSONString(param), cron, user.getId(), String.format(ProxyConstants.REMARK, orgTask.getName()));
            //赋值新的jobId
            orgTask.setJobId(jobId);
        }
        //状态回到待审核
        orgTask.setName(req.getName()).setAuditId(req.getAuditId()).setAuditName(req.getAuditName()).setCompleteExpandCount(completeExpandCount).setTotalExpandCount(totalExpandCount)
                .setStartTime(req.getStartTime()).setEndTime(req.getEndTime()).setRemark(req.getRemark()).setStatus(InspectionTaskStatusEnum.AUDIT.getCode());

        //赋值公共属性
        EntityBase.setUpdateParams(orgTask, user);
        //更新主表 包含了jobId
        int isOk  = inspectionTaskMapper.updateByPrimaryId(orgTask);

        //全量删除这个任务的从表和标签表的东西
        inspectionTaskExpandMapper.deleteByTaskIdWithOutExpandList(orgTask.getId(),req.getRetainList());

        inspectionDeptTagMapper.deleteByTaskIdWithOutDeptIdList(orgTask.getId(),retainDeptList);

        //明细表保存数据
        inspectionTaskExpandMapper.batchSaveInspectionTaskExpand(expandList);
        //明细标签关联表保存数据
        inspectionDeptTagMapper.batchSaveInspectionDeptTag(targetTagList);

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

        Integer status = req.getStatus();

        Date audtiTime = new Date();
        //审核通过
        inspectionTaskMapper.updateStatusById(status,req.getId(),audtiTime);
        InspectionAuditReason inspectionAuditReason = new InspectionAuditReason(user.getGroupId(),orgTask.getUpdateTimes(),status,req.getReason());
        //赋值公共属性
        EntityBase.setCreateParams(inspectionAuditReason, user);

        inspectionAuditReasonMapper.insert(inspectionAuditReason);

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
        //更新 完成数量+1
        inspectionTaskMapper.updateCompleteExpandCount(taskId,completeExpandCount);

        if(completeExpandCount.equals(orgTask.getTotalExpandCount())){
            //判断任务完成
            inspectionTaskMapper.updateStatusById(taskId,InspectionTaskStatusEnum.FINISH.getCode(),null);
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
    public JsonNewResult<Page<InspectionPlanTaskExpandListResp>> expandList(InspectionPlanTaskDetailReq req, Users user) {

        Page<InspectionTaskExpand> pageTemp = new Page<InspectionTaskExpand>();
        pageTemp.setPageNumber(req.getPageNo());
        pageTemp.setPageSize(req.getPageSize());

        //我审核
        List<InspectionTaskExpand> list = inspectionTaskExpandMapper.queryExpandListByTaskIdByPage(pageTemp,req.getId());
        //转换集合
        List<InspectionPlanTaskExpandListResp> result = ClazzConverterUtils.converterClass(list, InspectionPlanTaskExpandListResp.class);

        Page<InspectionPlanTaskExpandListResp> pageResult = new Page<InspectionPlanTaskExpandListResp>();

        BeanUtils.copyProperties(pageTemp, pageResult);

        pageResult.setContent(result);

        return JsonNewResult.success(pageResult);
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

        //这是个大变量
        List<Integer> taskIdList = inspectionDeptTagMapper.selectTaskIdByGroupIdAndTagIdList(groupId, req.getTagIdList());
        //任务
        List<InspectionTask> list = inspectionTaskMapper.queryAppListByRelateTypeByPage(pageTemp, req.getReleateType(), user.getId(), groupId, req.getName(), req.getStartTime(), req.getEndTime(), taskIdList);
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

            for (Integer tagId : tagSet) {

                String tagName = tagMap.get(tagId);

                eachTaskTagList.add(new InspectionPlanTagDetailResp(tagId, tagName));

            }
            //设置标签集合
            inspectionPlanTaskAppListResp.setTagList(eachTaskTagList);
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

        //判断是否是执行人或者审核人
        if(task.getOperatorId().equals(user.getId())){
            resp.setIsOperator(DefaultEnum.DEFAULT_TRUE.getCode());
        }

        if(task.getAuditId().equals(user.getId())){
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
            //明细集合
            for (InspectionPlanExpandDetailResp inspectionPlanExpandDetailResp : expandRespList) {

                Integer deptId = inspectionPlanExpandDetailResp.getDeptId();

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
        }
        //设置明细
        resp.setInspectionExpandList(expandRespList);

        return JsonNewResult.success(resp);

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
        //暂留回滚逻辑
        xxlJobProxy.stopJob(jobId);

        return JsonNewResult.success();

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

        //发送消息
        messageProxy.sendWebSocketAndJpush(task.getOperatorId(),user.getId(), MessageConstant.INSPECTION_PLAN_CATEGORY,String.format(MessageConstant.URGED_AUDIT,task.getName()),user.getGroupId(),
                MessageConstant.INSPECTION_JPUSH_TYPE,task.getId(), DisplayMainTypeEnum.INSPECTION,req.getTokenType());

        return JsonNewResult.success();

    }
}

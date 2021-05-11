package com.ovopark.service.Impl;

import com.alibaba.fastjson.JSON;
import com.ovopark.builder.InspectionTaskBuilder;
import com.ovopark.constants.MessageConstant;
import com.ovopark.constants.ProxyConstants;
import com.ovopark.expection.ResultCode;
import com.ovopark.expection.SysErrorException;
import com.ovopark.mapper.InspectionDeptTagMapper;
import com.ovopark.mapper.InspectionTagMapper;
import com.ovopark.mapper.InspectionTaskExpandMapper;
import com.ovopark.mapper.InspectionTaskMapper;
import com.ovopark.model.base.EntityBase;
import com.ovopark.model.enums.DisplayMainTypeEnum;
import com.ovopark.model.enums.InspectionTaskExpandStatusEnum;
import com.ovopark.model.enums.InspectionTaskStatusEnum;
import com.ovopark.model.login.Users;
import com.ovopark.model.req.*;
import com.ovopark.model.resp.InspectionPlanExpandDetailResp;
import com.ovopark.model.resp.InspectionPlanTagDetailResp;
import com.ovopark.model.resp.InspectionPlanTaskDetailResp;
import com.ovopark.model.resp.JsonNewResult;
import com.ovopark.po.InspectionDeptTag;
import com.ovopark.po.InspectionTag;
import com.ovopark.po.InspectionTask;
import com.ovopark.po.InspectionTaskExpand;
import com.ovopark.proxy.DepartProxy;
import com.ovopark.proxy.MessageProxy;
import com.ovopark.proxy.XxlJobProxy;
import com.ovopark.service.InspectionTaskService;
import com.ovopark.utils.ClazzConverterUtils;
import com.ovopark.utils.DateUtils;
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

        //构建任务
        InspectionTask task = builder.groupId(groupId).name(req.getName()).operatorId(user.getId()).operatorName(user.getUserName()).auditId(req.getAuditId())
                .auditName(req.getAuditName()).status(InspectionTaskStatusEnum.AUDIT.getCode()).startTime(req.getStartTime()).endTime(req.getEndTime()).remark(req.getRemark()).build();

        //赋值公共属性
        EntityBase.setCreateParams(task, user);

        inspectionTaskMapper.insertSelective(task);

        //处理明细
        List<InspectionPlanExpandAddReq> inspectionExpandList = req.getInspectionExpandList();
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

        //处理自动通过逻辑
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", task.getId());

        //截止时间回调处理
        Integer jobId = xxlJobProxy.sendCronToJob(ProxyConstants.XXL_JOB_HANDLE_TYPE_DISPLAY_TASK, JSON.toJSONString(param), cron, user.getId(), String.format(ProxyConstants.REMARK, task.getName()));

        //更新定时任务id
        inspectionTaskMapper.updateJobIdById(jobId,task.getId());

        return JsonNewResult.success();

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

        inspectionTaskExpandMapper.deleteByTaskId(taskId);

        inspectionDeptTagMapper.deleteByTaskId(taskId);
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

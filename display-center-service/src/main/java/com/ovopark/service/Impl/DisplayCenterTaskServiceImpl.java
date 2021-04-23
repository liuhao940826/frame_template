package com.ovopark.service.Impl;

import com.ovopark.expection.ResultCode;
import com.ovopark.expection.SysErrorException;
import com.ovopark.mapper.DisplayCenterTaskMapper;
import com.ovopark.model.base.EntityBase;
import com.ovopark.model.enums.DefaultEnum;
import com.ovopark.model.enums.DisplayCenterRelateTypeEnum;
import com.ovopark.model.enums.DisplayCenterTaskStatusEnum;
import com.ovopark.model.login.Users;
import com.ovopark.model.page.Page;
import com.ovopark.model.req.DisplayCenterTaskAddExpandOuterReq;
import com.ovopark.model.req.DisplayCenterTaskAddReq;
import com.ovopark.model.req.DisplayCenterTaskAppListReq;
import com.ovopark.model.resp.DisplayCenterTaskAppListResp;
import com.ovopark.model.resp.JsonNewResult;
import com.ovopark.po.DisplayCenterTask;
import com.ovopark.service.DisplayCenterTaskService;
import com.ovopark.utils.ClazzConverterUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


    @Override
    public JsonNewResult<Page<DisplayCenterTaskAppListResp>> appList(DisplayCenterTaskAppListReq req, Users user) {

        Page<DisplayCenterTask> pageTemp = new Page<DisplayCenterTask>();
        pageTemp.setPageNumber(req.getPageNo());
        pageTemp.setPageSize(req.getPageSize());

        //我审核
        List<DisplayCenterTask> list = displayCenterTaskMapper.queryAppListByRelateTypeByPage(pageTemp, req.getReleateType(), user.getId(), user.getGroupId());

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
    public JsonNewResult<Void> addExpland(DisplayCenterTaskAddExpandOuterReq req, Users user) {

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

        //TODO 添加明细没做

        return null;
    }
}

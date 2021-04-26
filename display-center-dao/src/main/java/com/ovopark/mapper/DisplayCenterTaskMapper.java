package com.ovopark.mapper;

import com.ovopark.model.page.Page;
import com.ovopark.po.DisplayCenterTask;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Classname DisplayCenterTaskMapper
 * @Description TODO
 * @Date 2021/4/22 上午10:13
 * @Created by liuhao
 */
public interface DisplayCenterTaskMapper {

    void insertSelective(DisplayCenterTask po);


    List<DisplayCenterTask> queryAppListByRelateTypeByPage(@Param("page")Page<DisplayCenterTask> pageTemp,@Param("releateType") Integer releateType,
                                                           @Param("userId") Integer userId,@Param("groupId")Integer groupId,
                                                           @Param("deptName")String deptName,@Param("name")String name,@Param("auditTime")Date auditTime);


    DisplayCenterTask selectByPrimaryKey(@Param("id") Integer taskId);

    int updateTaskStautsById(@Param("id")Integer taskId, @Param("status")Integer status, @Param("operatorTime")Date operatorTime, @Param("auditTime")Date auditTime,
                             @Param("actualScore")BigDecimal actualScore ,@Param("totalScore")BigDecimal totalScore );

    void updateJobId(@Param("jobId")Integer jobId, @Param("id")Integer id);

    List<DisplayCenterTask> queryWebListByPage(@Param("page")Page<DisplayCenterTask> pageTemp,@Param("groupId")Integer groupId,
                                               @Param("name")String name,
                                               @Param("deptName")String deptName, @Param("status")Integer status,
                                               @Param("operatorName")String operatorName, @Param("auditName")String auditName,
                                               @Param("startTime")String startTime, @Param("endTime")String endTime);
}

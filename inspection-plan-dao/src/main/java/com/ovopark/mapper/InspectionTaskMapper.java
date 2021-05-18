package com.ovopark.mapper;

import com.ovopark.model.page.Page;
import com.ovopark.po.InspectionTask;
import com.ovopark.po.InspectionTaskExpand;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Classname InspectionTaskMapper
 * @Description TODO
 * @Date 2021/5/8 下午4:00
 * @Created by liuhao
 */
public interface InspectionTaskMapper {
    void insertSelective(InspectionTask task);

    void updateJobIdById(@Param("jobId") Integer jobId, @Param("id")Integer id);

    InspectionTask selectbyPrimaryId(@Param("id")Integer id);

    int deleteByPrimaryId(@Param("id")Integer taskId);

    int updateByPrimaryId(InspectionTask orgTask);

    int updateStatusById(@Param("status")Integer status, @Param("id")Integer id,@Param("auditTime") Date auditTime);

    List<InspectionTask> queryAppListByRelateTypeByPage(@Param("page")Page<InspectionTask> pageTemp, @Param("releateType")Integer releateType,
                                                              @Param("userId")Integer userId, @Param("groupId")Integer groupId,
                                                              @Param("name")String name, @Param("startTime")String startTime, @Param("endTime")String endTime,
                                                              @Param("list") List<Integer> taskIdList);

    int updateCompleteExpandCount(@Param("id")Integer taskId, @Param("completeExpandCount")Integer completeExpandCount,@Param("percent")BigDecimal percent);

    List<InspectionTask> queryWebListByPage(@Param("page")Page<InspectionTask> pageTemp, @Param("groupId")Integer groupId,@Param("name")String name,
                                            @Param("operatorName")String operatorName, @Param("status")Integer status, @Param("auditName")String auditName,
                                            @Param("startTime")String startTime, @Param("endTime")String endTime, @Param("expression")String expression,
                                            @Param("percent") BigDecimal completePercent, @Param("list")List<Integer> taskIdList,
                                            @Param("sqlOrderExpression")String sqlOrderExpression);

    List<InspectionTask> selectTaskByIdList(@Param("list") List<Integer> taskIdList, @Param("groupId") Integer groupId);

    void deleteByIdList(@Param("list") List<Integer> taskIdList,@Param("groupId")Integer groupId);
}

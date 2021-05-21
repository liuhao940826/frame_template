package com.ovopark.mapper;

import com.ovopark.po.InspectionAuditReason;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Classname InspectionAuditReasonMapper
 * @Description TODO
 * @Date 2021/5/13 23:40
 * @Created by liuhao
 */
public interface InspectionAuditReasonMapper {



    void insert(InspectionAuditReason inspectionAuditReason);

    List<InspectionAuditReason> selectAudiReasonListByTaskId(@Param("taskId") Integer taskId);

    int deleteByTaskId(@Param("taskId")Integer taskId);
}

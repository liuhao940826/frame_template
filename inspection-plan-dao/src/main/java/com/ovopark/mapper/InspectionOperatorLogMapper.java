package com.ovopark.mapper;

import com.ovopark.po.InspectionOperatorLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Classname InspectionOperatorLogMapper
 * @Description TODO
 * @Date 2021/5/15 16:15
 * @Created by liuhao
 */
public interface InspectionOperatorLogMapper {

    void insertSelective (InspectionOperatorLog inspectionOperatorLog);

    List<InspectionOperatorLog> selectLogListByGroupAndTaskIdList(@Param("list") List<Integer> taskIdList,@Param("groupId") Integer groupId);
}

package com.ovopark.mapper;

import com.ovopark.po.InspectionTaskExpand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Classname InspectionTaskExpandMapper
 * @Description TODO
 * @Date 2021/5/10 下午4:02
 * @Created by liuhao
 */
public interface InspectionTaskExpandMapper {


    void batchSaveInspectionTaskExpand(@Param("list") List<InspectionTaskExpand> expandList);

    List<InspectionTaskExpand> selectExpandListByTaskId(@Param("taskId") Integer taskId,@Param("groupId")Integer groupId);
}

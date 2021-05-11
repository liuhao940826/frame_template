package com.ovopark.mapper;

import com.ovopark.po.InspectionDeptTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Classname InspectionDeptTagMapper
 * @Description TODO
 * @Date 2021/5/10 下午4:02
 * @Created by liuhao
 */
public interface InspectionDeptTagMapper {


    void batchSaveInspectionDeptTag(@Param("list") List<InspectionDeptTag> targetTagList);

    List<InspectionDeptTag> selectTagIdListByTaskAndDeptList(@Param("taskId") Integer taskId,@Param("groupId") Integer groupId,@Param("list") List<Integer> deptIdList);

    int deleteByTaskId(@Param("taskId")Integer taskId);
}

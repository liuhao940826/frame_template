package com.ovopark.mapper;

import com.ovopark.model.page.Page;
import com.ovopark.po.InspectionTaskExpand;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
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

    List<InspectionTaskExpand> selectExpandListByTaskIdList(@Param("list")List<Integer> taskIdList,@Param("groupId")Integer groupId);

    int deleteByTaskIdWithOutExpandList(@Param("taskId")Integer taskId,@Param("list") List<Integer> withOutExpandId);

    List<InspectionTaskExpand> queryExpandListByTaskIdByPage(@Param("page")Page<InspectionTaskExpand> pageTemp, @Param("taskId")Integer taskId,@Param("status")Integer status);

    Integer queryExpandListCountByTaskId(@Param("taskId")Integer taskId,@Param("status")Integer status);


    int updateStatusById(@Param("id") Integer id, @Param("status") Integer status);

    int deleteByTaskIdListWithOutExpandList(@Param("taskIdList")List<Integer> taskIdList, @Param("expandList") List<Integer> withOutExpandId,@Param("groupId") Integer groupId);


}

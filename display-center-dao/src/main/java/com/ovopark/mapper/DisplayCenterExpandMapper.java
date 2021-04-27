package com.ovopark.mapper;

import com.ovopark.po.DisplayCenterExpand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Classname DisplayCenterExpandMapper
 * @Description TODO
 * @Date 2021/4/22 上午10:13
 * @Created by liuhao
 */
public interface DisplayCenterExpandMapper {


    void batchSaveDisplayCenterExpand(@Param("list")List<DisplayCenterExpand> insertList);

    void batchUpdateActualScoreAndStatus(@Param("list")List<DisplayCenterExpand> auditList);

    List<DisplayCenterExpand> getDisplayExpandByTaskId(@Param("taskId") Integer taskId);

    List<DisplayCenterExpand> queryExpandByTaskIdList(@Param("list")List<Integer> taskIdList);
}

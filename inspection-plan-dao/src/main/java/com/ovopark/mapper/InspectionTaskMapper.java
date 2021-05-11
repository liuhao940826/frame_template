package com.ovopark.mapper;

import com.ovopark.po.InspectionTask;
import org.apache.ibatis.annotations.Param;

/**
 * @Classname InspectionTaskMapper
 * @Description TODO
 * @Date 2021/5/8 下午4:00
 * @Created by liuhao
 */
public interface InspectionTaskMapper {
    void insertSelective(InspectionTask task);

    void updateJobIdById(@Param("jobId") Integer jobId, @Param("id")Integer id);
}

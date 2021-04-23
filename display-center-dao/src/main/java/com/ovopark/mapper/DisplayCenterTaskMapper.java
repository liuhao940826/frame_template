package com.ovopark.mapper;

import com.ovopark.model.page.Page;
import com.ovopark.model.resp.DisplayCenterTaskAppListResp;
import com.ovopark.po.DisplayCenterTask;
import org.apache.ibatis.annotations.Param;

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
                                                           @Param("userId") Integer userId,@Param("groupId")Integer groupId);


    DisplayCenterTask selectByPrimaryKey(@Param("id") Integer taskId);
}

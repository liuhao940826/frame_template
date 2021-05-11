package com.ovopark.mapper;

import com.ovopark.po.InspectionCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Classname InspectionTagMapper
 * @Description TODO
 * @Date 2021/5/11 下午12:36
 * @Created by liuhao
 */
public interface InspectionCategoryMapper {

    int insertSelective(InspectionCategory tag);

    int updateByPrimaryId(InspectionCategory category);

    int deleteByPrimaryId(@Param("id") Integer id);

    List<InspectionCategory> selectCategoryListByGroupId(@Param("groupId") Integer groupId);
}

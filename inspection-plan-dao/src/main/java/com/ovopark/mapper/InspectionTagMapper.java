package com.ovopark.mapper;

import com.ovopark.po.InspectionTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Classname InspectionTagMapper
 * @Description TODO
 * @Date 2021/5/11 下午12:36
 * @Created by liuhao
 */
public interface InspectionTagMapper {

    int insertSelective (InspectionTag tag);

    int updateByPrimaryId(InspectionTag tag);

    int deleteByPrimaryId(@Param("id") Integer id);

    List<InspectionTag> selectTagListByCategoryIdList(@Param("list") List<Integer> categoryIdList, @Param("groupId") Integer groupId);

    List<InspectionTag> queryTagByTagIdList(@Param("groupId")Integer groupId, @Param("list") List<Integer> tagIdList);
}

package com.ovopark.service.Impl;

import com.google.common.collect.Lists;
import com.ovopark.mapper.InspectionCategoryMapper;
import com.ovopark.mapper.InspectionTagMapper;
import com.ovopark.model.base.EntityBase;
import com.ovopark.model.login.Users;
import com.ovopark.model.req.InspectionTagAddReq;
import com.ovopark.model.req.InspectionTagDeleteReq;
import com.ovopark.model.req.InspectionTagListReq;
import com.ovopark.model.req.InspectionTagUpdateReq;
import com.ovopark.model.resp.InspectionTagCategoryListResp;
import com.ovopark.model.resp.InspectionTagListResp;
import com.ovopark.model.resp.JsonNewResult;
import com.ovopark.po.InspectionCategory;
import com.ovopark.po.InspectionTag;
import com.ovopark.service.InspectionTagService;
import com.ovopark.utils.ClazzConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Classname InspectionTagServiceImpl
 * @Description TODO
 * @Date 2021/5/11 下午4:01
 * @Created by liuhao
 */
@Service
public class InspectionTagServiceImpl implements InspectionTagService {

    @Autowired
    InspectionTagMapper inspectionTagMapper;

    @Autowired
    InspectionCategoryMapper inspectionCategoryMapper;


    @Override
    public JsonNewResult<Void> add(InspectionTagAddReq req, Users user) {

        InspectionTag tag = new InspectionTag(req.getGroupId(),req.getCategoryId(),req.getName());

        //赋值公共属性
        EntityBase.setCreateParams(tag, user);

        inspectionTagMapper.insertSelective(tag);

        return JsonNewResult.success();

    }

    @Override
    public JsonNewResult<Void> update(InspectionTagUpdateReq req, Users user) {

        InspectionTag tag = new InspectionTag(req.getGroupId(),req.getCategoryId(),req.getName());

        //赋值公共属性
        EntityBase.setUpdateParams(tag, user);

        int isOk = inspectionTagMapper.updateByPrimaryId(tag);

        return JsonNewResult.success();

    }

    @Override
    public JsonNewResult<Void> delete(InspectionTagDeleteReq req, Users user) {

        int isOk = inspectionTagMapper.deleteByPrimaryId(req.getId());

        return JsonNewResult.success();
    }

    @Override
    public JsonNewResult<List<InspectionTagCategoryListResp>> list(InspectionTagListReq req, Users user) {

        Integer groupId = req.getGroupId();

        List<InspectionCategory> categoryList = inspectionCategoryMapper.selectCategoryListByGroupId(groupId);

        if (CollectionUtils.isEmpty(categoryList)) {
            return JsonNewResult.success(new ArrayList<>());
        }

        List<Integer> categoryIdList = categoryList.stream().map(InspectionCategory::getId).collect(Collectors.toList());

        List<InspectionTag> tagList = inspectionTagMapper.selectTagListByCategoryIdList(categoryIdList, groupId);

        //分类的返回集合
        List<InspectionTagCategoryListResp> result = ClazzConverterUtils.converterClass(categoryList, InspectionTagCategoryListResp.class);

        if (CollectionUtils.isEmpty(tagList)) {

            return JsonNewResult.success(result);

        }

        List<InspectionTagListResp> tagRespList = ClazzConverterUtils.converterClass(tagList, InspectionTagListResp.class);

        //分类标签映射
        Map<Integer, List<InspectionTagListResp>> categoryMapTagListMapping = tagRespList.stream().collect(Collectors.toMap(InspectionTagListResp::getCategoryId, Lists::newArrayList,
                (List<InspectionTagListResp> oldValue, List<InspectionTagListResp> newValue) -> {
                    oldValue.addAll(newValue);
                    return oldValue;
                }));

        for (InspectionTagCategoryListResp categoryListResp : result) {

            categoryListResp.setTags(categoryMapTagListMapping.get(categoryListResp.getId()));

        }

        return JsonNewResult.success(result);

    }
}

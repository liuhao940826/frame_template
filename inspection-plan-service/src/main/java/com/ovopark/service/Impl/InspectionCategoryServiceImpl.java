package com.ovopark.service.Impl;

import com.ovopark.mapper.InspectionCategoryMapper;
import com.ovopark.model.base.EntityBase;
import com.ovopark.model.login.Users;
import com.ovopark.model.req.InspectionCategoryAddReq;
import com.ovopark.model.req.InspectionCategoryDeleteReq;
import com.ovopark.model.req.InspectionCategoryUpdateReq;
import com.ovopark.model.resp.JsonNewResult;
import com.ovopark.po.InspectionCategory;
import com.ovopark.service.InspectionCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname InspectionCategoryServiceImpl
 * @Description TODO
 * @Date 2021/5/11 下午3:22
 * @Created by liuhao
 */
@Service
public class InspectionCategoryServiceImpl implements InspectionCategoryService {

    @Autowired
    InspectionCategoryMapper inspectionCategoryMapper;


    @Override
    public JsonNewResult<Void> add(InspectionCategoryAddReq req, Users user) {

        //分类
        InspectionCategory category =  new InspectionCategory(req.getName(),user.getGroupId());

        //赋值公共属性
        EntityBase.setCreateParams(category, user);

        inspectionCategoryMapper.insertSelective(category);

        return JsonNewResult.success();
    }


    @Override
    public JsonNewResult<Void> update(InspectionCategoryUpdateReq req, Users user) {

        //分类
        InspectionCategory category =  new InspectionCategory(req.getName(),req.getId());

        //赋值公共属性
        EntityBase.setUpdateParams(category, user);

        int isOk = inspectionCategoryMapper.updateByPrimaryId(category);

        return JsonNewResult.success();
    }


    @Override
    public JsonNewResult<Void> delete(InspectionCategoryDeleteReq req, Users user) {

        int isOk = inspectionCategoryMapper.deleteByPrimaryId(req.getId());

        return JsonNewResult.success();
    }
}

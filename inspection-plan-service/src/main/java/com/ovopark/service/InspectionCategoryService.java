package com.ovopark.service;

import com.ovopark.model.login.Users;
import com.ovopark.model.req.InspectionCategoryAddReq;
import com.ovopark.model.req.InspectionCategoryDeleteReq;
import com.ovopark.model.req.InspectionCategoryUpdateReq;
import com.ovopark.model.resp.JsonNewResult;

/**
 * @Classname InspectionCategoryService
 * @Description TODO
 * @Date 2021/5/11 下午3:21
 * @Created by liuhao
 */
public interface InspectionCategoryService {

    JsonNewResult<Void> add(InspectionCategoryAddReq req, Users user);

    JsonNewResult<Void> update(InspectionCategoryUpdateReq req, Users user);

    JsonNewResult<Void> delete(InspectionCategoryDeleteReq req, Users user);
}

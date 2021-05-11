package com.ovopark.service;

import com.ovopark.model.login.Users;
import com.ovopark.model.req.*;
import com.ovopark.model.resp.InspectionTagCategoryListResp;
import com.ovopark.model.resp.JsonNewResult;

import java.util.List;

/**
 * @Classname InspectionTagService
 * @Description TODO
 * @Date 2021/5/11 下午3:21
 * @Created by liuhao
 */
public interface InspectionTagService {

    JsonNewResult<Void> add(InspectionTagAddReq req, Users user);

    JsonNewResult<Void> update(InspectionTagUpdateReq req, Users user);

    JsonNewResult<Void> delete(InspectionTagDeleteReq req, Users user);

    JsonNewResult<List<InspectionTagCategoryListResp>> list(InspectionTagListReq req, Users user);
}

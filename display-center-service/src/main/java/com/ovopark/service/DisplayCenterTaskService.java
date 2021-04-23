package com.ovopark.service;

import com.ovopark.model.login.Users;
import com.ovopark.model.page.Page;
import com.ovopark.model.req.DisplayCenterTaskAddExpandOuterReq;
import com.ovopark.model.req.DisplayCenterTaskAddReq;
import com.ovopark.model.req.DisplayCenterTaskAppListReq;
import com.ovopark.model.resp.DisplayCenterTaskAppListResp;
import com.ovopark.model.resp.JsonNewResult;

/**
 * @Classname DisplayCenterTask
 * @Description TODO
 * @Date 2021/4/23 上午9:35
 * @Created by liuhao
 */
public interface DisplayCenterTaskService {

    JsonNewResult<Void> add(DisplayCenterTaskAddReq req, Users user);

    JsonNewResult<Page<DisplayCenterTaskAppListResp>> appList(DisplayCenterTaskAppListReq req, Users user);

    JsonNewResult<Void> addExpland(DisplayCenterTaskAddExpandOuterReq req, Users user);
}

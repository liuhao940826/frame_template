package com.ovopark.service;

import com.ovopark.model.login.Users;
import com.ovopark.model.page.Page;
import com.ovopark.model.req.*;
import com.ovopark.model.resp.DisplayCenterTaskAppListResp;
import com.ovopark.model.resp.DisplayCenterTaskDetailResp;
import com.ovopark.model.resp.DisplayCenterTaskWebListResp;
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

    JsonNewResult<Void> carryOut(DisplayCenterTaskAddExpandOuterReq req, Users user);

    JsonNewResult<Void> audit(DisplayCenterTaskAuditExpandOuterReq req, Users user);

    JsonNewResult<DisplayCenterTaskDetailResp> detail(DisplayCenterTaskDetailReq req, Users user);

    JsonNewResult<Page<DisplayCenterTaskWebListResp>> DisplayCenterTaskWebList(DisplayCenterTaskWebListReq req, Users user);
}

package com.ovopark.service;

import com.ovopark.model.login.Users;
import com.ovopark.model.page.Page;
import com.ovopark.model.req.*;
import com.ovopark.model.resp.*;

/**
 * @Classname InspectionTask
 * @Description TODO
 * @Date 2021/5/8 下午3:43
 * @Created by liuhao
 */
public interface InspectionTaskService {


    JsonNewResult<Void> add(InspectionPlanTaskAddReq req, Users user);

    JsonNewResult<InspectionPlanTaskDetailResp> detail(InspectionPlanTaskDetailReq req, Users user);

    JsonNewResult<Void> delete(InspectionPlanTaskDeleteReq req, Users user);

    JsonNewResult<Void> urged(InspectionPlanTaskUrgedReq req, Users user);

    JsonNewResult<Void> udpate(InspectionPlanTaskUpdateReq req, Users user);

    JsonNewResult<Void> audit(InspectionPlanTaskAuditReq req, Users user);

    JsonNewResult<Void> expire(InspectionPlanTaskExpireReq req, Users user);

    JsonNewResult<Page<InspectionPlanTaskExpandListResp>> expandList(InspectionPlanAppExpandReq req, Users user);

    JsonNewResult<Page<InspectionPlanTaskAppListResp>> appList(InspectionPlanTaskAppListReq req, Users user);

    JsonNewResult<Void> callBack(InspectionPlanTaskCallBackReq req, Users user);

    JsonNewResult<InspectionPlanTaskWebExpandListResp> webExpandList(InspectionPlanWebExpandReq req, Users user);

    JsonNewResult<Page<InspectionPlanTaskWebListResp>> webList(InspectionPlanTaskWebListReq req, Users user);
}

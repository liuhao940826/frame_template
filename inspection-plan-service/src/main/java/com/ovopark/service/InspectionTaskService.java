package com.ovopark.service;

import com.ovopark.model.login.Users;
import com.ovopark.model.req.InspectionPlanTaskAddReq;
import com.ovopark.model.req.InspectionPlanTaskDetailReq;
import com.ovopark.model.resp.InspectionPlanTaskDetailResp;
import com.ovopark.model.resp.JsonNewResult;

/**
 * @Classname InspectionTask
 * @Description TODO
 * @Date 2021/5/8 下午3:43
 * @Created by liuhao
 */
public interface InspectionTaskService {


    JsonNewResult<Void> add(InspectionPlanTaskAddReq req, Users user);

    JsonNewResult<InspectionPlanTaskDetailResp> detail(InspectionPlanTaskDetailReq req, Users user);
}

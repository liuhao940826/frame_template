package com.ovopark.service;

import com.ovopark.model.login.Users;
import com.ovopark.model.page.Page;
import com.ovopark.model.req.*;
import com.ovopark.model.resp.DisplayCenterTaskAppListResp;
import com.ovopark.model.resp.DisplayCenterTaskDetailResp;
import com.ovopark.model.resp.DisplayCenterTaskWebListResp;
import com.ovopark.model.resp.JsonNewResult;
import com.ovopark.po.Messages;

/**
 * @Classname DisplayCenterTask
 * @Description TODO
 * @Date 2021/4/23 上午9:35
 * @Created by liuhao
 */
public interface MessageService {
    Long insertMessages(Messages msg);
}

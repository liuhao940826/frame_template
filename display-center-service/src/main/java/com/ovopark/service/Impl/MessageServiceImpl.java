package com.ovopark.service.Impl;

import com.ovopark.mapper.MessagesMapper;
import com.ovopark.model.resp.JsonNewResult;
import com.ovopark.po.Messages;
import com.ovopark.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname MessageServiceImpl
 * @Description TODO
 * @Date 2021/4/28 下午2:41
 * @Created by liuhao
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessagesMapper messagesMapper;

    @Override
    public Long insertMessages(Messages msg) {

        messagesMapper.insertSelective(msg);

        return msg.getId();

    }
}

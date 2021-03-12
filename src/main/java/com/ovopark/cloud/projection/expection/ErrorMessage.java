package com.ovopark.cloud.projection.expection;

/**
 * @Classname Validation
 * @Description 自定义异常 异常信息类
 * @Date 2020/7/9 8:10 下午
 * @Created by liuhao
 */
public class ErrorMessage {
    private ResultCodeInfo error;
    private Object[] msgs;

    public ErrorMessage(ResultCodeInfo errorCode) {
        this.error = errorCode;
    }

    public ErrorMessage(ResultCodeInfo errorCode, Object... msgs) {
        this.error = errorCode;

        this.msgs = msgs;
    }

    public ResultCodeInfo getError() {
        return error;
    }

    public Object[] getMsgs() {
        return msgs;
    }

    @Override
    public String toString() {
        return String.format(error.getDesc(), msgs);
    }
}

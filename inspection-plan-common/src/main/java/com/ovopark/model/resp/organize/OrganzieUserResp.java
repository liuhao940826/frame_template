package com.ovopark.model.resp.organize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname OrganzieUserResp
 * @Description TODO
 * @Date 2021/6/1 下午4:35
 * @Created by liuhao
 */
public class OrganzieUserResp implements Serializable {

    private boolean  isError;

    private List<Integer> data = new ArrayList<>();

    private Integer code;

    private String message;

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

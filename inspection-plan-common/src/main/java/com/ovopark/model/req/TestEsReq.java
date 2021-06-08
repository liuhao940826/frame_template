package com.ovopark.model.req;

import java.io.Serializable;

/**
 * @Classname TestEsReq
 * @Description TODO
 * @Date 2020/12/9 下午5:15
 * @Created by liuhao
 */
public class TestEsReq implements Serializable {

    private String index ;

    private String type;

    private String  jsonStr;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }
}

package self.alan.model.resp;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname InspectionPlanTaskWebExpandListInnerResp
 * @Description TODO
 * @Date 2021/5/14 20:22
 * @Created by liuhao
 */
public class InspectionPlanTaskWebExpandListInnerResp {

    private Integer status;

    private String  statusStr;

    private List<String> tagNameList = new ArrayList<>();


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public List<String> getTagNameList() {
        return tagNameList;
    }

    public void setTagNameList(List<String> tagNameList) {
        this.tagNameList = tagNameList;
    }


    public InspectionPlanTaskWebExpandListInnerResp(Integer status, String statusStr) {
        this.status = status;
        this.statusStr = statusStr;
        this.tagNameList = tagNameList;
    }

    public InspectionPlanTaskWebExpandListInnerResp() {
    }
}

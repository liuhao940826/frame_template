package self.alan.model.resp;

import java.io.Serializable;
import java.util.Map;

/**
 * @Classname InspectionPlanTaskExpandListResp
 * @Description TODO
 * @Date 2021/5/11 下午8:01
 * @Created by liuhao
 */
public class InspectionPlanTaskWebExpandListResp implements Serializable {

    private Map<Integer, InspectionPlanTaskWebExpandListInnerResp> expandMap;


    public Map<Integer, InspectionPlanTaskWebExpandListInnerResp> getExpandMap() {
        return expandMap;
    }

    public void setExpandMap(Map<Integer, InspectionPlanTaskWebExpandListInnerResp> expandMap) {
        this.expandMap = expandMap;
    }

    public InspectionPlanTaskWebExpandListResp() {
    }

    public InspectionPlanTaskWebExpandListResp(Map<Integer, InspectionPlanTaskWebExpandListInnerResp> expandMap) {
        this.expandMap = expandMap;
    }
}

package self.alan.model.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname InspectionPlanExpandAddReq
 * @Description TODO
 * @Date 2021/5/8 下午3:25
 * @Created by liuhao
 */
public class InspectionPlanExpandDetailResp implements Serializable {

    private Integer id;

    private Integer deptId;

    private String deptName;

    private List<InspectionPlanTagDetailResp> tagList = new ArrayList<>();

    private String description;

    private Integer status;

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public List<InspectionPlanTagDetailResp> getTagList() {
        return tagList;
    }

    public void setTagList(List<InspectionPlanTagDetailResp> tagList) {
        this.tagList = tagList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

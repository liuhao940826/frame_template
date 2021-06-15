package self.alan.model.resp;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname InspectionPlanTaskAuditReason
 * @Description TODO
 * @Date 2021/5/21 下午1:54
 * @Created by liuhao
 */
public class InspectionPlanTaskAuditReasonResp implements Serializable {

    private Integer id;

    private Integer groupId;

    private Integer taskId;

    private Integer status;

    private String reason="";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

package self.alan.model.resp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Classname InspectionPlanTaskWebListResp
 * @Description TODO
 * @Date 2021/5/11 下午8:01
 * @Created by liuhao
 */
public class InspectionPlanTaskWebListResp implements Serializable {

    private Integer id;

    private String name;

    private Integer groupId;

    private Integer deptNum;

    private Integer operatorId;

    private String operatorName;

    private Integer auditId;

    private String auditName;

    private Integer status;

    private Integer completeExpandCount=0;

    private Integer totalExpandCount=0;

    private BigDecimal completePercent;

    private Date startTime;

    private String startTimeStr;

    private Date endTime;

    private String endTimeStr;

    private String  remark;

    private List<InspectionPlanTagDetailResp> tagList = new ArrayList<>();

    private List<InspectionPlanTaskAppLogListResp> logList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Integer getAuditId() {
        return auditId;
    }

    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCompleteExpandCount() {
        return completeExpandCount;
    }

    public void setCompleteExpandCount(Integer completeExpandCount) {
        this.completeExpandCount = completeExpandCount;
    }

    public Integer getTotalExpandCount() {
        return totalExpandCount;
    }

    public void setTotalExpandCount(Integer totalExpandCount) {
        this.totalExpandCount = totalExpandCount;
    }

    public BigDecimal getCompletePercent() {
        return completePercent;
    }

    public void setCompletePercent(BigDecimal completePercent) {
        this.completePercent = completePercent;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<InspectionPlanTagDetailResp> getTagList() {
        return tagList;
    }

    public void setTagList(List<InspectionPlanTagDetailResp> tagList) {
        this.tagList = tagList;
    }

    public Integer getDeptNum() {
        return deptNum;
    }

    public void setDeptNum(Integer deptNum) {
        this.deptNum = deptNum;
    }

    public List<InspectionPlanTaskAppLogListResp> getLogList() {
        return logList;
    }

    public void setLogList(List<InspectionPlanTaskAppLogListResp> logList) {
        this.logList = logList;
    }
}

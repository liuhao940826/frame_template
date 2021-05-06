package com.ovopark.po;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class Messages implements Serializable{

	private Long id;

    private Integer srcUserId;

    private Integer targetUserId;

    private String content;


    private String description;

    private String objectType;

    private String category;

    private Integer objectId;

    private String objectIds;

    private Integer subId;

    private String objectUrl;

    private Byte status;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    private Byte optionState;

    private Byte isAtall;

    private Integer enterpriseId;

    private String i18nKey;

    private String i18nParam;

    private String title;

    private Byte isDeal;

//    private Integer taskId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSrcUserId() {
        return srcUserId;
    }

    public void setSrcUserId(Integer srcUserId) {
        this.srcUserId = srcUserId;
    }

    public Integer getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Integer targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType == null ? null : objectType.trim();
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getObjectIds() {
        return objectIds;
    }

    public void setObjectIds(String objectIds) {
        this.objectIds = objectIds == null ? null : objectIds.trim();
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public String getObjectUrl() {
        return objectUrl;
    }

    public void setObjectUrl(String objectUrl) {
        this.objectUrl = objectUrl == null ? null : objectUrl.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Byte getOptionState() {
        return optionState;
    }

    public void setOptionState(Byte optionState) {
        this.optionState = optionState;
    }

    public Byte getIsAtall() {
        return isAtall;
    }

    public void setIsAtall(Byte isAtall) {
        this.isAtall = isAtall;
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getI18nKey() {
        return i18nKey;
    }

    public void setI18nKey(String i18nKey) {
        this.i18nKey = i18nKey == null ? null : i18nKey.trim();
    }

    public String getI18nParam() {
        return i18nParam;
    }

    public void setI18nParam(String i18nParam) {
        this.i18nParam = i18nParam == null ? null : i18nParam.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Byte getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(Byte isDeal) {
        this.isDeal = isDeal;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
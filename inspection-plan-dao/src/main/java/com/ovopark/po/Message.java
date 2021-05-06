package com.ovopark.po;

import java.io.Serializable;

/**
 * 消息
 * @author jxm
 *
 */
public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4967745256362770460L;
	
	/**
	 *  未处理
	 */
	public static int OPTION_NOT = 0;
	/**
	 *  已处理
	 */
	public static int OPTION_YES = 1;
	
	private long id;
	
	private Integer srcUserId;
	
	private Integer targetUserId;
	
	private String content;
	
	private String createTime;//yyyy-MM-dd HH:mm:ss

	private String description;
	
	
	/**
	 * 完整的类名
	 */
	private String objectType;
	
	private Integer objectId;
	
	private String objectIds;
	
	private Integer subId;
	
	private String objectURL;
	
	/**
	 * 状态
	 */
	private Integer status; //0:未读   1:已读

	/**
	 * 是否处理(默认未处理)
	 */
	private Integer option = 0;
	
	/**
	 * 是否@所有人
	 */
	private Integer isAtAll = 0;
	
	/**
	 * 是否@所有人
	 */
	private Integer enterpriseId;
	
	private String i18nKey;
	private String i18nParam;
	
	private String title;
	
	private Integer isDeal = 0;//是否处理  0：未处理  1：已处理

	private String category;

//	private Integer taskId;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getOption() {
		return option;
	}

	public void setOption(Integer option) {
		this.option = option;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
		this.content = content;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public Integer getSubId() {
		return subId;
	}

	public void setSubId(Integer subId) {
		this.subId = subId;
	}

	public String getObjectURL() {
		return objectURL;
	}

	public void setObjectURL(String objectURL) {
		this.objectURL = objectURL;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getObjectIds() {
		return objectIds;
	}

	public void setObjectIds(String objectIds) {
		this.objectIds = objectIds;
	}

	public Integer getIsAtAll() {
		return isAtAll;
	}

	public void setIsAtAll(Integer isAtAll) {
		this.isAtAll = isAtAll;
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
		this.i18nKey = i18nKey;
	}

	public String getI18nParam() {
		return i18nParam;
	}

	public void setI18nParam(String i18nParam) {
		this.i18nParam = i18nParam;
	}

	public Integer getIsDeal() {
		return isDeal;
	}

	public void setIsDeal(Integer isDeal) {
		this.isDeal = isDeal;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}

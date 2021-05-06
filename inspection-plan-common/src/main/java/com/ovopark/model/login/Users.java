package com.ovopark.model.login;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Users implements Serializable{
	
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 6814708140457447107L;

	private Integer id;

    private String userName;

    private String showName;

    private String password;

    private Integer deptIds;

    private String phone;

    private String mobilePhone;

    private String mail;

    private String localelan;

    private Integer unread = 0;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validateDate;

    private Integer pId;

    private Integer createrId;

    private String activateCode;

    private String privileges;

    private Integer isRegisterUser = 0;

    private Integer groupId;

    private String indicator;

    private Date createTime;

    private Integer userOrganId;

    private Date passwdUpdateTime;

    private Integer isSingleSign;

    private Integer isPhoneActivate;

    private Integer isMailActivate;

    private String employeeNumber;

    private Integer isDel;

    private String pyName;

    private Integer isAssign;

    private Integer isAgency;

    private Integer hasThumb;

    private Integer hasScoreConfig;

    private Integer exceptScoreRank;

    private Integer creater;

    private Integer isFrozen;

    private Date loginTime;

    private String dingdingUserId;

    private String thumbUpdateTime;

    private Integer superior;

    private String tlsName;

    private String thumbUrl;

    private String staffImageUrl;

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getTlsName() {
        return tlsName;
    }

    public void setTlsName(String tlsName) {
        this.tlsName = tlsName;
    }

    public  void setSuperior(Integer superior){this.superior=superior;}

    public  Integer getSuperior(){return superior;}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName == null ? null : showName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(Integer deptIds) {
        this.deptIds = deptIds;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail == null ? null : mail.trim();
    }

    public String getLocalelan() {
        return localelan;
    }

    public void setLocalelan(String localelan) {
        this.localelan = localelan == null ? null : localelan.trim();
    }

    public Integer getUnread() {
        return unread;
    }

    public void setUnread(Integer unread) {
        this.unread = unread;
    }

    public Date getValidateDate() {
        return validateDate;
    }

    public void setValidateDate(Date validateDate) {
        this.validateDate = validateDate;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }

    public String getActivateCode() {
        return activateCode;
    }

    public void setActivateCode(String activateCode) {
        this.activateCode = activateCode == null ? null : activateCode.trim();
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges == null ? null : privileges.trim();
    }

    public Integer getIsRegisterUser() {
        return isRegisterUser;
    }

    public void setIsRegisterUser(Integer isRegisterUser) {
        this.isRegisterUser = isRegisterUser;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator == null ? null : indicator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUserOrganId() {
        return userOrganId;
    }

    public void setUserOrganId(Integer userOrganId) {
        this.userOrganId = userOrganId;
    }

    public Date getPasswdUpdateTime() {
        return passwdUpdateTime;
    }

    public void setPasswdUpdateTime(Date passwdUpdateTime) {
        this.passwdUpdateTime = passwdUpdateTime;
    }

    public Integer getIsSingleSign() {
        return isSingleSign;
    }

    public void setIsSingleSign(Integer isSingleSign) {
        this.isSingleSign = isSingleSign;
    }

    public Integer getIsPhoneActivate() {
        return isPhoneActivate;
    }

    public void setIsPhoneActivate(Integer isPhoneActivate) {
        this.isPhoneActivate = isPhoneActivate;
    }

    public Integer getIsMailActivate() {
        return isMailActivate;
    }

    public void setIsMailActivate(Integer isMailActivate) {
        this.isMailActivate = isMailActivate;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber == null ? null : employeeNumber.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getPyName() {
        return pyName;
    }

    public void setPyName(String pyName) {
        this.pyName = pyName == null ? null : pyName.trim();
    }

    public Integer getIsAssign() {
        return isAssign;
    }

    public void setIsAssign(Integer isAssign) {
        this.isAssign = isAssign;
    }

    public Integer getIsAgency() {
        return isAgency;
    }

    public void setIsAgency(Integer isAgency) {
        this.isAgency = isAgency;
    }

    public Integer getHasThumb() {
        return hasThumb;
    }

    public void setHasThumb(Integer hasThumb) {
        this.hasThumb = hasThumb;
    }

    public Integer getHasScoreConfig() {
        return hasScoreConfig;
    }

    public void setHasScoreConfig(Integer hasScoreConfig) {
        this.hasScoreConfig = hasScoreConfig;
    }

    public Integer getExceptScoreRank() {
        return exceptScoreRank;
    }

    public void setExceptScoreRank(Integer exceptScoreRank) {
        this.exceptScoreRank = exceptScoreRank;
    }

    public Integer getCreater() {
        return creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    public Integer getIsFrozen() {
        return isFrozen;
    }

    public void setIsFrozen(Integer isFrozen) {
        this.isFrozen = isFrozen;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getDingdingUserId() {
        return dingdingUserId;
    }

    public void setDingdingUserId(String dingdingUserId) {
        this.dingdingUserId = dingdingUserId == null ? null : dingdingUserId.trim();
    }

    public String getThumbUpdateTime() {
        return thumbUpdateTime;
    }

    public void setThumbUpdateTime(String thumbUpdateTime) {
        this.thumbUpdateTime = thumbUpdateTime == null ? null : thumbUpdateTime.trim();
    }

    public String getStaffImageUrl() {
        return staffImageUrl;
    }

    public void setStaffImageUrl(String staffImageUrl) {
        this.staffImageUrl = staffImageUrl;
    }
}
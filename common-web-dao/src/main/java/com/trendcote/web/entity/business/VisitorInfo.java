package com.trendcote.web.entity.business;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trendcote.web.contrants.BusinessTable;
import java.util.Date;

/**
 * 访客信息表
 */
@TableName(BusinessTable.VisitorInfoT.TABLE_NAME)
public class VisitorInfo {
    @TableId
    @TableField(BusinessTable.VisitorInfoT.BASE_TABLE_ID)
	private Long id;
    @TableField(BusinessTable.VisitorInfoT.WX_OPEN_ID)
    private String wxOpenId;
    @TableField(BusinessTable.VisitorInfoT.VISITOR_NAME)
	private String visitorName;
    @TableField(BusinessTable.VisitorInfoT.VISITOR_COMPANY)
	private String visitorCompany;
    @TableField(BusinessTable.VisitorInfoT.VISITOR_CARD_TYPE)
	private Integer visitorCardType;
    @TableField(BusinessTable.VisitorInfoT.VISITOR_CARD_ID)
	private String visitorCardId;
    @TableField(BusinessTable.VisitorInfoT.VISITOR_CARD_ADDRESS)
	private String visitorCardAddress;
    @TableField(BusinessTable.VisitorInfoT.VISITOR_CARD_ISSUE)
	private String visitorCardIssue;
    @TableField(BusinessTable.VisitorInfoT.VISITOR_CARD_VALIDITY)
	private String visitorCardValidity;
	@TableField(BusinessTable.VisitorInfoT.VISITOR_DUTY)
	private String visitorDuty;
    @TableField(BusinessTable.VisitorInfoT.AGE)
	private String age;
    @TableField(BusinessTable.VisitorInfoT.GENDER)
	private String gender;
    @TableField(BusinessTable.VisitorInfoT.MOBILE)
	private String mobile;
    @TableField(BusinessTable.VisitorInfoT.ADDRESS)
	private String address;
    @TableField(BusinessTable.VisitorInfoT.VEHICLE_NO)
	private String vehicleNo;
    @TableField(BusinessTable.VisitorInfoT.TO_COMPANY)
	private String toCompany;
    @TableField(BusinessTable.VisitorInfoT.TO_DEPT)
	private String toDept;
    @TableField(BusinessTable.VisitorInfoT.TO_JOB)
	private String toJob;
    @TableField(BusinessTable.VisitorInfoT.TO_STAFF_ID)
	private String toStaffId;
    @TableField(BusinessTable.VisitorInfoT.TO_STAFF_NAME)
	private String toStaffName;
    @TableField(BusinessTable.VisitorInfoT.TO_ENTRY_IDS)
	private String toEntryIds;
    @TableField(BusinessTable.VisitorInfoT.TO_ENTRY_PHONES)
	private String toEntryPhones;
    @TableField(BusinessTable.VisitorInfoT.CERT_PHOTO)
    private String certPhoto;
    @TableField(BusinessTable.VisitorInfoT.CUR_PHOTO)
    private String curPhoto;
    @TableField(BusinessTable.VisitorInfoT.IN_TIME)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date inTime;
    @TableField(BusinessTable.VisitorInfoT.OUT_TIME)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date outTime;
    @TableField(BusinessTable.VisitorInfoT.PERSON_TYPE)
	private Integer personType;
    @TableField(BusinessTable.VisitorInfoT.REMARK)
	private String remark;
    @TableField(BusinessTable.VisitorInfoT.VISITOR_DEVICE_ID)
	private String visitorDeviceId;
    @TableField(BusinessTable.VisitorInfoT.VISITOR_TYPE)
	private Integer visitorType;
    @TableField(BusinessTable.VisitorInfoT.RESERVATION_TYPE)
	private Integer reservationType;
    @TableField(BusinessTable.VisitorInfoT.REGISTER_ACCOUNT)
	private String registerAccount;
    @TableField(BusinessTable.VisitorInfoT.BASE_TABLE_CREATE_TIME)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
    @TableField(BusinessTable.VisitorInfoT.BASE_TABLE_UPDATE_TIME)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
    @TableField(BusinessTable.VisitorInfoT.TIME_FLAG)
    private Integer timeFlag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWxOpenId() {
		return wxOpenId;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

	public String getVisitorName() {
		return visitorName;
	}

	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

	public String getVisitorCompany() {
		return visitorCompany;
	}

	public void setVisitorCompany(String visitorCompany) {
		this.visitorCompany = visitorCompany;
	}

	public Integer getVisitorCardType() {
		return visitorCardType;
	}

	public void setVisitorCardType(Integer visitorCardType) {
		this.visitorCardType = visitorCardType;
	}

	public String getVisitorCardId() {
		return visitorCardId;
	}

	public void setVisitorCardId(String visitorCardId) {
		this.visitorCardId = visitorCardId;
	}

	public String getVisitorCardAddress() {
		return visitorCardAddress;
	}

	public void setVisitorCardAddress(String visitorCardAddress) {
		this.visitorCardAddress = visitorCardAddress;
	}

	public String getVisitorCardIssue() {
		return visitorCardIssue;
	}

	public void setVisitorCardIssue(String visitorCardIssue) {
		this.visitorCardIssue = visitorCardIssue;
	}

	public String getVisitorCardValidity() {
		return visitorCardValidity;
	}

	public void setVisitorCardValidity(String visitorCardValidity) {
		this.visitorCardValidity = visitorCardValidity;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getToCompany() {
		return toCompany;
	}

	public void setToCompany(String toCompany) {
		this.toCompany = toCompany;
	}

	public String getToDept() {
		return toDept;
	}

	public void setToDept(String toDept) {
		this.toDept = toDept;
	}

	public String getToJob() {
		return toJob;
	}

	public void setToJob(String toJob) {
		this.toJob = toJob;
	}

	public String getToStaffId() {
		return toStaffId;
	}

	public void setToStaffId(String toStaffId) {
		this.toStaffId = toStaffId;
	}

	public String getToStaffName() {
		return toStaffName;
	}

	public void setToStaffName(String toStaffName) {
		this.toStaffName = toStaffName;
	}

	public String getToEntryIds() {
		return toEntryIds;
	}

	public void setToEntryIds(String toEntryIds) {
		this.toEntryIds = toEntryIds;
	}

	public String getToEntryPhones() {
		return toEntryPhones;
	}

	public void setToEntryPhones(String toEntryPhones) {
		this.toEntryPhones = toEntryPhones;
	}

	public String getCertPhoto() {
		return certPhoto;
	}

	public void setCertPhoto(String certPhoto) {
		this.certPhoto = certPhoto;
	}

	public String getCurPhoto() {
		return curPhoto;
	}

	public void setCurPhoto(String curPhoto) {
		this.curPhoto = curPhoto;
	}

	public Date getInTime() {
		return inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public Integer getPersonType() {
		return personType;
	}

	public void setPersonType(Integer personType) {
		this.personType = personType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVisitorDeviceId() {
		return visitorDeviceId;
	}

	public void setVisitorDeviceId(String visitorDeviceId) {
		this.visitorDeviceId = visitorDeviceId;
	}

	public Integer getVisitorType() {
		return visitorType;
	}

	public void setVisitorType(Integer visitorType) {
		this.visitorType = visitorType;
	}

	public Integer getReservationType() {
		return reservationType;
	}

	public void setReservationType(Integer reservationType) {
		this.reservationType = reservationType;
	}

	public String getRegisterAccount() {
		return registerAccount;
	}

	public void setRegisterAccount(String registerAccount) {
		this.registerAccount = registerAccount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getTimeFlag() {
		return timeFlag;
	}

	public void setTimeFlag(Integer timeFlag) {
		this.timeFlag = timeFlag;
	}

    public String getVisitorDuty() {
        return visitorDuty;
    }

    public void setVisitorDuty(String visitorDuty) {
        this.visitorDuty = visitorDuty;
    }
}
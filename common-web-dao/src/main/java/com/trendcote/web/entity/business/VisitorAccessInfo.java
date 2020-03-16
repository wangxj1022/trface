package com.trendcote.web.entity.business;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trendcote.web.contrants.BusinessTable;

import java.util.Date;

/**
 * 访客访问记录表
 */
@TableName(BusinessTable.VisitorAccessT.TABLE_NAME)
public class VisitorAccessInfo {

	@TableField(BusinessTable.VisitorAccessT.BASE_TABLE_ID)
	@TableId
	private Long id;
	@TableField(BusinessTable.VisitorAccessT.ACCESS_ID)
	private String accessId;
	@TableField(BusinessTable.VisitorAccessT.PERSON_ID)
	private String personId;
	@TableField(BusinessTable.VisitorAccessT.STAFF_ID)
	private String staffId;
	@TableField(BusinessTable.VisitorAccessT.STAFF_NAME)
	private String staffName;
	@TableField(BusinessTable.VisitorAccessT.STAFF_DEPT)
	private String staffDept;
	@TableField(BusinessTable.VisitorAccessT.STAFF_PHONE)
	private String staffPhone;
	@TableField(BusinessTable.VisitorAccessT.PERSON_NAME)
	private String personName;
	@TableField(BusinessTable.VisitorAccessT.PERSON_COMPANY)
	private String personCompany;
	@TableField(BusinessTable.VisitorAccessT.PERSON_PHONE)
	private String personPhone;
	@TableField(BusinessTable.VisitorAccessT.PERSON_CODE)
	private String personCode;
	@TableField(BusinessTable.VisitorAccessT.PERSON_GENDER)
	private String personGender;
	@TableField(BusinessTable.VisitorAccessT.PERSON_BIRTHDAY)
	@JSONField(format = "yyyy-MM-dd")
	private Date personBirthday;
	@TableField(BusinessTable.VisitorAccessT.PERSON_ADDRESS)
	private String personAddress;
	@TableField(BusinessTable.VisitorAccessT.PERSON_DUTY)
	private String personDuty;
	@TableField(BusinessTable.VisitorAccessT.IN_DEVICE_ID)
	private String inDeviceId;
	@TableField(BusinessTable.VisitorAccessT.IN_TIME)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date inTime;
	@TableField(BusinessTable.VisitorAccessT.IN_CAPTURE_IMAEG)
	private String inCaptureImage;
	@TableField(BusinessTable.VisitorAccessT.IN_COMPARE_IMAGE)
	private String inCompareImage;
	@TableField(BusinessTable.VisitorAccessT.IN_COMPARE_SCORE)
	private int inCompareScore;
	@TableField(BusinessTable.VisitorAccessT.IN_COMPARE_RESULT)
	private int inCompareResult;
	@TableField(BusinessTable.VisitorAccessT.IN_COMPARE_DELAY)
	private int inCompareDelay;
	@TableField(BusinessTable.VisitorAccessT.IN_PERSON_TYPE)
	private int inPersonType;
	@TableField(BusinessTable.VisitorAccessT.OUT_DEVICE_ID)
	private String outDeviceId;
	@TableField(BusinessTable.VisitorAccessT.OUT_TIME)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date outTime;
	@TableField(BusinessTable.VisitorAccessT.OUT_CAPTURE_IMAEG)
	private String outCaptureImage;
	@TableField(BusinessTable.VisitorAccessT.OUT_COMPARE_IMAGE)
	private String outCompareImage;
	@TableField(BusinessTable.VisitorAccessT.OUT_COMPARE_SCORE)
	private int outCompareScore;
	@TableField(BusinessTable.VisitorAccessT.OUT_COMPARE_RESULT)
	private int outCompareResult;
	@TableField(BusinessTable.VisitorAccessT.OUT_COMPARE_DELAY)
	private int outCompareDelay;
	@TableField(BusinessTable.VisitorAccessT.OUT_PERSON_TYPE)
	private int outPersonType;
	@TableField(BusinessTable.VisitorAccessT.ACCESS_STATSU)
	private int accessStatus;
	@TableField(BusinessTable.VisitorAccessT.ACCESS_REASON)
	private String accessReason;
	@TableField(BusinessTable.VisitorAccessT.BASE_TABLE_CREATE_TIME)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	@TableField(BusinessTable.VisitorAccessT.BASE_TABLE_UPDATE_TIME)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	public String getPersonGender() {
		return personGender;
	}

	public void setPersonGender(String personGender) {
		this.personGender = personGender;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPersonPhone() {
		return personPhone;
	}

	public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public String getAccessReason() {
		return accessReason;
	}

	public void setAccessReason(String accessReason) {
		this.accessReason = accessReason;
	}

	public void setPersonPhone(String personPhone) {
		this.personPhone = personPhone;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffDept() {
		return staffDept;
	}

	public void setStaffDept(String staffDept) {
		this.staffDept = staffDept;
	}

	public String getStaffPhone() {
		return staffPhone;
	}

	public void setStaffPhone(String staffPhone) {
		this.staffPhone = staffPhone;
	}

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public Date getPersonBirthday() {
		return personBirthday;
	}

	public void setPersonBirthday(Date personBirthday) {
		this.personBirthday = personBirthday;
	}

	public String getPersonAddress() {
		return personAddress;
	}

	public void setPersonAddress(String personAddress) {
		this.personAddress = personAddress;
	}

	public String getInDeviceId() {
		return inDeviceId;
	}

	public void setInDeviceId(String inDeviceId) {
		this.inDeviceId = inDeviceId;
	}

	public Date getInTime() {
		return inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	public String getInCaptureImage() {
		return inCaptureImage;
	}

	public void setInCaptureImage(String inCaptureImage) {
		this.inCaptureImage = inCaptureImage;
	}

	public String getInCompareImage() {
		return inCompareImage;
	}

	public void setInCompareImage(String inCompareImage) {
		this.inCompareImage = inCompareImage;
	}

	public int getInCompareScore() {
		return inCompareScore;
	}

	public void setInCompareScore(int inCompareScore) {
		this.inCompareScore = inCompareScore;
	}

	public int getInCompareResult() {
		return inCompareResult;
	}

	public void setInCompareResult(int inCompareResult) {
		this.inCompareResult = inCompareResult;
	}

	public int getInCompareDelay() {
		return inCompareDelay;
	}

	public void setInCompareDelay(int inCompareDelay) {
		this.inCompareDelay = inCompareDelay;
	}

	public int getInPersonType() {
		return inPersonType;
	}

	public void setInPersonType(int inPersonType) {
		this.inPersonType = inPersonType;
	}

	public String getOutDeviceId() {
		return outDeviceId;
	}

	public void setOutDeviceId(String outDeviceId) {
		this.outDeviceId = outDeviceId;
	}

	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public String getOutCaptureImage() {
		return outCaptureImage;
	}

	public void setOutCaptureImage(String outCaptureImage) {
		this.outCaptureImage = outCaptureImage;
	}

	public String getOutCompareImage() {
		return outCompareImage;
	}

	public void setOutCompareImage(String outCompareImage) {
		this.outCompareImage = outCompareImage;
	}

	public int getOutCompareScore() {
		return outCompareScore;
	}

	public void setOutCompareScore(int outCompareScore) {
		this.outCompareScore = outCompareScore;
	}

	public int getOutCompareResult() {
		return outCompareResult;
	}

	public void setOutCompareResult(int outCompareResult) {
		this.outCompareResult = outCompareResult;
	}

	public int getOutCompareDelay() {
		return outCompareDelay;
	}

	public void setOutCompareDelay(int outCompareDelay) {
		this.outCompareDelay = outCompareDelay;
	}

	public int getOutPersonType() {
		return outPersonType;
	}

	public void setOutPersonType(int outPersonType) {
		this.outPersonType = outPersonType;
	}

	public int getAccessStatus() {
		return accessStatus;
	}

	public void setAccessStatus(int accessStatus) {
		this.accessStatus = accessStatus;
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

	public String getPersonDuty() {
		return personDuty;
	}

	public void setPersonDuty(String personDuty) {
		this.personDuty = personDuty;
	}

	public String getPersonCompany() {
		return personCompany;
	}

	public void setPersonCompany(String personCompany) {
		this.personCompany = personCompany;
	}
}

package com.trendcote.web.entity.business;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trendcote.web.contrants.BusinessTable;

import java.util.Date;

/**
 * 员工访问记录表
 */
@TableName(BusinessTable.StaffAccessT.TABLE_NAME)
public class StaffAccessInfo {
    @TableField(BusinessTable.StaffAccessT.BASE_TABLE_ID)
    @TableId
	private Integer id;
    @TableField(BusinessTable.StaffAccessT.STAFF_NAME)
	private String staffName;
    @TableField(BusinessTable.StaffAccessT.STAFF_PHONE)
	private String staffPhone;
    @TableField(BusinessTable.StaffAccessT.STAFF_CODE)
	private String staffCode;
    @TableField(BusinessTable.StaffAccessT.STAFF_GENDER)
	private int staffGender;
    @TableField(BusinessTable.StaffAccessT.STAFF_NATION)
    private int staffNation;
    @TableField(BusinessTable.StaffAccessT.STAFF_BIRTHDAY)
	@JSONField(format = "yyyy-MM-dd")
	private Date staffBirthday;
    @TableField(BusinessTable.StaffAccessT.STAFF_ADDRESS)
	private String staffAddress;
    @TableField(BusinessTable.StaffAccessT.IN_DEVICE_ID)
	private String inDeviceId;
    @TableField(BusinessTable.StaffAccessT.IN_TIME)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date inTime;
    @TableField(BusinessTable.StaffAccessT.IN_CAPTURE_IMAEG)
	private String inCaptureImage;
    @TableField(BusinessTable.StaffAccessT.IN_COMPARE_IMAGE)
	private String inCompareImage;
    @TableField(BusinessTable.StaffAccessT.IN_COMPARE_SCORE)
	private int inCompareScore;
    @TableField(BusinessTable.StaffAccessT.IN_COMPARE_RESULT)
	private int inCompareResult;
    @TableField(BusinessTable.StaffAccessT.IN_COMPARE_DELAY)
	private int inCompareDelay;
    @TableField(BusinessTable.StaffAccessT.IN_PERSON_TYPE)
	private int inPersonType;
    @TableField(BusinessTable.StaffAccessT.OUT_DEVICE_ID)
	private String outDeviceId;
    @TableField(BusinessTable.StaffAccessT.OUT_TIME)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date outTime;
    @TableField(BusinessTable.StaffAccessT.OUT_CAPTURE_IMAEG)
	private String outCaptureImage;
    @TableField(BusinessTable.StaffAccessT.OUT_COMPARE_IMAGE)
	private String outCompareImage;
    @TableField(BusinessTable.StaffAccessT.OUT_COMPARE_SCORE)
	private int outCompareScore;
    @TableField(BusinessTable.StaffAccessT.OUT_COMPARE_RESULT)
	private int outCompareResult;
    @TableField(BusinessTable.StaffAccessT.OUT_COMPARE_DELAY)
	private int outCompareDelay;
    @TableField(BusinessTable.StaffAccessT.OUT_PERSON_TYPE)
	private int outPersonType;
    @TableField(BusinessTable.StaffAccessT.ACCESS_STATSU)
	private int accessStatus;
    @TableField(BusinessTable.StaffAccessT.BASE_TABLE_CREATE_TIME)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
    @TableField(BusinessTable.StaffAccessT.BASE_TABLE_UPDATE_TIME)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
    @TableField(exist = false)
    private String deptName;
	@TableField(BusinessTable.StaffAccessT.STATUS)
    private int status;
	@TableField(BusinessTable.StaffAccessT.TEMPERATURE)
	private float temperature;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public String getStaffPhone() {
		return staffPhone;
	}

	public void setStaffPhone(String staffPhone) {
		this.staffPhone = staffPhone;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

    public void setId(Integer id) {
        this.id = id;
    }

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public Date getStaffBirthday() {
		return staffBirthday;
	}

	public void setStaffBirthday(Date staffBirthday) {
		this.staffBirthday = staffBirthday;
	}

	public String getStaffAddress() {
		return staffAddress;
	}

	public void setStaffAddress(String staffAddress) {
		this.staffAddress = staffAddress;
	}

    public int getStaffGender() {
        return staffGender;
    }

    public void setStaffGender(int staffGender) {
        this.staffGender = staffGender;
    }

    public int getStaffNation() {
        return staffNation;
    }

    public void setStaffNation(int staffNation) {
        this.staffNation = staffNation;
    }

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
}

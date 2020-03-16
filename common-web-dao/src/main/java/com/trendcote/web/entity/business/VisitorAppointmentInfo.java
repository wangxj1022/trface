package com.trendcote.web.entity.business;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.trendcote.web.contrants.BusinessTable;

import java.util.Date;

/**
 * 访客预约表
 */
public class VisitorAppointmentInfo {

    @TableId
    @TableField(BusinessTable.VisitorAppointmentInfoT.BASE_TABLE_ID)
    private Long id;
    @TableField(BusinessTable.VisitorAppointmentInfoT.APPOINTMENT_ID)
    private String appointmentId;
    @TableField(BusinessTable.VisitorAppointmentInfoT.STAFF_ID)
    private String staffId;
    @TableField(BusinessTable.VisitorAppointmentInfoT.STAFF_NAME)
    private String staffName;
    @TableField(BusinessTable.VisitorAppointmentInfoT.STAFF_DEPT)
    private String staffDept;
    @TableField(BusinessTable.VisitorAppointmentInfoT.STAFF_PHONE)
    private String staffPhone;
    @TableField(BusinessTable.VisitorAppointmentInfoT.PERSON_ID)
    private String personId;
    @TableField(BusinessTable.VisitorAppointmentInfoT.PERSON_NAME)
    private String personName;
    @TableField(BusinessTable.VisitorAppointmentInfoT.PERSON_CODE)
    private String personCode;
    @TableField(BusinessTable.VisitorAppointmentInfoT.PERSON_GENDER)
    private String personGender;
    @TableField(BusinessTable.VisitorAppointmentInfoT.PERSON_BIRTHDAY)
    @JSONField(format = "yyyy-MM-dd")
    private Date personBirthday;
    @TableField(BusinessTable.VisitorAppointmentInfoT.PERSON_ADDRESS)
    private String personAddress;
    @TableField(BusinessTable.VisitorAppointmentInfoT.PERSON_COMPANY)
    private String personCompany;
    @TableField(BusinessTable.VisitorAppointmentInfoT.PERSON_PHONE)
    private String personPhone;
    @TableField(BusinessTable.VisitorAppointmentInfoT.PERSON_DUTY)
    private String personDuty;
    @TableField(BusinessTable.VisitorAppointmentInfoT.PERSON_CARD_PHOTO)
    private String personCardPhoto;
    @TableField(BusinessTable.VisitorAppointmentInfoT.PERSON_PHOTO)
    private String personPhoto;
    @TableField(BusinessTable.VisitorAppointmentInfoT.BEGIN_TIME)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    @TableField(BusinessTable.VisitorAppointmentInfoT.END_TIME)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @TableField(BusinessTable.VisitorAppointmentInfoT.APPOINTMENT_STATUS)
    private Integer appointmentStatus;
    @TableField(BusinessTable.VisitorAppointmentInfoT.APPOINTMENT_REASON)
    private String appointmentReason;
    @TableField(BusinessTable.VisitorAppointmentInfoT.APPOINTMENT_REMARK)
    private String appointmentRemark;
    @TableField(BusinessTable.VisitorAppointmentInfoT.BASE_TABLE_CREATE_TIME)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @TableField(BusinessTable.VisitorAppointmentInfoT.BASE_TABLE_UPDATE_TIME)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
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

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonCode() {
        return personCode;
    }

    public void setPersonCode(String personCode) {
        this.personCode = personCode;
    }

    public String getPersonGender() {
        return personGender;
    }

    public void setPersonGender(String personGender) {
        this.personGender = personGender;
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

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(Integer appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getAppointmentReason() {
        return appointmentReason;
    }

    public void setAppointmentReason(String appointmentReason) {
        this.appointmentReason = appointmentReason;
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

    public String getPersonCompany() {
        return personCompany;
    }

    public void setPersonCompany(String personCompany) {
        this.personCompany = personCompany;
    }

    public String getPersonPhone() {
        return personPhone;
    }

    public void setPersonPhone(String personPhone) {
        this.personPhone = personPhone;
    }

    public String getPersonDuty() {
        return personDuty;
    }

    public void setPersonDuty(String personDuty) {
        this.personDuty = personDuty;
    }

    public String getPersonCardPhoto() {
        return personCardPhoto;
    }

    public void setPersonCardPhoto(String personCardPhoto) {
        this.personCardPhoto = personCardPhoto;
    }

    public String getPersonPhoto() {
        return personPhoto;
    }

    public void setPersonPhoto(String personPhoto) {
        this.personPhoto = personPhoto;
    }

    public String getAppointmentRemark() {
        return appointmentRemark;
    }

    public void setAppointmentRemark(String appointmentRemark) {
        this.appointmentRemark = appointmentRemark;
    }
}

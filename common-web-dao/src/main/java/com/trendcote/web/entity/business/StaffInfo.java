package com.trendcote.web.entity.business;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trendcote.web.contrants.BusinessTable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 员工信息表
 */
@TableName(BusinessTable.StaffT.TABLE_NAME)
public class StaffInfo {
    @TableId
    @TableField(BusinessTable.StaffT.BASE_TABLE_ID)
    private Integer id;
    @TableField(BusinessTable.StaffT.STAFF_ID)
    private String staffId;
    @TableField(BusinessTable.StaffT.WX_OPEN_ID)
    private String wxOpenId;
    @TableField(BusinessTable.StaffT.STAFF_NAME)
    private String staffName;
    @TableField(BusinessTable.StaffT.IS_OUT_WORK)
    private Integer isOutWork;
    @TableField(BusinessTable.StaffT.STAFF_ID_CARDNO)
    private String staffIdCardno;
    @TableField(BusinessTable.StaffT.STAFF_CARD_TYPE)
    private Integer staffCardType;
    @TableField(BusinessTable.StaffT.ENTRY_IDS)
    private String entryIds;
    @TableField(BusinessTable.StaffT.STAFF_CARD_ID)
    private String staffCardId;
    @TableField(BusinessTable.StaffT.AGE)
    private String age;
    @TableField(BusinessTable.StaffT.GENDER)
    private String gender;
    @TableField(BusinessTable.StaffT.MARITAL_STATUS)
    private Integer maritalStatus;
    @TableField(BusinessTable.StaffT.NATION)
    private String nation;
    @TableField(BusinessTable.StaffT.BIRTH_PLACE)
    private String birthPlace;
    @TableField(BusinessTable.StaffT.BIRTH_DATE)
    @JSONField(format = "yyyy-MM-dd")
    private Date birthDate;
    @TableField(BusinessTable.StaffT.POLITICAL_STATUS)
    private String politicalStatus;
    @TableField(BusinessTable.StaffT.COUNTRY)
    private String country;
    @TableField(BusinessTable.StaffT.EDUCATIONAL_BACKGROUND)
    private Integer educationalBackground;
    @TableField(BusinessTable.StaffT.MOBILE)
    private String mobile;
    @TableField(BusinessTable.StaffT.PHONE)
    private String phone;
    @TableField(BusinessTable.StaffT.ADDRESS)
    private String address;
    @TableField(BusinessTable.StaffT.VEHICLE_NO)
    private String vehicleNo;
    @TableField(BusinessTable.StaffT.EMAIL)
    private String email;
    @TableField(BusinessTable.StaffT.DEPT_ID)
    private String deptId;
    @TableField(BusinessTable.StaffT.JOB)
    private String job;
    @TableField(BusinessTable.StaffT.STAFF_DESC)
    private String staffDesc;
    @TableField(BusinessTable.StaffT.PERSON_TYPE)
    private Integer personType;
    @TableField(BusinessTable.StaffT.BASE_TABLE_CREATE_TIME)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @TableField(BusinessTable.StaffT.CERT_PHOTO)
    private String certPhoto;
    @TableField(BusinessTable.StaffT.CUR_PHOTO)
    private String curPhoto;
    @TableField(BusinessTable.StaffT.STATUS)
    private Integer status;
    @TableField(BusinessTable.StaffT.STAFF_STATUS)
    private Integer staffStatus;

    public Integer getStaffStatus() {
        return staffStatus;
    }

    public void setStaffStatus(Integer staffStatus) {
        this.staffStatus = staffStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId == null ? null : staffId.trim();
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName == null ? null : staffName.trim();
    }

    public Integer getStaffCardType() {
        return staffCardType;
    }

    public void setStaffCardType(Integer staffCardType) {
        this.staffCardType = staffCardType;
    }

    public String getEntryIds() {
        return entryIds;
    }

    public void setEntryIds(String entryIds) {
        this.entryIds = entryIds == null ? null : entryIds.trim();
    }

    public String getStaffCardId() {
        return staffCardId;
    }

    public void setStaffCardId(String staffCardId) {
        this.staffCardId = staffCardId == null ? null : staffCardId.trim();
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age == null ? null : age.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace == null ? null : birthPlace.trim();
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus == null ? null : politicalStatus.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public Integer getEducationalBackground() {
        return educationalBackground;
    }

    public void setEducationalBackground(Integer educationalBackground) {
        this.educationalBackground = educationalBackground;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo == null ? null : vehicleNo.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public String getStaffDesc() {
        return staffDesc;
    }

    public void setStaffDesc(String staffDesc) {
        this.staffDesc = staffDesc == null ? null : staffDesc.trim();
    }

    public Integer getPersonType() {
        return personType;
    }

    public void setPersonType(Integer personType) {
        this.personType = personType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCertPhoto() {
        return certPhoto;
    }

    public void setCertPhoto(String certPhoto) {
        this.certPhoto = certPhoto == null ? null : certPhoto.trim();
    }

    public String getCurPhoto() {
        return curPhoto;
    }

	public void setCurPhoto(String curPhoto) {
        this.curPhoto = curPhoto == null ? null : curPhoto.trim();
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public Integer getIsOutWork() {
        return isOutWork;
    }

    public void setIsOutWork(Integer isOutWork) {
        this.isOutWork = isOutWork;
    }

    public String getStaffIdCardno() {
        return staffIdCardno;
    }

    public void setStaffIdCardno(String staffIdCardno) {
        this.staffIdCardno = staffIdCardno;
    }
}
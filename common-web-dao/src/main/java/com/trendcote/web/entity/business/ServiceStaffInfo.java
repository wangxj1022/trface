package com.trendcote.web.entity.business;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trendcote.web.contrants.BusinessTable;

import java.util.Date;

/**
 * 特殊人员信息表
 */
@TableName(BusinessTable.ServiceStaffInfoT.TABLE_NAME)
public class ServiceStaffInfo {
    @TableId
    @TableField(BusinessTable.ServiceStaffInfoT.BASE_TABLE_ID)
    private Long id;
    @TableField(BusinessTable.ServiceStaffInfoT.SERVICE_STAFF_DEPT)
    private String serviceStaffDept;
    @TableField(BusinessTable.ServiceStaffInfoT.SERVICE_STAFF_NAME)
    private String serviceStaffName;
    @TableField(BusinessTable.ServiceStaffInfoT.SERVICE_STAFF_NUMBER)
    private String serviceStaffNumber;
    @TableField(BusinessTable.ServiceStaffInfoT.SERVICE_STAFF_TEL)
    private String serviceStaffTel;
    @TableField(BusinessTable.ServiceStaffInfoT.SERVICE_STAFF_ICNO)
    private String serviceStaffIcNo;
    @TableField(BusinessTable.ServiceStaffInfoT.SERVICE_STAFF_PHOTO)
    private String serviceStaffPhoto;
    @TableField(BusinessTable.ServiceStaffInfoT.SERVICE_STAFF_IDCARD)
    private String serviceStaffIdCard;
    @TableField(BusinessTable.ServiceStaffInfoT.IS_OUT_WORK)
    private Integer isOutWork;
    @TableField(BusinessTable.ServiceStaffInfoT.STATUS)
    private Integer status;
    @TableField(BusinessTable.ServiceStaffInfoT.PIC_STATUS)
    private Integer picStatus;
    @TableField(BusinessTable.ServiceStaffInfoT.REMARK)
    private String remark;
    @TableField(BusinessTable.StaffT.BASE_TABLE_CREATE_TIME)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @TableField(BusinessTable.StaffT.BASE_TABLE_UPDATE_TIME)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @TableField(BusinessTable.ServiceStaffInfoT.UPDATE_FLAG)
    private int updateflag;
    @TableField(BusinessTable.ServiceStaffInfoT.DEPTNAME)
    private String deptname;

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public int getUpdateflag() {
        return updateflag;
    }

    public void setUpdateflag(int updateflag) {
        this.updateflag = updateflag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceStaffDept() {
        return serviceStaffDept;
    }

    public void setServiceStaffDept(String serviceStaffDept) {
        this.serviceStaffDept = serviceStaffDept;
    }

    public String getServiceStaffName() {
        return serviceStaffName;
    }

    public void setServiceStaffName(String serviceStaffName) {
        this.serviceStaffName = serviceStaffName;
    }

    public String getServiceStaffNumber() {
        return serviceStaffNumber;
    }

    public void setServiceStaffNumber(String serviceStaffNumber) {
        this.serviceStaffNumber = serviceStaffNumber;
    }

    public String getServiceStaffTel() {
        return serviceStaffTel;
    }

    public void setServiceStaffTel(String serviceStaffTel) {
        this.serviceStaffTel = serviceStaffTel;
    }

    public String getServiceStaffIcNo() {
        return serviceStaffIcNo;
    }

    public void setServiceStaffIcNo(String serviceStaffIcNo) {
        this.serviceStaffIcNo = serviceStaffIcNo;
    }

    public String getServiceStaffPhoto() {
        return serviceStaffPhoto;
    }

    public void setServiceStaffPhoto(String serviceStaffPhoto) {
        this.serviceStaffPhoto = serviceStaffPhoto;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getServiceStaffIdCard() {
        return serviceStaffIdCard;
    }

    public void setServiceStaffIdCard(String serviceStaffIdCard) {
        this.serviceStaffIdCard = serviceStaffIdCard;
    }

    public Integer getIsOutWork() {
        return isOutWork;
    }

    public void setIsOutWork(Integer isOutWork) {
        this.isOutWork = isOutWork;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPicStatus() {
        return picStatus;
    }

    public void setPicStatus(Integer picStatus) {
        this.picStatus = picStatus;
    }

}
package com.trendcote.web.entity.business;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trendcote.web.contrants.BusinessTable;

import java.util.Date;

/**
 *  昆泰信息表
 */
@TableName(BusinessTable.StaffktInfoT.TABLE_NAME)
public class StaffktInfo {
    @TableId
    @TableField(BusinessTable.StaffktInfoT.BASE_TABLE_ID)
    private Long id;
    @TableField(BusinessTable.StaffktInfoT.STAFF_KT_DEPT)
    private String staffktDept;
    @TableField(BusinessTable.StaffktInfoT.STAFF_KT_NAME)
    private String staffktName;
    @TableField(BusinessTable.StaffktInfoT.STAFF_KT_NUMBER)
    private String staffktNumber;
    @TableField(BusinessTable.StaffktInfoT.STAFF_KT_TEL)
    private String staffktTel;
    @TableField(BusinessTable.StaffktInfoT.STAFF_KT_ICNO)
    private String staffktIcNo;
    @TableField(BusinessTable.StaffktInfoT.STAFF_KT_PHOTO)
    private String staffktPhoto;
    @TableField(BusinessTable.StaffktInfoT.STAFF_KT_IDCARD)
    private String staffktIdCard;
    @TableField(BusinessTable.StaffktInfoT.IS_OUT_WORK)
    private Integer isOutWork;
    @TableField(BusinessTable.StaffktInfoT.REMARK)
    private String remark;
    @TableField(BusinessTable.StaffktInfoT.BASE_TABLE_CREATE_TIME)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @TableField(BusinessTable.StaffktInfoT.BASE_TABLE_UPDATE_TIME)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableField(BusinessTable.StaffktInfoT.UPDATE_FLAG)
    private Integer updateFlag;
    @TableField(BusinessTable.StaffktInfoT.SYNC_RETRYCNT)
    private Integer syncRetryCnt;
    @TableField(BusinessTable.StaffktInfoT.STATUS)
    private Integer status;
    @TableField(BusinessTable.StaffktInfoT.PIC_STATUS)
    private Integer picStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStaffktDept() {
        return staffktDept;
    }

    public void setStaffktDept(String staffktDept) {
        this.staffktDept = staffktDept;
    }

    public String getStaffktName() {
        return staffktName;
    }

    public void setStaffktName(String staffktName) {
        this.staffktName = staffktName;
    }

    public String getStaffktNumber() {
        return staffktNumber;
    }

    public void setStaffktNumber(String staffktNumber) {
        this.staffktNumber = staffktNumber;
    }

    public String getStaffktTel() {
        return staffktTel;
    }

    public void setStaffktTel(String staffktTel) {
        this.staffktTel = staffktTel;
    }

    public String getStaffktIcNo() {
        return staffktIcNo;
    }

    public void setStaffktIcNo(String staffktIcNo) {
        this.staffktIcNo = staffktIcNo;
    }

    public String getStaffktPhoto() {
        return staffktPhoto;
    }

    public void setStaffktPhoto(String staffktPhoto) {
        this.staffktPhoto = staffktPhoto;
    }

    public String getStaffktIdCard() {
        return staffktIdCard;
    }

    public void setStaffktIdCard(String staffktIdCard) {
        this.staffktIdCard = staffktIdCard;
    }

    public Integer getIsOutWork() {
        return isOutWork;
    }

    public void setIsOutWork(Integer isOutWork) {
        this.isOutWork = isOutWork;
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

    public Integer getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(Integer updateFlag) {
        this.updateFlag = updateFlag;
    }

    public Integer getSyncRetryCnt() {
        return syncRetryCnt;
    }

    public void setSyncRetryCnt(Integer syncRetryCnt) {
        this.syncRetryCnt = syncRetryCnt;
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
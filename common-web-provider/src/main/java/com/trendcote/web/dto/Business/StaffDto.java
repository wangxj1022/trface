package com.trendcote.web.dto.Business;

/**
 * @author 莹
 * @date 2018/6/4
 */
public class StaffDto
{
    private String staffCode;   //员工号

    private Integer staffStatus;

    private String staffName;

    private String staffICCode; // 员工IC卡号

    private String outDeviceId;  // 设备IP

    public String getOutDeviceId() {
        return outDeviceId;
    }

    public void setOutDeviceId(String outDeviceId) {
        this.outDeviceId = outDeviceId;
    }

    public String getStaffICCode() {
        return staffICCode;
    }

    public void setStaffICCode(String staffICCode) {
        this.staffICCode = staffICCode;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Integer getStaffStatus() {
        return staffStatus;
    }

    public void setStaffStatus(Integer staffStatus) {
        this.staffStatus = staffStatus;
    }
}

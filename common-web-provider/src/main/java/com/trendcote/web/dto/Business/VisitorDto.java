package com.trendcote.web.dto.Business;

/**
 * @author 莹
 * @date 2018/6/4
 */
public class VisitorDto {

    private String visitorCompany;  //访客公司
    private String visitorName; //访客名
    private String mobile;  //访客手机
    private String inDeviceId; // 设备IP

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVisitorCompany() {
        return visitorCompany;
    }

    public void setVisitorCompany(String visitorCompany) {
        this.visitorCompany = visitorCompany;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getInDeviceId() {
        return inDeviceId;
    }

    public void setInDeviceId(String inDeviceId) {
        this.inDeviceId = inDeviceId;
    }
}

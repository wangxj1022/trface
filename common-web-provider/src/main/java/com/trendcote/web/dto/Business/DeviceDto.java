package com.trendcote.web.dto.Business;

/**
 * @Description 设备类
 * @Date 2019/6/13 18:13
 * @Created by xym
 */

public class DeviceDto {

    private String entryName;  //进出口名称 例： 东门、西门、南门、北门

    private String deviceId;    // 设备编号

    private String deviceIp;    //设备IP地址

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }
}

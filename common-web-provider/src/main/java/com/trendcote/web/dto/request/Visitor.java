package com.trendcote.web.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *@Description 请求设备接口: 访客对象
 */
public class Visitor {
    private String name;        //姓名
    private String idCardNo;    //身份证
    private String beginTime;   //开始时间 yyyy-MM-dd HH:mm:ss
    private String endTime;     //离开时间 yyyy-MM-dd HH:mm:ss

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "name='" + name + '\'' +
                ", idCardNo='" + idCardNo + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}

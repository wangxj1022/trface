package com.trendcote.web.dto.Business;

/**
 * @Description 进出口信息
 * @Date 2019/6/17 15:37
 * @Created by xym
 */

public class EntryDto {
    private String entryName;  //进出口名称 例： 东门、西门、南门、北门

    private Integer entryCategory;  //出入口类别： 0：员工通道，1：访客通道，2：员工和访客通道

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public Integer getEntryCategory() {
        return entryCategory;
    }

    public void setEntryCategory(Integer entryCategory) {
        this.entryCategory = entryCategory;
    }
}

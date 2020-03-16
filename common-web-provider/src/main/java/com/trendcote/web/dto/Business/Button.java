package com.trendcote.web.dto.Business;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author 岩
 * @date 2018/10/16/016
 */
public class Button {
    private String type;
    private String name;
    private String key;
    @JSONField(name = "sub_button")
    private List<Button> subButton;
    private String url;

    public Button() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Button> getSubButton() {
        return subButton;
    }

    public void setSubButton(List<Button> subButton) {
        this.subButton = subButton;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Button{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", subButton=" + subButton +
                ", url='" + url + '\'' +
                '}';
    }
}

package com.trendcote.web.dto.echarts;

import java.util.List;

/**
 * @Description Echarts 的 Serie对象
 * @Date 2019/7/5 14:17
 * @Created by xym
 */

public class Serie {

    public String name;

    public String type;

    public List<Integer> data;

    public Serie(String name, String type, List<Integer> data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public Serie() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }


}

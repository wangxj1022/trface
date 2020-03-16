package com.trendcote.web.dto.echarts;

import java.util.List;

/**
 * @Description Echarts 的 Series对象
 * @Date 2019/7/4 11:51
 * @Created by xym
 */

public class Series {

    public List<String> xAxisDatas;

    public List<Serie> series;

    public List<Serie> getSeries() {
        return series;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }

    public List<String> getxAxisDatas() {
        return xAxisDatas;
    }

    public void setxAxisDatas(List<String> xAxisDatas) {
        this.xAxisDatas = xAxisDatas;
    }
}

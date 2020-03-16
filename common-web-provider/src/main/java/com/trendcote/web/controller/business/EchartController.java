package com.trendcote.web.controller.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.trendcote.web.constrants.GlobalConstant;
import com.trendcote.web.contrants.BusinessTable;
import com.trendcote.web.dto.echarts.Serie;
import com.trendcote.web.dto.echarts.Series;
import com.trendcote.web.entity.business.EchartsInfo;
import com.trendcote.web.entity.business.StaffInfo;
import com.trendcote.web.service.business.IEchartsInfoService;
import com.trendcote.web.service.business.IStaffService;
import com.trendcote.web.service.business.IVisitorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description 首页Echarts显示
 * @Date 2019/7/4 11:37
 * @Created by xym
 */
@Controller
@RequestMapping("/echarts")
public class EchartController {

    @Autowired
    private IEchartsInfoService echartsInfoService;

    /*series: [
    {
            name: '员工',
            type: 'bar',
            barGap: 0,
            label: labelOption,
            data: [320, 332, 301, 334, 390]
    },
    {
            name: '访客',
            type: 'bar',
            label: labelOption,
            data: [220, 182, 191, 234, 290]
    }
    ]*/

    @RequestMapping("/indexView")
    @ResponseBody
    public Series queryEchartsData(){

        // X轴数据
        List<String> xAxisDatas = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        for(int i = 0;i<5;i++){
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE,-(4-i));
            String singleDate = sdf.format(calendar.getTime());
            xAxisDatas.add(i,singleDate);
        }

        // Series数据
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        // Date afterTime = calendar.getTime();

        calendar.add(Calendar.DATE,-4);
        Date beforeTime = calendar.getTime();

        calendar.add(Calendar.DATE,+5);
        Date afterTime = calendar.getTime();

        // beforeTime --> afterTime ( 表仅统计近5天的数据 )
        List<EchartsInfo> staffList = echartsInfoService.list(new QueryWrapper<EchartsInfo>()
                .select("date","count")
                .eq(BusinessTable.EchartsInfoT.TYPE, GlobalConstant.DEFAULT)
                .ge(BusinessTable.EchartsInfoT.DATE, beforeTime)
                .lt(BusinessTable.EchartsInfoT.DATE, afterTime)
                .groupBy(BusinessTable.EchartsInfoT.DATE,BusinessTable.EchartsInfoT.COUNT));
        List<Integer> staffCounts = staffList.stream().map(EchartsInfo::getCount).collect(Collectors.toList());

        List<EchartsInfo> visitorList = echartsInfoService.list(new QueryWrapper<EchartsInfo>()
                .select("date","count")
                .eq(BusinessTable.EchartsInfoT.TYPE, GlobalConstant.NOT_DEFAULT)
                .ge(BusinessTable.EchartsInfoT.DATE, beforeTime)
                .lt(BusinessTable.EchartsInfoT.DATE, afterTime)
                .groupBy(BusinessTable.EchartsInfoT.DATE,BusinessTable.EchartsInfoT.COUNT));
        List<Integer> visitorCounts = visitorList.stream().map(EchartsInfo::getCount).collect(Collectors.toList());

        List<Serie> serieLists = new ArrayList<>();
        // selectOne groupby
        Serie serie1 = new Serie("员工","bar",staffCounts);
        Serie serie2 = new Serie("访客","bar",visitorCounts);
        serieLists.add(0,serie1);   //员工最近5天信息
        serieLists.add(1,serie2);   //访客最近5天信息
        Series series = new Series();
        series.setSeries(serieLists);   //数据信息
        series.setxAxisDatas(xAxisDatas);     //X轴信息

        return  series;
    }

}

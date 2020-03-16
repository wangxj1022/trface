package com.trendcote.web.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Timer;
@Configuration
public class Times {

    @Autowired
    public void test(){
        //创建一个timer实例
        Timer timer = new Timer();
        //新建实例
        MyTimes myTimes = new MyTimes();
        timer.schedule(myTimes,2000L,10000L);

    }
}

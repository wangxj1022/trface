package com.trendcote.web.utils;

import com.trendcote.web.entity.business.ServiceStaffInfo;
import com.trendcote.web.service.business.IServiceStaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.TimerTask;

@Component
public class MyTimes extends TimerTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyTimes.class);

    @Autowired
    private IServiceStaffService iServiceStaffService;
    /**
     * 静态的本类，为了可以使以上操作注入
     */
    private static MyTimes myTimes;

    public void setiServiceStaffService(IServiceStaffService iServiceStaffService){
        this.iServiceStaffService = iServiceStaffService;
    }

    /**
     * 初始化
     */
    @PostConstruct
    public void init(){
        myTimes = this;
        myTimes.iServiceStaffService = this.iServiceStaffService;
    }

    /**
     * 定时任务逻辑代码
     */
    @Override
    public void run() {
        List<ServiceStaffInfo> list = myTimes.iServiceStaffService.findList();
        if (null !=list){
            for (ServiceStaffInfo staffInfo :list){
                if (null == staffInfo.getServiceStaffPhoto() ||"" == staffInfo.getServiceStaffPhoto()){
                    myTimes.iServiceStaffService.updateByIdCard(staffInfo.getServiceStaffIcNo());
                }else{
                    myTimes.iServiceStaffService.updateByIDCardT(staffInfo.getServiceStaffIcNo());
                }
            }
        }
    }
}

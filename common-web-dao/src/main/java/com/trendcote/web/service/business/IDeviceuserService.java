package com.trendcote.web.service.business;


import com.baomidou.mybatisplus.extension.service.IService;
import com.trendcote.web.entity.business.Deviceuser;

public interface IDeviceuserService extends IService<Deviceuser> {
    void delectByIds(String deviceid);
}

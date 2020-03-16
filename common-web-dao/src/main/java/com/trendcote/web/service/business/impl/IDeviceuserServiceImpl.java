package com.trendcote.web.service.business.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trendcote.web.entity.business.Deviceuser;
import com.trendcote.web.mapper.business.DeviceuserMapper;
import com.trendcote.web.service.business.IDeviceuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IDeviceuserServiceImpl extends ServiceImpl<DeviceuserMapper, Deviceuser> implements IDeviceuserService {
    @Autowired
    private  DeviceuserMapper deviceuserMapper;
    @Override
    public void delectByIds(String deviceid) {
        this.deviceuserMapper.deleteByIds(deviceid);
    }
}

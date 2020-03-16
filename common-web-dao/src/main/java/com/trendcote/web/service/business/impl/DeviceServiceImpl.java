package com.trendcote.web.service.business.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trendcote.web.entity.business.EntryDeviceInfo;
import com.trendcote.web.mapper.business.DeviceMapper;
import com.trendcote.web.service.business.IDeviceService;
import org.springframework.stereotype.Service;

/**
 * @author 莹
 * @date 2018/6/4
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper,EntryDeviceInfo> implements IDeviceService {
}

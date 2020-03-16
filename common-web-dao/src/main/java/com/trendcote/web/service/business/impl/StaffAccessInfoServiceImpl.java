package com.trendcote.web.service.business.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trendcote.web.entity.business.StaffAccessInfo;
import com.trendcote.web.mapper.business.StaffAccessInfoMapper;
import com.trendcote.web.service.business.IStaffAccessInfoService;
import com.trendcote.web.service.business.IStaffService;
import org.springframework.stereotype.Service;

/**
 * @author 莹
 * @date 2018/6/4
 */
@Service
public class StaffAccessInfoServiceImpl extends ServiceImpl<StaffAccessInfoMapper,StaffAccessInfo>  implements IStaffAccessInfoService{
}

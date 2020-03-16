package com.trendcote.web.service.business.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trendcote.web.entity.business.StaffInfo;
import com.trendcote.web.mapper.business.StaffMapper;
import com.trendcote.web.service.business.IStaffService;
import org.springframework.stereotype.Service;

/**
 * @author 莹
 * @date 2018/6/4
 */
@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper,StaffInfo> implements IStaffService {
}

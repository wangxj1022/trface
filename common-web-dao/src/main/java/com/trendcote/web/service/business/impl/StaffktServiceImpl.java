package com.trendcote.web.service.business.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trendcote.web.entity.business.StaffktInfo;
import com.trendcote.web.mapper.business.StaffktMapper;
import com.trendcote.web.service.business.IStaffktService;
import org.springframework.stereotype.Service;

/**
 * @author 莹
 * @date 2018/6/4
 */
@Service
public class StaffktServiceImpl extends ServiceImpl<StaffktMapper,StaffktInfo> implements IStaffktService {
}

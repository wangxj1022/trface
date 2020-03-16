package com.trendcote.web.service.business.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trendcote.web.entity.business.VisitorInfo;
import com.trendcote.web.mapper.business.VisitorInfoMapper;
import com.trendcote.web.service.business.IVisitorInfoService;
import org.springframework.stereotype.Service;

/**
 * @author 莹
 * @date 2018/6/4
 */
@Service
public class VisitorInfoServiceImpl extends ServiceImpl<VisitorInfoMapper, VisitorInfo> implements IVisitorInfoService {
}

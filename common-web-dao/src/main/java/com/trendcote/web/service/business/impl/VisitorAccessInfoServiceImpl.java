package com.trendcote.web.service.business.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trendcote.web.entity.business.VisitorAccessInfo;
import com.trendcote.web.mapper.business.VisitorAccessInfoMapper;
import com.trendcote.web.service.business.IVisitorAccessInfoService;
import org.springframework.stereotype.Service;

/**
 * @author 莹
 * @date 2018/6/4
 */
@Service
public class VisitorAccessInfoServiceImpl extends ServiceImpl<VisitorAccessInfoMapper,VisitorAccessInfo> implements IVisitorAccessInfoService{
}

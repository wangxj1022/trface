package com.trendcote.web.service.business.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trendcote.web.entity.business.EchartsInfo;
import com.trendcote.web.mapper.business.EchartsInfoMapper;
import com.trendcote.web.service.business.IEchartsInfoService;
import org.springframework.stereotype.Service;

/**
 * @author 莹
 * @date 2018/6/4
 */
@Service
public class EchartsInfoServiceImpl extends ServiceImpl<EchartsInfoMapper,EchartsInfo> implements IEchartsInfoService{
}

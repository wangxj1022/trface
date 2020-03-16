package com.trendcote.web.service.business.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trendcote.web.entity.business.EntryInfo;
import com.trendcote.web.mapper.business.EntryInfoMapper;
import com.trendcote.web.service.business.IEntryInfoService;
import org.springframework.stereotype.Service;

/**
 * @author 莹
 * @date 2018/6/4
 */
@Service
public class EntryInfoServiceImpl extends ServiceImpl<EntryInfoMapper,EntryInfo> implements IEntryInfoService{
}

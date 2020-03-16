package com.trendcote.web.service.business.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trendcote.web.entity.business.EmailInfo;
import com.trendcote.web.mapper.business.EmailInfoMapper;
import com.trendcote.web.service.business.IEmailInfoService;
import org.springframework.stereotype.Service;

/**
 * @author 莹
 * @date 2018/6/4
 */
@Service
public class EmailInfoServiceImpl extends ServiceImpl<EmailInfoMapper,EmailInfo> implements IEmailInfoService {
}

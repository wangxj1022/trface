package com.trendcote.web.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trendcote.web.entity.system.TbParamsInfo;
import com.trendcote.web.mapper.system.SysParamMapper;
import com.trendcote.web.service.system.ISysParamService;
import org.springframework.stereotype.Service;

/**
 * @author 莹
 * @date 2018/6/4
 */
@Service
public class SysParamServiceImpl extends ServiceImpl<SysParamMapper,TbParamsInfo> implements ISysParamService {
}

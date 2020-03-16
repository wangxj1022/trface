package com.trendcote.web.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trendcote.web.entity.system.SysOperLog;
import com.trendcote.web.mapper.system.SysOperLogMapper;
import com.trendcote.web.service.system.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 莹
 * @date 2018/6/4
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper,SysOperLog> implements ISysOperLogService{
    @Autowired
    private SysOperLogMapper sysOperLogMapper;
    @Override
    public boolean saveLog(String loginName, String ip, String content,String Detail) {
        SysOperLog log = new SysOperLog();
        log.setIp(ip);
        log.setLoginName(loginName);
        log.setOperContent(content);
        log.setOperDetail(Detail);
        log.setCreateTime(new Date());
        int result= sysOperLogMapper.insert(log);
        if(result==1){
            return true;
        }else{
            return false;
        }
    }
}

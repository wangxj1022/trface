package com.trendcote.web.service.business.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trendcote.web.entity.business.SyncLogInfo;
import com.trendcote.web.mapper.business.SyncLogInfoMapper;
import com.trendcote.web.service.business.ISyncLogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description 同步日志方法
 * @Date 2019/7/3 14:49
 * @Created by xym
 */
@Service
public class SyncLogInfoServiceImpl extends ServiceImpl<SyncLogInfoMapper,SyncLogInfo> implements ISyncLogInfoService {

    @Autowired
    private SyncLogInfoMapper syncLogInfoMapper;

    @Override
    public boolean saveLog(String content,Integer recordAdd,Integer recordReduce,Integer remark) {
        SyncLogInfo log = new SyncLogInfo();
        log.setOperContent(content);
        log.setRecordAdd(recordAdd);
        log.setRecordReduce(recordReduce);
        log.setCreateTime(new Date());
        log.setRemark(remark);
        int result= syncLogInfoMapper.insert(log);
        if(result==1){
            return true;
        }else{
            return false;
        }
    }
}
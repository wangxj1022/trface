package com.trendcote.web.service.business;

import com.baomidou.mybatisplus.extension.service.IService;
import com.trendcote.web.entity.business.SyncLogInfo;

/**
 * @Description TODO
 * @Date 2019/7/3 14:47
 * @Created by xym
 */

public interface ISyncLogInfoService extends IService<SyncLogInfo> {
    public boolean saveLog(String content,Integer recordAdd,Integer recordReduce,Integer remark);
}

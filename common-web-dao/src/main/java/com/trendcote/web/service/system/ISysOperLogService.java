package com.trendcote.web.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.trendcote.web.entity.system.SysOperLog;

/**
 * @author 莹
 * @date 2018/6/4
 */
public interface ISysOperLogService extends IService<SysOperLog> {

    public boolean saveLog(String loginName,String ip,String content,String Detail);
}

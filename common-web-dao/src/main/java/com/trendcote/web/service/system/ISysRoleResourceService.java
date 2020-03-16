package com.trendcote.web.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.trendcote.web.entity.system.SysRole;
import com.trendcote.web.entity.system.SysRoleResource;

/**
 * @author 莹
 * @date 2018/6/4
 */
public interface ISysRoleResourceService extends IService<SysRoleResource> {
    public void grant(SysRole role);
}

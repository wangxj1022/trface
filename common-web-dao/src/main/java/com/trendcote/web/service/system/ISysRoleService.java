package com.trendcote.web.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.trendcote.web.entity.system.SysOrganization;
import com.trendcote.web.entity.system.SysRole;

/**
 * @author 莹
 * @date 2018/6/4
 */
public interface ISysRoleService  extends IService<SysRole>{

    public SysRole get(Integer id) ;
}

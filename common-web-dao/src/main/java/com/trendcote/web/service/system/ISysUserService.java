package com.trendcote.web.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.trendcote.web.entity.system.SysUser;

import java.util.List;

/**
 * @author 莹
 * @date 2018/6/4
 */
public interface ISysUserService extends IService<SysUser>{
    public SysUser login(SysUser user);
    public List<String> listResource(Long id);
    public boolean checkRole(Integer id);
}

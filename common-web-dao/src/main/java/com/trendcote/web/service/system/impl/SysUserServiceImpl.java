package com.trendcote.web.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trendcote.web.contrants.SystemTable;
import com.trendcote.web.entity.system.SysResource;
import com.trendcote.web.entity.system.SysUser;
import com.trendcote.web.mapper.system.SysResourceMapper;
import com.trendcote.web.mapper.system.SysUserMapper;
import com.trendcote.web.service.system.ISysUserService;
import com.trendcote.web.utils.MD5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莹
 * @date 2018/6/4
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper ,SysUser>implements ISysUserService{
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysResourceMapper sysResourceMapper;
    @Override
    public SysUser login(SysUser user) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(SystemTable.Sys_user.LOGIN_NAME,user.getUsername());
        queryWrapper.eq(SystemTable.Sys_user.PASSWORD, MD5Util.md5(user.getPassword()));
        SysUser t = sysUserMapper.selectOne(queryWrapper);
        if (t != null) {
            SysUser u = new SysUser();
            BeanUtils.copyProperties(t, u);
            return u;
        }
        return null;
    }
    @Override
    public List<String> listResource(Long id) {
        List<String> resourceList = new ArrayList<String>();
        List<SysResource> resources = sysResourceMapper.getByUserId(id);
        for (SysResource resource : resources) {
            resourceList.add(resource.getUrl());
        }
        return resourceList;
    }
    @Override
    public boolean checkRole(Integer id) {
        if(sysUserMapper.getCountByRoleId(id)>0){
            return true ;
        }else {
            return false;
        }
    }
}

package com.trendcote.web.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trendcote.web.contrants.SystemTable;
import com.trendcote.web.entity.system.SysRole;
import com.trendcote.web.entity.system.SysRoleResource;
import com.trendcote.web.mapper.system.SysRoleResourceMapper;
import com.trendcote.web.service.system.ISysResourceService;
import com.trendcote.web.service.system.ISysRoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 莹
 * @date 2018/6/4
 */
@Service
public class SysRoleResourceServiceImpl extends ServiceImpl<SysRoleResourceMapper,SysRoleResource> implements ISysRoleResourceService{
    @Autowired
    private SysRoleResourceMapper sysRoleResourceMapper;

    @Override
    public void grant(SysRole role) {
        sysRoleResourceMapper.delete(new QueryWrapper<SysRoleResource>().eq(SystemTable.Sys_Role_Resource.ROLE_ID,role.getId()));
        String[] resourceids = null;

        if(!"".equals(role.getResourceIds())){
            resourceids = role.getResourceIds().split(",");
            List list = Arrays.asList(resourceids);
            Set set = new HashSet(list);
            resourceids=(String [])set.toArray(new String[0]);
            SysRoleResource sysRoleResource=null;
            for(String s :resourceids){
                sysRoleResource = new SysRoleResource();
                sysRoleResource.setRoleId(role.getId());
                sysRoleResource.setResourceId(Integer.valueOf(s));
                sysRoleResourceMapper.insert(sysRoleResource);
            }
        }

    }

}

package com.trendcote.web.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trendcote.web.contrants.SystemTable;
import com.trendcote.web.entity.system.SysResource;
import com.trendcote.web.mapper.system.SysResourceMapper;
import com.trendcote.web.service.system.ISysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莹
 * @date 2018/6/4
 */
@Service
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper ,SysResource> implements ISysResourceService {

    @Autowired
    private SysResourceMapper sysResourceMapper;
    @Override
    public List<String> listAllResource() {
        List<String> resourceList = new ArrayList<String>();
        List<SysResource> l = sysResourceMapper.selectList(new QueryWrapper<SysResource>().orderByAsc(SystemTable.Sys_Resource.SEQ));
        for (int i = 0; i < l.size(); i++) {
            resourceList.add(l.get(i).getUrl());
        }
        return resourceList;
    }

    @Override
    public List<SysResource> getParentResourceByUserId(int type, Long userId) {
        return sysResourceMapper.getParentResourceByUserId(type,userId);
    }

    public List<SysResource> findResourceByRoleId(Integer roleId){
        return sysResourceMapper.findResourceByRoleId(roleId);
    }
}

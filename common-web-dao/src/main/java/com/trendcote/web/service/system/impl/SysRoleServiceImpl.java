package com.trendcote.web.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trendcote.web.contrants.SystemTable;
import com.trendcote.web.entity.system.SysRole;
import com.trendcote.web.entity.system.SysRoleResource;
import com.trendcote.web.mapper.system.SysRoleMapper;
import com.trendcote.web.mapper.system.SysRoleResourceMapper;
import com.trendcote.web.service.system.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.basic.BasicButtonUI;
import java.util.HashMap;
import java.util.List;

/**
 * @author 莹
 * @date 2018/6/4
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper,SysRole> implements ISysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleResourceMapper sysRoleResourceMapper;
    @Override
    public SysRole get(Integer id) {
        SysRole t = sysRoleMapper.selectById(id);
        List<SysRoleResource> s = sysRoleResourceMapper.selectList(new QueryWrapper<SysRoleResource>().eq(SystemTable.Sys_Role_Resource.ROLE_ID,id));
        if ((s != null) && !s.isEmpty()) {
            boolean b = false;
            String ids = "";
            for (SysRoleResource tr : s) {
                if (b) {
                    ids += ",";
                } else {
                    b = true;
                }
                ids += tr.getResourceId();
            }
            t.setResourceIds(ids);
        }
        return t;
    }


}

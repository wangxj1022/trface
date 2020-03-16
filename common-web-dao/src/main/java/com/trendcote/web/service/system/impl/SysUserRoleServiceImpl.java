package com.trendcote.web.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trendcote.web.entity.system.SysUserRole;
import com.trendcote.web.mapper.system.SysUserRoleMapper;
import com.trendcote.web.service.system.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.soap.Addressing;

/**
 * @author 莹
 * @date 2018/6/4
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper,SysUserRole> implements ISysUserRoleService {
}

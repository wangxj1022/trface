package com.trendcote.web.shiro;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.trendcote.web.contrants.SystemTable;
import com.trendcote.web.entity.system.SysResource;
import com.trendcote.web.entity.system.SysRole;
import com.trendcote.web.entity.system.SysUser;
import com.trendcote.web.entity.system.SysUserRole;
import com.trendcote.web.service.system.ISysResourceService;
import com.trendcote.web.service.system.ISysRoleService;
import com.trendcote.web.service.system.ISysUserRoleService;
import com.trendcote.web.service.system.ISysUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户验证
 * @author 莹
 * @date 2018/6/4
 */
@Service
public class MyShiroRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysResourceService sysResourceService;
    @Autowired
    private ISysUserRoleService sysUserRoleService;
    @Autowired
    private ISysRoleService sysRoleService;
    /**
     * 权限验证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.debug("权限校验开始");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        if (user.getUsername().equals("admin")) {
            // 超级管理员，添加所有角色、添加所有权限
            authorizationInfo.addRole("*");
            authorizationInfo.addStringPermission("*");
        } else {
             //普通用户，查询用户的角色，根据角色查询权限
            Long userId=user.getId();
            QueryWrapper<SysUserRole> sysUserRoleQueryWrapper = new QueryWrapper<SysUserRole>();
            sysUserRoleQueryWrapper.eq(SystemTable.Sys_User_Role.USER_ID,userId);
            List<SysUserRole> roles = sysUserRoleService.list(sysUserRoleQueryWrapper);
            if (null != roles && roles.size() > 0) {
                for (SysUserRole role : roles) {
                    SysRole sysRole=sysRoleService.getById(role.getRoleId());
                    authorizationInfo.addRole(sysRole.getCode());
                    // 角色对应的权限数据
                    List<SysResource> resourceList = sysResourceService.findResourceByRoleId(role.getRoleId());
                    if (null != resourceList && resourceList.size() > 0) {
                        // 授权角色下所有权限
                        for (SysResource perm : resourceList) {
                            if(null!=perm.getUrl()&&!"".equals(perm.getUrl())){
                                authorizationInfo.addStringPermission(perm.getUrl().trim());
                            }
                        }
                    }
                }
           }
      }
        return authorizationInfo;
    }

    /**
     * 用户验证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.debug("用户登录验证开始");
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String userName = token.getUsername();
        QueryWrapper<SysUser> userQueryWrapper= new QueryWrapper<SysUser>();
        userQueryWrapper.eq(SystemTable.Sys_user.LOGIN_NAME,userName);
        SysUser user= sysUserService.getOne(userQueryWrapper);
        if (user == null) {
            logger.debug("用户登录验证结束,用户不存在!{}", JSON.toJSONString(user));
            return null;
        } else {
            // 密码存在
            // 第一个参数 ，登陆后，需要在session保存数据
            // 第二个参数，查询到密码(加密规则要和自定义的HashedCredentialsMatcher中的HashAlgorithmName散列算法一致)
            // 第三个参数 ，realm名字
            logger.debug("用户登录验证成功");
            return new SimpleAuthenticationInfo(user, DigestUtils.md5Hex(user.getPassword()), getName());
        }

    }

    /**
     * 清除所有缓存【实测无效】
     */
    public void clearCachedAuth(){
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}

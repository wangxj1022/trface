package com.trendcote.web.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.trendcote.web.contrants.SystemTable;
import com.trendcote.web.dto.page.Json;
import com.trendcote.web.entity.system.SysUser;
import com.trendcote.web.service.system.ISysUserService;
import com.trendcote.web.utils.ShiroFilterUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class UserActionInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(UserActionInterceptor.class);
    @Autowired
    private ISysUserService sysUserService;

    private final String kickoutUrl = "/toLogin"; // 退出后重定向的地址

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception e)
            throws Exception {
        // TODO Auto-generated method stub
        logger.debug("整个请求完成之后调用。DispatcherServlet视图渲染完成之后。（主要是用于进行资源清理工作）");

    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object obj, ModelAndView mv)
            throws Exception {
        // TODO Auto-generated method stub
        logger.debug("请求处理之后调用；在视图渲染之前，controller处理之后。");

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        // TODO Auto-generated method stub
        logger.debug("请求到达后台方法之前调用（controller之前）");
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if (user != null) {
            QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<SysUser>();
            sysUserQueryWrapper.eq(SystemTable.Sys_user.LOGIN_NAME,user.getUsername());
            SysUser dbUser = sysUserService.getOne(sysUserQueryWrapper);
            if(null!=dbUser){
               return true;
            }else {
                SecurityUtils.getSubject().logout();
                isAjaxResponse(request, response);
            }
        }
        return true;
    }

    private boolean isAjaxResponse(HttpServletRequest request,
                                   HttpServletResponse response) throws IOException {
        // ajax请求
        /**
         * 判断是否已经踢出
         * 1.如果是Ajax 访问，那么给予json返回值提示。
         * 2.如果是普通请求，直接跳转到登录页
         */
        //判断是不是Ajax请求
        Json responseResult = new Json();
        if (ShiroFilterUtils.isAjax(request)) {
            logger.debug(getClass().getName() + "，当前用户的信息或权限已变更，重新登录后生效！");
            responseResult.setSuccess(false);
            responseResult.setMsg("您的信息或权限已变更，重新登录后生效");
            ShiroFilterUtils.out(response, responseResult);
        } else {
            // 重定向
            WebUtils.issueRedirect(request, response, kickoutUrl);
        }
        return false;
    }

}

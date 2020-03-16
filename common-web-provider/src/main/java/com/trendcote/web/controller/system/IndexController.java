package com.trendcote.web.controller.system;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.Constants;
import com.trendcote.web.constrants.GlobalConstant;
import com.trendcote.web.constrants.SysConstants;
import com.trendcote.web.contrants.BusinessTable;
import com.trendcote.web.contrants.SystemTable;
import com.trendcote.web.dto.page.Json;
import com.trendcote.web.dto.system.LoginDto;
import com.trendcote.web.entity.business.VisitorAppointmentInfo;
import com.trendcote.web.entity.system.SysUser;
import com.trendcote.web.service.business.IVisitorAppointmentInfoService;
import com.trendcote.web.service.system.ISysOperLogService;
import com.trendcote.web.service.system.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Controller
public class IndexController{

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysOperLogService sysOperLogService;

    @Autowired
    private IVisitorAppointmentInfoService appointmentInfoService;

    @RequestMapping("/toLogin")
    public String toLogin(HttpServletRequest request) {
        return "toLogin";
    }

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        return "index";
    }
    @RequestMapping("/{page}")
    public String toPage(@PathVariable("page") String page) {
        return page;
    }
    @RequestMapping("/login")
    public String toLogin() {
        return "login";
    }
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public Json login(@Validated  LoginDto user, HttpServletRequest req, HttpSession session) {
        logger.debug("用户登录请求{}" ,JSON.toJSONString(user));
        Json j = new Json();
        int count = 0 ;
        try {
            /*if(!user.getVerifycode().equals(session.getAttribute(Constants.KAPTCHA_SESSION_KEY))){
                j.setMsg("验证码输入有误");
                j.setSuccess(false);
                logger.error("验证码输入错误");
                return j;
            }*/
            QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<SysUser>();
            sysUserQueryWrapper.eq(SystemTable.Sys_user.LOGIN_NAME,user.getUsername());
            sysUserQueryWrapper.eq(SystemTable.Sys_user.IS_DEFAULT,GlobalConstant.DEFAULT);
            SysUser dbUser = sysUserService.getOne(sysUserQueryWrapper);
            if (dbUser == null) {
                j.setMsg("用户不存在，请您联系管理员");
                j.setSuccess(false);
                logger.debug("用户不存在,用户名{}",user.getUsername());
                return j;
            }
            AuthenticationToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword(),true);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);

            sysOperLogService.saveLog(user.getUsername(),req.getRemoteHost(), SysConstants.LOG_LOGIN,"用户"+user.getUsername()+"登录");
            j.setSuccess(true);
            j.setMsg("登录成功！");
            return j;
        } catch (UnknownAccountException uae) {
            logger.error("用户登录，用户验证未通过：未知用户！");
            j.setSuccess(false);
            j.setMsg("用户验证未通过,请联系管理员！");
        } catch (IncorrectCredentialsException ice) {
            logger.error("用户登录，用户验证未通过：错误的凭证，密码输入错误！");
            j.setSuccess(false);
            j.setMsg("用户名或密码不正确");
        } catch (LockedAccountException lae) {
            logger.error("用户登录，用户验证未通过：账户已锁定！");
            j.setSuccess(false);
            j.setMsg("用户已经被锁定");
        } catch (ExcessiveAttemptsException eae) {
            logger.error("用户登录，用户验证未通过：错误次数大于达到3次,账户已锁定！");
            j.setSuccess(false);
            j.setMsg("次数达到3次,已被锁定,请2分钟之后重新登录！");
        } catch (AuthenticationException ae) {
            logger.error("用户登录，用户验证未通过：认证异常，异常信息如下！");
            j.setSuccess(false);
            j.setMsg("用户名密码输入错误！");
        } catch (Exception e) {
            logger.error("用户登录，用户验证未通过：操作异常，异常信息如下！");
            j.setSuccess(false);
            j.setMsg("系统异常！");
        }
        return j;
    }
}

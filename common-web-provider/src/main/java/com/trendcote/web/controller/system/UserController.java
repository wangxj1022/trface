package com.trendcote.web.controller.system;


import javax.servlet.http.HttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trendcote.web.constrants.GlobalConstant;
import com.trendcote.web.constrants.SysConstants;
import com.trendcote.web.contrants.SystemTable;
import com.trendcote.web.dto.page.Grid;
import com.trendcote.web.dto.page.Json;
import com.trendcote.web.dto.page.PageFilter;
import com.trendcote.web.dto.system.LoginDto;
import com.trendcote.web.dto.system.PwdDto;
import com.trendcote.web.entity.system.SysOrganization;
import com.trendcote.web.entity.system.SysRole;
import com.trendcote.web.entity.system.SysUser;
import com.trendcote.web.entity.system.SysUserRole;
import com.trendcote.web.service.system.*;
import org.apache.shiro.SecurityUtils;
import org.h2.engine.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


@Controller
@Transactional(rollbackFor = Exception.class)
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private ISysOperLogService sysOperLogService;
	@Autowired
	private ISysUserRoleService sysUserRoleService;
	@Autowired
	private ISysOrganizationService sysOrganizationService;
	@Autowired
	private ISysRoleService sysRoleService;

	@RequestMapping("/manager")
	public String manager() {
		return "/system/user";
	}

	@RequestMapping(value = "/dataGrid",method = RequestMethod.POST)
	@ResponseBody
	public Grid dataGrid(SysUser user, PageFilter ph) throws ParseException {

	    Grid grid = new Grid();
		Page<SysUser> pg= new Page<>(ph.getPage(),ph.getRows());
        pg.setDesc(SystemTable.Sys_user.BASE_TABLE_CREATE_TIME);

		QueryWrapper<SysUser> sysUserQueryWrapper =  new QueryWrapper<SysUser>()
				.like(user.getName()!=null,SystemTable.Sys_user.NAME,user.getName())
				.eq(SystemTable.Sys_user.IS_DEFAULT,GlobalConstant.DEFAULT);

			sysUserQueryWrapper.ge(ph.getNewBeginTime()!=null,SystemTable.Sys_user.BASE_TABLE_CREATE_TIME,ph.getNewBeginTime());
			sysUserQueryWrapper.le(ph.getNewEndTime()!=null,SystemTable.Sys_user.BASE_TABLE_CREATE_TIME,ph.getNewEndTime());
		IPage<SysUser> userIPage=sysUserService.page(pg ,sysUserQueryWrapper);

		// 部门
        List<SysUser> sysUsers = userIPage.getRecords();
        List<SysOrganization> orgs = sysOrganizationService.list(new QueryWrapper<>());
        // 角色
        List<SysRole> roles = sysRoleService.list(new QueryWrapper<SysRole>().eq(SystemTable.Sys_Role.IS_DEFAULT, GlobalConstant.DEFAULT));

        for( SysUser sysUser : sysUsers ){
            for( SysOrganization sysOrg : orgs ){
                if( sysUser.getOrganizationId()!=null && sysUser.getOrganizationId().equals(sysOrg.getId())){
                    sysUser.setOrganizationName(sysOrg.getName());  //部门名
                }
            }
            // 匹配角色
            SysUserRole userRole = sysUserRoleService.getOne(new QueryWrapper<SysUserRole>().eq(SystemTable.Sys_User_Role.USER_ID, sysUser.getId()));
            for( SysRole sysRole : roles){
                if( userRole.getRoleId()!=null && userRole.getRoleId().equals(sysRole.getId()) ){
                    sysUser.setRoleNames(sysRole.getName());
                }
            }
        }

		grid.setRows(userIPage.getRecords());
		grid.setTotal(userIPage.getTotal());
		return grid;
	}

	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public Json add(SysUser user,HttpServletRequest req) throws Exception {
		Json j = new Json();
		try{
			QueryWrapper<SysUser> sysUserQueryWrapper  = new QueryWrapper<SysUser>();
			//sysUserQueryWrapper.eq(SystemTable.Sys_user.CODE, user.getCode()).or().eq(SystemTable.Sys_user.LOGIN_NAME,user.getUsername());
			sysUserQueryWrapper.eq(SystemTable.Sys_user.LOGIN_NAME,user.getUsername());
			sysUserQueryWrapper.eq(SystemTable.Sys_user.IS_DEFAULT,GlobalConstant.DEFAULT);
			SysUser dbUser= sysUserService.getOne(sysUserQueryWrapper);
			if(null != dbUser){
				j.setSuccess(false);
				j.setMsg("编号或者登录名已存在!");
				return j;
			}
			user.setCreateTime(new Date());
			sysUserService.save(user);
			// sys_user表
			// SysUser db_user = sysUserService.getOne(new QueryWrapper<SysUser>().eq(SystemTable.Sys_user.CODE,user.getCode()));
			SysUser db_user = sysUserService.getOne(new QueryWrapper<SysUser>().eq(SystemTable.Sys_user.LOGIN_NAME,user.getUsername()));
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setRoleId(user.getRoleId());
			sysUserRole.setUserId(db_user.getId());
            sysUserRoleService.save(sysUserRole);
            SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
			sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_OPER_CREATE,"添加操作员:"+db_user.getName() );
			j.setSuccess(true);
			j.setMsg("操作成功！");
		}catch (Exception e){
			logger.error("添加操作员异常exception:{}",e.fillInStackTrace());
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("操作失败！");
			throw new Exception();
		}
		return j;
	}

	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public Json delete(Long id,HttpServletRequest req) throws Exception {
		Json j = new Json();
		try {
		    SysUser db_user = sysUserService.getById(id);
		    if(db_user==null){
		        j.setMsg("操作员信息不存在！");
		        j.setSuccess(false);
		        return j;
            }
            db_user.setIsdefault(GlobalConstant.NOT_DEFAULT);
			sysUserService.updateById(db_user);
			SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
			sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_OPER_REMOVE,"删除操作员:"+db_user.getName());
			j.setMsg("操作成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.error("删除操作员异常exception:{}",e.fillInStackTrace());
			e.printStackTrace();
			j.setMsg("操作失败！");
			j.setSuccess(false);
			throw new Exception();
		}
		return j;
	}

	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public Json edit(SysUser user,HttpServletRequest req) throws Exception {
		Json j = new Json();
		try {
		    SysUser db_user=sysUserService.getById(user.getId());
		    if(db_user==null){
		        j.setSuccess(false);
		        j.setMsg("操作员信息不存在！");
		        return j;
            }
            if(!user.getUsername().equals(db_user.getUsername())){
		    	if(sysUserService.getOne(new QueryWrapper<SysUser>().eq(SystemTable.Sys_user.LOGIN_NAME,user.getUsername()))!=null){
					j.setSuccess(false);
					j.setMsg("登录名已存在！");
					return j;
				}
			}
            if(null==user.getPassword()||"".equals(user.getPassword())){
				user.setPassword(db_user.getPassword());
			}
			user.setUpdateTime(new Date());
		    user.setCreateTime(db_user.getCreateTime());
			sysUserService.updateById(user);
			//更新角色
			SysUserRole userRole = new SysUserRole();
			userRole.setUserId(db_user.getId());
			userRole.setRoleId(user.getRoleId());
			sysUserRoleService.update(userRole,new QueryWrapper<SysUserRole>().eq(SystemTable.Sys_User_Role.USER_ID,db_user.getId()));
			//更新自己信息时候，更新缓存
			SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
			if(loginUser.getId().equals(db_user.getId())){
				loginUser.setUsername(user.getName());
			}
			sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_OPER_MODIFY,"编辑操作员:" +db_user.getName() );
			j.setSuccess(true);
			j.setMsg("操作成功！");
		} catch (Exception e) {
			logger.error("编辑操作员异常exception:{}",e.fillInStackTrace());
			e.printStackTrace();
			j.setMsg("操作失败！");
			j.setSuccess(false);
			throw new Exception();
		}
		return j;
	}
	@RequestMapping("/updatePwdPage")
	public String editPwd(){
		return "/updatePassword";
	}


	@RequestMapping(value = "updatePwd", method = RequestMethod.POST)
	@ResponseBody
	public Json setPwd(PwdDto dto,HttpServletRequest req) throws Exception {
		Json j = new Json();
		try {
			// 判断用户是否登录
			SysUser existUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
			if (null == existUser) {
				j.setMsg("操作失败,您还没有登录或者登录超时！");
				j.setSuccess(false);
				return j;
			}
			if(!existUser.getPassword().equals(dto.getOldPwd())){
				j.setMsg("操作失败,原密码输入有误！");
				j.setSuccess(false);
				return j;
			}
			// 修改密码
			SysUser sysUser = new SysUser();
			sysUser.setPassword(dto.getNewPwd());
			sysUser.setUpdateTime(new Date());
			sysUserService.update(sysUser,new QueryWrapper<SysUser>().eq(SystemTable.Sys_user.LOGIN_NAME,existUser.getUsername()));
			sysOperLogService.saveLog(existUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_OPER_INIT_PASSWORD ,"编辑操作员密码:"+sysUser.getName()  );
			j.setMsg("操作成功,下次登录生效！");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.error("修改密码异常exception:{}",e.fillInStackTrace());
			e.printStackTrace();
			j.setMsg("操作失败！");
			j.setSuccess(false);
			throw new Exception();
		}
		return j;
	}

}

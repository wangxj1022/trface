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
import com.trendcote.web.dto.page.Tree;
import com.trendcote.web.entity.system.SysRole;
import com.trendcote.web.entity.system.SysUser;
import com.trendcote.web.service.system.ISysOperLogService;
import com.trendcote.web.service.system.ISysRoleResourceService;
import com.trendcote.web.service.system.ISysRoleService;
import com.trendcote.web.service.system.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Transactional(rollbackFor = Exception.class)
@RequestMapping("/role")
public class RoleController {
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	private ISysRoleService sysRoleService;
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private ISysOperLogService sysOperLogService;
	@Autowired
	private ISysRoleResourceService sysRoleResourceService;

	@RequestMapping("/manager")
	public String manager() {
		return "/system/role";
	}

	@RequestMapping(value = "/dataGrid",method = RequestMethod.POST)
	@ResponseBody
	public Grid dataGrid(SysRole role, PageFilter ph ) {
		//只查询有效的角色
		Grid grid = new Grid();
		Page<SysRole> pg= new Page<SysRole>(ph.getPage(),ph.getRows());
		pg.setDesc(SystemTable.Sys_Role.BASE_TABLE_CREATE_TIME);
		IPage<SysRole> iPg= sysRoleService.page(pg,new QueryWrapper<SysRole>()
				.eq(SystemTable.Sys_Role.IS_DEFAULT, GlobalConstant.DEFAULT));
		grid.setRows(pg.getRecords());
		grid.setTotal(pg.getTotal());
		return grid;
	}

	@RequestMapping("/tree")
	@ResponseBody
	public List<Tree> tree() {
		List<SysRole> l = sysRoleService.list(new QueryWrapper<SysRole>().eq(SystemTable.Sys_Role.IS_DEFAULT, GlobalConstant.DEFAULT));
		List<Tree> lt = new ArrayList<Tree>();
		if ((l != null) && (l.size() > 0)) {
			for (SysRole r : l) {
				Tree tree = new Tree();
				tree.setId(String.valueOf(r.getId()));
				tree.setText(r.getName());
				lt.add(tree);
			}
		}
		return lt;
	}

	@RequestMapping("/getIdByName")
	@ResponseBody
	public Integer getIdByName(String roleNames) {
		SysRole one = sysRoleService.getOne(new QueryWrapper<SysRole>().eq(SystemTable.Sys_Role.NAME, roleNames));
		return one.getId();
	}



	@RequestMapping("/add")
	@ResponseBody
	public Json add( SysRole role,HttpServletRequest req) throws Exception {
		Json j = new Json();
		try {
			// 角色编号去掉之后  不需要判断是否已存在了
			/*List<SysRole> dbRoles=sysRoleService.list(new QueryWrapper<SysRole>()
					.eq(SystemTable.Sys_Role.CODE,role.getCode())
					.eq(SystemTable.Sys_Role.IS_DEFAULT,GlobalConstant.DEFAULT));

			if(null!=dbRoles&&dbRoles.size()>0){
				j.setSuccess(false);
				j.setMsg("添加失败,角色编号已存在！");
				return j;
			}*/

			role.setIsdefault(GlobalConstant.DEFAULT);
			role.setCreateTime(new Date());
			sysRoleService.save(role);
			SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
			sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_ROLE_ADD,"添加角色:"+role.getName());
			j.setSuccess(true);
			j.setMsg("操作成功！");
		} catch (Exception e) {
			logger.error("添加角色失败exception:{}",e.fillInStackTrace());
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("操作失败！");
			throw new Exception();
		}
		return j;
	}
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id,HttpServletRequest req) throws Exception {
		Json j = new Json();
		try {
			if(sysUserService.checkRole(id)){
				j.setMsg("操作失败,该角色已绑定操作员！");
				j.setSuccess(false);
			}else{
				SysRole sysRole = new SysRole();
				sysRole.setCreateTime(new Date());
				sysRole.setIsdefault(GlobalConstant.NOT_DEFAULT);
				sysRole.setId(id);
				SysRole role = sysRoleService.getById(sysRole);
				sysRoleService.updateById(sysRole);
				SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
				sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_ROLE_REMOVE,"删除角色:"+role.getName());
				j.setMsg("操作成功！");
				j.setSuccess(true);
			}
		} catch (Exception e) {
			logger.error("删除角色失败exception:{}",e.fillInStackTrace());
			e.printStackTrace();
			j.setMsg("操作失败！");
			j.setSuccess(false);
			throw new Exception();
		}
		return j;
	}
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(SysRole role,HttpServletRequest req) throws Exception {
		Json j = new Json();
		try {
			if(null == role.getId()){
				logger.error("id为空");
				j.setSuccess(false);
				j.setMsg("解析数据异常,请您稍后尝试！");
				return j;
			}
			SysRole db_sysRole=sysRoleService.getById(role.getId());
			if(db_sysRole==null){
				j.setSuccess(false);
				j.setMsg("角色信息不存在！");
				return j;
			}
			/*if(!db_sysRole.getCode().equals(role.getCode())){
				List<SysRole> dbRoles=sysRoleService.list(new QueryWrapper<SysRole>()
						.eq(SystemTable.Sys_Role.CODE,role.getCode())
						.eq(SystemTable.Sys_Role.IS_DEFAULT,GlobalConstant.DEFAULT));
				if(null!=dbRoles&&dbRoles.size()>0){
					j.setSuccess(false);
					j.setMsg("操作失败,角色编号已存在！");
					return j;
				}
			}*/

			sysRoleService.updateById(role);
			SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
			sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_ROLE_MODIFY ,"编辑角色:"+db_sysRole.getName()+"变为"+role.getName());
			j.setSuccess(true);
			j.setMsg("操作成功！");
		} catch (Exception e) {
			logger.error("编辑角色失败exception:{}",e.fillInStackTrace());
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("操作失败！");
			throw new Exception();
		}
		return j;
	}
	@RequestMapping("/grant")
	@ResponseBody
	public Json grant(SysRole role,HttpServletRequest req) throws Exception {
		Json j = new Json();
		try {
			sysRoleResourceService.grant(role);
			SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
			sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_ROLE_ASSIGN_PERMISSION,"授权角色"+role.getName() );
			j.setMsg("操作成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.error("角色授权失败exception:{}",e.fillInStackTrace());
			e.printStackTrace();
			j.setMsg("操作失败！");
			j.setSuccess(false);
			throw new Exception();
		}
		return j;
	}
	@RequestMapping("/get")
	@ResponseBody
	public SysRole get(Integer id) {
		return sysRoleService.get(id);
	}
}

package com.trendcote.web.controller.system;


import javax.servlet.http.HttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.trendcote.web.constrants.GlobalConstant;
import com.trendcote.web.constrants.SysConstants;
import com.trendcote.web.contrants.SystemTable;
import com.trendcote.web.dto.page.Json;
import com.trendcote.web.dto.page.Tree;
import com.trendcote.web.entity.system.SysOrganization;
import com.trendcote.web.entity.system.SysRole;
import com.trendcote.web.entity.system.SysUser;
import com.trendcote.web.service.system.ISysOperLogService;
import com.trendcote.web.service.system.ISysOrganizationService;
import com.trendcote.web.service.system.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@Transactional(rollbackFor=Exception.class)
@RequestMapping("/organization")
public class OrganizationController {
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	private ISysOrganizationService sysOrganizationService;

	@Autowired
	private ISysOperLogService sysOperLogService;
	@Autowired
	private ISysUserService sysUserService;

	@RequestMapping("/manager")
	public String manager() {
		return "/system/organization";
	}

	@RequestMapping(value = "/treeGrid",method = RequestMethod.POST)
	@ResponseBody
	public List<SysOrganization> treeGrid() {
		QueryWrapper<SysOrganization> queryWrapper=new QueryWrapper<SysOrganization>().orderByAsc(SystemTable.Sys_Organization.SEQ);
		List<SysOrganization> rpl=sysOrganizationService.list(queryWrapper);
		return rpl;
	}

	@RequestMapping(value = "/tree" ,method = RequestMethod.POST)
	@ResponseBody
	public List<Tree> tree() {

		//阳光用
		List<Tree> returnListTree = new ArrayList<>();

		Tree tree = new Tree();
		// 遍历树形结构
		List<SysOrganization> orgList = sysOrganizationService.list(null);
		List<Tree> orgChildTree = null;

		if( orgList != null && orgList.size() > 0 ){
			// 找到根节点
			for( SysOrganization org : orgList ){
				if( null == org.getPid() ){
					// 一级子节点处理
					orgChildTree = new ArrayList<>();
					for( SysOrganization orgChild : orgList ){
						if( orgChild.getPid() == org.getId()){
							Tree treeChild = new Tree();
							treeChild.setText( orgChild.getName() );
							treeChild.setId( String.valueOf(orgChild.getId()) );
							// 判断子节点是不是有子节点
							List<Tree> treeChilds = getChildTree(orgChild.getId(),orgList);
							if(treeChilds != null){
								treeChild.setChildren(treeChilds);
							}
							orgChildTree.add(treeChild);
						}
					}
					// 根节点处理
					tree.setId(String.valueOf(org.getId()));
					tree.setText(org.getName());
					tree.setChildren(orgChildTree);
				}
			}

			returnListTree.add(tree);
		}

		/*List<SysOrganization> orgList = sysOrganizationService.list(new QueryWrapper<SysOrganization>().isNull(SystemTable.Sys_Organization.ADDRESS));
		List<Tree> lt = new ArrayList<Tree>();
		if (orgList != null && (orgList.size() > 0)){
			for (SysOrganization r : orgList) {
				Tree tree = new Tree();
				tree.setId(String.valueOf(r.getId()));
				tree.setText(r.getName());
				lt.add(tree);
			}
		}*/


		return returnListTree;
	}

	public List<Tree> getChildTree(Long parId , List<SysOrganization> lists){
		List<Tree> childTrees = new ArrayList<>();
		for( SysOrganization organization : lists ){
			Tree tree = null ;
			if( parId == organization.getPid()){
				tree = new Tree();
				tree.setId(String.valueOf( organization.getId()) );
				tree.setText( organization.getName() );

				List<Tree> childChildTrees = new ArrayList<>();
				for( SysOrganization organizationChild : lists ){
					Tree treeChild = null ;
					if( organizationChild.getPid() == organization.getId() ){
						treeChild = new Tree();
						treeChild.setId(String.valueOf( organizationChild.getId()) );
						treeChild.setText( organizationChild.getName() );
						List<Tree> childTree = getChildTree(organizationChild.getId(), lists);
						if(childTree == null ){
							break;
						}
						childChildTrees.add(treeChild);
					}
				}
				tree.setChildren(childChildTrees);

				childTrees.add(tree);
			}

		}
		return childTrees;
	}


	@RequestMapping("/addPage")
	public String addPage( Long pid, HttpServletRequest request) {
		request.setAttribute("pid", pid);
		// 父级机构名称加载
		SysOrganization o = sysOrganizationService.getById(pid);
		if( o!= null ){
			SysOrganization org = sysOrganizationService.getById(o.getPid());
			request.setAttribute("pname", org.getName());
		}
		return "/system/organizationAdd";
	}

	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public Json add(SysOrganization organization, HttpServletRequest req) throws Exception {
		Json j = new Json();
		try {
			// 部门编号改为ID号码
			/*if(sysOrganizationService.list(new QueryWrapper<SysOrganization>().eq(SystemTable.Sys_Organization.CODE,organization.getCode())).size()>0){
				j.setSuccess(false);
				j.setMsg("部门编号已存在！");
				return j;
			}*/
			if(sysOrganizationService.list(new QueryWrapper<SysOrganization>().eq(SystemTable.Sys_Organization.BASE_TABLE_ID,organization.getId())).size()>0){
				j.setSuccess(false);
				j.setMsg("部门编号已存在！");
				return j;
			}


			organization.setCreateTime(new Date());
			sysOrganizationService.save(organization);
			SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
			sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_ORGANIZATION_ADD,"添加机构:"+organization.getName());
			j.setSuccess(true);
			j.setMsg("操作成功！");
		} catch (Exception e) {
			logger.error("添加部门异常exception:{}",e.fillInStackTrace());
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("操作失败！");
			throw new Exception() ;
		}
		return j;
	}

	@RequestMapping(value = "/get" ,method = RequestMethod.POST)
	@ResponseBody
	public SysOrganization get(Long id) {
		return sysOrganizationService.getById(id);
	}

	@RequestMapping(value = "/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		SysOrganization o = sysOrganizationService.getById(id);
		request.setAttribute("org", o);
		return "/system/organizationEdit";
	}
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public Json edit(SysOrganization sysOrganization,HttpServletRequest req) throws Exception {
		Json j = new Json();
		try {
			SysOrganization db_sysOrganization= sysOrganizationService.getById(sysOrganization.getId());

			if(db_sysOrganization==null){
				j.setSuccess(false);
				j.setMsg("部门信息不存在！");
				return j;
			}
			if(!sysOrganization.getCode().equals(db_sysOrganization.getCode())){
				List<SysOrganization> sysOrganizationList=sysOrganizationService.list(new QueryWrapper<SysOrganization>().eq(SystemTable.Sys_Organization.CODE,sysOrganization.getCode()));
				if(sysOrganizationList!=null&&sysOrganizationList.size()>0){
					j.setSuccess(false);
					j.setMsg("部门编号已存在！");
					return j;
				}
			}
			sysOrganization.setUpdateTime(new Date());
			sysOrganizationService.updateById(sysOrganization);
			SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
			sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_ORGANIZATION_MODIFY,"编辑机构:"+db_sysOrganization.getName()+"变为"+sysOrganization.getName());
			j.setSuccess(true);
			j.setMsg("操作成功！");
		} catch (Exception e) {
			logger.error("修改部门异常exception:{}",e.fillInStackTrace());
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("操作失败！");
            throw new Exception() ;
		}
		return j;
	}
	@Transactional(rollbackFor = Exception.class)
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public Json delete(Long id,HttpServletRequest req) throws Exception {
		Json j = new Json();
		try {
			SysOrganization db_sysOrganization= sysOrganizationService.getById(id);
			if(db_sysOrganization==null){
				j.setSuccess(false);
				j.setMsg("部门信息不存在！");
				return j;
			}
			QueryWrapper<SysOrganization> sysOrganizationQueryWrapper = new QueryWrapper<SysOrganization>();
			sysOrganizationQueryWrapper.eq(SystemTable.Sys_Organization.PID,id);
			List<SysOrganization> sysOrganizationList=sysOrganizationService.list(sysOrganizationQueryWrapper);
			if(null != sysOrganizationList && sysOrganizationList.size()>0){
				j.setMsg("操作失败,请先删除子部门！");
				j.setSuccess(false);
				return j;
			}
			List<SysUser> sysUsers = sysUserService.list(new QueryWrapper<SysUser>().eq(SystemTable.Sys_user.CODE,db_sysOrganization.getCode()));
			if(null != sysUsers && sysUsers.size()>0){
				j.setMsg("操作失败,已经关联操作员,不允许删除！");
				j.setSuccess(false);
				return j;
			}
			SysOrganization organization = sysOrganizationService.getById(id);
			sysOrganizationService.removeById(id);
			SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
			sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_ORGANIZATION_REMOVE,"删除机构:"+organization.getName());
			j.setMsg("操作成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.error("删除部门异常exception:{}",e.fillInStackTrace());
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("操作失败！");
            throw new Exception() ;
		}
		return j;
	}
}

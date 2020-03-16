package com.trendcote.web.controller.system;

import javax.servlet.http.HttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.trendcote.web.constrants.GlobalConstant;
import com.trendcote.web.constrants.SysConstants;
import com.trendcote.web.contrants.SystemTable;
import com.trendcote.web.dto.page.Json;
import com.trendcote.web.dto.page.Tree;
import com.trendcote.web.entity.system.SysResource;
import com.trendcote.web.entity.system.SysUser;
import com.trendcote.web.service.system.ISysOperLogService;
import com.trendcote.web.service.system.ISysResourceService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@Transactional(rollbackFor = Exception.class)
@RequestMapping("/resource")
public class ResourceController{
	private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
	@Autowired
	private ISysResourceService sysResourceService;
	@Autowired
	private ISysOperLogService sysOperLogService;

	@RequestMapping("/manager")
	public String manager() {
		return "/system/resource";
	}

	@RequestMapping("/tree")
	@ResponseBody
	public Json tree() {
		SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
		List<Tree> list=tree(loginUser);
		Json j = new Json();
		j.setObj(list);
		j.setSuccess(true);
		return j;
	}
	@RequestMapping("/allTree")
	@ResponseBody
	public List<Tree> allTree(boolean flag) {// true获取全部资源,false只获取菜单资源
		return listAllTree(flag);
	}

	@RequestMapping("/treeGrid")
	@ResponseBody
	public List<SysResource> treeGrid() {
		QueryWrapper queryWrapper=new QueryWrapper<SysResource>().orderByAsc(SystemTable.Sys_Resource.SEQ);
		List<SysResource> rpl=sysResourceService.list(queryWrapper);
		return rpl;
	}
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		SysResource r = sysResourceService.getById(id);
		request.setAttribute("resource", r);
		return "/system/resourceEdit";
	}
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(SysResource sysResource,HttpServletRequest req) throws Exception {
		Json j = new Json();
		try {
			if(null==sysResource.getId()){
				j.setSuccess(false);
				j.setMsg("解析数据异常！");
				return j;
			}
			SysResource db_resource=sysResourceService.getById(sysResource.getId());
			if(null== db_resource){
				j.setSuccess(false);
				j.setMsg("数据不存在！");
				return j;
			}
			sysResource.setUpdateTime(new Date());
			sysResourceService.updateById(sysResource);
			SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
			sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_RESOURCE_MODIFY,"编辑资源:"+db_resource.getName()+"变为"+sysResource.getName());
			j.setSuccess(true);
			j.setMsg("操作成功！");
		} catch (Exception e) {
			logger.error("编辑资源失败exception:{}",e.fillInStackTrace());
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("操作失败！");
			throw new Exception();
		}
		return j;
	}
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Long id,HttpServletRequest req) throws Exception {
		Json j = new Json();
		try {
			List<SysResource> resourceList=sysResourceService.list(new QueryWrapper<SysResource>().eq(SystemTable.Sys_Resource.PID,id));
		    if(resourceList!=null&&resourceList.size()>0){
               j.setMsg("操作失败,请先删除子节点！");
               j.setSuccess(false);
                return  j;
            }
			SysResource resource = sysResourceService.getById(id);
			sysResourceService.removeById(id);
			SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
			sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_RESOURCE_REMOVE,"删除资源:"+resource.getName());
			j.setMsg("操作成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.error("删除资源失败exception:{}",e.fillInStackTrace());
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("操作失败！");
			throw new Exception();
		}
		return j;
	}

	@RequestMapping("/addPage")
	public String addPage( Long pid, HttpServletRequest request) {
        request.setAttribute("pid", pid);
	    return "/system/resourceAdd";
	}
	@RequestMapping("/add")
	@ResponseBody
	public Json add(@Validated SysResource sysResource ,HttpServletRequest req) throws Exception {
		Json j = new Json();
		try {
			sysResource.setCreateTime(new Date());
			sysResourceService.save(sysResource);
			SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
			sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_RESOURCE_ADD,"添加资源:"+sysResource.getName());
			j.setSuccess(true);
			j.setMsg("操作成功！");
		} catch (Exception e) {
			logger.error("添加资源失败exception:{}",e.fillInStackTrace());
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("操作失败！");
			throw new Exception();
		}
		return j;
	}
	public List<Tree> tree(SysUser user){
		List<SysResource> l = null;
		List<Tree> lt = new ArrayList<Tree>();
		List<Tree> ltc = new ArrayList<Tree>();
		QueryWrapper<SysResource> queryWrapper;
		if ("admin".equals(user.getUsername())) {
			queryWrapper= new QueryWrapper<SysResource>();
			queryWrapper.eq(SystemTable.Sys_Resource.RESOURCE_TYPE,GlobalConstant.DEFAULT);
			queryWrapper.orderByAsc(SystemTable.Sys_Resource.SEQ);
			l = sysResourceService.list(queryWrapper);
		} else {
			l=sysResourceService.getParentResourceByUserId(GlobalConstant.DEFAULT,user.getId());
		}
		Tree tree= null;
		if ((l != null) && (l.size() > 0)) {
			for (SysResource r : l) {
				tree = new Tree();
				ltc = new ArrayList<Tree>();
				tree.setId(String.valueOf(r.getId()));
				tree.setText(r.getName());
				Map<String, Object> attr = new HashMap<String, Object>();
				attr.put("url", r.getUrl());
				tree.setAttributes(attr);
				if (r.getPid()==null) {
					tree.setState("closed");
					if(sysResourceService.list(new QueryWrapper<SysResource>().eq("pid",r.getId())).size() > 0){
						for(SysResource rr : l ){
							if(r.getId().equals(rr.getPid())){
								Tree  cTree = new Tree();
								cTree.setText(rr.getName());
								Map<String, Object> attrc = new HashMap<String, Object>();
								attrc.put("url", rr.getUrl());
								cTree.setAttributes(attrc);
								ltc.add(cTree);
							}
						}
					}
					tree.setChildren(ltc);
					lt.add(tree);
				}else{
					continue;
				}
			}
		}
		return lt;
	}
	public List<Tree> listAllTree(boolean flag) {
		List<SysResource> rs = null;
		List<Tree> lt = new ArrayList<Tree>();
		List<Tree> ltc = new ArrayList<Tree>();
		if (flag) {
			rs = sysResourceService.list(new QueryWrapper<SysResource>());
		} else {
			rs = sysResourceService.list(new QueryWrapper<SysResource>().eq(SystemTable.Sys_Resource.RESOURCE_TYPE,0));
		}
		Tree tree= null;
		if ((rs != null) && (rs.size() > 0)) {
			for (SysResource r : rs) {
                tree = new Tree();
                tree.setId(r.getId().toString());
                if (r.getPid() != null) {
                    tree.setPid(r.getPid().toString());
                }
                tree.setText(r.getName());
                lt.add(tree);
			}
		}
		return lt;
	}
 }

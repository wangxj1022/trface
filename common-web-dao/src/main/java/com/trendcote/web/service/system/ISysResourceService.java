package com.trendcote.web.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.trendcote.web.entity.system.SysResource;

import java.util.List;

/**
 * @author 莹
 * @date 2018/6/4
 */
public interface ISysResourceService extends IService<SysResource> {


    public List<String> listAllResource() ;

    public List<SysResource> getParentResourceByUserId(int type ,Long userId);

    public List<SysResource> findResourceByRoleId(Integer roleId);
}

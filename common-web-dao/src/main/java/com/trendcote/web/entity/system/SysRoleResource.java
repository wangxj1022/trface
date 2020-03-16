package com.trendcote.web.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trendcote.web.contrants.SystemTable;

/**
 * @author 莹
 * @date 2018/6/4
 */
@TableName(SystemTable.Sys_Role_Resource.TABLE_NAME)
public class SysRoleResource {
    @TableField(SystemTable.Sys_Role_Resource.ROLE_ID)
//    @TableId
    private Integer roleId;
    @TableField(SystemTable.Sys_Role_Resource.RESOURCE_ID)
//    @TableId
    private Integer resourceId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }
}

package com.trendcote.web.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trendcote.web.contrants.SystemTable;

/**
 * @author 莹
 * @date 2018/6/4
 */
@TableName(SystemTable.Sys_User_Role.TABLE_NAME)
public class SysUserRole {
    @TableField(SystemTable.Sys_User_Role.ROLE_ID)
    private Integer roleId;
    @TableField(SystemTable.Sys_User_Role.USER_ID)
    private Long userId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

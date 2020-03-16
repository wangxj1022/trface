package com.trendcote.web.entity.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trendcote.web.contrants.SystemTable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author 莹
 * @date 2018/6/4
 */
@TableName(SystemTable.Sys_user.TABLE_NAME)
public class SysUser implements java.io.Serializable {
    @TableId
    @TableField(SystemTable.Sys_user.BASE_TABLE_ID)
    private Long id;
    @TableField(SystemTable.Sys_user.LOGIN_NAME)
    @NotBlank
    private String username;
    @TableField(SystemTable.Sys_user.NAME)
    private String name;
    @TableField(SystemTable.Sys_user.PASSWORD)
    @NotBlank
    private String password;
    @TableField(SystemTable.Sys_user.SEX)
    private Integer sex;
    @TableField(SystemTable.Sys_user.CODE)
    private String code ;
    @TableField(SystemTable.Sys_user.AGE)
    private Integer age;
    @TableField(SystemTable.Sys_user.BASE_TABLE_CREATE_TIME)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @TableField(SystemTable.Sys_user.IS_DEFAULT)
    private Integer isdefault; // 是否默认
    @TableField(SystemTable.Sys_user.PHONE)
    private String phone;
    @TableField(SystemTable.Sys_user.ORGANIZATION_ID)
    private Long organizationId;
    @TableField(SystemTable.Sys_user.BASE_TABLE_UPDATE_TIME)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @TableField(exist = false)
    private String organizationName;
    @TableField(exist = false)
    private Integer roleId;
    @TableField(exist = false)
    private String roleNames;
    @TableField(exist = false)
    private String organizationCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Integer isdefault) {
        this.isdefault = isdefault;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

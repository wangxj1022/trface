package com.trendcote.web.entity.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trendcote.web.contrants.SystemTable;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@TableName(SystemTable.Sys_Role.TABEL_NAME)
public class SysRole implements java.io.Serializable {
	@TableId
	@TableField(SystemTable.Sys_Role.BASE_TABLE_ID)
	private Integer id;
	@TableField(SystemTable.Sys_Role.NAME)
	private String name;
	@TableField(SystemTable.Sys_Role.IS_DEFAULT)
	private Integer isdefault;
	@TableField(SystemTable.Sys_Role.DESCRIPTION)
	private String description;
	@TableField(SystemTable.Sys_Role.CODE)
	private String code;
	@TableField(SystemTable.Sys_Role.BASE_TABLE_CREATE_TIME)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
    @TableField(SystemTable.Sys_Role.BASE_TABLE_UPDATE_TIME)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	@TableField(exist = false)
	private String resourceIds;
	@TableField(exist = false)
	private String resourceNames;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(Integer isdefault) {
		this.isdefault = isdefault;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getResourceNames() {
		return resourceNames;
	}

	public void setResourceNames(String resourceNames) {
		this.resourceNames = resourceNames;
	}
}

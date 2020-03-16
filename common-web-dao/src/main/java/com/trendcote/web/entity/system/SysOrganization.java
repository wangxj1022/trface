package com.trendcote.web.entity.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trendcote.web.contrants.SystemTable;

import java.util.Date;
import java.util.List;

@TableName(SystemTable.Sys_Organization.TABLE_NAME)
public class SysOrganization implements java.io.Serializable {

	@TableField(SystemTable.Sys_Organization.BASE_TABLE_ID)
	@TableId
	private Long id;
	@TableField(SystemTable.Sys_Organization.NAME)
	private String name;
	@TableField(SystemTable.Sys_Organization.ADDRESS)
	private String address;
	@TableField(SystemTable.Sys_Organization.CODE)
	private String code;
	@TableField(SystemTable.Sys_Organization.SEQ)
	private Integer seq;
	@TableField(SystemTable.Sys_Organization.PID)
	private Long pid;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@TableField(SystemTable.Sys_Organization.BASE_TABLE_UPDATE_TIME)
	private Date updateTime;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@TableField(SystemTable.Sys_Organization.BASE_TABLE_CREATE_TIME)
	private Date createTime;
	@TableField(exist = false)
	private String pname;
	@TableField(exist = false)
	private String iconCls;
	@TableField(exist = false)
	private List<SysOrganization> children;

	public List<SysOrganization> getChildren() {
		return children;
	}

	public void setChildren(List<SysOrganization> children) {
		this.children = children;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
}

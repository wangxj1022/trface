package com.trendcote.web.entity.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trendcote.web.contrants.SystemTable;

import java.util.Date;

@TableName(SystemTable.Sys_Oper_Log.TABEL_NAME)
public class SysOperLog implements java.io.Serializable{

	@TableField(SystemTable.Sys_Oper_Log.BASE_TABLE_ID)
	@TableId
	private Long id;
	@TableField(SystemTable.Sys_Oper_Log.LOGIN_NAME)
	private String loginName;
	@TableField(SystemTable.Sys_Oper_Log.IP)
	private String ip;
	@TableField(SystemTable.Sys_Oper_Log.OPER_CONTENT)
	private String operContent;
	@TableField(SystemTable.Sys_Oper_Log.OPER_DETAIL)
	private String operDetail;
	@TableField(SystemTable.Sys_Oper_Log.BASE_TABLE_CREATE_TIME)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOperContent() {
		return operContent;
	}

	public void setOperContent(String operContent) {
		this.operContent = operContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperDetail() {
		return operDetail;
	}

	public void setOperDetail(String operDetail) {
		this.operDetail = operDetail;
	}
}

package com.trendcote.web.entity.business;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trendcote.web.contrants.BusinessTable;

import java.util.Date;

@TableName(BusinessTable.SyncLogInfoT.TABLE_NAME)
public class SyncLogInfo implements java.io.Serializable{

	@TableField(BusinessTable.SyncLogInfoT.BASE_TABLE_ID)
	@TableId
	private Long id;
	@TableField(BusinessTable.SyncLogInfoT.LOGIN_NAME)
	private String loginName;
	@TableField(BusinessTable.SyncLogInfoT.IP)
	private String ip;
	@TableField(BusinessTable.SyncLogInfoT.OPER_CONTENT)
	private String operContent;
	@TableField(BusinessTable.SyncLogInfoT.RECORD_ADD)
	private Integer recordAdd;
	@TableField(BusinessTable.SyncLogInfoT.RECORD_REDUCE)
	private Integer recordReduce;

	@TableField(BusinessTable.SyncLogInfoT.BASE_TABLE_CREATE_TIME)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@TableField(BusinessTable.SyncLogInfoT.REMARK)
	private int remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

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

	public Integer getRecordAdd() {
		return recordAdd;
	}

	public void setRecordAdd(Integer recordAdd) {
		this.recordAdd = recordAdd;
	}

	public Integer getRecordReduce() {
		return recordReduce;
	}

	public void setRecordReduce(Integer recordReduce) {
		this.recordReduce = recordReduce;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getRemark() {
		return remark;
	}

	public void setRemark(int remark) {
		this.remark = remark;
	}
}

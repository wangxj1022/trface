package com.trendcote.web.entity.business;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trendcote.web.contrants.BusinessTable;

import java.util.Date;

/**
 *  邮箱配置表
 */
@TableName(BusinessTable.EmailInfoT.TABLE_NAME)
public class EmailInfo {
	@TableField(BusinessTable.EmailInfoT.BASE_TABLE_ID)
	@TableId
	private Long id;
	@TableField(BusinessTable.EmailInfoT.EMAIL_ADRESS)
	private String emailAddress;
	@TableField(BusinessTable.EmailInfoT.EMAIL_NAME)
	private String emailName;
	@TableField(BusinessTable.EmailInfoT.REMARK1)
	private String remark1;
	@TableField(BusinessTable.EmailInfoT.REMARK2)
	private String remark2;
	@TableField(BusinessTable.EntryInfoT.BASE_TABLE_CREATE_TIME)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	@TableField(BusinessTable.EntryInfoT.BASE_TABLE_UPDATE_TIME)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getEmailName() {
		return emailName;
	}

	public void setEmailName(String emailName) {
		this.emailName = emailName;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
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
}
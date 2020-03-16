package com.trendcote.web.entity.business;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trendcote.web.contrants.BusinessTable;
import java.util.Date;

/**
 * 进出口信息表
 */
@TableName(BusinessTable.EntryInfoT.TABLE_NAME)
public class EntryInfo {
	@TableField(BusinessTable.EntryInfoT.BASE_TABLE_ID)
	@TableId
	private Long id;
	@TableField(BusinessTable.EntryInfoT.ENTRY_ID)
	private String entryId;
	@TableField(BusinessTable.EntryInfoT.ENTRY_NAME)
	private String entryName;
	@TableField(BusinessTable.EntryInfoT.ENTRY_CATEGORY)
	private Integer entryCategory;
	@TableField(BusinessTable.EntryInfoT.ENTRY_TYPE)
	private Integer entryType;
	@TableField(BusinessTable.EntryInfoT.ENTRY_PHONE)
	private String entryPhone;
	@TableField(BusinessTable.EntryInfoT.REMARK)
	private String remark;
	@TableField(BusinessTable.EntryInfoT.BASE_TABLE_CREATE_TIME)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	@TableField(BusinessTable.EntryInfoT.BASE_TABLE_UPDATE_TIME)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId == null ? null : entryId.trim();
	}

	public String getEntryName() {
		return entryName;
	}

	public void setEntryName(String entryName) {
		this.entryName = entryName == null ? null : entryName.trim();
	}

	public Integer getEntryCategory() {
		return entryCategory;
	}

	public void setEntryCategory(Integer entryCategory) {
		this.entryCategory = entryCategory;
	}

	public Integer getEntryType() {
		return entryType;
	}

	public void setEntryType(Integer entryType) {
		this.entryType = entryType;
	}

	public String getEntryPhone() {
		return entryPhone;
	}

	public void setEntryPhone(String entryPhone) {
		this.entryPhone = entryPhone == null ? null : entryPhone.trim();
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
package com.trendcote.web.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trendcote.web.contrants.SystemTable;

/**
 * 参数配置表
 */
@TableName(SystemTable.Tb_Params.TABLE_NAME)
public class TbParamsInfo{
	@TableField(SystemTable.Tb_Params.P_Name)
	private String pName;
	@TableField(SystemTable.Tb_Params.P_VALUE)
	private String pValue;
	@TableField(SystemTable.Tb_Params.P_DESC)
	private String pDesc;

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getpValue() {
		return pValue;
	}

	public void setpValue(String pValue) {
		this.pValue = pValue;
	}

	public String getpDesc() {
		return pDesc;
	}

	public void setpDesc(String pDesc) {
		this.pDesc = pDesc;
	}

}
package com.trendcote.web.entity.business;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trendcote.web.contrants.BusinessTable;

import java.util.Date;

/**
 * @Description Echarts统计表
 * @Date 2019/7/5 14:48
 * @Created by xym
 */
@TableName(BusinessTable.EchartsInfoT.TABLE_NAME)
public class EchartsInfo {

    @TableId
    @TableField(BusinessTable.EchartsInfoT.BASE_TABLE_ID)
    private Long id;
    @TableId(BusinessTable.EchartsInfoT.TYPE)
    private Integer type;
    @TableField(BusinessTable.EchartsInfoT.COUNT)
    private Integer count;
    @TableField(BusinessTable.EchartsInfoT.DATE)
    @JSONField(format = "yyyy-MM-dd")
    private Date date;
    @TableField(BusinessTable.EchartsInfoT.REMARK)
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

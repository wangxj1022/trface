package com.trendcote.web.entity.business;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trendcote.web.contrants.BusinessTable;

import java.util.Date;

@TableName(BusinessTable.DeviceuserT.TABLE_NAME)
public class Deviceuser {
    @TableField(BusinessTable.DeviceuserT.DEVICEID)
    public String deviceid;
    @TableField(BusinessTable.DeviceuserT.USERID)
    public String userid;
    @TableField(BusinessTable.DeviceuserT.UPDATETIME)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date updatetime;
    @TableField(BusinessTable.DeviceuserT.SYNCFLAG)
    public Integer syncflag;
    @TableField(BusinessTable.DeviceuserT.ERRMSG)
    public String errmsg;
    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getSyncflag() {
        return syncflag;
    }

    public void setSyncflag(Integer syncflag) {
        this.syncflag = syncflag;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}

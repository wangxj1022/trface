package com.trendcote.web.entity.business;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trendcote.web.contrants.BusinessTable;

import java.util.Date;

/**
 * 返乡人员表
 */
@TableName(BusinessTable.FangKeT.TABLE_NAME)
public class FangKe {

    @TableField(BusinessTable.FangKeT.BASE_TABLE_ID)
    @TableId
    private int id;
    @TableField(BusinessTable.FangKeT.REQID)
    private String reqid;
    @TableField(BusinessTable.FangKeT.LOGTIME)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date logtime;
    @TableField(BusinessTable.FangKeT.XM)
    private String xm;
    @TableField(BusinessTable.FangKeT.XB)
    private String xb;
    @TableField(BusinessTable.FangKeT.MZ)
    private String mz;
    @TableField(BusinessTable.FangKeT.SR)
    private String sr;
    @TableField(BusinessTable.FangKeT.SFZ)
    private String sfz;
    @TableField(BusinessTable.FangKeT.YXQ)
    private String yxq;
    @TableField(BusinessTable.FangKeT.FZJG)
    private String fzjg;
    @TableField(BusinessTable.FangKeT.ZZ)
    private String zz;
    @TableField(BusinessTable.FangKeT.PROVINCE)
    private String province;
    @TableField(BusinessTable.FangKeT.CITY)
    private String city;
    @TableField(BusinessTable.FangKeT.ISCLOSER)
    private int iscloser;
    @TableField(BusinessTable.FangKeT.TRIPTYPE)
    private String triptype;
    @TableField(BusinessTable.FangKeT.TRIPNO)
    private String tripno;
    @TableField(BusinessTable.FangKeT.TEMPERATURE)
    private Float temperature;
    @TableField(BusinessTable.FangKeT.PHONENO)
    private String phoneno;
    @TableField(BusinessTable.FangKeT.TOADDRESS)
    private String toaddress;
    @TableField(BusinessTable.FangKeT.MEMO)
    private String memo;
    @TableField(BusinessTable.FangKeT.DEVICEID)
    private String deviceid;
    @TableField(BusinessTable.FangKeT.PHOTO)
    private String photo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReqid() {
        return reqid;
    }

    public void setReqid(String reqid) {
        this.reqid = reqid;
    }

    public Date getLogtime() {
        return logtime;
    }

    public void setLogtime(Date logtime) {
        this.logtime = logtime;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getSfz() {
        return sfz;
    }

    public void setSfz(String sfz) {
        this.sfz = sfz;
    }

    public String getYxq() {
        return yxq;
    }

    public void setYxq(String yxq) {
        this.yxq = yxq;
    }

    public String getFzjg() {
        return fzjg;
    }

    public void setFzjg(String fzjg) {
        this.fzjg = fzjg;
    }

    public String getZz() {
        return zz;
    }

    public void setZz(String zz) {
        this.zz = zz;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getIscloser() {
        return iscloser;
    }

    public void setIscloser(int iscloser) {
        this.iscloser = iscloser;
    }

    public String getTriptype() {
        return triptype;
    }

    public void setTriptype(String triptype) {
        this.triptype = triptype;
    }

    public String getTripno() {
        return tripno;
    }

    public void setTripno(String tripno) {
        this.tripno = tripno;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getToaddress() {
        return toaddress;
    }

    public void setToaddress(String toaddress) {
        this.toaddress = toaddress;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}

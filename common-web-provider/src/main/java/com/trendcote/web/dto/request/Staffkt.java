package com.trendcote.web.dto.request;



/**
 *@Description  请求设备接口: 特殊员工对象
 */
public class Staffkt {

    private String deptName;    //部门名称
    private String name;    //员工姓名
    private String number;      //员工编号
    private String photo;       //员工照片
    private Integer isOutWork;   //是否外勤
    private String cardNo;      //员工卡号（人事系统暂无，参数传空）
    private String idCardNo;    //员工身份证号

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsOutWork() {
        return isOutWork;
    }

    public void setIsOutWork(Integer isOutWork) {
        this.isOutWork = isOutWork;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return "ServiceStaff{" +
                "deptName='" + deptName + '\'' +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", photo='" + photo + '\'' +
                ", isOutWork=" + isOutWork +
                ", cardNo='" + cardNo + '\'' +
                ", idCardNo='" + idCardNo + '\'' +
                '}';
    }
}

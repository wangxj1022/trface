package com.trendcote.web.dto.request;



/**
 *@Description  请求设备接口:员工对象
 */
public class Staff {

    private String name;    //员工姓名
    private Integer isOutWork;   //是否外勤
    private String number;      //员工编号
    private String cardNo;      //员工卡号（人事系统暂无，参数传空）
    private String idCardNo;    //员工身份证号
    private String photo;       //员工照片

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

    @Override
    public String toString() {
        return "Staff{" +
                "name='" + name + '\'' +
                ", isOutWork=" + isOutWork +
                ", number='" + number + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", idCardNo='" + idCardNo + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}

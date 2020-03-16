package com.trendcote.web.dto.poi;

/**
 * @Description 每日员工对象统计
 * @Date 2019/9/10 14:05
 * @Created by xym
 */

public class StaffDailyRec {

    private int faceSum;    // 刷脸通过数量

    private int icSum;      // 刷员工卡通过数量

    private int idcardSum;  // 刷身份证通过数量

    private int sum;        // 通过总人数

    public int getFaceSum() {
        return faceSum;
    }

    public void setFaceSum(int faceSum) {
        this.faceSum = faceSum;
    }

    public int getIcSum() {
        return icSum;
    }

    public void setIcSum(int icSum) {
        this.icSum = icSum;
    }

    public int getIdcardSum() {
        return idcardSum;
    }

    public void setIdcardSum(int idcardSum) {
        this.idcardSum = idcardSum;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}

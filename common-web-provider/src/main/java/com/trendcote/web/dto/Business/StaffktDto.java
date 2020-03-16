package com.trendcote.web.dto.Business;

/**
 *@Description 昆泰搜索对象
 *@param 
 *@return 
 */
public class StaffktDto
{
    private String staffktNumber;   // 员工编号

    private String staffktDept;   // 单位

    private String staffktName;   // 姓名

    private String staffktTel; //手机号

    public String getStaffktNumber() {
        return staffktNumber;
    }

    public void setStaffktNumber(String staffktNumber) {
        this.staffktNumber = staffktNumber;
    }

    public String getStaffktDept() {
        return staffktDept;
    }

    public void setStaffktDept(String staffktDept) {
        this.staffktDept = staffktDept;
    }

    public String getStaffktName() {
        return staffktName;
    }

    public void setStaffktName(String staffktName) {
        this.staffktName = staffktName;
    }

    public String getStaffktTel() {
        return staffktTel;
    }

    public void setStaffktTel(String staffktTel) {
        this.staffktTel = staffktTel;
    }
}

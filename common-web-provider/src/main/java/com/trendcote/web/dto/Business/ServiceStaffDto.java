package com.trendcote.web.dto.Business;

/**
 *@Description 特殊人员搜索对象
 *@param 
 *@return 
 */
public class ServiceStaffDto
{
    private String serviceStaffDept;   // 单位

    private String serviceStaffName;   // 姓名

    private String serviceStaffTel;     //手机号

    private String serviceStaffNumber;  //员工编号

    private String serviceStaffIcNo;

    public String getServiceStaffIcNo() {
        return serviceStaffIcNo;
    }

    public void setServiceStaffIcNo(String serviceStaffIcNo) {
        this.serviceStaffIcNo = serviceStaffIcNo;
    }

    public String getServiceStaffTel() {
        return serviceStaffTel;
    }

    public void setServiceStaffTel(String serviceStaffTel) {
        this.serviceStaffTel = serviceStaffTel;
    }

    public String getServiceStaffDept() {
        return serviceStaffDept;
    }

    public void setServiceStaffDept(String serviceStaffDept) {
        this.serviceStaffDept = serviceStaffDept;
    }

    public String getServiceStaffName() {
        return serviceStaffName;
    }

    public void setServiceStaffName(String serviceStaffName) {
        this.serviceStaffName = serviceStaffName;
    }

    public String getServiceStaffNumber() {
        return serviceStaffNumber;
    }

    public void setServiceStaffNumber(String serviceStaffNumber) {
        this.serviceStaffNumber = serviceStaffNumber;
    }
}

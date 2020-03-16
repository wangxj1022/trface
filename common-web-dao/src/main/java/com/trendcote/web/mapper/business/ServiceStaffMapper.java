package com.trendcote.web.mapper.business;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trendcote.web.entity.business.ServiceStaffInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


/**
 * @author wangxingji
 */
public interface ServiceStaffMapper extends BaseMapper<ServiceStaffInfo> {
    @Select("select service_staff_icNo from service_staff_info WHERE id=#{id} ")
    String findById(@Param("id") Long id);

    @Select("select service_staff_icNo from service_staff_info where service_staff_icNo =#{serviceStaffIcNo}")
    String findOne(@Param("serviceStaffIcNo") String serviceStaffIcNo);

    @Select("select service_staff_icNo,service_staff_photo from service_staff_info")
    List<ServiceStaffInfo> findList();
    @Update("update service_staff_info set picStatus = 0 where service_staff_icNo = #{serviceStaffIcNo}")
    void updateByIdCard(@Param("serviceStaffIcNo") String serviceStaffIcNo);
    @Update("update service_staff_info set picStatus = 1 where service_staff_icNo = #{serviceStaffIcNo}")
    void updateByIDCardT(@Param("serviceStaffIcNo") String serviceStaffIcNo);
}

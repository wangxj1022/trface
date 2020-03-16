package com.trendcote.web.service.business;

import com.baomidou.mybatisplus.extension.service.IService;
import com.trendcote.web.entity.business.ServiceStaffInfo;

import java.util.List;

/**
 * @author 莹
 * @date 2018/6/4
 */
public interface IServiceStaffService extends IService<ServiceStaffInfo> {
    String findById(Long id);

    void insert(List<ServiceStaffInfo> list);

    List<ServiceStaffInfo> findList();

    void updateByIdCard(String serviceStaffIcNo);

    void updateByIDCardT(String serviceStaffIcNo);
}

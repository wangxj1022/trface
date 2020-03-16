package com.trendcote.web.service.business.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trendcote.web.contrants.BusinessTable;
import com.trendcote.web.entity.business.ServiceStaffInfo;
import com.trendcote.web.mapper.business.ServiceStaffMapper;
import com.trendcote.web.service.business.IServiceStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author 莹
 * @date 2018/6/4
 */
@Service
public class ServiceStaffServiceImpl extends ServiceImpl<ServiceStaffMapper, ServiceStaffInfo> implements IServiceStaffService {
    @Autowired
    private ServiceStaffMapper serviceStaffMapper;

    @Override
    public String findById(Long id) {
        return this.serviceStaffMapper.findById(id);
    }

    @Override
    public void insert(List<ServiceStaffInfo> list) {
        for (int i =0 ; i<list.size();i++) {
            System.out.println("============="+list.get(i).getServiceStaffIcNo());
               String idCardNo = this.serviceStaffMapper.findOne(list.get(i).getServiceStaffIcNo());
               if (null == idCardNo) {
                   ServiceStaffInfo staffInfoOne = new ServiceStaffInfo();
                   staffInfoOne.setServiceStaffIcNo(list.get(i).getServiceStaffIcNo());
                   staffInfoOne.setServiceStaffName(list.get(i).getServiceStaffName());
                   staffInfoOne.setIsOutWork(0);
                   staffInfoOne.setCreateTime(new Date());
                   FileSystemView fsv = FileSystemView.getFileSystemView();//获取本的桌面路径
                   String pathPhoto = fsv.getHomeDirectory().toString() + "\\staffphoto\\";
                   String[] imageList = new File(pathPhoto).list();
                   for (String img : imageList) {
                       String imagnum = img.substring(0, img.lastIndexOf("."));
                       if (imagnum.equals(list.get(i).getServiceStaffIcNo())) {
                           String imgpath = pic2String(new File(pathPhoto + img));
                           staffInfoOne.setServiceStaffPhoto(imgpath);
                           staffInfoOne.setPicStatus(1);
                       }
                   }
                   this.serviceStaffMapper.insert(staffInfoOne);
               } else {
                   ServiceStaffInfo staffInfo = new ServiceStaffInfo();
                   staffInfo.setServiceStaffIcNo(list.get(i).getServiceStaffIcNo());
                   staffInfo.setServiceStaffName(list.get(i).getServiceStaffName());
                   staffInfo.setIsOutWork(0);
                   staffInfo.setUpdateTime(new Date());
                   FileSystemView fsv = FileSystemView.getFileSystemView();//获取本的桌面路径
                   String pathPhoto = fsv.getHomeDirectory().toString() + "\\staffphoto\\";
                   String[] imageList = new File(pathPhoto).list();
                   System.out.println(imageList.length);
                   for (String img : imageList) {
                       String imagnum = img.substring(0, img.lastIndexOf("."));
                       if (imagnum.equals(list.get(i).getServiceStaffIcNo())) {
                           String imgpath = pic2String(new File(pathPhoto + img));
                           staffInfo.setServiceStaffPhoto(imgpath);
                           staffInfo.setPicStatus(1);
                       }
                   }
                   this.serviceStaffMapper.update(staffInfo, new QueryWrapper<ServiceStaffInfo>().eq(BusinessTable.ServiceStaffInfoT.SERVICE_STAFF_ICNO, list.get(i).getServiceStaffIcNo()));
               }
           }

    }

    @Override
    public List<ServiceStaffInfo> findList() {
        return this.serviceStaffMapper.findList();
    }

    @Override
    public void updateByIdCard(String serviceStaffIcNo) {
        this.serviceStaffMapper.updateByIdCard(serviceStaffIcNo);
    }

    @Override
    public void updateByIDCardT(String serviceStaffIcNo) {
        this.serviceStaffMapper.updateByIDCardT(serviceStaffIcNo);
    }

    public static String pic2String( File picture){
        FileInputStream fis = null;
        byte[] data = null;

        try {
            fis = new FileInputStream(picture);
            data = new byte[fis.available()];
            fis.read(data);

        } catch (FileNotFoundException e) {
            System.out.println("图片找不到,"+ e.toString());
        } catch (IOException e){
            System.out.println("IO异常,"+ e.toString());
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        BASE64Encoder encoder = new BASE64Encoder();
        String encode = encoder.encode(data).replace("\r\n","");
        return encode;
    }

}

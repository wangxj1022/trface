package com.trendcote.web.controller.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trendcote.web.constrants.GlobalConstant;
import com.trendcote.web.constrants.SysConstants;
import com.trendcote.web.contrants.BusinessTable;
import com.trendcote.web.dto.Business.DeviceDto;
import com.trendcote.web.dto.page.Grid;
import com.trendcote.web.dto.page.Json;
import com.trendcote.web.dto.page.PageFilter;
import com.trendcote.web.entity.business.Deviceuser;
import com.trendcote.web.entity.business.EntryDeviceInfo;
import com.trendcote.web.entity.business.EntryInfo;
import com.trendcote.web.entity.system.SysUser;
import com.trendcote.web.service.business.IDeviceService;
import com.trendcote.web.service.business.IDeviceuserService;
import com.trendcote.web.service.business.IEntryInfoService;
import com.trendcote.web.service.system.ISysOperLogService;
import com.trendcote.web.utils.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Stream;


/**
 * @Description 设备管理
 * @Date 2019/6/13 17:25
 * @Created by xym
 */
@Transactional(rollbackFor = Exception.class)
@Controller
@RequestMapping("/device")
public class DeviceController {

    public static final Logger LOGGER= LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    private ISysOperLogService sysOperLogService;

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private IEntryInfoService entryInfoService;
    @Autowired
    private IDeviceuserService iDeviceuserService;

    /**
     *@Description 闸机管理页面
     *@param 
     *@return 
     */
    @RequestMapping("/manager")
    public String deviceManager(){
        return "/business/device/entryDeviceQuery";
    }

    /**
     *@Description 闸机设备检索
     *@param
     *@return 
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public Grid dataGrid(DeviceDto dto, PageFilter ph){

        Grid grid = new Grid();
        Page<EntryDeviceInfo> pg = new Page<>(ph.getPage(),ph.getRows());
        pg.setDesc(BusinessTable.DeviceT.BASE_TABLE_CREATE_TIME);   //根据访客预约登记时间降序排列

        QueryWrapper<EntryDeviceInfo> queryWrapper = new QueryWrapper<>();
        //进出口条件搜索
        if( StringUtils.isNotEmpty(dto.getEntryName())){
            EntryInfo entryInfo = entryInfoService.getOne(new QueryWrapper<EntryInfo>().eq(BusinessTable.EntryInfoT.ENTRY_NAME, dto.getEntryName()));
            if( entryInfo != null ){
                queryWrapper.eq(BusinessTable.DeviceT.ENTRY_ID, entryInfo.getEntryId());
            } else {
                queryWrapper.eq(BusinessTable.DeviceT.ENTRY_ID, -1);    //该出入口没有设备，则ID默认为-1
            }
        }

        queryWrapper.eq(StringUtils.isNotEmpty(dto.getDeviceId()),BusinessTable.DeviceT.DEVICE_ID,dto.getDeviceId());
        queryWrapper.eq(StringUtils.isNotEmpty(dto.getDeviceIp()),BusinessTable.DeviceT.DEVICE_IP,dto.getDeviceIp());
        IPage<EntryDeviceInfo> deviceInfoIPage=deviceService.page(pg,queryWrapper);
        //设备是否在线判断  后台Sercer进行判断 :
        /*List<EntryDeviceInfo> devices = deviceInfoIPage.getRecords();
        for( EntryDeviceInfo device : devices ){
            boolean alive = HttpClientUtil.isAlive(device.getDeviceIp());
            if( !alive ){
                device.setRunMode(GlobalConstant.DEFAULT);
            } else {
                device.setRunMode(GlobalConstant.NOT_DEFAULT);
            }
        }*/
        grid.setRows(deviceInfoIPage.getRecords());
        grid.setTotal(deviceInfoIPage.getTotal());
        return grid;
    }


    /**
     *@Description 删除设备方法
     *@param 
     *@return 
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Json deleteDevice(Long id, HttpServletRequest req){
        Json j = new Json();
        try {
            EntryDeviceInfo device = deviceService.getById(id);
            if( device == null ){
                j.setSuccess(false);
                j.setMsg("该设备不存在");
                return j;
            }
            deviceService.removeById(id);
            j.setSuccess(true);
            j.setMsg("删除设备成功");
            SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_DEVICE_DEL,"删除设备:"+device.getDeviceName()+device.getDeviceIp());
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("删除设备异常");
            LOGGER.error("删除设备失败{}",e.fillInStackTrace());
        }
        return j;
    }

    @RequestMapping("/zhuce")
    @ResponseBody
    public Json zhuce(Long id){
        Json j = new Json();
        //根据id 查询 设备的编号
        EntryDeviceInfo entryDeviceInfo = this.deviceService.getOne(new QueryWrapper<EntryDeviceInfo>().eq(BusinessTable.DeviceT.BASE_TABLE_ID,id));
        if (null != entryDeviceInfo){
            String deviceid = entryDeviceInfo.getDeviceId();
            //根据设备编号 修改字段
            EntryDeviceInfo deviceInfo = new EntryDeviceInfo();
            String n ="";
            deviceInfo.setWelcomeDesc(n);
            this.deviceService.update(deviceInfo,new QueryWrapper<EntryDeviceInfo>().eq(BusinessTable.DeviceT.DEVICE_ID,deviceid));
            //根据设备编号删除tb_deviceuser 表中的对应的记录
            this.iDeviceuserService.delectByIds(deviceid);
        }
        j.setSuccess(true);
        j.setMsg("注册成功！");
        return j;


    }


    /**
     *@Description 跳转到添加页面
     *@param
     *@return
     */
    @RequestMapping("/add")
    public String addDevice(){
        return "/business/device/deviceAdd";
    }

    /**
     *@Description 添加闸机设备
     *@param
     *@return 
     */
    @RequestMapping("/save")
    @ResponseBody
    public Json saveDevice(EntryDeviceInfo deviceInfo,HttpServletRequest req){
        Json json = new Json();
        try {
            EntryDeviceInfo device = deviceService.getOne(new QueryWrapper<EntryDeviceInfo>()
                    .eq(BusinessTable.DeviceT.DEVICE_ID, deviceInfo.getDeviceId())
                    .or()
                    .eq(BusinessTable.DeviceT.DEVICE_IP,deviceInfo.getDeviceIp()));
            if (device != null) {
                json.setSuccess(false);
                json.setMsg("该设备已经存在！请查看设备 ID/IP 是否已存在");
                return json;
            }

            deviceInfo.setRunMode(0);       // 通信：默认为离线模式；每次查则进行判断
            deviceInfo.setActiveStatus(1);  // 告警：默认为正常
            Date now = new Date();
            deviceInfo.setCreateTime(now);  //创建时间
            deviceInfo.setUpdateTime(now);  //更新时间
            deviceInfo.setDeviceType(1);
            deviceService.save(deviceInfo);
            json.setSuccess(true);
            json.setMsg("添加成功！");
            SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_DEVICE_ADD,"存储设备:"+deviceInfo.getDeviceName()+deviceInfo.getDeviceIp());
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("添加异常！");
            LOGGER.error("添加设备失败{}",e.fillInStackTrace());
        }
        return json;
    }

    /**
     *@Description 跳转到修改页面
     *@param
     *@return
     */
    @RequestMapping("/detail")
    public String deviceDetail(Long id,Model model){
        EntryDeviceInfo device = deviceService.getById(id);
        model.addAttribute("device",device);
        return "/business/device/deviceDetail";
    }


    /**
     *@Description 更新闸机设备
     *@param
     *@return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Json updateDevice(EntryDeviceInfo deviceInfo,HttpServletRequest req){
        Json json = new Json();
        try {
            EntryDeviceInfo device = deviceService.getOne(new QueryWrapper<EntryDeviceInfo>().eq(BusinessTable.DeviceT.BASE_TABLE_ID, deviceInfo.getId()));
            if (device == null) {
                json.setSuccess(false);
                json.setMsg("更新设备不存在！");
                return json;
            }
            if (deviceInfo.getDeviceId().equals(device.getDeviceId())){
                Date now = new Date();
                deviceInfo.setUpdateTime(now);  //更新时间
                deviceInfo.setActiveStatus(deviceInfo.getActiveStatus());  // 告警：默认为正常
                deviceService.updateById(deviceInfo);
            }else {
                Date now = new Date();
                deviceInfo.setUpdateTime(now);  //更新时间
                deviceInfo.setActiveStatus(deviceInfo.getActiveStatus());  // 告警：默认为正常
                String m = "";
                deviceInfo.setWelcomeDesc(m);
                deviceService.updateById(deviceInfo);
            }
            json.setSuccess(true);
            json.setMsg("更新成功！");
            //阳光用
           /* SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_DEVICE_UPDATE,"更新闸机设备:"+device.getDeviceName()+device.getDeviceIp());*/
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("更新失败！");
            LOGGER.error("更新设备失败{}",e.fillInStackTrace());
        }
        return json;
    }


    /**
     *@Description 返回所有设备
     *@param 
     *@return 
     */
    @RequestMapping("/listAll")
    @ResponseBody
    public List<EntryDeviceInfo> listAll(){
        List<EntryDeviceInfo> lists = deviceService.list(null);
        return lists;
    }


}

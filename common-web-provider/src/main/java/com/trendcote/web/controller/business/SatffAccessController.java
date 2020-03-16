package com.trendcote.web.controller.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trendcote.web.constrants.SysConstants;
import com.trendcote.web.contrants.BusinessTable;
import com.trendcote.web.dto.Business.StaffDto;
import com.trendcote.web.dto.page.Grid;
import com.trendcote.web.dto.page.Json;
import com.trendcote.web.dto.page.PageFilter;
import com.trendcote.web.dto.poi.StaffDailyRec;
import com.trendcote.web.entity.business.EntryDeviceInfo;
import com.trendcote.web.entity.business.ServiceStaffInfo;
import com.trendcote.web.entity.business.StaffAccessInfo;
import com.trendcote.web.entity.system.SysUser;
import com.trendcote.web.service.business.IDeviceService;
import com.trendcote.web.service.business.IServiceStaffService;
import com.trendcote.web.service.business.IStaffAccessInfoService;
import com.trendcote.web.service.business.IStaffService;
import com.trendcote.web.service.system.ISysOperLogService;
import com.trendcote.web.utils.DataUtil;
import com.trendcote.web.utils.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 莹
 * @date 2018/6/4
 */
@Controller
@RequestMapping("/staffAccess")
public class SatffAccessController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SatffAccessController.class);
    @Autowired
    private IStaffAccessInfoService staffAccessInfoService;
    @Autowired
    private IStaffService staffService;
    @Autowired
    private IServiceStaffService serviceStaffService;
    @Autowired
    private ISysOperLogService sysOperLogService;
    @Autowired
    private IDeviceService iDeviceService;

    @RequestMapping("/manager")
    public String manager(){
        return "/business/staff/staffAccess";
    }

    @RequestMapping(value = "/dataGrid",method = RequestMethod.POST)
    @ResponseBody
    public Grid dataGrid(StaffDto dto, PageFilter ph){
        Grid grid = new Grid();
        Page<StaffAccessInfo> pg = new Page<StaffAccessInfo>(ph.getPage(),ph.getRows());
        pg.setDesc(BusinessTable.StaffAccessT.IN_TIME);
        QueryWrapper<StaffAccessInfo> queryWrapper = new QueryWrapper<StaffAccessInfo>();
        //不查询图片字段
        queryWrapper.excludeColumns(StaffAccessInfo.class, BusinessTable.StaffAccessT.IN_CAPTURE_IMAEG);
        queryWrapper.excludeColumns(StaffAccessInfo.class, BusinessTable.StaffAccessT.IN_COMPARE_IMAGE);
        queryWrapper.excludeColumns(StaffAccessInfo.class, BusinessTable.StaffAccessT.OUT_CAPTURE_IMAEG);
        queryWrapper.excludeColumns(StaffAccessInfo.class, BusinessTable.StaffAccessT.OUT_COMPARE_IMAGE);
        // queryWrapper.eq(BusinessTable.StaffAccessT.STATUS,0);   逻辑删除字段并未启用
        queryWrapper.eq(DataUtil.isStringNotEmpty(dto.getStaffCode()),BusinessTable.StaffAccessT.STAFF_CODE,dto.getStaffCode());
        queryWrapper.like(DataUtil.isStringNotEmpty(dto.getStaffName()), BusinessTable.StaffAccessT.STAFF_NAME,dto.getStaffName());
        //queryWrapper.eq(StringUtils.isNotEmpty(dto.getOutDeviceId()), BusinessTable.StaffAccessT.OUT_DEVICE_ID,dto.getOutDeviceId());
        queryWrapper.ge(ph.getNewBeginTime()!=null, BusinessTable.StaffAccessT.IN_TIME,ph.getNewBeginTime());
        queryWrapper.le(ph.getNewEndTime()!=null, BusinessTable.StaffAccessT.IN_TIME,ph.getNewEndTime());

        IPage<StaffAccessInfo> staffAccessInfoIPage=staffAccessInfoService.page(pg,queryWrapper);
        grid.setRows(staffAccessInfoIPage.getRecords());
        grid.setTotal(staffAccessInfoIPage.getTotal());
        return grid;
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Json delete(Long id, HttpServletRequest req) {
        Json j = new Json();
        try {
            StaffAccessInfo db_staffAccess = staffAccessInfoService.getById(id);
            if(db_staffAccess==null){
                j.setMsg("员工出入信息不存在！");
                j.setSuccess(false);
                return j;
            }
            if(db_staffAccess.getAccessStatus()==0){
                j.setMsg("员工出入信息状态不合法！");
                j.setSuccess(false);
                return j;
            }
            db_staffAccess.setStatus(1);
            staffAccessInfoService.updateById(db_staffAccess);
            SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_STAFF_ACCESS_DEL,"删除员工记录:"+db_staffAccess.getStaffName()+","+db_staffAccess.getCreateTime());
            j.setMsg("操作成功！");
            j.setSuccess(true);
        } catch (Exception e) {
            LOGGER.error("删除员工出入信息异常exception:{}",e.fillInStackTrace());
            e.printStackTrace();
            j.setMsg("操作失败！");
            j.setSuccess(false);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return j;
    }

    @RequestMapping("/detail")
    public String  get(Long id, HttpServletRequest request){
        StaffAccessInfo staffAccessInfo = staffAccessInfoService.getById(id);
        String curPhoto = "";
        String inPhoto = "";
        String sId = staffAccessInfo.getOutDeviceId();
        if( null != id ){
            // 现场抓拍照查询
            StaffAccessInfo accessInfo = this.staffAccessInfoService.getOne(new QueryWrapper<StaffAccessInfo>().eq(StringUtils.isNotEmpty(BusinessTable.StaffAccessT.BASE_TABLE_ID), BusinessTable.StaffAccessT.BASE_TABLE_ID,id));
            if(null != accessInfo ){
                curPhoto = accessInfo.getOutCaptureImage();
            }
            //原始录入照片查询
            ServiceStaffInfo serviceStaff = serviceStaffService.getOne(new QueryWrapper<ServiceStaffInfo>().eq(StringUtils.isNotEmpty(BusinessTable.ServiceStaffInfoT.SERVICE_STAFF_ICNO), BusinessTable.ServiceStaffInfoT.SERVICE_STAFF_ICNO, staffAccessInfo.getStaffCode()));
            if( null != serviceStaff ){
                inPhoto = serviceStaff.getServiceStaffPhoto();
            }


        }
        staffAccessInfo.setInCaptureImage(inPhoto);
        staffAccessInfo.setOutCaptureImage(curPhoto);
        request.setAttribute("staffAccessInfo", staffAccessInfo);
        return "/business/staff/staffAccessInfoDetail";
    }


    /**
     *@Description 暂未使用：导出-Excel
     *@param 
     *@return 
     */
    @RequestMapping("/exportExcel")
    @ResponseBody
    public Json exportExcelStaff(String name, String startDate, String endDate){
        Json j  = new Json();
        // 检索结果查询
        QueryWrapper<StaffAccessInfo> queryWrapper = new QueryWrapper<>();
        // 不查询图片字段
        queryWrapper.excludeColumns(StaffAccessInfo.class, BusinessTable.StaffAccessT.IN_CAPTURE_IMAEG);
        queryWrapper.like(DataUtil.isStringNotEmpty(name), BusinessTable.StaffAccessT.STAFF_NAME,name);
        queryWrapper.ge(StringUtils.isNotEmpty(startDate), BusinessTable.StaffAccessT.IN_TIME,startDate);
        queryWrapper.le(StringUtils.isNotEmpty(endDate), BusinessTable.StaffAccessT.IN_TIME,endDate);
        List<StaffAccessInfo> staffAccessInfos = staffAccessInfoService.list(queryWrapper);

        // 导出为Excel
        // 当日刷卡统计对象 : 1. 身份证 2. 员工IC卡 3.人脸
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String currentDay = sdf.format(new Date());
        int sum = staffAccessInfoService.list(new QueryWrapper<StaffAccessInfo>().gt(BusinessTable.StaffAccessT.BASE_TABLE_CREATE_TIME, currentDay)).size();
        int idcardSum = staffAccessInfoService.list(new QueryWrapper<StaffAccessInfo>().gt(BusinessTable.StaffAccessT.BASE_TABLE_CREATE_TIME, currentDay).eq(BusinessTable.StaffAccessT.IN_PERSON_TYPE, 1)).size();
        int icSum = staffAccessInfoService.list(new QueryWrapper<StaffAccessInfo>().gt(BusinessTable.StaffAccessT.BASE_TABLE_CREATE_TIME, currentDay).eq(BusinessTable.StaffAccessT.IN_PERSON_TYPE, 2)).size();
        int faceSum = staffAccessInfoService.list(new QueryWrapper<StaffAccessInfo>().gt(BusinessTable.StaffAccessT.BASE_TABLE_CREATE_TIME, currentDay).eq(BusinessTable.StaffAccessT.IN_PERSON_TYPE, 3)).size();
        StaffDailyRec rec = new StaffDailyRec();
        rec.setSum(sum);
        rec.setIdcardSum(idcardSum);
        rec.setIcSum(icSum);
        rec.setFaceSum(faceSum);
        SimpleDateFormat sdf2  = new SimpleDateFormat("MMdd");
        String prefix = sdf2.format(new Date());
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            ExcelUtil.generateExcel("员工出入记录", prefix + "出入统计", staffAccessInfos, rec);
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("操作失败");
            return j;
        } finally {
            if( fis!= null ){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if( bis!= null ){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        j.setSuccess(true);
        j.setMsg("导出成功，文件在桌面！");
        return j;
    }

}

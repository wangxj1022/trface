package com.trendcote.web.controller.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trendcote.web.constrants.SysConstants;
import com.trendcote.web.contrants.BusinessTable;
import com.trendcote.web.dto.Business.EntryDto;
import com.trendcote.web.dto.page.Grid;
import com.trendcote.web.dto.page.Json;
import com.trendcote.web.dto.page.PageFilter;
import com.trendcote.web.entity.business.EntryInfo;
import com.trendcote.web.entity.system.SysUser;
import com.trendcote.web.service.business.impl.EntryInfoServiceImpl;
import com.trendcote.web.service.system.ISysOperLogService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Description 进出口管理
 * @Date 2019/6/16 23:03
 * @Created by xym
 */

@Transactional(rollbackFor = Exception.class)
@Controller
@RequestMapping("/entry")
public class EntryController {

    public static final Logger LOGGER= LoggerFactory.getLogger(EntryController.class);

    @Autowired
    private ISysOperLogService sysOperLogService;

    @Autowired
    private EntryInfoServiceImpl entryInfoService;

    /**
     *@Description 进出口管理
     *@param 
     *@return 
     */
    @RequestMapping("/manager")
    public String entryManager(){
        return "/business/entry/entryQuery";
    }
    
    /**
     *@Description 出入口列表
     *@param
     *@return 
     */
    @ResponseBody
    @RequestMapping("/dataGrid")
    public Grid entryDataGrid(EntryDto dto, PageFilter ph){

        Grid grid = new Grid();
        Page<EntryInfo> pg = new Page<>(ph.getPage(),ph.getRows());
        pg.setDesc(BusinessTable.DeviceT.BASE_TABLE_CREATE_TIME);   //根据访客预约登记时间降序排列

        QueryWrapper<EntryInfo> queryWrapper = new QueryWrapper<>();
        if( StringUtils.isNotEmpty(dto.getEntryName())){
            queryWrapper.like(BusinessTable.EntryInfoT.ENTRY_NAME,dto.getEntryName());
        }
        if( dto.getEntryCategory() != null){
            queryWrapper.eq(BusinessTable.EntryInfoT.ENTRY_CATEGORY,dto.getEntryCategory());
        }

        IPage<EntryInfo> entryInfoIPage = entryInfoService.page(pg,queryWrapper);
        grid.setRows(entryInfoIPage.getRecords());
        grid.setTotal(entryInfoIPage.getTotal());
        return grid;

    }


    /**
     *@Description 出入口信息添加页面
     *@param 
     *@return 
     */
    @RequestMapping("/add")
    public String entryAdd(){
        return "/business/entry/entryAdd";
    }

    /**
     *@Description 添加出入口信息
     *@param 
     *@return 
     */
    @ResponseBody
    @RequestMapping("/save")
    public Json entrySave(EntryInfo entryInfo, HttpServletRequest req){
        Json json = new Json();
        try {
            EntryInfo entry = entryInfoService.getOne(new QueryWrapper<EntryInfo>().eq(BusinessTable.EntryInfoT.ENTRY_ID, entryInfo.getEntryId()));
            if (entry != null) {
                json.setSuccess(false);
                json.setMsg("该进出设备编号已经存在！");
                return json;
            }

            Date now = new Date();
            entryInfo.setCreateTime(now);  //创建时间
            entryInfo.setUpdateTime(now);  //更新时间
            entryInfoService.save(entryInfo);

            json.setSuccess(true);
            json.setMsg("添加成功！");
            SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_ENTRY_ADD ,"添加出入口信息:"+entryInfo.getEntryName());
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("添加异常！");
            LOGGER.error("添加进出口信息异常{}",e.fillInStackTrace());
        }
        return json;
    }

    /**
     *@Description 进出口信息删除
     *@param 
     *@return 
     */
    @ResponseBody
    @RequestMapping("/delete")
    public Json delEntry(Long id,HttpServletRequest req){
        Json j = new Json();
        try {
            EntryInfo entry = entryInfoService.getById(id);
            if( entry == null ){
                j.setSuccess(false);
                j.setMsg("进出口信息不存在");
                return j;
            }

            entryInfoService.removeById(id);
            j.setSuccess(true);
            j.setMsg("删除出入口信息成功");
            SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_ENTRY_DEL , "删除出入口信息:" + entry.getEntryName() );
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("删除出入口信息异常");
            LOGGER.error("删除出入口信息异常{}",e.fillInStackTrace());
        }
        return j;
    }

    /**
     *@Description 进出口信息编辑页面
     *@param
     *@return
     */
    @RequestMapping("/detail")
    public String entryDetail(Long id,Model model){
        EntryInfo entry = entryInfoService.getById(id);
        model.addAttribute("entry",entry);
        return "/business/entry/entryDetail";
    }


    /**
     *@Description 编辑进出口
     *@param
     *@return 
     */
    @ResponseBody
    @RequestMapping("/update")
    public Json entryUpdate(EntryInfo entryInfo,HttpServletRequest req){
        Json json = new Json();
        try {
            EntryInfo device = entryInfoService.getOne(new QueryWrapper<EntryInfo>().eq(BusinessTable.EntryInfoT.ENTRY_ID, entryInfo.getEntryId()));
            if (device == null) {
                json.setSuccess(false);
                json.setMsg("该进出口信息不存在！");
                return json;
            }

            Date now = new Date();
            entryInfo.setUpdateTime(now);  //更新时间
            entryInfoService.updateById(entryInfo);
            json.setSuccess(true);
            json.setMsg("更新成功！");
            SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_ENTRY_UPDATE ,"更新出入口信息:"+entryInfo.getEntryName() );

        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("更新异常！");
            LOGGER.error("更新进出口信息异常{}",e.fillInStackTrace());
        }
        return json;
    }

}

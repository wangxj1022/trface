package com.trendcote.web.controller.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trendcote.web.constrants.GlobalConstant;
import com.trendcote.web.constrants.LogConstants;
import com.trendcote.web.constrants.SysConstants;
import com.trendcote.web.contrants.BusinessTable;
import com.trendcote.web.dto.page.Grid;
import com.trendcote.web.dto.page.Json;
import com.trendcote.web.dto.page.PageFilter;
import com.trendcote.web.entity.business.SyncLogInfo;
import com.trendcote.web.entity.system.SysUser;
import com.trendcote.web.service.business.ISyncLogInfoService;
import com.trendcote.web.service.system.ISysOperLogService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 同步日志管理模块
 * @Date 2019/7/3 13:33
 * @Created by xym
 */

@Controller
@RequestMapping("/synclog")
public class LogController {


    public static final Logger LOGGER = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private ISyncLogInfoService syncLogInfoService;

    @Autowired
    private ISysOperLogService sysOperLogService;

    /**
     *@Description 员工同步日志管理
     *@param 
     *@return 
     */
    @RequestMapping("/staffManager")
    public String syncStaffManager(){
        return "/business/synclog/staffManager";
    }


    /**
     *@Description 访客同步日志管理
     *@param
     *@return 
     */
    @RequestMapping("/visitorManager")
    public String syncVisitorManager(){
        return "/business/synclog/visitorManager";
    }


    /**
     *@Description 员工同步日志管理查询
     *@param 
     *@return 
     */
    @RequestMapping("/staffDataGrid")
    @ResponseBody
    public Grid syncStaffLogQuery(PageFilter ph){
        Grid grid = new Grid();
        Page<SyncLogInfo> pg = new Page<>(ph.getPage(),ph.getRows());
        pg.setDesc(BusinessTable.SyncLogInfoT.BASE_TABLE_CREATE_TIME);   //根据访客预约登记时间降序排列

        QueryWrapper<SyncLogInfo> syncLogQueryWrapper= new QueryWrapper<SyncLogInfo>()
                .ge(ph.getNewBeginTime()!=null ,BusinessTable.SyncLogInfoT.BASE_TABLE_CREATE_TIME, ph.getNewBeginTime())
                .le(ph.getNewEndTime()!=null,BusinessTable.SyncLogInfoT.BASE_TABLE_CREATE_TIME,ph.getNewEndTime())
                .eq(BusinessTable.SyncLogInfoT.REMARK, LogConstants.LOG_SYNC_STAFF);  // 员工

        IPage<SyncLogInfo> dbPg = syncLogInfoService.page(pg,syncLogQueryWrapper);
        grid.setRows(dbPg.getRecords());
        grid.setTotal(dbPg.getTotal());

        return grid;
    }

    /**
     *@Description 同步日志删除
     *@param 
     *@return 
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(String logIds, HttpServletRequest req) throws Exception {
        Json j = new Json();
        try {
            String[] str = logIds.split(",");
            for(String l : str){
                syncLogInfoService.removeById(Long.valueOf(l));
            }
            SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_SYNCLOG_REMOVE,"删除同步日志");
            j.setMsg("操作成功！");
            j.setSuccess(true);
        } catch (Exception e) {
            j.setMsg("操作失败！");
            j.setSuccess(false);
            LOGGER.error("删除同步日志失败exception:{}",e.fillInStackTrace());
            throw  new Exception();
        }
        return j;
    }



    /**
     *@Description 访客同步日志管理查询
     *@param
     *@return
     */
    @RequestMapping("/visitorDataGrid")
    @ResponseBody
    public Grid syncVisitorLogQuery(PageFilter ph){
        Grid grid = new Grid();

        Page<SyncLogInfo> pg = new Page<>(ph.getPage(),ph.getRows());
        pg.setDesc(BusinessTable.SyncLogInfoT.BASE_TABLE_CREATE_TIME);   //根据访客预约登记时间降序排列

        QueryWrapper<SyncLogInfo> syncLogQueryWrapper= new QueryWrapper<SyncLogInfo>()
                .ge(ph.getNewBeginTime()!=null ,BusinessTable.SyncLogInfoT.BASE_TABLE_CREATE_TIME, ph.getNewBeginTime())
                .le(ph.getNewEndTime()!=null,BusinessTable.SyncLogInfoT.BASE_TABLE_CREATE_TIME,ph.getNewEndTime())
                .eq(BusinessTable.SyncLogInfoT.REMARK, LogConstants.LOG_SYNC_VISITOR);  // 访客

        IPage<SyncLogInfo> dbPg = syncLogInfoService.page(pg,syncLogQueryWrapper);
        grid.setRows(dbPg.getRecords());
        grid.setTotal(dbPg.getTotal());

        return grid;
    }

}

package com.trendcote.web.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trendcote.web.constrants.SysConstants;
import com.trendcote.web.contrants.SystemTable;
import com.trendcote.web.dto.page.Grid;
import com.trendcote.web.dto.page.Json;
import com.trendcote.web.dto.page.PageFilter;
import com.trendcote.web.entity.system.SysOperLog;
import com.trendcote.web.entity.system.SysUser;
import com.trendcote.web.service.system.ISysOperLogService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * @author 莹
 * @date 2018/6/4
 */
@Controller
@Transactional(rollbackFor=Exception.class)
@RequestMapping("/log")
public class OperLogController {

    private static final Logger logger = LoggerFactory.getLogger(OperLogController.class);
    @Autowired
    private ISysOperLogService sysOperLogService;

    @RequestMapping("/manager")
    public String manager() {
        return "/system/operLog";
    }
    @RequestMapping(value = "/dataGrid" ,method = RequestMethod.POST)
    @ResponseBody
    public Grid dataGrid(PageFilter ph) throws ParseException {
        Grid grid = new Grid();
        Page<SysOperLog> pg = new Page<SysOperLog>(ph.getPage(), ph.getRows());
        pg.setDesc(SystemTable.Sys_Role.BASE_TABLE_CREATE_TIME);
        QueryWrapper<SysOperLog> sysOperLogQueryWrapper= new QueryWrapper<SysOperLog>()
                .ge(ph.getNewBeginTime()!=null ,SystemTable.Sys_Oper_Log.BASE_TABLE_CREATE_TIME, ph.getNewBeginTime())
                .le(ph.getNewEndTime()!=null,SystemTable.Sys_Oper_Log.BASE_TABLE_CREATE_TIME,ph.getNewEndTime());
        IPage<SysOperLog>dbPg = sysOperLogService.page(pg,sysOperLogQueryWrapper);
        grid.setRows(dbPg.getRecords());
        grid.setTotal(dbPg.getTotal());
        return grid;
    }
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(String logIds, HttpServletRequest req) throws Exception {
        Json j = new Json();
        try {
            String[] str = logIds.split(",");
            for(String l : str){
                sysOperLogService.removeById(Long.valueOf(l));
            }
            SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_OPERLOG_REMOVE,loginUser.getUsername()+"删除日志.");
            j.setMsg("操作成功！");
            j.setSuccess(true);
        } catch (Exception e) {
            j.setMsg("操作失败！");
            j.setSuccess(false);
            logger.error("删除日志失败exception:{}",e.fillInStackTrace());
            throw  new Exception();
        }
        return j;
    }
}

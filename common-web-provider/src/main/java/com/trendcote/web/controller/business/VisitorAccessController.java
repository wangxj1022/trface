package com.trendcote.web.controller.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trendcote.web.constrants.SysConstants;
import com.trendcote.web.contrants.BusinessTable;
import com.trendcote.web.dto.Business.VisitorDto;
import com.trendcote.web.dto.page.Grid;
import com.trendcote.web.dto.page.Json;
import com.trendcote.web.dto.page.PageFilter;
import com.trendcote.web.entity.business.VisitorAccessInfo;
import com.trendcote.web.entity.system.SysUser;
import com.trendcote.web.service.business.IVisitorAccessInfoService;
import com.trendcote.web.service.system.ISysOperLogService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 莹
 * @date 2018/6/4
 */
@Controller
@RequestMapping("/visitorAccess")
public class VisitorAccessController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VisitorAccessController.class);
    @Autowired
    private IVisitorAccessInfoService visitorAccessInfoService;
    @Autowired
    private ISysOperLogService sysOperLogService;

    @RequestMapping("/manager")
    public String manager(){
        return "/business/visitor/visitorAccessQuery";
    }

    @RequestMapping(value = "/dataGrid",method = RequestMethod.POST)
    @ResponseBody
    public Grid dataGrid(VisitorDto dto, PageFilter ph){
        Grid grid = new Grid();
        Page<VisitorAccessInfo> pg = new Page<VisitorAccessInfo>(ph.getPage(),ph.getRows());
        pg.setDesc(BusinessTable.VisitorAccessT.BASE_TABLE_CREATE_TIME);

        QueryWrapper<VisitorAccessInfo> queryWrapper = new QueryWrapper<VisitorAccessInfo>();
        //queryWrapper.ge(ph.getNewBeginTime()!=null,BusinessTable.VisitorAccessT.BASE_TABLE_CREATE_TIME,ph.getNewBeginTime());  // 挑选出大于该时间的记录
        queryWrapper.eq(StringUtils.isNotEmpty(dto.getVisitorName()),BusinessTable.VisitorAccessT.PERSON_NAME,dto.getVisitorName());    // 访客姓名
        queryWrapper.eq(StringUtils.isNotEmpty(dto.getInDeviceId()),BusinessTable.StaffAccessT.IN_DEVICE_ID,dto.getInDeviceId());
        queryWrapper.ge(ph.getNewBeginTime()!=null,BusinessTable.VisitorAccessT.BASE_TABLE_CREATE_TIME,ph.getNewBeginTime());
        queryWrapper.le(ph.getNewEndTime()!=null,BusinessTable.VisitorAccessT.BASE_TABLE_CREATE_TIME,ph.getNewEndTime());

        //queryWrapper.eq(StringUtils.isNotEmpty(dto.getMobile()),BusinessTable.VisitorAccessT.PERSON_PHONE,dto.getMobile());

        //审核角色：管理员全部查看，用户仅查看个人
        /*SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if( !"admin".equals(user.getUsername()) ){
            queryWrapper.eq(StringUtils.isNotEmpty(user.getUsername()),BusinessTable.VisitorAccessT.STAFF_NAME,user.getName());
        }*/

        IPage<VisitorAccessInfo> visitorAccessInfoIPage=visitorAccessInfoService.page(pg,queryWrapper);
        grid.setRows(visitorAccessInfoIPage.getRecords());
        grid.setTotal(visitorAccessInfoIPage.getTotal());
        return grid;
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Json delete(Long id, HttpServletRequest req) {
        Json j = new Json();
        try {
            VisitorAccessInfo db_visitorAccess = visitorAccessInfoService.getById(id);
            if(db_visitorAccess==null){
                j.setMsg("访客出入信息不存在！");
                j.setSuccess(false);
                return j;
            }
            //db_visitorAccess.setStatus(1);
            visitorAccessInfoService.updateById(db_visitorAccess);
            SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_VISITOR_ACCESS_DEL,"删除访客到访记录:"+db_visitorAccess.getPersonName());
            j.setMsg("操作成功！");
            j.setSuccess(true);
        } catch (Exception e) {
            LOGGER.error("删除访客出入信息异常exception:{}",e.fillInStackTrace());
            e.printStackTrace();
            j.setMsg("操作失败！");
            j.setSuccess(false);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return j;
    }


    @RequestMapping("/detail")
    public String  get(Long id, Model model){
        VisitorAccessInfo visitorAccessInfo = visitorAccessInfoService.getById(id);
//        Organization organization  =organizationServiceI.selectByCode(staffAccessInfo.getStaffDept());
//        if(organization!=null) {
//                staffAccessInfo.setStaffDept(organization.getName());
//            }
        // staffAccessInfo.setInCaptureImage("data:image/png;base64,"+staffAccessInfo.getInCaptureImage());
        model.addAttribute("visitorAccessInfo", visitorAccessInfo);
        return "/business/visitor/visitorAccessInfoDetail";
    }

}

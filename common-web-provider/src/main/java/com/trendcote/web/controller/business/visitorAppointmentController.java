package com.trendcote.web.controller.business;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trendcote.web.constrants.GlobalConstant;
import com.trendcote.web.constrants.HttpConstants;
import com.trendcote.web.constrants.SysConstants;
import com.trendcote.web.contrants.BusinessTable;
import com.trendcote.web.dto.Business.VisitorDto;
import com.trendcote.web.dto.page.*;
import com.trendcote.web.dto.request.Visitor;
import com.trendcote.web.entity.business.VisitorAppointmentInfo;
import com.trendcote.web.entity.system.SysUser;
import com.trendcote.web.service.business.IVisitorAppointmentInfoService;
import com.trendcote.web.service.system.ISysOperLogService;
import com.trendcote.web.utils.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;

/**
 * @Description 预约审批
 * @Date 2019/6/11 13:25
 * @Created by xym
 */
@Controller
@Transactional(rollbackFor = Exception.class)
@RequestMapping("/visitorAppointment")
public class visitorAppointmentController {

    public static final Logger LOGGER= LoggerFactory.getLogger(visitorAppointmentController.class);

    @Autowired
    private ISysOperLogService sysOperLogService;

    @Autowired
    private IVisitorAppointmentInfoService visitorAppointmentInfoService;

   /* @Value("${device.server.url1}")
    private String addStaffUrl;*/

    @Autowired
    private RestTemplate restTemplate ;

    /**
     *@Description 跳转访客预约页面
     *@param
     *@return
     */
    @RequestMapping("/manager")
    public String visitorAppointment(){
        return "/business/visitor/visitorAppointmentQuery";
    }



    /**
     *@Description 访客预约查询
     *@param dto 检索条件（公司名称、手机号） ph 分页信息
     *@return
     */
    @RequestMapping(value = "/dataGrid",method = RequestMethod.POST)
    @ResponseBody
    public Grid dataGrid(VisitorDto dto, PageFilter ph){
        Grid grid = new Grid();
        Page<VisitorAppointmentInfo> pg = new Page<>(ph.getPage(),ph.getRows());
        pg.setDesc(BusinessTable.VisitorAppointmentInfoT.BASE_TABLE_CREATE_TIME);   //根据访客预约登记时间降序排列

        QueryWrapper<VisitorAppointmentInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(dto.getVisitorCompany()),BusinessTable.VisitorAppointmentInfoT.PERSON_COMPANY,dto.getVisitorCompany());
        queryWrapper.eq(StringUtils.isNotEmpty(dto.getMobile()),BusinessTable.VisitorAppointmentInfoT.PERSON_PHONE,dto.getMobile());
        queryWrapper.eq(BusinessTable.VisitorAppointmentInfoT.APPOINTMENT_STATUS, GlobalConstant.DEFAULT);  //仅显示未审批预约
        //审核角色：管理员全部查看，用户仅查看个人
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if( !"admin".equals(user.getUsername()) ){
            queryWrapper.eq(StringUtils.isNotEmpty(user.getUsername()),BusinessTable.VisitorAppointmentInfoT.STAFF_NAME,user.getName());
        }

        IPage<VisitorAppointmentInfo> visitorAppointmentInfoIPage=visitorAppointmentInfoService.page(pg,queryWrapper);
        grid.setRows(visitorAppointmentInfoIPage.getRecords());
        grid.setTotal(visitorAppointmentInfoIPage.getTotal());
        return grid;
    }

    /**
     *@Description 访客预约-审批页面
     *@param
     *@return
     */
    @RequestMapping("/check")
    public String visitorAppointmentdetail(Long id , Model model){
        VisitorAppointmentInfo visitorAppointmentInfo = visitorAppointmentInfoService.getById(id);
        model.addAttribute("visitorAppointmentInfo",visitorAppointmentInfo);
        return "/business/visitor/visitorAppointmentCheck";
    }

    /**
     *@Description 预约通过
     *@param 
     *@return 
     */
    @RequestMapping("/pass")
    @ResponseBody
    public Json visitorAppointmentPass(Long id, HttpServletRequest req){

        Json j = new Json();
        try {
            VisitorAppointmentInfo visitorAppointment = visitorAppointmentInfoService.getById(id);
            if( visitorAppointment == null ){
                j.setSuccess(false);
                j.setMsg("预约信息不存在！");
                return j;
            }

            /*//信息同步到设备: 添加访客信息
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            BaseRequest baseRequest = new BaseRequest(new Head());
            Visitor visitor = new Visitor();
            visitor.setIdCardNo(visitorAppointment.getPersonId());
            visitor.setName(visitorAppointment.getPersonName());
            visitor.setBeginTime(sdf.format( visitorAppointment.getBeginTime()) );
            visitor.setEndTime(sdf.format( visitorAppointment.getEndTime() ));
            baseRequest.setBody(visitor);
            String jsonString = JSON.toJSONString(baseRequest);
            String deviceServerResp = HttpClientUtil.sendPostRequest(addStaffUrl, jsonString,restTemplate);
            BaseResponse baseResponse = JSON.parseObject(deviceServerResp,BaseResponse.class);
            if(HttpConstants.SUCCESS.getErrorCode().equals(baseResponse.getHead().getRespCode())){
                //入库
                visitorAppointment.setAppointmentStatus(1); //审批状态： 已完成
                visitorAppointmentInfoService.updateById(visitorAppointment);
            }else{
                LOGGER.error("设备添加该员工失败:{}",visitorAppointment.getPersonName());
            }*/

            //更新数据库
            visitorAppointment.setAppointmentStatus(1); //审批状态： 已完成
            visitorAppointmentInfoService.updateById(visitorAppointment);

            j.setSuccess(true);
            j.setMsg("操作成功");
            SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_VISITOR_APPOINTMENT_PASS,"访客预约审批通过:"+visitorAppointment.getPersonName());
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("操作失败");
            LOGGER.error("审批通过失败{}",e.fillInStackTrace());
        }
        return j;
    }


    /**
     *@Description 预约拒绝
     *@param
     *@return
     */
    @RequestMapping("/reject")
    @ResponseBody
    public Json visitorAppointmentReject( Long id ,HttpServletRequest req){
        Json j = new Json();
        try {
            VisitorAppointmentInfo visitorAppointment = visitorAppointmentInfoService.getById(id);
            if( visitorAppointment == null ){
                j.setSuccess(false);
                j.setMsg("预约信息不存在！");
                return j;
            }
            visitorAppointmentInfoService.removeById(id);
            j.setSuccess(true);
            j.setMsg("操作成功");
            SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_VISITOR_APPOINTMENT_REJECT,"访客预约拒绝:"+visitorAppointment.getPersonName());
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("操作失败");
            LOGGER.error("审批拒绝失败{}",e.fillInStackTrace());
        }
        return j;
    }


    /**
     *@Description 跳转来访数据查询 -- 访客真实刷卡记录
     *@param 
     *@return 
     */
    @RequestMapping(value = "/appointedManager")
    public String appointedQuery(){
        return "/business/visitor/visitorAppointedQuery";
    }

    /**
     *@Description 预约数据查询（已审批过的预约记录）
     *@param dto 检索条件（公司名称、手机号、时间） ph 分页信息
     *@return
     */
    @RequestMapping(value = "/appointedDataGrid",method = RequestMethod.POST)
    @ResponseBody
    public Grid appointedDataGrid(VisitorDto dto, PageFilter ph){
        Grid grid = new Grid();
        Page<VisitorAppointmentInfo> pg = new Page<>(ph.getPage(),ph.getRows());
        pg.setDesc(BusinessTable.VisitorAppointmentInfoT.BASE_TABLE_CREATE_TIME);   //根据访客预约登记时间降序排列
        QueryWrapper<VisitorAppointmentInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.le(ph.getNewBeginTime()!=null,BusinessTable.VisitorAppointmentInfoT.BEGIN_TIME,ph.getNewBeginTime());
        queryWrapper.ge(ph.getNewBeginTime()!=null,BusinessTable.VisitorAppointmentInfoT.END_TIME,ph.getNewBeginTime());
        queryWrapper.like(StringUtils.isNotEmpty(dto.getVisitorCompany()),BusinessTable.VisitorAppointmentInfoT.PERSON_COMPANY,dto.getVisitorCompany());
        queryWrapper.eq(StringUtils.isNotEmpty(dto.getMobile()),BusinessTable.VisitorAppointmentInfoT.PERSON_PHONE,dto.getMobile());
        //queryWrapper.eq(BusinessTable.VisitorAppointmentInfoT.APPOINTMENT_STATUS, GlobalConstant.NOT_DEFAULT);  //仅显示已审批预约（ 预约记录 ） --- 阳光项目不涉及审批：故注释掉这部分

        //审核角色：管理员全部查看，用户仅查看个人
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if( !"admin".equals(user.getUsername()) ){
            queryWrapper.eq(StringUtils.isNotEmpty(user.getUsername()),BusinessTable.VisitorAppointmentInfoT.STAFF_NAME,user.getName());
        }

        IPage<VisitorAppointmentInfo> visitorAppointmentInfoIPage=visitorAppointmentInfoService.page(pg,queryWrapper);
        grid.setRows(visitorAppointmentInfoIPage.getRecords());
        grid.setTotal(visitorAppointmentInfoIPage.getTotal());
        return grid;
    }


}

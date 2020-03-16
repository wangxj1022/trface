package com.trendcote.web.controller.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.trendcote.web.constrants.SysConstants;
import com.trendcote.web.contrants.BusinessTable;
import com.trendcote.web.contrants.SystemTable;
import com.trendcote.web.dto.Business.DeviceuserDto;
import com.trendcote.web.dto.Business.ServiceStaffDto;
import com.trendcote.web.dto.page.*;

import com.trendcote.web.entity.business.Deviceuser;
import com.trendcote.web.entity.business.ServiceStaffInfo;
import com.trendcote.web.entity.system.SysOrganization;
import com.trendcote.web.entity.system.SysUser;
import com.trendcote.web.entity.system.TbParamsInfo;
import com.trendcote.web.service.business.IDeviceuserService;
import com.trendcote.web.service.business.IServiceStaffService;
import com.trendcote.web.service.business.IStaffService;
import com.trendcote.web.service.system.ISysOperLogService;
import com.trendcote.web.service.system.ISysOrganizationService;
import com.trendcote.web.service.system.ISysParamService;
import com.trendcote.web.utils.DataUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;


import static com.trendcote.web.controller.business.ExcelImport.parseExcel;

/**
 * @Description 特殊人员操作类
 * @Date 2019/7/29 18:07
 * @Created by xym
 */

@Transactional(rollbackFor = Exception.class)
@Controller
@RequestMapping("/serviceStaff")
public class ServiceStaffController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceStaffController.class);

    // 特殊人员操作类
    @Autowired
    private IServiceStaffService serviceStaffService;

    // 员工操作类
    @Autowired
    private IStaffService staffService;


    // 记录操作日志类
    @Autowired
    private ISysOperLogService sysOperLogService;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IDeviceuserService iDeviceuserService;
    @Autowired
    private ISysOrganizationService iSysOrganizationService;
    @Autowired
    private ISysParamService iSysParamService;


   /* @Value("${device.server.url1}")
    private String addStaff;

    @Value("${device.server.url5}")
    private String addServiceStaffUrl;
    @Value("${device.server.url6}")
    private String delServiceStaffUrl;*/


    /**
     *@Description 跳转页面
     *@param
     *@return
     */
    @RequestMapping("/manager")
    private String serviceStaffManager(){
        return "/business/serviceStaff/serviceStaffQuery";
    }


    /**
     *@Description 特殊人群列表查询
     *@param
     *@return
     */
    @RequestMapping(value = "/dataGrid",method = RequestMethod.POST)
    @ResponseBody
    public Grid dataGrid(ServiceStaffDto dto, PageFilter ph){
        SysOrganization sysOrganization = this.iSysOrganizationService.getOne(new QueryWrapper<SysOrganization>().eq(SystemTable.Sys_Organization.BASE_TABLE_ID,dto.getServiceStaffDept()));

        Grid grid = new Grid();
        Page<ServiceStaffInfo> pg = new Page<ServiceStaffInfo>(ph.getPage(),ph.getRows());
        pg.setDesc(BusinessTable.ServiceStaffInfoT.BASE_TABLE_CREATE_TIME);
        QueryWrapper<ServiceStaffInfo> queryWrapper = new QueryWrapper<ServiceStaffInfo>();

        queryWrapper.like(StringUtils.isNotEmpty(dto.getServiceStaffName()), BusinessTable.ServiceStaffInfoT.SERVICE_STAFF_NAME,dto.getServiceStaffName());  //姓名
        //queryWrapper.eq(StringUtils.isNotEmpty(dto.getServiceStaffTel()), BusinessTable.ServiceStaffInfoT.SERVICE_STAFF_TEL,dto.getServiceStaffTel());  //手机号
        queryWrapper.eq(StringUtils.isNotEmpty(dto.getServiceStaffIcNo()), BusinessTable.ServiceStaffInfoT.SERVICE_STAFF_ICNO,dto.getServiceStaffIcNo());  //员工工号
        IPage<ServiceStaffInfo> serviceStaffInfoIPage=serviceStaffService.page(pg,queryWrapper);
        if (null !=sysOrganization){
            ServiceStaffInfo staffInfo = new ServiceStaffInfo();
            //String bumenName = staffInfo.getServiceStaffDept();
            staffInfo.setServiceStaffDept(sysOrganization.getName());
        }
        grid.setRows(serviceStaffInfoIPage.getRecords());
        grid.setTotal(serviceStaffInfoIPage.getTotal());
        return grid;
    }

    /**
     * 查询照片的像素
     * @return
     */
    @RequestMapping("/imageList")
    @ResponseBody
    public TbParamsInfo imageList(){
        String imageNames= "pixel";
        TbParamsInfo tbParamsInfo = this.iSysParamService.getOne(new QueryWrapper<TbParamsInfo>().eq(SystemTable.Tb_Params.P_Name,imageNames));
        //System.out.println("=============="+tbParamsInfo.getpValue());
        return tbParamsInfo;
    }

    /**
     *@Description 跳转图像预览页面
     *@param
     *@return
     *//*
    @RequestMapping(value = "/preview")
    public String checkPic(Long id , Model model){
        ServiceStaffInfo serviceStaff = serviceStaffService.getById(id);
        model.addAttribute("serviceStaff",serviceStaff);
        return "/business/serviceStaff/serviceStaffPreview";
    }*/

    /**
     *@Description 人员信息回显
     *@param
     *@return
     */
    @RequestMapping(value = "/preview")
    @ResponseBody
    public ServiceStaffInfo serviceStaffEdit(Long id ){
        ServiceStaffInfo serviceStaff = serviceStaffService.getById(id);
        return serviceStaff;
    }
    @RequestMapping(value = "/selectByIdCardNo")
    @ResponseBody
    public Grid selectByIdCardNo(DeviceuserDto deviceuserDto, Long id , PageFilter ph){
        //根据id查询员工的卡号
        String serviceStaffIcNo = this.serviceStaffService.findById(id);
        deviceuserDto.setUserid(serviceStaffIcNo);
        Grid grid = new Grid();
        Page<Deviceuser> pg = new Page<Deviceuser>(ph.getPage(),ph.getRows());
        pg.setDesc(BusinessTable.DeviceuserT.DEVICEID);
        QueryWrapper<Deviceuser> queryWrapper=new QueryWrapper<Deviceuser>();
        queryWrapper.eq(StringUtils.isNotEmpty(deviceuserDto.getUserid()), BusinessTable.DeviceuserT.USERID,deviceuserDto.getUserid());
        IPage<Deviceuser> deviceuserIPage=this.iDeviceuserService.page(pg,queryWrapper);
        grid.setRows(deviceuserIPage.getRecords());
        grid.setTotal(deviceuserIPage.getTotal());
        return grid;
    }



    /**
     *@Description 特殊人员删除
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
                /*// 删除设备中该员工信息  阳光需要
                ServiceStaffInfo serviceStaff = serviceStaffService.getById(l);

                if( StringUtils.isNotEmpty(serviceStaff.getServiceStaffIcNo()) ){
                    String cardNo = serviceStaff.getServiceStaffIcNo();     //IC卡号
                    String number = "111111";  //ic卡号已存在，编号无所谓,跨过校验
                    BaseRequest request = new BaseRequest(new Head());
                    Staff staffReq = new Staff();
                    staffReq.setNumber(number);       // 员工编号（必填项）
                    staffReq.setCardNo(cardNo);       // IC卡号
                    request.setBody(staffReq);
                    String jsonString = JSONObject.toJSONString(request);
                    String responseString = HttpClientUtil.sendPostRequest(delServiceStaffUrl, jsonString, restTemplate);
                    BaseResponse response = JSONObject.parseObject(responseString, BaseResponse.class);
                    if( !HttpConstants.SUCCESS.getErrorCode().equals( response.getHead().getRespCode()) ){
                        logger.error("设备删除特殊员工信息失败:"+serviceStaff.getServiceStaffName());
                        continue;
                    }
                }*/
                // 删除数据库
                ServiceStaffInfo staffLog = serviceStaffService.getById(Long.valueOf(l));
                serviceStaffService.removeById(Long.valueOf(l));
                // 存储操作日志
                SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
                sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_SERVICESTAFF_REMOVE, "删除手动添加类员工"+staffLog.getServiceStaffName() );
            }

            j.setMsg("操作成功！");
            j.setSuccess(true);
        } catch (Exception e) {
            j.setMsg("操作失败！");
            j.setSuccess(false);
            logger.error("删除特殊员工失败exception:{}",e.fillInStackTrace());
            throw  new Exception();
        }
        return j;
    }

    /**
     * 全部重发按钮
     * @param
     * @return
     */
    @RequestMapping("/quanbuchongfa")
    @ResponseBody
    public Json quanbuchongfa(){
        Json j = new Json();
        //String[] str = logIds.split(",");
        /*for(String m : str){*/
            ServiceStaffInfo serviceStaffInfo =new ServiceStaffInfo();
            serviceStaffInfo.setUpdateTime(new Date());
            serviceStaffInfo.setUpdateflag(0);
            serviceStaffService.update(serviceStaffInfo,new QueryWrapper<ServiceStaffInfo>().eq(BusinessTable.ServiceStaffInfoT.UPDATE_FLAG,1));
//        }
        j.setSuccess(true);
        j.setMsg("操作成功！");
        return j;
    }

    /**
     *@Description 人员新增页面
     *@param
     *@return
     */
    @RequestMapping("/add")
    public String entryAdd(){
        return "/business/serviceStaff/serviceStaffAdd";
    }


    /**
     *@Description 重发-按钮
     *@param
     *@return
     */
    @RequestMapping(value = "/reAdd")
    @ResponseBody
    public Json reAddStaff(Long id){
        Json j = new Json();
        ServiceStaffInfo serviceStaffInfo = serviceStaffService.getById(id);
        serviceStaffInfo.setUpdateTime(new Date());
        serviceStaffInfo.setUpdateflag(0);
        serviceStaffService.updateById(serviceStaffInfo);
        // 重新下发卡  阳光用
        /*BaseRequest request = new BaseRequest(new Head());
        ServiceStaff serviceStaffRequest = new ServiceStaff();
        serviceStaffRequest.setNumber( serviceStaffInfo.getServiceStaffNumber() ); // 员工编号
        serviceStaffRequest.setName(serviceStaffInfo.getServiceStaffName());       // 姓名
        if( serviceStaffInfo.getIsOutWork() == null ){
            serviceStaffInfo.setIsOutWork(0);
        }
        serviceStaffRequest.setIsOutWork(serviceStaffInfo.getIsOutWork());  // 是否外勤

        if( StringUtils.isEmpty(serviceStaffInfo.getServiceStaffIcNo()) ){
            serviceStaffInfo.setServiceStaffIcNo("1006" + DataUtil.generateRandom(10)); // 重新下发的卡前缀为 1006
        }
        serviceStaffRequest.setCardNo( serviceStaffInfo.getServiceStaffIcNo() );   // IC卡号
        serviceStaffRequest.setPhoto( serviceStaffInfo.getServiceStaffPhoto());     // 员工照片
        request.setBody(serviceStaffRequest);
        String jsonStr = JSON.toJSONString(request);
        try {
            String serverResp = HttpClientUtil.sendPostRequest(addServiceStaffUrl, jsonStr,restTemplate);
            BaseResponse response = JSON.parseObject(serverResp, BaseResponse.class);
            if(HttpConstants.SUCCESS.getErrorCode().equals(response.getHead().getRespCode())){
                j.setSuccess(true);
                j.setMsg("操作成功！");
            }else{
                j.setSuccess(false);
                j.setMsg("操作失败,重发员工信息失败！");
            }
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("操作失败,重发员工信息异常！");
        }*/
        j.setSuccess(true);
        j.setMsg("操作成功！");
        return j;
    }



    /**
     *@Description 特殊人员下发  ( 加synchronized锁：并发 JNA/JNI 调用会引起Tomcat崩溃 )
     *@param
     *@return
     */
   /* @RequestMapping("/save")
    @ResponseBody
    public synchronized Json saveServiceStaff(ServiceStaffInfo serviceStaffInfo){

        // 传入参数校验
        Json j = new Json();
        j.setMsg("未完善员工信息");
        if( StringUtils.isEmpty(serviceStaffInfo.getServiceStaffDept())) return j;
        if( StringUtils.isEmpty(serviceStaffInfo.getServiceStaffName())) return j;
        if( StringUtils.isEmpty(serviceStaffInfo.getServiceStaffPhoto())) return j;
        if( StringUtils.isEmpty(serviceStaffInfo.getServiceStaffIcNo())  ){
            // IC卡号如果为空，则生成默认卡号进行下发
            serviceStaffInfo.setServiceStaffIcNo( "1007" + DataUtil.generateRandom(10));
        }

        // 下发设备；存库
        //上传设备员工信息
        try {
            BaseRequest baseRequest = new BaseRequest(new Head());
            ServiceStaff staffReq = new ServiceStaff();
            staffReq.setDeptName(serviceStaffInfo.getServiceStaffDept());   // 部门
            staffReq.setName(serviceStaffInfo.getServiceStaffName());       // 姓名
            staffReq.setNumber(serviceStaffInfo.getServiceStaffNumber());   // 员工编号
            staffReq.setCardNo(serviceStaffInfo.getServiceStaffIcNo());     // IC卡号
            staffReq.setIsOutWork(serviceStaffInfo.getIsOutWork());         // 是否外勤
            staffReq.setPhoto(serviceStaffInfo.getServiceStaffPhoto());     // 员工照片
            staffReq.setPhion(serviceStaffInfo.getServiceStaffTel());
            baseRequest.setBody(staffReq);
            String jsonString = JSON.toJSONString(baseRequest);
            String deviceServerResp = HttpClientUtil.sendPostRequest(addServiceStaffUrl, jsonString,restTemplate);
            BaseResponse baseResponse = JSON.parseObject(deviceServerResp, BaseResponse.class);
            if(HttpConstants.SUCCESS.getErrorCode().equals(baseResponse.getHead().getRespCode())){
                j.setSuccess(true);
                j.setMsg("操作成功！");
            }else{
                j.setSuccess(false);
                j.setMsg("操作失败,下发特殊人员信息失败！");
            }
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("下发特殊人员信息异常");
            logger.error("下发特殊人员信息异常{}",e.fillInStackTrace());
        }
        return j;
    }
*/


    /**
     *@Description 特殊员工信息修改方法（ 设备修改 ）
     *@param
     *@return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Json updateStaff(ServiceStaffInfo serviceStaffInfo ){

        // 传入参数校验
        Json j = new Json();
        if( serviceStaffInfo.getServiceStaffPhoto().equals("null") ){
            serviceStaffInfo.setServiceStaffPhoto("");
            serviceStaffInfo.setPicStatus(0);
        }

        ServiceStaffInfo dbStaff = serviceStaffService.getById(serviceStaffInfo.getId());
        // 判断修改字段
        if( serviceStaffInfo.getServiceStaffName().equals(dbStaff.getServiceStaffName())
                || serviceStaffInfo.getServiceStaffIcNo().equals(dbStaff.getServiceStaffIcNo())
                || serviceStaffInfo.getServiceStaffPhoto().equals(dbStaff.getServiceStaffPhoto())
        || serviceStaffInfo.getServiceStaffNumber().equals(dbStaff.getServiceStaffNumber())){
            SysOrganization s = this.iSysOrganizationService.getOne(new QueryWrapper<SysOrganization>().eq(SystemTable.Sys_Organization.BASE_TABLE_ID,serviceStaffInfo.getServiceStaffDept()));
                serviceStaffInfo.setServiceStaffDept(serviceStaffInfo.getServiceStaffDept());   // 部门
                serviceStaffInfo.setDeptname(s.getName());
                // 未修改 姓名、IC卡、照片 字段
                serviceStaffInfo.setUpdateTime(new Date());
                serviceStaffInfo.setUpdateflag(1);
                serviceStaffService.updateById(serviceStaffInfo);
                j.setSuccess(true);
                j.setMsg("操作成功！");
            return j;
        }else{

            SysOrganization s = this.iSysOrganizationService.getOne(new QueryWrapper<SysOrganization>().eq(SystemTable.Sys_Organization.BASE_TABLE_ID,serviceStaffInfo.getServiceStaffDept()));
            serviceStaffInfo.setServiceStaffDept(serviceStaffInfo.getServiceStaffDept());   // 部门
            serviceStaffInfo.setDeptname(s.getName());
            // 修改 姓名、IC卡、照片字段 重新下发
            try {
                ServiceStaffInfo staffInfoone = this.serviceStaffService.getOne(new QueryWrapper<ServiceStaffInfo>().eq(BusinessTable.ServiceStaffInfoT.SERVICE_STAFF_NUMBER,serviceStaffInfo.getServiceStaffNumber()));
                if (null == staffInfoone) {
                    serviceStaffInfo.setUpdateTime(new Date());
                    serviceStaffInfo.setUpdateflag(0);
                    serviceStaffService.updateById(serviceStaffInfo);
                    j.setSuccess(true);
                    j.setMsg("操作成功！");
                }else {
                    j.setMsg("员工编号["+serviceStaffInfo.getServiceStaffNumber()+"] 已存在，请重新编辑！");
                    j.setSuccess(false);
                }

               /*
               // 以下代码为阳光需要的
               BaseRequest request = new BaseRequest(new Head());

                Staff staffReq = new Staff();
                staffReq.setNumber(number);       // 员工编号（必填项）
                staffReq.setCardNo(cardNo);       // IC卡号
                request.setBody(staffReq);
                String jsonString = JSONObject.toJSONString(request);
                String responseString = HttpClientUtil.sendPostRequest(delServiceStaffUrl, jsonString, restTemplate);
                BaseResponse response = JSONObject.parseObject(responseString, BaseResponse.class);
                if( HttpConstants.SUCCESS.getErrorCode().equals( response.getHead().getRespCode()) ){
                    // 删除数据库
                    serviceStaffService.removeById( dbStaff.getId() );
                    // 下发卡
                    BaseRequest baseRequest = new BaseRequest(new Head());
                    ServiceStaff req = new ServiceStaff();
                    req.setNumber(serviceStaffInfo.getServiceStaffNumber()); // 员工编号
                    req.setName(serviceStaffInfo.getServiceStaffName());     // 姓名
                    req.setIsOutWork(serviceStaffInfo.getIsOutWork());       // 是否外勤
                    req.setCardNo(serviceStaffInfo.getServiceStaffIcNo());   // IC卡号 | 身份证号
                    req.setPhoto(serviceStaffInfo.getServiceStaffPhoto());   // 员工照片
                    req.setDeptName(serviceStaffInfo.getServiceStaffDept());  // 部门
                    baseRequest.setBody(req);
                    String jsonStr = JSON.toJSONString(baseRequest);
                    logger.debug("更新下发：jsonStr:"+jsonStr);
                    String deviceServerResp = HttpClientUtil.sendPostRequest(addServiceStaffUrl, jsonStr,restTemplate);
                    BaseResponse baseResponse = JSON.parseObject(deviceServerResp, BaseResponse.class);
                    if(HttpConstants.SUCCESS.getErrorCode().equals(baseResponse.getHead().getRespCode())){
                        j.setSuccess(true);
                        j.setMsg("操作成功！");
                    }else{
                        j.setSuccess(false);
                        j.setMsg("操作失败,更新特殊人员信息失败！");
                    }
                }else {
                    j.setSuccess(false);
                    j.setMsg("更新特殊人员信息失败:删除过程失败");
                }*/

            } catch (Exception e) {
                j.setSuccess(false);
                j.setMsg("更新特殊人员信息异常");
                logger.error("更新特殊人员信息异常{}",e.fillInStackTrace());
            }
            return j;
        }
    }

    /**
     * 批量导入员工信息
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/excelImport")
    @ResponseBody
    public Json excelImport(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        Json j = new Json();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        FileSystemView fsv = FileSystemView.getFileSystemView();//获取本地桌面路径
        String path = fsv.getHomeDirectory().toString()+"\\stafflist.xlsx";
        if (null== path){
            j.setMsg("文件不存在，找不到指定的Excel文件!");
            j.setSuccess(false);
        }else {
            List<ServiceStaffInfo> list = parseExcel(path);
            try {
                this.serviceStaffService.insert(list);
            } catch (Exception e) {
                j.setMsg("导入失败！");
                j.setSuccess(false);
            }
            j.setMsg("导入成功！共导入 [" + list.size() + "] 条数据");
            j.setSuccess(true);
        }
        return j;
    }

    /**
     * Excel 导入
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/fileUpload")
    @ResponseBody
    public Json fileUpload(@RequestParam("file") MultipartFile file,HttpServletRequest request){
        Json j = new Json();
        //判断文件是否为空
        if (!file.isEmpty()){
            SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
            String suffix = sdf.format(new Date());
            //文件保存路径
            String filePath = System.getProperty("user.dir")  + File.separator + suffix+file.getOriginalFilename();
            try {
                //转存文件暂时存到项目根目录
                file.transferTo(new File(filePath));
                List<ServiceStaffInfo> list = parseExcel(filePath);
                try {
                    this.serviceStaffService.insert(list);
                } catch (Exception e) {
                   j.setMsg("导入失败");
                   j.setSuccess(false);
                }
                j.setMsg("导入成功！共导入 [" + list.size() + "] 条数据");
                j.setSuccess(true);
                //执行成功之后，删除暂存的excel 文件
                File file1 = new File(filePath);
                file1.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return j;

    }


    @RequestMapping("/insertPerson")
    @ResponseBody
    public Json insertPerson(ServiceStaffInfo staffInfo){
        Json j = new Json();
        j.setMsg("未完善员工信息");
        if( StringUtils.isEmpty(staffInfo.getServiceStaffDept())) return j;
        if( StringUtils.isEmpty(staffInfo.getServiceStaffName())) return j;
        if( StringUtils.isEmpty(staffInfo.getServiceStaffPhoto())) return j;
        if( StringUtils.isEmpty(staffInfo.getServiceStaffIcNo())  ){
            // IC卡号如果为空，则生成默认卡号进行下发
            staffInfo.setServiceStaffIcNo( "1007" + DataUtil.generateRandom(10));
        }
        try {
           ServiceStaffInfo staffInfo1 = new ServiceStaffInfo();

            //根据编号查询部门名称
            SysOrganization s = this.iSysOrganizationService.getOne(new QueryWrapper<SysOrganization>().eq(SystemTable.Sys_Organization.BASE_TABLE_ID,staffInfo.getServiceStaffDept()));
            staffInfo1.setServiceStaffDept(staffInfo.getServiceStaffDept());   // 部门编号
            staffInfo1.setDeptname(s.getName());
            staffInfo1.setServiceStaffName(staffInfo.getServiceStaffName());       // 姓名
            staffInfo1.setServiceStaffNumber(staffInfo.getServiceStaffNumber());       // 员工编号
            staffInfo1.setIsOutWork(staffInfo.getIsOutWork());             // 是否外勤
            staffInfo1.setServiceStaffTel(staffInfo.getServiceStaffTel());
            staffInfo1.setRemark("手工录入方式");
            staffInfo1.setCreateTime(new Date());    // 创建时间
            staffInfo1.setUpdateTime(new Date());    // 更新时间
            if( staffInfo.getServiceStaffIcNo() != null ){
                staffInfo1.setServiceStaffIcNo(staffInfo.getServiceStaffIcNo());      // IC卡号
            }
            if( staffInfo.getServiceStaffPhoto() != null ){
                staffInfo1.setServiceStaffPhoto(staffInfo.getServiceStaffPhoto());      // 员工照片
                staffInfo1.setPicStatus(1);
            }
            ServiceStaffInfo staffInfos = this.serviceStaffService.getOne(new QueryWrapper<ServiceStaffInfo>().eq(BusinessTable.ServiceStaffInfoT.SERVICE_STAFF_NUMBER,staffInfo.getServiceStaffNumber()));
            if (null != staffInfos){
                j.setMsg("员工编号 ["+staffInfo.getServiceStaffNumber()+"] 已存在，请重新添加");
                j.setSuccess(false);
            }
            ServiceStaffInfo staffInfo2 = this.serviceStaffService.getOne(new QueryWrapper<ServiceStaffInfo>().eq(BusinessTable.ServiceStaffInfoT.SERVICE_STAFF_ICNO,staffInfo.getServiceStaffIcNo()));
            if (null !=staffInfo2){
                j.setMsg("员工工号 ["+staffInfo.getServiceStaffIcNo()+"] 已存在，请重新添加");
                j.setSuccess(false);
            }
            // 存库
            serviceStaffService.save(staffInfo1);
            j.setMsg("添加成功！");
            j.setSuccess(true);
        } catch (Exception e) {
            j.setMsg("添加失败！");
            j.setSuccess(false);
        }
        return j;
    }



}

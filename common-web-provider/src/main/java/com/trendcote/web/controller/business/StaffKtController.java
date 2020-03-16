/*
package com.trendcote.web.controller.business;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trendcote.web.constrants.HttpConstants;
import com.trendcote.web.constrants.SysConstants;
import com.trendcote.web.contrants.BusinessTable;
import com.trendcote.web.dto.Business.StaffDto;
import com.trendcote.web.dto.Business.StaffktDto;
import com.trendcote.web.dto.page.*;
import com.trendcote.web.dto.request.ServiceStaff;
import com.trendcote.web.dto.request.Staff;
import com.trendcote.web.dto.request.Staffkt;
import com.trendcote.web.entity.business.ServiceStaffInfo;
import com.trendcote.web.entity.business.StaffInfo;
import com.trendcote.web.entity.business.StaffktInfo;
import com.trendcote.web.entity.business.StaffktInfo;
import com.trendcote.web.entity.system.SysUser;
import com.trendcote.web.service.business.IServiceStaffService;
import com.trendcote.web.service.business.IStaffService;
import com.trendcote.web.service.business.IStaffktService;
import com.trendcote.web.service.system.ISysOperLogService;
import com.trendcote.web.utils.DataUtil;
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
import java.util.*;

*/
/**
 * 昆泰人员类 （ 独立维护 ）
 *//*

@Controller
@Transactional(rollbackFor = Exception.class)
@RequestMapping("/staffkt")
public class StaffKtController {
    public static final Logger LOGGER= LoggerFactory.getLogger(StaffKtController.class);

    @Autowired
    private IStaffktService staffktService;
    @Autowired
    private ISysOperLogService sysOperLogService;
    @Autowired
    private IStaffService staffService;
    @Autowired
    private IServiceStaffService serviceStaffService;

    @Autowired
    private RestTemplate restTemplate ;

    @Value("${device.server.url9}")
    private String addStaffktUrl;
    @Value("${device.server.url10}")
    private String delStaffktUrl;

    */
/**
     *@Description 跳转员工查询页面
     *@param
     *@return
     *//*

    @RequestMapping(value = "/manager")
    public String staffktQuery(){
        return "/business/staffkt/staffktQuery";
    }


    @RequestMapping(value = "/dataGrid",method = RequestMethod.POST)
    @ResponseBody
    public Grid dataGrid(StaffktDto dto, PageFilter ph){
        Grid grid = new Grid();
        Page<StaffktInfo> pg = new Page<StaffktInfo>(ph.getPage(),ph.getRows());
        pg.setDesc(BusinessTable.StaffktInfoT.BASE_TABLE_CREATE_TIME);
        QueryWrapper<StaffktInfo> queryWrapper = new QueryWrapper<StaffktInfo>();
        queryWrapper.eq(DataUtil.isStringNotEmpty(dto.getStaffktNumber()),BusinessTable.StaffktInfoT.STAFF_KT_NUMBER,dto.getStaffktNumber());  //员工编号
        queryWrapper.eq(DataUtil.isStringNotEmpty(dto.getStaffktName()),BusinessTable.StaffktInfoT.STAFF_KT_NAME,dto.getStaffktName());        //姓名
        IPage<StaffktInfo> staffInfoIPage=staffktService.page(pg,queryWrapper);
        grid.setRows(staffInfoIPage.getRecords());
        grid.setTotal(staffInfoIPage.getTotal());
        return grid;
    }


    */
/**
     *@Description 跳转人员新增页面
     *@param
     *@return
     *//*

    @RequestMapping("/add")
    public String add(){
        return "/business/staffkt/staffktAdd";
    }

    */
/**
     *@Description 跳转人员新增页面
     *@param
     *@return
     *//*

    @RequestMapping("/addFromDb")
    public String addFromDb(){
        return "/business/staffkt/staffktAddFromDb";
    }


    */
/**
     *@Description 人员删除
     *@param
     *@return
     *//*

    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(String logIds, HttpServletRequest req) throws Exception {
        Json j = new Json();
        try {
            String[] str = logIds.split(",");
            StaffktInfo staffktInfo = new StaffktInfo();
            staffktInfo.setUpdateTime(new Date());
            for(String l : str){
                // 删除设备信息
                StaffktInfo staffkt = staffktService.getById(l);
                if( StringUtils.isNotEmpty(staffkt.getStaffktIcNo()) ){
                    String cardNo = staffkt.getStaffktIcNo();   //IC卡号
                    BaseRequest request = new BaseRequest(new Head());
                    Staffkt staffktReq = new Staffkt();
                    staffktReq.setCardNo(cardNo);               // IC卡号
                    request.setBody(staffktReq);
                    String jsonString = JSONObject.toJSONString(request);
                    String responseString = HttpClientUtil.sendPostRequest(delStaffktUrl, jsonString, restTemplate);
                    BaseResponse response = JSONObject.parseObject(responseString, BaseResponse.class);
                    if( !HttpConstants.SUCCESS.getErrorCode().equals( response.getHead().getRespCode()) ){
                        LOGGER.error("设备删除特殊员工信息失败:"+staffkt.getStaffktName());
                        continue;
                    }
                }

                // 删除数据库
                staffktInfo.setId(Long.valueOf(l));
                staffktInfo.setUpdateFlag(0);
                staffktInfo.setSyncRetryCnt(0);
                staffktInfo.setStatus(0);
                staffktService.update( staffktInfo,new QueryWrapper<StaffktInfo>().eq(BusinessTable.StaffktInfoT.BASE_TABLE_ID,staffktInfo.getId()) );
                // 存储操作日志
                SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
                sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_STAFFKT_REMOVE,"删除昆泰28层员工:"+staffktInfo.getStaffktName());
            }
            j.setMsg("操作成功！");
            j.setSuccess(true);
        } catch (Exception e) {
            j.setMsg("操作失败！");
            j.setSuccess(false);
            LOGGER.error("删除昆泰员工失败exception:{}",e.fillInStackTrace());
            throw  new Exception();
        }
        return j;
    }


    */
/**
     *@Description 昆泰重发按钮
     *@param
     *@return
     *//*

    @RequestMapping(value = "/reAdd")
    @ResponseBody
    public Json reAddStaff(Long id){
        Json j = new Json();
        StaffktInfo staffktInfo = staffktService.getById(id);
        // 重新下发卡
        BaseRequest request = new BaseRequest(new Head());
        Staffkt staffktRequest = new Staffkt();
        staffktRequest.setNumber( staffktInfo.getStaffktNumber() );   // 员工编号
        staffktRequest.setName( staffktInfo.getStaffktName() );       // 姓名
        if( staffktInfo.getIsOutWork() == null ){
            staffktInfo.setIsOutWork(0);
        }
        staffktRequest.setIsOutWork(staffktInfo.getIsOutWork());  // 是否外勤

        if( StringUtils.isEmpty(staffktInfo.getStaffktIcNo()) ){
            staffktInfo.setStaffktIcNo("1006" + DataUtil.generateRandom(10)); // 重新下发的卡前缀为 1006
        }
        staffktRequest.setCardNo(staffktInfo.getStaffktIcNo());   // IC卡号
        staffktRequest.setPhoto(staffktInfo.getStaffktPhoto());       // 员工照片
        request.setBody(staffktRequest);
        String jsonStr = JSON.toJSONString(request);
        try {
            String serverResp = HttpClientUtil.sendPostRequest(addStaffktUrl, jsonStr,restTemplate);
            BaseResponse response = JSON.parseObject(serverResp,BaseResponse.class);
            if(HttpConstants.SUCCESS.getErrorCode().equals(response.getHead().getRespCode())){
                j.setSuccess(true);
                j.setMsg("操作成功！");
                // 更新时间
                staffktInfo.setUpdateTime(new Date());
                staffktService.update(staffktInfo,new QueryWrapper<StaffktInfo>().eq(BusinessTable.StaffktInfoT.BASE_TABLE_ID,staffktInfo.getId()));
            }else{
                j.setSuccess(false);
                j.setMsg("操作失败,重发员工信息失败！");
            }
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("操作失败,重发员工信息异常！");
        }
        return j;
    }



    */
/**
     *@Description 人员保存
     *@param
     *@return
     *//*

    @RequestMapping("/save")
    @ResponseBody
    public synchronized Json saveStaffkt(StaffktInfo staffktInfo){
        // 传入参数校验
        Json j = new Json();
        j.setMsg("未完善员工信息");
        if( StringUtils.isEmpty(staffktInfo.getStaffktIcNo())) return j;
        if( StringUtils.isEmpty(staffktInfo.getStaffktName())) return j;
        if( StringUtils.isEmpty(staffktInfo.getStaffktPhoto())){
            staffktInfo.setPicStatus(0);    // 不含照片
        }

        // 存储员工信息
        try {
            // 判断是否已存在
            StaffktInfo staffkt = staffktService.getOne(new QueryWrapper<StaffktInfo>().eq(BusinessTable.StaffktInfoT.STAFF_KT_ICNO, staffktInfo.getStaffktIcNo()));
            if( staffkt != null ){
                j.setSuccess(false);
                j.setMsg("人员已存在，请重新添加！");
            }else{
                // 下发到设备
                try {
                    BaseRequest baseRequest = new BaseRequest(new Head());
                    Staffkt staffktReq = new Staffkt();
                    staffktReq.setName(staffktInfo.getStaffktName());       // 姓名
                    staffktReq.setCardNo(staffktInfo.getStaffktIcNo());     // IC卡号
                    staffktReq.setPhoto(staffktInfo.getStaffktPhoto());     // 员工照片
                    baseRequest.setBody(staffktReq);
                    String jsonString = JSON.toJSONString(baseRequest);
                    String deviceServerResp = HttpClientUtil.sendPostRequest(addStaffktUrl, jsonString,restTemplate);
                    BaseResponse baseResponse = JSON.parseObject(deviceServerResp,BaseResponse.class);
                    if(HttpConstants.SUCCESS.getErrorCode().equals(baseResponse.getHead().getRespCode())){
                        j.setSuccess(true);
                        j.setMsg("操作成功！");
                        // 存储数据库
                        // staffktInfo.setStatus(1);  // 状态默认为 1 启用
                        staffktInfo.setUpdateTime(new Date());
                        staffktService.save(staffktInfo);
                        j.setSuccess(true);
                        j.setMsg("操作成功！");
                    }else{
                        j.setSuccess(false);
                        j.setMsg("下发人员信息失败，请重试！");
                    }
                } catch (Exception e) {
                    j.setSuccess(false);
                    j.setMsg("下发人员信息失败,服务端异常！");
                }
            }
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("存储昆泰人员信息异常");
            LOGGER.error("存储昆泰人员信息异常{}",e.fillInStackTrace());
        }
        return j;
    }

    */
/**
     *@Description 跳转预览页面
     *@param 
     *@return 
     *//*

    @RequestMapping(value = "/preview")
    public String checkPic(Long id , Model model){
        StaffktInfo staffktInfo = staffktService.getById(id);
        model.addAttribute("staffktInfo",staffktInfo);
        return "/business/staffkt/staffktPreview";
    }


    */
/**
     *@Description 更新昆泰员工信息
     *@param 
     *@return 
     *//*

    @RequestMapping(value = "/update")
    @ResponseBody
    public Json updateStaff( StaffktInfo staffktInfo ){

        // 传入参数校验
        Json j = new Json();
        if( staffktInfo.getStaffktPhoto().equals("null") ){
            staffktInfo.setStaffktPhoto("");
        }
        StaffktInfo dbStaff = staffktService.getById(staffktInfo.getId());

        // 判断修改字段
        if( staffktInfo.getStaffktName().equals(dbStaff.getStaffktName()) && staffktInfo.getStaffktIcNo().equals(dbStaff.getStaffktIcNo()) && staffktInfo.getStaffktPhoto().equals(dbStaff.getStaffktPhoto()) ){
            // 未修改 姓名、IC卡、照片 字段
            staffktInfo.setUpdateTime(new Date());
            staffktService.updateById(staffktInfo);
            j.setSuccess(true);
            j.setMsg("操作成功！");
        }else{
            // 修改 姓名、IC卡、照片字段 重新下发
            // 删除旧信息、重新下发新信息
            if( StringUtils.isNotEmpty(dbStaff.getStaffktIcNo()) ){
                String cardNo = dbStaff.getStaffktIcNo();   //IC卡号
                BaseRequest request = new BaseRequest(new Head());
                Staffkt staffktReq = new Staffkt();
                staffktReq.setCardNo(cardNo);               //IC卡号
                request.setBody(staffktReq);
                String jsonString = JSONObject.toJSONString(request);
                String responseString = HttpClientUtil.sendPostRequest(delStaffktUrl, jsonString, restTemplate);
                BaseResponse response = JSONObject.parseObject(responseString, BaseResponse.class);
                if( !HttpConstants.SUCCESS.getErrorCode().equals( response.getHead().getRespCode()) ){
                    LOGGER.error("设备昆泰员工信息失败:"+dbStaff.getStaffktName());
                }
            }

            // 重新下发至设备
            try {
                BaseRequest baseRequest = new BaseRequest(new Head());
                Staffkt staffktReq = new Staffkt();
                staffktReq.setName(staffktInfo.getStaffktName());       // 姓名
                staffktReq.setCardNo(staffktInfo.getStaffktIcNo());     // IC卡号
                staffktReq.setPhoto(staffktInfo.getStaffktPhoto());     // 员工照片
                baseRequest.setBody(staffktReq);
                String jsonString = JSON.toJSONString(baseRequest);
                String deviceServerResp = HttpClientUtil.sendPostRequest(addStaffktUrl, jsonString,restTemplate);
                BaseResponse baseResponse = JSON.parseObject(deviceServerResp,BaseResponse.class);
                if(HttpConstants.SUCCESS.getErrorCode().equals(baseResponse.getHead().getRespCode())){
                    j.setSuccess(true);
                    j.setMsg("操作成功！");

                    // 更改库
                    staffktInfo.setUpdateTime(new Date());
                    staffktInfo.setUpdateFlag(0);
                    staffktInfo.setSyncRetryCnt(0);
                    staffktService.updateById(staffktInfo);
                    j.setSuccess(true);
                    j.setMsg("操作成功！");

                }else{
                    j.setSuccess(false);
                    j.setMsg("操作失败,下发特殊人员信息失败！");
                }
            } catch (Exception e) {
                j.setSuccess(false);
                j.setMsg("下发特殊人员信息异常");
            }

        }
        return j;
    }


    */
/**
     *@Description 从内勤人员中搜索进行添加
     *@param
     *@return
     *//*

    @RequestMapping("/staffAdd")
    @ResponseBody
    public Json saveServiceStaff(String id,String type){
        Json j = new Json();
        // 判断员工是否存在
        if( "staff_info".equals(type) ){
            StaffInfo staff = staffService.getById(id);
            StaffktInfo staffkt = new StaffktInfo();
            if( staff != null ){
                staffkt.setStaffktDept(staff.getDeptId());      //部门
                staffkt.setStaffktName(staff.getStaffName());   //姓名
                staffkt.setStaffktNumber(staff.getStaffId());   //编号
                staffkt.setStaffktIcNo(staff.getStaffCardId()); //IC卡号
                staffkt.setStaffktPhoto(staff.getCurPhoto());   //照片
                staffkt.setIsOutWork(staff.getIsOutWork());     //内勤外勤
                staffkt.setCreateTime(new Date());
                // 下发至设备
                Json json = saveStaffkt(staffkt);
                if( json.isSuccess() ){
                    j.setMsg("添加完成!");
                    j.setSuccess(true);
                }else{
                    j.setMsg(json.getMsg());
                    j.setSuccess(false);
                }
            }else{
                j.setMsg("未检索到该人员，请重新搜索！");
                j.setSuccess(false);
            }
        }else if( "service_staff_info".equals(type) ){
            ServiceStaffInfo serviceStaff = serviceStaffService.getById(id);
            StaffktInfo staffkt = new StaffktInfo();
            if( serviceStaff != null ){
                staffkt.setStaffktDept(serviceStaff.getServiceStaffDept());     //部门
                staffkt.setStaffktName(serviceStaff.getServiceStaffName());     //姓名
                staffkt.setStaffktNumber(serviceStaff.getServiceStaffNumber()); //编号
                staffkt.setStaffktIcNo(serviceStaff.getServiceStaffIcNo());     //IC卡号
                staffkt.setStaffktPhoto(serviceStaff.getServiceStaffPhoto());   //照片
                staffkt.setIsOutWork(serviceStaff.getIsOutWork());              //内外勤
                staffkt.setCreateTime(new Date());
                Json json = saveStaffkt(staffkt);
                if( json.isSuccess() ){
                    j.setMsg("添加完成!");
                    j.setSuccess(true);
                }else{
                    j.setMsg(json.getMsg());
                    j.setSuccess(false);
                }
            }else{
                j.setMsg("未检索到该人员，请重新搜索！");
                j.setSuccess(false);
            }
        }else{
            j.setMsg("人员类别异常，请重新添加！");
            j.setSuccess(true);
        }
        return j;
    }


}
*/

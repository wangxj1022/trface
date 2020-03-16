/*
package com.trendcote.web.controller.business;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trendcote.web.constrants.HttpConstants;
import com.trendcote.web.constrants.SysConstants;
import com.trendcote.web.contrants.BusinessTable;
import com.trendcote.web.dto.Business.StaffDto;
import com.trendcote.web.dto.page.*;
import com.trendcote.web.dto.request.Staff;
import com.trendcote.web.entity.business.StaffInfo;
import com.trendcote.web.entity.system.SysUser;
import com.trendcote.web.service.business.IStaffService;
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
import java.util.Date;

*/
/**
 * @author 莹
 * @date 2018/6/4
 *//*

@Controller
@Transactional(rollbackFor = Exception.class)
@RequestMapping("/staff")
public class StaffController {
    public static final Logger LOGGER= LoggerFactory.getLogger(StaffController.class);
    @Autowired
    private ISysOperLogService sysOperLogService;
    @Autowired
    private IStaffService staffService;
    @Autowired
    private RestTemplate restTemplate ;

    @Value("${device.server.url1}")
    private String addStaffUrl;
    @Value("${device.server.url2}")
    private String delStaffUrl;


    @RequestMapping(value = "/dataGrid",method = RequestMethod.POST)
    @ResponseBody
    public Grid dataGrid(StaffDto dto,PageFilter ph){
        Grid grid = new Grid();
        Page<StaffInfo> pg = new Page<StaffInfo>(ph.getPage(),ph.getRows());
        pg.setDesc(BusinessTable.StaffT.BASE_TABLE_CREATE_TIME);
        QueryWrapper<StaffInfo> queryWrapper = new QueryWrapper<StaffInfo>();
        queryWrapper.eq(BusinessTable.StaffT.STATUS,0);             // 0正常1删除
        queryWrapper.eq(BusinessTable.StaffT.STAFF_CARD_TYPE,1);    // 仅显示提供IC卡信息的用户
        queryWrapper.eq(DataUtil.isStringNotEmpty(dto.getStaffCode()),BusinessTable.StaffT.STAFF_ID,dto.getStaffCode());  //员工编号
        //queryWrapper.eq(DataUtil.isStringNotEmpty(dto.getStaffICCode()),BusinessTable.StaffT.STAFF_CARD_ID,dto.getStaffICCode());  //IC卡编号
        queryWrapper.eq(DataUtil.isStringNotEmpty(dto.getStaffName()),BusinessTable.StaffT.STAFF_NAME,dto.getStaffName());  //姓名
        IPage<StaffInfo> staffInfoIPage=staffService.page(pg,queryWrapper);
        grid.setRows(staffInfoIPage.getRecords());
        grid.setTotal(staffInfoIPage.getTotal());
        return grid;
    }




    @RequestMapping("/addPage")
    public String addPage() {
        return "/business/staff/staffinfoAdd";
    }

    @RequestMapping("/detail")
    public String  get(Long id, Model model){
        StaffInfo staffInfo = staffService.getById(id);
        model.addAttribute("staff", staffInfo);
        return "/business/staff/staffDetail";
    }
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(Long id ,HttpServletRequest req){
        Json j = new Json();
        try{
            StaffInfo db_staff=staffService.getById(id);
            if(null==db_staff){
                j.setSuccess(false);
                j.setMsg("员工信息不存在！");
                return j;
            }
            db_staff.setStatus(1);
            db_staff.setUpdateTime(new Date());
            staffService.updateById(db_staff);
            SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_STAFF_DEL,"删除自动同步类员工:"+db_staff.getStaffName() );
        }catch (Exception e){
            j.setSuccess(false);
            j.setMsg("操作失败,系统异常！");
            LOGGER.error("删除员工异常exception{}",e.fillInStackTrace());
        }
        return j;
    }


    //-----xym-------

    */
/**
     *@Description 跳转员工查询页面
     *@param 
     *@return 
     *//*

    @RequestMapping(value = "/manager")
    public String staffQuery(){
        return "/business/staff/staffQuery";
    }

    */
/**
     *@Description 跳转图像预览页面
     *@param 
     *@return 
     *//*

    @RequestMapping(value = "/preview")
    public String checkPic(Long id , Model model){
        StaffInfo staff = staffService.getById(id);
        model.addAttribute("staff",staff);
        return "/business/staff/staffPreview";
    }


    */
/**
     *@Description 员工信息修改方法（ 设备修改 ）
     *@param 
     *@return 
     *//*

    @RequestMapping(value = "/update")
    @ResponseBody
    public Json updateStaff( StaffInfo staffInfo ){

        // 传入参数校验
        Json j = new Json();
        if( staffInfo.getIsOutWork() == null ){
            j.setSuccess(false);
            j.setMsg("未完善员工内外勤信息");
            return j;
        }
        if( staffInfo.getCurPhoto().equals("null") ){
            staffInfo.setCurPhoto("");
        }

        // 数据库对象
        StaffInfo dbStaffInfo = staffService.getById(staffInfo.getId());

        if( staffInfo.getStaffName().equals(dbStaffInfo.getStaffName()) && staffInfo.getStaffCardId().equals(dbStaffInfo.getStaffCardId()) && staffInfo.getCurPhoto().equals(dbStaffInfo.getCurPhoto()) ){
            //  姓名、IC卡、员工照片 均未发生改动 则更新数据库即可
            staffInfo.setUpdateTime(new Date());
            staffService.updateById(staffInfo);
            j.setSuccess(true);
            j.setMsg("操作成功！");
            return j;
        }else{
            //  姓名、一卡通、员工照片发生改动，则需要进行重新下发
            // 删除旧卡
            String number = dbStaffInfo.getStaffId();       // 编号
            String cardNo = dbStaffInfo.getStaffCardId();   // IC卡号
            try {
                BaseRequest baseRequest = new BaseRequest(new Head());
                Staff staffReq = new Staff();
                staffReq.setNumber(number);   // 员工编号
                staffReq.setCardNo(cardNo);   // IC卡号
                baseRequest.setBody(staffReq);
                String jsonString = JSON.toJSONString(baseRequest);
                String deviceServerResp = HttpClientUtil.sendPostRequest(delStaffUrl, jsonString,restTemplate);
                BaseResponse baseResponse = JSON.parseObject(deviceServerResp,BaseResponse.class);
                if(HttpConstants.SUCCESS.getErrorCode().equals(baseResponse.getHead().getRespCode())){
                    // 下发新卡
                    BaseRequest request = new BaseRequest(new Head());
                    Staff staffRequest = new Staff();
                    staffRequest.setNumber(staffInfo.getStaffId());       // 员工编号
                    staffRequest.setName(staffInfo.getStaffName());       // 姓名
                    if( staffInfo.getIsOutWork() == null ){
                        staffInfo.setIsOutWork(0);
                    }
                    staffRequest.setIsOutWork(staffInfo.getIsOutWork());  // 是否外勤

                    if( StringUtils.isEmpty(staffInfo.getStaffCardId()) ){
                        staffInfo.setStaffCardId("1001" + DataUtil.generateRandom(10));
                    }
                    staffRequest.setCardNo(staffInfo.getStaffCardId());   // IC卡号
                    staffRequest.setPhoto(staffInfo.getCurPhoto());       // 员工照片
                    request.setBody(staffRequest);
                    String jsonStr = JSON.toJSONString(request);
                    String serverResp = HttpClientUtil.sendPostRequest(addStaffUrl, jsonStr,restTemplate);
                    BaseResponse response = JSON.parseObject(serverResp,BaseResponse.class);
                    if(HttpConstants.SUCCESS.getErrorCode().equals(response.getHead().getRespCode())){
                        j.setSuccess(true);
                        j.setMsg("操作成功！");
                    }else{
                        j.setSuccess(false);
                        j.setMsg("操作失败,更新员工信息失败！");
                    }
                }else{
                    j.setSuccess(false);
                    j.setMsg("更新员工信息失败:删除过程失败");
                }
            } catch (Exception e) {
                j.setSuccess(false);
                j.setMsg("更新员工信息异常");
                LOGGER.error("更新员工信息异常{}",e.fillInStackTrace());
            }
            return j;
        }

    }


    */
/**
     *@Description 跳转图像预览页面
     *@param
     *@return
     *//*

    @RequestMapping(value = "/reAdd")
    @ResponseBody
    public Json reAddStaff(Long id){
        Json j = new Json();
        StaffInfo staffInfo = staffService.getById(id);
        // 重新下发卡
        BaseRequest request = new BaseRequest(new Head());
        Staff staffRequest = new Staff();
        staffRequest.setNumber(staffInfo.getStaffId());       // 员工编号
        staffRequest.setName(staffInfo.getStaffName());       // 姓名
        if( staffInfo.getIsOutWork() == null ){
            staffInfo.setIsOutWork(0);
        }
        staffRequest.setIsOutWork(staffInfo.getIsOutWork());  // 是否外勤

        if( StringUtils.isEmpty(staffInfo.getStaffCardId()) ){
            staffInfo.setStaffCardId("1006" + DataUtil.generateRandom(10)); // 重新下发的卡前缀为 1006
        }
        staffRequest.setCardNo(staffInfo.getStaffCardId());   // IC卡号
        staffRequest.setPhoto(staffInfo.getCurPhoto());       // 员工照片
        request.setBody(staffRequest);
        String jsonStr = JSON.toJSONString(request);
        try {
            String serverResp = HttpClientUtil.sendPostRequest(addStaffUrl, jsonStr,restTemplate);
            BaseResponse response = JSON.parseObject(serverResp,BaseResponse.class);
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
        }
        return j;
    }


}
*/

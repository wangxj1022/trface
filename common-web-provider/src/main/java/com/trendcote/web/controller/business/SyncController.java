package com.trendcote.web.controller.business;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.trendcote.web.config.SyncConfig;
import com.trendcote.web.constrants.GlobalConstant;
import com.trendcote.web.constrants.HttpConstants;
import com.trendcote.web.contrants.BusinessTable;
import com.trendcote.web.dto.page.BaseRequest;
import com.trendcote.web.dto.page.BaseResponse;
import com.trendcote.web.dto.page.Head;
import com.trendcote.web.dto.request.Staff;
import com.trendcote.web.dto.request.Visitor;
import com.trendcote.web.entity.business.*;
import com.trendcote.web.service.business.*;
import com.trendcote.web.utils.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 同步方法操作类： 同步员工、同步访客、同步来访数据（访客数据里面应该有）
 * @Date 2019/6/24 10:13
 * @Created by xym
 */
@Controller
@Transactional(rollbackFor = Exception.class)
@RequestMapping("/sync")
public class SyncController {

    public static final Logger LOGGER = LoggerFactory.getLogger(SyncController.class);

    @Autowired
    private IStaffService staffService;

    @Autowired
    private IVisitorInfoService visitorService;

    @Autowired
    private IVisitorAppointmentInfoService visitorAppointmentInfoService;

    @Autowired
    private IVisitorAccessInfoService visitorAccessInfoService;

    @Autowired
    private IEchartsInfoService echartsInfoService;

    @Autowired
    private SyncConfig syncConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ISyncLogInfoService syncLogInfoService;

    @Autowired
    private IServiceStaffService iServiceStaffService;
    @Autowired
    private FangkeService fangkeService;

    /*@Value("${device.server.url1}")
    private String addStaffUrl;
    @Value("${device.server.url2}")
    private String delStaffUrl;
    @Value("${device.server.url3}")
    private String addVisitorUrl;
    @Value("${device.server.url4}")
    private String delVisitorUrl;*/
   /* @Value("${device.server.url7}")
    private String addStaffByIdCard;
    @Value("${device.server.url8}")
    private String delStaffByIdCard;*/



    /* 定时开关 */
    @Value("${schedule}")
    private String schedule;


    /**
     *@Description 每天凌晨2点 , 定时新增 校验通过的 身份证员工
     *@param
     *@return
     */
    /*@Scheduled( cron = "0 0 2 * * ? " )
    public void addStaffTimer(){

        if( "disable".equals(schedule)){
            return ;
        }

        // 需要同步的时间段( 近一天 )
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1); // 前一天
        Date date = calendar.getTime();
        String curDate = sdf.format(date);
        LOGGER.info(">>>>>开始下发昨天身份证校验通过员工<<<<<");

        // 下发：前一天新增身份证用户 ( type 身份证；create_time 大于昨天凌晨 )
        List<StaffInfo> staffs = staffService.list(new QueryWrapper<StaffInfo>().eq(BusinessTable.StaffT.STAFF_CARD_TYPE, GlobalConstant.DEFAULT)
                .gt(BusinessTable.StaffT.BASE_TABLE_CREATE_TIME, curDate));
        List<String> staffIds = staffs.stream().map(StaffInfo::getStaffCardId).collect(Collectors.toList());

        for(String staffId : staffIds){

            if(StringUtils.isEmpty(staffId))  continue;
            // 设备上添加
            BaseRequest request = new BaseRequest(new Head());
            Staff staffReq = new Staff();
            staffReq.setIdCardNo(staffId);  // 身份证号
            request.setBody(staffReq);
            String jsonString = JSON.toJSONString(request);
            String respString = HttpClientUtil.sendPostRequest(addStaffByIdCard, jsonString, restTemplate);
            BaseResponse response = JSON.parseObject(respString, BaseResponse.class);
            if( !HttpConstants.SUCCESS.getErrorCode().equals(response.getHead().getRespCode())){
                LOGGER.error("设备下发身份证员工失败,身份证号:"+staffId);
                continue;
            }

        }

    }
*/


    /**
     *@Description 定时删除身份证记录 （ 暂定：每周一 凌晨1点 清除一次 ）
     *@param
     *@return
     */
  /*  @Scheduled( cron = "0 0 1 ? * 1")
    public void delStaffTimer(){

        if( "disable".equals(schedule)){
            return ;
        }

        try {
            // 查询出来这部分仅刷身份证的员工
            List<StaffInfo> listStaffInfos = staffService.list(new QueryWrapper<StaffInfo>().eq(BusinessTable.StaffT.STAFF_CARD_TYPE,GlobalConstant.DEFAULT));

            for( StaffInfo staff : listStaffInfos ){
                String cardNo = staff.getStaffCardId();    //卡号为身份证号
                if( StringUtils.isEmpty(cardNo) )    continue;
                BaseRequest request = new BaseRequest(new Head());
                Staff staffReq = new Staff();
                staffReq.setCardNo(cardNo);            // 身份证号
                request.setBody(staffReq);
                String jsonString = JSON.toJSONString(request);
                String responseString = HttpClientUtil.sendPostRequest(delStaffByIdCard, jsonString, restTemplate);
                BaseResponse response = JSON.parseObject(responseString, BaseResponse.class);
                if( !HttpConstants.SUCCESS.getErrorCode().equals( response.getHead().getRespCode()) ){
                    LOGGER.error("设备删除该员工信息失败:"+staff.getStaffIdCardno());
                    continue;
                }
            }
        } catch (Exception e) {
            LOGGER.error("定时任务删除员工信息失败{}",e.fillInStackTrace());
        }
    }
*/


    /**
     *@Description 定时删除超时访客信息： ( 晚10点定时删除 以访客预约结束时间为准 )
     *@param
     *@return
     */
    /*@Scheduled( cron = "0 0 22 * * ? " )
    public void delVisitorTimer(){

        if( "disable".equals(schedule)){
            return ;
        }

        try {
            // 删除超时的访客信息
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String curDate = sdf.format(date);

            List<VisitorInfo> visitors = visitorService.list(new QueryWrapper<VisitorInfo>().lt(BusinessTable.VisitorInfoT.OUT_TIME, curDate));
            List<String> listVisitorCardIds = visitors.stream().map(VisitorInfo::getVisitorCardId).collect(Collectors.toList());

            for( String visitorCardId : listVisitorCardIds ){
                if(StringUtils.isEmpty(visitorCardId))  continue;
                // 设备上删除
                BaseRequest request = new BaseRequest(new Head());
                Visitor visitorReq = new Visitor();
                visitorReq.setIdCardNo(visitorCardId);
                request.setBody(visitorReq);
                String jsonString = JSON.toJSONString(request);
                String respString = HttpClientUtil.sendPostRequest(delVisitorUrl, jsonString, restTemplate);
                BaseResponse response = JSON.parseObject(respString, BaseResponse.class);

                if( !HttpConstants.SUCCESS.getErrorCode().equals(response.getHead().getRespCode())){
                    LOGGER.error("设备删除该访客信息失败:"+visitorCardId);
                    continue;
                }
                // 数据据库删除
                *//*if( HttpConstants.SUCCESS.getErrorCode().equals(response.getHead().getRespCode())){
                    visitorService.remove(new QueryWrapper<VisitorInfo>().eq(BusinessTable.VisitorInfoT.VISITOR_CARD_ID,visitorCardId));
                }else {
                    LOGGER.error("设备删除该访客信息失败:"+visitorCardId);
                }*//*
            }
        } catch (Exception e) {
            LOGGER.error("定时任务删除访客信息失败{}",e.fillInStackTrace());
        }
    }
*/

    /**
     *@Description 定时统计：每2小时 统计员工和访客数量 (用于Echarts显示数据)
     *@param
     *@return
     */
    @Scheduled( cron = "0 0 */2 * * ? ")
    public void echartsRecord(){

        // 定时开关判断
        if( "disable".equals(schedule) ){
            return ;
        }

        // 员工数量: 状态正常，持有IC卡类型的用户
        int staffCount = this.iServiceStaffService.count(new QueryWrapper<ServiceStaffInfo>()
                .eq(BusinessTable.ServiceStaffInfoT.STATUS, GlobalConstant.NOT_DEFAULT));
        // 访客数量:
        int visitorCount = this.fangkeService.count(null);

        // 入库
        EchartsInfo staffEchart = new EchartsInfo();
        staffEchart.setCount(staffCount);
        staffEchart.setRemark("");
        staffEchart.setType(0);
        staffEchart.setDate(new Date());

        EchartsInfo visitorEchart = new EchartsInfo();
        visitorEchart.setCount(visitorCount);
        visitorEchart.setType(1);
        visitorEchart.setDate(new Date());

        // 信息统计：没有当天信息则进行添加，有当天信息则进行新增
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = sdf.format(calendar.getTime());

        calendar.add(Calendar.DAY_OF_MONTH,+1);
        String endTime = sdf.format(calendar.getTime());

        EchartsInfo staff = echartsInfoService.getOne(new QueryWrapper<EchartsInfo>()
                .eq(BusinessTable.EchartsInfoT.TYPE,GlobalConstant.DEFAULT)
                .between(BusinessTable.EchartsInfoT.DATE,startTime,endTime));
        EchartsInfo visitor = echartsInfoService.getOne(new QueryWrapper<EchartsInfo>()
                .eq(BusinessTable.EchartsInfoT.TYPE,GlobalConstant.NOT_DEFAULT)
                .between(BusinessTable.EchartsInfoT.DATE, startTime,endTime));

        if( staff == null ){
            echartsInfoService.save(staffEchart);    // 统计员工Echart
        }else{
            echartsInfoService.update(staffEchart,new QueryWrapper<EchartsInfo>()
                    .eq(BusinessTable.EchartsInfoT.TYPE,GlobalConstant.DEFAULT)
                    .between(BusinessTable.EchartsInfoT.DATE,startTime,endTime));
        }

        if( visitor == null ){
            echartsInfoService.save(visitorEchart);  // 统计访客Echart
        }else{
            echartsInfoService.update(visitorEchart,new QueryWrapper<EchartsInfo>()
                    .eq(BusinessTable.EchartsInfoT.TYPE,GlobalConstant.NOT_DEFAULT)
                    .between(BusinessTable.EchartsInfoT.DATE,startTime,endTime));
        }
    }



    /**
     *@Description 过期方法 || 访客预约数据同步：只同步过来前一天的
     *@param
     *@return
     */
    /*@PostMapping("/appointment")
    @ResponseBody
    @Scheduled( cron = "0 0 5 * * ? ")
    public Json syncAppointment(){
        Json j = new Json();

        if( "disable".equals(schedule)){
            j.setSuccess(false);
            j.setMsg("同步失败,同步开关未开启！");
            return j;
        }

        // 需要同步的时间段( 近一天 )
        Calendar calendar = Calendar.getInstance();
        Date endDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date startDate = calendar.getTime();

        try {
            // 调用阳光OA接口获取最近24h访客数据：姓名、身份证号、开始时间、结束时间不能为空！！！!
            String url = syncConfig.getSyncVisitorUrl();
            String param1 = DateUtil.format(startDate);
            String param2 = DateUtil.format(endDate);
            String respStr = HttpClientUtil.sendGetRequest(url, param1,param2, restTemplate);

            List<VisitorInfo> listOAVisitors = JsonUtil.parseJson(respStr,syncConfig.getEoaPublicKey());

            if( listOAVisitors == null ){
                j.setMsg("同步完成，未发现新数据");
                j.setSuccess(true);
                return j;
            }

            // 删除旧的预约记录
            visitorAppointmentInfoService.remove(null);
            // 新的预约记录入库
            for( VisitorInfo visitor : listOAVisitors ){
                VisitorAppointmentInfo visitorAppoint = new VisitorAppointmentInfo();
                visitorAppoint.setPersonName(visitor.getVisitorName());   //姓名
                visitorAppoint.setPersonCode(visitor.getVisitorCardId()); //身份证
                visitorAppoint.setBeginTime(visitor.getInTime());         //预约开始时间
                visitorAppoint.setEndTime(visitor.getOutTime());          //预约结束时间
                visitorAppoint.setPersonCompany(visitor.getVisitorCompany());     //公司
                visitorAppoint.setCreateTime(new Date());
                visitorAppoint.setUpdateTime(new Date());
                visitorAppointmentInfoService.save(visitorAppoint);
            }
        } catch (Exception e) {
            LOGGER.error("同步访客预约信息异常{}",e.fillInStackTrace());
            j.setSuccess(false);
            j.setMsg("同步访客预约信息出现异常");
            return j;
        }

        j.setMsg("同步完成");
        j.setSuccess(true);
        return j;
    }*/


}

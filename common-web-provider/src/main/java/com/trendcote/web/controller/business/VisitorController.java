package com.trendcote.web.controller.business;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trendcote.web.constrants.HttpConstants;
import com.trendcote.web.constrants.SysConstants;
import com.trendcote.web.contrants.BusinessTable;
import com.trendcote.web.dto.Business.VisitorDto;
import com.trendcote.web.dto.page.*;
import com.trendcote.web.entity.business.VisitorInfo;
import com.trendcote.web.entity.system.SysUser;
import com.trendcote.web.service.business.IVisitorInfoService;
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
import java.util.Date;

/**
 * @Description 访客查询
 * @author 莹
 * @date 2018/6/4
 */
@Controller
@Transactional(rollbackFor = Exception.class)
@RequestMapping("/visitor")
public class VisitorController {

    public static final Logger LOGGER= LoggerFactory.getLogger(VisitorController.class);
    @Autowired
    private ISysOperLogService sysOperLogService;
    @Autowired
    private IVisitorInfoService visitorInfoService;


    // 访客审批 ： 方法过期 ( 阳光金融目前不涉及访客预约审批功能 )
    /*@RequestMapping(value = "/check",method = RequestMethod.POST)
    @ResponseBody
    public Json check(Integer id , HttpServletRequest req){
        Json j = new Json();
        try{
            VisitorInfo db_visitor=visitorInfoService.getById(id);
            if(db_visitor==null){
                j.setSuccess(false);
                j.setMsg("访客信息不存在！");
                return j;
            }
//            if(0!=db_visitor.get()){
//                j.setSuccess(false);
//                j.setMsg("员工状态不合法！");
//                return j;
//            }
            //上传设备员工信息
            BaseRequest baseRequest = new BaseRequest(new Head());
            VisitorInfo visitorInfoReq = new VisitorInfo();
            visitorInfoReq.setCurPhoto(db_visitor.getCurPhoto());
            visitorInfoReq.setVisitorName(db_visitor.getVisitorName());
            baseRequest.setBody(visitorInfoReq);
            String jsonString = JSON.toJSONString(baseRequest);
            String deviceServerResp = HttpClientUtil.sendPostRequest(deviceUrl, jsonString,restTemplate);
            BaseResponse baseResponse = JSON.parseObject(deviceServerResp,BaseResponse.class);
            if(HttpConstants.SUCCESS.equals(baseResponse.getHead().getRespCode())){
                //db_visitor.setStatus(1);
                db_visitor.setUpdateTime(new Date());
                visitorInfoService.updateById(db_visitor);
                SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
                sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_VISITOR_CHECK);
                j.setSuccess(true);
                j.setMsg("操作成功！");
            }else{
                j.setSuccess(false);
                j.setMsg("操作失败,设备服务-"+baseResponse.getHead().getRespMsg()+"！");
            }
        }catch (Exception e){
            j.setSuccess(false);
            j.setMsg("操作失败,系统异常！");
            LOGGER.error("审核访客异常exception{}",e.fillInStackTrace());
        }
        return j;
    }*/


    /**
     *@Description 跳转访客查询页面
     *@param
     *@return
     */
    @RequestMapping("/manager")
    public String visitorQuery(){
        return "/business/visitor/visitorQuery";
    }

    /**
     *@Description 访客查询
     *@param dto 检索条件（公司名称、手机号） ph 分页信息
     *@return
     */
    @RequestMapping(value = "/dataGrid",method = RequestMethod.POST)
    @ResponseBody
    public Grid dataGrid(VisitorDto dto, PageFilter ph){
        Grid grid = new Grid();
        Page<VisitorInfo> pg = new Page<VisitorInfo>(ph.getPage(),ph.getRows());
        pg.setDesc(BusinessTable.VisitorInfoT.BASE_TABLE_CREATE_TIME);   //根据创建记录时间倒序
        QueryWrapper<VisitorInfo> queryWrapper = new QueryWrapper<VisitorInfo>();
        queryWrapper.eq(StringUtils.isNotEmpty(dto.getVisitorName()),BusinessTable.VisitorInfoT.VISITOR_NAME,dto.getVisitorName()); // 根据姓名查询
        //queryWrapper.eq(StringUtils.isNotEmpty(dto.getMobile()),BusinessTable.VisitorInfoT.MOBILE,dto.getMobile());

        IPage<VisitorInfo> visitorInfoIPage=visitorInfoService.page(pg,queryWrapper);
        grid.setRows(visitorInfoIPage.getRecords());
        grid.setTotal(visitorInfoIPage.getTotal());
        return grid;
    }

    /**
     *@Description 访客详情页面(弹框)
     *@param id 选中访客id
     *@return
     */
    @RequestMapping("/detail")
    public String visitorDetail(Long id, Model model){
        VisitorInfo visitor = visitorInfoService.getById(id);
        model.addAttribute("visitor",visitor);
        return "/business/visitor/visitorInfoDetail";
    }

    /**
     *@Description 删除访客信息
     *@param id 选中访客的id号
     *@return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(Long id ,HttpServletRequest req){
        Json j = new Json();

        try{
            VisitorInfo visitor = visitorInfoService.getById(id);
            if( null==visitor ){
                j.setSuccess(false);
                j.setMsg("该条访客信息不存在！");
                return j;
            }
            visitorInfoService.removeById(id);
            SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_VISITOR_DEL, "删除访客:"+visitor.getVisitorName());
            j.setMsg("操作成功！");
            j.setSuccess(true);
        }catch (Exception e){
            j.setSuccess(false);
            j.setMsg("操作失败,系统异常！");
            LOGGER.error("删除访客异常exception{}",e.fillInStackTrace());
        }
        return j;
    }

}

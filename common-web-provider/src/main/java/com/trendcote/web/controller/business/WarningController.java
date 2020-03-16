package com.trendcote.web.controller.business;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.trendcote.web.constrants.SysConstants;
import com.trendcote.web.contrants.SystemTable;
import com.trendcote.web.dto.page.Json;
import com.trendcote.web.entity.system.SysUser;
import com.trendcote.web.entity.system.TbParamsInfo;
import com.trendcote.web.service.system.ISysOperLogService;
import com.trendcote.web.service.system.ISysParamService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aliyuncs.http.MethodType;
import javax.servlet.http.HttpServletRequest;
import com.aliyuncs.exceptions.ServerException;

/**
 * @Description 预警管理页面
 * @Date 2020/1/8 9:34
 * @Created by xym
 */

@Controller
@RequestMapping("/warning")
public class WarningController {

    public static final Logger LOGGER= LoggerFactory.getLogger( WarningController.class );

    @Autowired
    private ISysParamService sysParamService;
    @Autowired
    private ISysOperLogService sysOperLogService;
    @Autowired
    private JavaMailSender javaMailSender;

    // 邮件测试
    @RequestMapping("/email")
    @ResponseBody
    public Json sendEmail( HttpServletRequest req ){
        Json json = new Json();
        try {
            // 判断邮件开关是否开启
            TbParamsInfo emailFlag = sysParamService.getOne(new QueryWrapper<TbParamsInfo>().eq(SystemTable.Tb_Params.P_Name, "emailFlag"));
            if( emailFlag !=null && "1".equals( emailFlag.getpValue() ) ){
                // 开始发送邮件
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom( sysParamService.getOne(new QueryWrapper<TbParamsInfo>().eq(SystemTable.Tb_Params.P_Name, "emailFrom")).getpValue() );
                message.setTo( sysParamService.getOne(new QueryWrapper<TbParamsInfo>().eq(SystemTable.Tb_Params.P_Name, "emailTo")).getpValue() );
                message.setSubject( sysParamService.getOne(new QueryWrapper<TbParamsInfo>().eq(SystemTable.Tb_Params.P_Name, "emailSubject")).getpValue() );
                message.setText( sysParamService.getOne(new QueryWrapper<TbParamsInfo>().eq(SystemTable.Tb_Params.P_Name, "emailText")).getpValue() );
                javaMailSender.send(message);
                json.setSuccess(true);
                json.setMsg("发送邮件成功！");
                SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
                sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_WARNING_SENDMAIL,"发送邮件详情");
            }else{
                json.setSuccess(false);
                json.setMsg("未打开邮件开关！");
            }
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("发送邮件失败！");
            LOGGER.error("发送邮件失败{}",e.fillInStackTrace());
        }
        return json;
    }


    // 短息测试
    @RequestMapping("/message")
    @ResponseBody
    public Json sendMessage( HttpServletRequest req ){
        Json json = new Json();
        try {
            // 判断邮件开关是否开启
            TbParamsInfo messageFlag = sysParamService.getOne(new QueryWrapper<TbParamsInfo>().eq(SystemTable.Tb_Params.P_Name, "messageFlag"));
            if( messageFlag !=null && "1".equals( messageFlag.getpValue() ) ){
                // 开始发送短信
                DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIffjVVmOrEwUC", "ld632iz1ElDRj6L9oPARny4aD6bJog");
                IAcsClient client = new DefaultAcsClient(profile);
                CommonRequest request = new CommonRequest();
                request.setMethod(MethodType.POST);
                request.setDomain("dysmsapi.aliyuncs.com");
                request.setVersion("2017-05-25");
                request.setAction("SendSms");
                request.putQueryParameter("RegionId", "cn-hangzhou");
                request.putQueryParameter("PhoneNumbers", sysParamService.getOne(new QueryWrapper<TbParamsInfo>().eq(SystemTable.Tb_Params.P_Name, "messagePhoneNumbers")).getpValue()  );  // 目标手机号
                request.putQueryParameter("SignName", sysParamService.getOne(new QueryWrapper<TbParamsInfo>().eq(SystemTable.Tb_Params.P_Name, "messageSignName")).getpValue() );           // 短信签名名称
                request.putQueryParameter("TemplateCode", sysParamService.getOne(new QueryWrapper<TbParamsInfo>().eq(SystemTable.Tb_Params.P_Name, "messageTemplateCode")).getpValue()  );  // 短信模板ID
                //request.putQueryParameter("TemplateParam", "{\"password\":\"lihaoaikanpian\"}");    // 短信参数
                try {
                    CommonResponse response = client.getCommonResponse(request);
                    json.setSuccess(true);
                    json.setMsg("发送短信成功！");
                    System.out.println(response.getData());
                } catch (ServerException e) {
                    e.printStackTrace();
                } catch (ClientException e) {
                    e.printStackTrace();
                }
                SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
                sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_WARNING_SENDMESSAGE,"发送短信详情");
            }else{
                json.setSuccess(false);
                json.setMsg("未打开短信开关！");
            }
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("发送短信失败！");
            LOGGER.error("发送短信失败{}",e.fillInStackTrace());
        }
        return json;
    }




}

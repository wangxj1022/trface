package com.trendcote.web.controller.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trendcote.web.constrants.SysConstants;
import com.trendcote.web.contrants.BusinessTable;
import com.trendcote.web.dto.Business.EmailDto;
import com.trendcote.web.dto.page.Grid;
import com.trendcote.web.dto.page.Json;
import com.trendcote.web.dto.page.PageFilter;
import com.trendcote.web.entity.business.EmailInfo;
import com.trendcote.web.entity.business.EntryInfo;
import com.trendcote.web.entity.system.SysUser;
import com.trendcote.web.service.business.IEmailInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @Description 邮件发送Controller
 * @Date 2019/9/10 15:54
 * @Created by xym
 */

@Controller
@RequestMapping("/email")
public class EmailController {

    @Resource
    private JavaMailSender javaMailSender;

    @Autowired
    private IEmailInfoService emailInfoService;


    /**
     *@Description 邮箱管理页面
     *@param
     *@return
     */
    @RequestMapping("/manager")
    public String emailManager(){
        return "/business/email/emailQuery";
    }

    /**
     *@Description 邮箱添加页面
     *@param
     *@return
     */
    @RequestMapping("/add")
    public String emailAdd(){
        return "/business/email/emailAdd";
    }



    @RequestMapping(value = "/dataGrid",method = RequestMethod.POST)
    @ResponseBody
    public Grid dataGrid(EmailDto dto, PageFilter ph){
        Grid grid = new Grid();
        Page<EmailInfo> pg = new Page<>(ph.getPage(),ph.getRows());
        pg.setDesc(BusinessTable.EmailInfoT.BASE_TABLE_CREATE_TIME);
        QueryWrapper<EmailInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(dto.getEmailAddress()),BusinessTable.EmailInfoT.EMAIL_ADRESS,dto.getEmailAddress());
        queryWrapper.eq(StringUtils.isNotEmpty(dto.getEmailName()),BusinessTable.EmailInfoT.EMAIL_NAME,dto.getEmailName());
        IPage<EmailInfo> emailInfoIPage=emailInfoService.page(pg,queryWrapper);
        grid.setRows(emailInfoIPage.getRecords());
        grid.setTotal(emailInfoIPage.getTotal());
        return grid;
    }


    /**
     *@Description 添加邮箱
     *@param
     *@return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Json saveEmail(EmailInfo emailInfo){
        Json json = new Json();
        try {
            EmailInfo email = emailInfoService.getOne(new QueryWrapper<EmailInfo>().eq(BusinessTable.EmailInfoT.EMAIL_ADRESS, emailInfo.getEmailAddress()));
            if ( email != null ) {
                json.setSuccess(false);
                json.setMsg("该邮箱已经存在！请重新添加");
                return json;
            }

            emailInfo.setCreateTime(new Date());  //创建时间
            emailInfo.setUpdateTime(new Date());  //更新时间
            emailInfoService.save(emailInfo);
            json.setSuccess(true);
            json.setMsg("添加成功！");
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("添加异常！");
        }
        return json;
    }


    /**
     *@Description 邮箱删除
     *@param
     *@return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public Json delEmail(Long id){
        Json j = new Json();
        try {
            EmailInfo email = emailInfoService.getById(id);
            if( email == null ){
                j.setSuccess(false);
                j.setMsg("邮箱信息不存在");
                return j;
            }

            emailInfoService.removeById(id);
            j.setSuccess(true);
            j.setMsg("删除邮箱信息成功");
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("删除邮箱信息异常");
        }
        return j;
    }


    /**
     *@Description 编辑邮箱
     *@param
     *@return
     */
    @RequestMapping("/detail")
    public String entryDetail(Long id,Model model){
        EmailInfo email = emailInfoService.getById(id);
        model.addAttribute("email",email);
        return "/business/email/emailDetail";
    }


    /**
     *@Description 编辑进出口
     *@param
     *@return
     */
    @ResponseBody
    @RequestMapping("/update")
    public Json entryUpdate(EmailInfo emailInfo){
        Json json = new Json();
        try {

            Date now = new Date();
            emailInfo.setUpdateTime(now);  //更新时间
            emailInfoService.updateById(emailInfo);
            json.setSuccess(true);
            json.setMsg("更新成功！");

        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("更新异常！");
        }
        return json;
    }




    /**
     *@Description 邮件发送
     *@param 
     *@return 
     */
    @ResponseBody
    @RequestMapping("/send")
    public Json mimeEmail() throws MessagingException {

        Json json = new Json();
        List<EmailInfo> emails = emailInfoService.list(null);
        for( EmailInfo email : emails ){
            if(StringUtils.isEmpty( email.getEmailAddress() )){
                continue;
            }
            // MimeMessage 本身的 API 有些笨重，我们可以使用 MimeMessageHelper
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            // 第二个参数是 true ，表明这个消息是 multipart类型的/
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("m18404985342@163.com");
            mimeMessageHelper.setTo(email.getEmailAddress());
            mimeMessageHelper.setSubject("阳光金融城-今日出入统计");
            mimeMessageHelper.setText("出入统计见附件.");
            //添加附件,第一个参数表示添加到 Email 中附件的名称，第二个参数是图片资源
            mimeMessageHelper.addAttachment("出入统计表.xls", new ClassPathResource("D:\\阳光日志\\0910\\0910151734.xls"));
            try {
                javaMailSender.send(mimeMessage);
            } catch (MailException e) {
                // 邮件发送异常
                json.setSuccess(false);
                json.setMsg("发送异常！");
                return json;
            }
        }

        json.setSuccess(true);
        json.setMsg("发送成功！");
        return json;

    }
}

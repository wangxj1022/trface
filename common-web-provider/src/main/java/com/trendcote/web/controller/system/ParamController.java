package com.trendcote.web.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trendcote.web.constrants.SysConstants;
import com.trendcote.web.contrants.SystemTable;
import com.trendcote.web.dto.page.Grid;
import com.trendcote.web.dto.page.Json;
import com.trendcote.web.dto.page.PageFilter;
import com.trendcote.web.dto.system.ParamDto;
import com.trendcote.web.entity.system.SysUser;
import com.trendcote.web.entity.system.TbParamsInfo;
import com.trendcote.web.service.system.ISysOperLogService;
import com.trendcote.web.service.system.ISysParamService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * @Description 参数管理页面
 * @Date 2019/6/13 17:25
 * @Created by xym
 */
@Controller
@RequestMapping("/param")
public class ParamController {

    public static final Logger LOGGER= LoggerFactory.getLogger(ParamController.class);

    @Autowired
    private ISysOperLogService sysOperLogService;
    @Autowired
    private ISysParamService sysParamService;

    /**
     *@Description 参数管理页面
     *@param
     *@return
     */
    @RequestMapping("/manager")
    public String deviceManager(){
        return "/system/param";
    }

    /**
     *@Description 参数查询
     *@param
     *@return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public Grid dataGrid(ParamDto dto, PageFilter ph){
        Grid grid = new Grid();
        Page<TbParamsInfo> pg = new Page<>(ph.getPage(),ph.getRows());

        QueryWrapper<TbParamsInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(dto.getParamName()), SystemTable.Tb_Params.P_Name, dto.getParamName());

        IPage<TbParamsInfo> paramInfoIPage=sysParamService.page(pg,queryWrapper);

        grid.setRows(paramInfoIPage.getRecords());
        grid.setTotal(paramInfoIPage.getTotal());
        return grid;
    }


    /**
     *@Description 参数信息编辑页面
     *@param
     *@return
     */
    @RequestMapping("/detail")
    public String entryDetail(String value,Model model){
        TbParamsInfo param = sysParamService.getOne(new QueryWrapper<TbParamsInfo>().eq(SystemTable.Tb_Params.P_Name,value));
        //TbParamsInfo param = sysParamService.getById(id);
        model.addAttribute("tbParam",param);
        return "/system/paramDetail";
    }


    /**
     *@Description 更新参数信息
     *@param
     *@return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Json updateParam(TbParamsInfo paramInfo, HttpServletRequest req){
        Json json = new Json();
        try {
            sysParamService.update(paramInfo,new QueryWrapper<TbParamsInfo>().eq(SystemTable.Tb_Params.P_Name,paramInfo.getpName()));
            json.setSuccess(true);
            json.setMsg("更新成功！");
            SysUser loginUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            sysOperLogService.saveLog(loginUser.getUsername(),req.getRemoteHost(), SysConstants.LOG_PARAM_UPDATE,"更新参数信息详情");
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("更新失败！");
            LOGGER.error("更新设备失败{}",e.fillInStackTrace());
        }
        return json;
    }

}

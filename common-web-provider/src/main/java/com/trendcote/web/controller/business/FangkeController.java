package com.trendcote.web.controller.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trendcote.web.contrants.BusinessTable;
import com.trendcote.web.dto.Business.FangkeDto;
import com.trendcote.web.dto.page.Grid;
import com.trendcote.web.dto.page.PageFilter;
import com.trendcote.web.entity.business.FangKe;
import com.trendcote.web.service.business.FangkeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Transactional(rollbackFor = Exception.class)
@Controller
@RequestMapping("/fangke")
public class FangkeController {

    private static final Logger logger = LoggerFactory.getLogger(FangkeController.class);
    @Autowired
    private FangkeService fangkeService;


    /**
     * 跳转页面
     * @return
     */
    @RequestMapping("/manager")
    private String mannger(){
        return "/business/visitor/fangke";
    }

    /**
     * 返乡人员列表查询
     * @param dto
     * @param pageFilter
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public Grid findAll(FangkeDto dto, PageFilter pageFilter){
            Grid grid = new Grid();
            Page<FangKe> page = new Page<FangKe>();
            //按照登记时间降序
            page.setDesc(BusinessTable.FangKeT.LOGTIME);
            QueryWrapper<FangKe> queryWrapper = new QueryWrapper<FangKe>();
            //查询条件
            queryWrapper.like(StringUtils.isNotEmpty(dto.getXm()), BusinessTable.FangKeT.XM, dto.getXm());//姓名
            queryWrapper.like(StringUtils.isNotEmpty(dto.getTriptype()), BusinessTable.FangKeT.TRIPTYPE, dto.getTriptype());//回程方式
            queryWrapper.eq(StringUtils.isNotEmpty(dto.getTripno()), BusinessTable.FangKeT.TRIPNO, dto.getTripno());//车次
            queryWrapper.ge(pageFilter.getNewBeginTime() != null, BusinessTable.FangKeT.LOGTIME, pageFilter.getNewBeginTime());
            queryWrapper.le(pageFilter.getNewEndTime() != null, BusinessTable.FangKeT.LOGTIME, pageFilter.getNewEndTime());
            IPage<FangKe> iPage = this.fangkeService.page(page, queryWrapper);
            grid.setRows(iPage.getRecords());
            grid.setTotal(iPage.getTotal());
            return grid;
    }

    @RequestMapping("/detail")
    public String deail(int id, HttpServletRequest request){

        System.out.println("-----------------"+id);
        //根据id 查询所有
        FangKe fangKe = this.fangkeService.getById(id);
       /* String images = "";
        if (0 != id){
            FangKe fangKe1 = this.fangkeService.getOne(new QueryWrapper<FangKe>().eq(StringUtils.isNotEmpty(BusinessTable.FangKeT.BASE_TABLE_ID),BusinessTable.FangKeT.BASE_TABLE_ID,id));
            if (null != fangKe1){
                images = fangKe1.getPhoto();
            }
        }
        fangKe.setPhoto(images);*/
        request.setAttribute("fangKe",fangKe);
        return "/business/visitor/fangkedeil";

    }
}

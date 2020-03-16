package com.trendcote.web.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description 编号生成工具类
 * @Date 2019/6/11 13:48
 * @Created by xym
 */

public class SerialNumberUtil {

    /**
     *@Description 生成访客预约编号
     *@param
     *@return
     */
    public static String generateAppointNum(){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");//设置日期格式
        return LocalDateTime.now().format(fmt);
    }


}

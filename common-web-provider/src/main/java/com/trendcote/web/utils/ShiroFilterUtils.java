package com.trendcote.web.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendcote.web.dto.page.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ShiroFilterUtils {
	private static final Logger logger = LoggerFactory.getLogger(ShiroFilterUtils.class);
	private final static ObjectMapper objectMapper = new ObjectMapper();

    public static boolean isAjax(ServletRequest request){
    	String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
    	if("XMLHttpRequest".equalsIgnoreCase(header)){
    		logger.debug("shiro工具类ShiroFilterUtils.isAjax当前请求,为Ajax请求");
    		return Boolean.TRUE;
    	}
    	logger.debug("shiro工具类ShiroFilterUtils.isAjax当前请求,非Ajax请求");
    	return Boolean.FALSE;
    }

	public static void out(HttpServletResponse response, Json result){
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			out = response.getWriter();
			out.println(objectMapper.writeValueAsString(result));
			logger.error("用户在线数量限制ShiroFilterUtils.out响应json信息成功");
		} catch (Exception e) {
			logger.error("用户在线数量限制ShiroFilterUtils.out响应json信息出错", e);
		}finally{
			if(null != out){
				out.flush();
				out.close();
			}
		}
	}

}

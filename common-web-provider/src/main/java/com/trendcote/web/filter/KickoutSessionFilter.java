package com.trendcote.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendcote.web.dto.page.Json;
import com.trendcote.web.entity.system.SysUser;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

public class KickoutSessionFilter extends AccessControlFilter {

	private static final Logger logger = LoggerFactory
			.getLogger(KickoutSessionFilter.class);

	private final static ObjectMapper objectMapper = new ObjectMapper();
	private String kickoutUrl;
	private boolean kickoutAfter = false;
	private int maxSession = 1;
	private SessionManager sessionManager;
	private Cache<String, Deque<Serializable>> cache;

	public void setKickoutUrl(String kickoutUrl) {
		this.kickoutUrl = kickoutUrl;
	}

	public void setKickoutAfter(boolean kickoutAfter) {
		this.kickoutAfter = kickoutAfter;
	}

	public void setMaxSession(int maxSession) {
		this.maxSession = maxSession;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	// 设置Cache的key的前缀
	public void setCacheManager(CacheManager cacheManager) {
		//必须和ehcache缓存配置中的缓存name一致
		this.cache = cacheManager.getCache("shiro-activeSessionCache");
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		try {
			Subject subject = getSubject(request, response);
			if (!subject.isAuthenticated() && !subject.isRemembered()) {
				Json j = new Json();
//				if (ShiroFilterUtils.isAjax(request) ) {
//					logger.debug(getClass().getName()+ "当前用户已经在其他地方登录，并且是Ajax请求！");
//					j.setSuccess(false);
//					j.setMsg("您已在别处登录或session失效，请您修改密码或重新登录！");
//					out(response, j);
//					return false;
//				}else{
					return true;
//				}
			}
			HttpServletRequest req=(HttpServletRequest) request;
			String path = req.getRequestURI();
			logger.debug("当前请求的url{}" , path);
			if(path.equals("/toLogin")){
				return true;
			}
			Session session = subject.getSession();
			SysUser user = (SysUser) subject.getPrincipal();
			String username = user.getUsername();
			Serializable sessionId = session.getId();
			Deque<Serializable> deque = cache.get(username);
			if (deque == null) {
				deque = new ArrayDeque<Serializable>();
			}
			if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
				deque.push(sessionId);
				cache.put(username, deque);
			}
			while (deque.size() > maxSession) {
				Serializable kickoutSessionId = null;
				if (kickoutAfter) {
					kickoutSessionId = deque.removeFirst();
				} else {
					kickoutSessionId = deque.removeLast();
				}
				cache.put(username, deque);
				try {
					// 获取被踢出的sessionId的session对象
					Session kickoutSession = sessionManager
							.getSession(new DefaultSessionKey(kickoutSessionId));
					if (kickoutSession != null) {
						// 设置会话的kickout属性表示踢出了
						kickoutSession.setAttribute("kickout", true);
					}
				} catch (Exception e) {
					//sessionID 不存在异常 忽略
				}
			}
			// 如果被踢出了，(前者或后者)直接退出，重定向到踢出后的地址
			if ((Boolean) session.getAttribute("kickout") != null
					&& (Boolean) session.getAttribute("kickout") == true) {
				// 会话被踢出了
				try {
					// 退出登录
					subject.logout();
				} catch (Exception e) {
					// ignore
				}
				saveRequest(request);
				logger.debug("==踢出后用户重定向的路径kickoutUrl:" + kickoutUrl);
				// ajax请求
				return isAjaxResponse(request,response);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("控制用户在线数量KickoutSessionFilter.onAccessDenied异常！{}",e.fillInStackTrace());
			return isAjaxResponse(request,response);
		}
	}
	/**
	 *
	 * @描述：response输出json

	 * @创建时间：2018年4月24日 下午5:14:22
	 * @param response
	 * @param result
	 */
	public static void out(ServletResponse response, Json result){
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");//设置编码
			response.setContentType("application/json");//设置返回类型
			out = response.getWriter();
			out.println(objectMapper.writeValueAsString(result));//输出
			logger.error("用户在线数量限制【elderlycare-web-->KickoutSessionFilter.out】响应json信息成功");
		} catch (Exception e) {
			logger.error("用户在线数量限制【elderlycare-web-->KickoutSessionFilter.out】响应json信息出错", e);
		}finally{
			if(null != out){
				out.flush();
				out.close();
			}
		}
	}
	private boolean isAjaxResponse(ServletRequest request, ServletResponse response) throws IOException {
		Json j = new Json();
//		if (ShiroFilterUtils.isAjax(request) ) {
//			logger.debug(getClass().getName()+ "当前用户已经在其他地方登录，并且是Ajax请求！");
//			j.setSuccess(false);
//			j.setMsg("您已在别处登录或session失效，请您修改密码或重新登录");
//			out(response, j);
//		}else{
			WebUtils.issueRedirect(request, response, kickoutUrl);
		//}
		return false;
	}

}

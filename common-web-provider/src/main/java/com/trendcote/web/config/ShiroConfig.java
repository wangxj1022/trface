package com.trendcote.web.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.trendcote.web.filter.KickoutSessionFilter;
import com.trendcote.web.shiro.MyShiroRealm;
import com.trendcote.web.shiro.RetryLimitHashedCredentialsMatcher;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.servlet.Filter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置
 */
@Configuration
@EnableTransactionManagement
public class ShiroConfig {
	private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

	@Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(org.apache.shiro.mgt.SecurityManager securityManager) {
		logger.debug("shiro拦截器工厂类注入开始");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		HashMap<String,Filter> hashMap=new HashMap<String,Filter>();
		hashMap.put("kickout",kickoutSessionFilter());
		shiroFilterFactoryBean.setFilters(hashMap);
		// 指定要求登录时的链接
		shiroFilterFactoryBean.setLoginUrl("/toLogin");
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/index");
		// 未授权时跳转的界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/error");
		// filterChainDefinitions拦截器=map必须用：LinkedHashMap，因为它必须保证有序
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 配置退出过滤器,具体的退出代码Shiro已经实现
		filterChainDefinitionMap.put("/logout", "logout");

		//配置记住我或认证通过可以访问的地址
		filterChainDefinitionMap.put("/user/userList", "user");
		filterChainDefinitionMap.put("/", "user");
//		// 配置不会被拦截的链接 从上向下顺序判断<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问【放行】-->
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/user/login", "anon");
		filterChainDefinitionMap.put("/kaptcha", "anon");
		filterChainDefinitionMap.put("/style/*", "anon");
		filterChainDefinitionMap.put("/style/*/*", "anon");
		filterChainDefinitionMap.put("/style/*/*/*", "anon");
		filterChainDefinitionMap.put("/jslib/*", "anon");
		filterChainDefinitionMap.put("/jslib/js/*", "anon");
		filterChainDefinitionMap.put("/jslib/js/*/*", "anon");
		filterChainDefinitionMap.put("/jslib/js/*/*/*", "anon");
		filterChainDefinitionMap.put("/jslib/jquery-easyui-1.6.2/*", "anon");
		filterChainDefinitionMap.put("/jslib/jquery-easyui-1.6.2/*/*", "anon");
		filterChainDefinitionMap.put("/jslib/jquery-easyui-1.6.2/*/*/*", "anon");
		filterChainDefinitionMap.put("/jslib/jquery-easyui-1.6.2/*/*/*/*", "anon");
		filterChainDefinitionMap.put("/jslib/jquery-easyui-1.6.2/*/*/*/*/*", "anon");
		filterChainDefinitionMap.put("/jslib/kindeditor/*", "anon");
		filterChainDefinitionMap.put("/jslib/kindeditor/*/*", "anon");
		filterChainDefinitionMap.put("/jslib/kindeditor/*/*/*", "anon");
		filterChainDefinitionMap.put("/jslib/kindeditor/*/*/*/*", "anon");
		filterChainDefinitionMap.put("/**", "kickout,authc");
		filterChainDefinitionMap.put("/*/*", "authc");
		filterChainDefinitionMap.put("/*/*/*", "authc");
		filterChainDefinitionMap.put("/*/*/*/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		logger.debug("shiro拦截器工厂类注入成功");
		return shiroFilterFactoryBean;
	}

	/**
	 * shiro安全管理器设置realm认证和ehcache缓存管理
	 * @return
	 */
	@Bean
    public org.apache.shiro.mgt.SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealm(shiroRealm());
		// //注入ehcache缓存管理器;
		securityManager.setCacheManager(ehCacheManager());
		// //注入session管理器;
		securityManager.setSessionManager(sessionManager());
		//注入Cookie记住我管理器
		securityManager.setRememberMeManager(rememberMeManager());
		return securityManager;
	}

	/**
	 * 身份认证realm; (账号密码校验；权限等)
	 *
	 * @return
	 */
	@Bean
    public MyShiroRealm shiroRealm() {
		MyShiroRealm shiroRealm = new MyShiroRealm();
		//使用自定义的CredentialsMatcher进行密码校验和输错次数限制
		shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return shiroRealm;
	}
	@Bean
	public EhCacheManager ehCacheManager() {
		logger.debug("shiro整合ehcache缓存开始");
		EhCacheManager ehcache = new EhCacheManager();
		CacheManager cacheManager = CacheManager.getCacheManager("shiro");
		  if(cacheManager == null){
		  try {
		  	    cacheManager = CacheManager.create(ResourceUtils.getInputStreamForPath("classpath:config/ehcache.xml"));
			  } catch (CacheException | IOException e) {
					e.printStackTrace();
			  }
		  }
		  ehcache.setCacheManager(cacheManager);
		return ehcache;
	}

	/**
	 * 设置记住我cookie过期时间
	 * @return
	 */
	/*@Bean
	public SimpleCookie remeberMeCookie(){
		SimpleCookie scookie=new SimpleCookie("rememberMe");
		//记住我cookie生效时间1小时 ,单位秒
		scookie.setMaxAge(216000);
		return scookie;
	}*/

	/**
	 * 配置cookie记住我管理器   解决登录报错‘dofinal’ 问题
	 * @return
	 */
	@Bean
	public CookieRememberMeManager rememberMeManager(){
		CookieRememberMeManager cookieRememberMeManager=new CookieRememberMeManager();
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		simpleCookie.setMaxAge(259200000);
		cookieRememberMeManager.setCookie(simpleCookie);
		cookieRememberMeManager.setCipherKey(Base64.decode("6ZmI6I2j5Y+R5aSn5ZOlAA=="));
		//cookieRememberMeManager.setCookie(remeberMeCookie());
		return cookieRememberMeManager;
	}

	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher(ehCacheManager());
		hashedCredentialsMatcher.setHashAlgorithmName("md5");
		hashedCredentialsMatcher.setHashIterations(1);
		return hashedCredentialsMatcher;
	}
	@Bean
    public ShiroDialect shiroDialect(){  
		return new ShiroDialect();
    }

	@Bean
	public EnterpriseCacheSessionDAO enterCacheSessionDAO() {
		EnterpriseCacheSessionDAO enterCacheSessionDAO = new EnterpriseCacheSessionDAO();
		enterCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
		return enterCacheSessionDAO;
	}

	@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionDAO(enterCacheSessionDAO());
		sessionManager.setSessionIdCookie(sessionIdCookie());
		return sessionManager;
	}
	@Bean
	public SimpleCookie sessionIdCookie() {
		SimpleCookie simpleCookie = new SimpleCookie();
		simpleCookie.setHttpOnly(true);
		simpleCookie.setName("SHRIOSESSIONID");
		//单位秒
		simpleCookie.setMaxAge(86400);
		return simpleCookie;
	}
	public KickoutSessionFilter kickoutSessionFilter(){
		KickoutSessionFilter kickoutSessionFilter = new KickoutSessionFilter();
		kickoutSessionFilter.setCacheManager(ehCacheManager());
		kickoutSessionFilter.setSessionManager(sessionManager());
		kickoutSessionFilter.setKickoutAfter(false);
		kickoutSessionFilter.setMaxSession(1);
		kickoutSessionFilter.setKickoutUrl("/toLogin?kickout=1");
		return kickoutSessionFilter;
	}
	@Bean
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
		return authorizationAttributeSourceAdvisor;
	}

}

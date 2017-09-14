package com.focus3d.pano.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.focus3d.pano.login.dao.SessionDB;
import com.focus3d.pano.model.PanoMemLoginModel;
import com.focustech.cief.cop.ws.auth.Auth;
import com.focustech.cief.cop.ws.auth.AuthHolder;
import com.focustech.common.utils.EncryptUtil;
import com.focustech.common.utils.HttpUtil;
import com.focustech.common.utils.StringUtils;
import com.focustech.common.utils.TCUtil;
/**
 *
 * *
 * @author lihaijun
 *
 */
public class LoginFilter extends AbstractFilter {
	
	private static final Logger log = LoggerFactory.getLogger(LoginFilter.class);
	public static final String SESSION_KEY = "login";
	public static final String SESSION_GOTO = "goto";
	public static final String LOGIN_PAGE_NAME = "member/login/nomal";
	//public static final String WECHAT_SERVER_AUTH = "http%3A%2F%2F" + "app-wx.3d-focus.com%2Fwechat%2Fpage-auth";
	public static final String WECHAT_SERVER_AUTH = "http%3A%2F%2F" + "wx.joy-homeplus.com" + "%2Fwechat%2Fpage-auth";
	//动态链接
	protected static final String[] DYNAMIC_RESOURCES = {
		"/index.html"
		,"/monitor.html"
		,"/crossdomain.xml"
		,"/favicon.ico"
		,"/cross-domain-policy.xml"
		,"/cross-domain-policy.dtd"
		, "/common/*"
		,"/index"
		, "/qrcode/*"
		,"/sms/send"
		, "/captchas/*"
		, "/" + LOGIN_PAGE_NAME
		, "/user/logout"
		, "/user/register*"
		, "/user/login*"
		, "/user/password-find"
		, "/f/*"
		, "/fp/*"
		, "/out/*"
		,"/member/login/*"
		,"/order/lianpaynotify"
		,"/order/lianpayreturn"
		,"/order/wxpaynotify"
		,"/order/orderspage"
		,"/order/pay/complete"
		,"/perspective/*"
	};
	public static Auth auth = new Auth();
	
	@Value("${rpc.fs.domain}")
	private String fileServerDomain;
	@Value("${pano.domain}")
	private String siteDomain;
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fc) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		int resourceType = TCUtil.iv(req.getAttribute("resourceType"));
		if(resourceType == 1){	
			fc.doFilter(req, resp);
			return;
		}
		HttpSession session = request.getSession();
		String sessionId = session.getId();
		String servletPath = request.getServletPath();
		Object sessionObj = session.getAttribute(SESSION_KEY);
		if(sessionObj == null){
			if(SessionDB.get(sessionId) != null){
				request.getSession().setAttribute(SESSION_KEY, SessionDB.get(sessionId));
				sessionObj = session.getAttribute(SESSION_KEY);
			}
		}
		boolean isLogin = sessionObj != null;
		
		boolean isAuthed = true;
		
		if(isWeixinBrowser(request) && SessionDB.get(sessionId) == null){
			StringBuffer urlParameterUrl = getRequestParameter(request);
			String callbackUrl = servletPath + urlParameterUrl.toString();
			log.info("微信登录后跳转链接：" + callbackUrl);
			log.info("跳转到微信授权登录");
			SessionDB.addTempSession(sessionId, callbackUrl);
			response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxed31115f33aab720&redirect_uri=" + WECHAT_SERVER_AUTH + "&response_type=code&scope=snsapi_userinfo&state=proj720ANDloginAND" + sessionId + "AND" + HttpUtil.encodeUrl(siteDomain) + "#wechat_redirect");
			return;
		}
		if(isNotNeedAuthCheckUrl(servletPath, request)){
			if("/home/index".equals(servletPath)){
				//首页会话设置用户信息
				LoginThreadLocal.setLoginInfo(sessionObj);
				PanoMemLoginModel loginInfo = LoginThreadLocal.getLoginInfo();
				if(loginInfo != null){
					req.setAttribute(SESSION_KEY, loginInfo);
				}
			}
		} else {
			if(!isLogin) {
				 if(request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").equals("XMLHttpRequest")) {
					 log.info("ajax 请求未登录，需要登录");
					 response.setHeader("sessionstatus", "timeout"); 
				 } else {
					 log.info("跳转到常规页面登录");
					 response.sendRedirect("/" + LOGIN_PAGE_NAME);
					 session.setAttribute(SESSION_GOTO, servletPath + getRequestParameter(request));
				 }
				 return;
			} else {
				req.setAttribute("usn", EncryptUtil.encode(((PanoMemLoginModel)sessionObj).getUserSn()));
				req.setAttribute("fserver", fileServerDomain);
				isAuthed = isAuthedUrl(servletPath, sessionObj);
			}
		}
		
		if(isLogin){
			String gotoPage = TCUtil.sv(session.getAttribute(SESSION_GOTO));
			if(StringUtils.isNotEmpty(gotoPage)){
				session.removeAttribute(SESSION_GOTO);
				response.sendRedirect(gotoPage);
			}
			LoginThreadLocal.setLoginInfo(sessionObj);
		}
		if(isAuthed){
			AuthHolder.setAuth(auth);
			fc.doFilter(req, resp);
		} else {
			response.setStatus(403);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			response.getOutputStream().print("禁止访问");
		}
	}

	/**
	 * 获取请求参数
	 * *
	 * @param request
	 * @return
	 */
	private StringBuffer getRequestParameter(HttpServletRequest request) {
		Enumeration parameterNames = request.getParameterNames();
		StringBuffer urlParameterUrl = new StringBuffer("?1=1");
		while (parameterNames.hasMoreElements()) {
			Object object = (Object) parameterNames.nextElement();
			String parameterKey = TCUtil.sv(object);
			String parameterValue = request.getParameter(parameterKey);
			urlParameterUrl.append("&" + parameterKey + "=" + parameterValue);
		}
		return urlParameterUrl;
	}
	
	/**
	 * 是否是不需要登录的url
	 * *
	 * @param servletPath
	 * @return
	 */
	public boolean isNotNeedAuthCheckUrl(String servletPath, HttpServletRequest request){
		int resourceType = TCUtil.iv(request.getAttribute("resourceType"));
		boolean flag = (resourceType == 1);
		if(!flag){
			for(String resource : DYNAMIC_RESOURCES){
				if(resource.contains("*")){
					Pattern pattern = Pattern.compile("^" + resource.replace("*", ".*"));
					Matcher matcher = pattern.matcher(servletPath);
					if(matcher.matches()){
						flag = true;
						break;
					}
				} else if(servletPath.equalsIgnoreCase(resource)){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	/**
	 * 是否已经授权了的url
	 * *
	 * @param servletPath
	 * @return
	 */
	public boolean isAuthedUrl(String servletPath, Object sessinObj){/*
		boolean isPass = false;
		if(sessinObj != null) {
			//登录用户验证功能菜单权限
			if(sessinObj instanceof PanoLoginModel){
				if(servletPath.equals("/index")){
					isPass = true;
				} else {
					PanoLoginModel loginInfo = (PanoLoginModel)sessinObj;
					Long userId = loginInfo.getUserSn();
					if(userId != null){
						List<PanoUserRoleModel> roles = userRoleService.listByUserId(userId);
						if(ListUtils.isNotEmpty(roles)){
							//后续添加到缓存
							List<PanoRoleResourceModel> resources = roleResourceService.listByRoleId(roles.get(0).getRoleSn());
							for (PanoRoleResourceModel agentRoleResource : resources) {
								PanoResourceModel resource = resourceService.getBySn(agentRoleResource.getResourceSn());
								if(servletPath.startsWith(resource.getResourceInterface())){
									isPass = true;
									break;
								}
							}
						}
					}
				
				}
			}
		}
		return isPass;
	*/
		return true;
	}

	@Override
	public void init(FilterConfig fcg) throws ServletException {
		auth.setUsername("system");
		auth.setUserSn(-1L);
		auth.setFromSubSystem("focus3d_agent");
	}
	/**
	 * 
	 * *
	 * @param req
	 * @return
	 */
	public boolean isWeixinBrowser(HttpServletRequest req){
		String ua = TCUtil.sv(req.getHeader("User-Agent")).toLowerCase();
		return ua.contains("micromessenger");
	}
}

package com.focus3d.pano.member.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focus3d.pano.common.controller.BaseController;
import com.focus3d.pano.filter.LoginFilter;
import com.focus3d.pano.login.constant.LoginTypeEnum;
import com.focus3d.pano.login.dao.SessionDB;
import com.focus3d.pano.login.service.PanoMemLoginService;
import com.focus3d.pano.model.PanoMemLoginModel;
import com.focus3d.pano.model.PanoValidateModel;
import com.focus3d.pano.sms.service.SmsValidateService;
import com.focustech.common.utils.StringUtils;
import com.focustech.common.utils.TCUtil;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping(value = "/member/login")
public class MemberLoginController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(MemberLoginController.class);
	@Autowired
	private SmsValidateService smsValidateService;
	@Autowired
	private PanoMemLoginService<PanoMemLoginModel> memLoginService;
	/**
	 * 常规登录
	 * *
	 * @return
	 */
	@RequestMapping(value = "nomal", method = RequestMethod.GET)
	public String nomalLogin() {
		return "/member/login";
	}
	/**
	 * 常规登录
	 * *
	 * @param memLoginModel
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "nomal", method = RequestMethod.POST)
	public String nomalLogin(PanoMemLoginModel memLoginModel, ModelMap modelMap, HttpServletRequest request){
		String mobile = memLoginModel.getLoginName();
		String smsCode = memLoginModel.getSmsCode();
		if(StringUtils.isNotEmpty(mobile) && StringUtils.isNotEmpty(smsCode)){
			PanoValidateModel messageValidate = smsValidateService.selectByMobilePhone(mobile, smsCode);
			if(messageValidate != null && messageValidate.getStatus() == 1){
				memLoginService.insertOrUpdate(memLoginModel, LoginTypeEnum.MOBILE);
				smsValidateService.setStatus(messageValidate, 0);
				addLoginToSession(memLoginModel, request);;
			}
		}
		return redirect("/member/center");
	}
	
	/**
	 * 添加session
	 * *
	 * @param userinfo
	 * @throws IOException 
	 */
	@RequestMapping(value = "/wx/addsession", method = RequestMethod.POST)
	public void addSession(String sessionId, String userInfo, HttpServletResponse response) throws IOException{
		log.debug(sessionId);
		log.debug(userInfo);
		if(StringUtils.isNotEmpty(sessionId) && StringUtils.isNotEmpty(userInfo)){
			String gotoPage = TCUtil.sv(SessionDB.getTemp(sessionId));
			SessionDB.removeTemp(sessionId);
			//加入session
			JSONObject jo = JSONObject.fromObject(userInfo);
			String openId = jo.getString("openid");
			PanoMemLoginModel loginInfo = memLoginService.getByName(openId, LoginTypeEnum.WX);
			if(loginInfo == null){
				memLoginService.insertOrUpdate(jo, LoginTypeEnum.WX);
			} 
			loginInfo.setGotoPage(gotoPage);
			SessionDB.addSession(sessionId, loginInfo);
			ajaxOutput(response, "ok");
		}
	}
	
	/**
	 * 登录验证
	 * *
	 * @param sessionId 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "wx", method = RequestMethod.GET)
	public String login(String sessionId, String panoId, HttpServletRequest request, HttpServletResponse response) throws IOException{
		PanoMemLoginModel loginInfo = SessionDB.get(TCUtil.sv(sessionId));
		//手机浏览器sessionId
		String mobileSessionId = request.getSession().getId();
		if(loginInfo != null){
			if(!mobileSessionId.equals(sessionId)){
				//pc扫码登录的
				//SessionDB.addSession(mobileSessionId, loginInfo);
				//SessionDB.remove(sessionId);
			}
		}
		loginInfo = SessionDB.get(mobileSessionId);
		String msg = "";
		String view = "";
		if(loginInfo != null){
			msg = "登录成功";
			addLoginToSession(loginInfo, request);
			view = redirect(getSiteDomain() + loginInfo.getGotoPage());
		} else {
			removeLoginFromSession(request);
			msg = "登录失败";
		}
		log.info(msg);
		return view;
	}
	
	/**
	 * 
	 * *
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "exist", method = RequestMethod.GET)
	public String exist(ModelMap modelMap, HttpServletRequest request){
		removeLoginFromSession(request);
		return redirect("/index");
	}
	/**
	 * 
	 * *
	 * @param memLoginModel
	 * @param request
	 */
	private void addLoginToSession(PanoMemLoginModel memLoginModel, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(LoginFilter.SESSION_KEY);
		session.setAttribute(LoginFilter.SESSION_KEY, memLoginModel);
	}
	/**
	 * 
	 * *
	 * @param request
	 */
	private void removeLoginFromSession( HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(LoginFilter.SESSION_KEY);
		SessionDB.remove(session.getId());
	}
}

package com.focus3d.pano.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.focus3d.pano.common.controller.BaseController;
import com.focus3d.pano.filter.LoginThreadLocal;
import com.focus3d.pano.model.PanoMemLoginModel;
import com.focus3d.pano.model.PanoMemUserModel;
import com.focus3d.pano.user.service.PanoMemUserService;

/**
 * 
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping(value = "/member/center")
public class MemberCenterController extends BaseController{
	@Autowired
	private PanoMemUserService<PanoMemUserModel> memUserService;
	/**
	 * 
	 * *
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String tomy(HttpServletRequest request,HttpSession session) {
		PanoMemLoginModel loginInfo = LoginThreadLocal.getLoginInfo();
		PanoMemUserModel user = memUserService.getBySn(loginInfo.getUserSn());
		request.setAttribute("memuser", user);
		return "/member/center";
	}
	
	/**
	 * 进入实名认证
	 */
	@RequestMapping("/tocer")
	public String tocer(HttpServletRequest request) {
		Long userSn = LoginThreadLocal.getLoginInfo().getUserSn();
		PanoMemUserModel memuser = memUserService.getBySn(userSn);
		request.setAttribute("memuser", memuser);
		return "/userside/cer";
	}

	/**
	 * 修改实名认证身份信息
	 */
	@RequestMapping("/upCert")
	public String upCert(HttpServletRequest request, @RequestParam String SN,
			@RequestParam String NAME, @RequestParam String SEX,
			@RequestParam String CERT_NO) {
		long userSn = LoginThreadLocal.getLoginInfo().getUserSn();
		int USER_SEX = Integer.parseInt(SEX);
		PanoMemUserModel memUser = memUserService.getBySn(userSn);
		memUser.setSn(userSn);
		memUser.setName(NAME);
		memUser.setSex(USER_SEX);
		memUser.setCertNo(CERT_NO);
		memUserService.update(memUser);
		return redirect("/member/center");
	}
}

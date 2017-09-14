package com.focus3d.pano.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.config.BaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.focus3d.pano.common.controller.BaseController;
import com.focus3d.pano.filter.LoginThreadLocal;
import com.focus3d.pano.member.service.PanoUserReceiveAddressService;
import com.focus3d.pano.model.PanoUserReceiveAddressModel;
import com.focustech.common.utils.TCUtil;

/**
 * 
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping(value = "/address")
public class PanoUserReceiveAddressController extends BaseController{
	@Autowired
	private PanoUserReceiveAddressService<PanoUserReceiveAddressModel> receiveAddressService;
	
	/**
	 * 选择收货地址
	 * *
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public String choose(String packageSns, ModelMap modelMap) {
		Long userSn = LoginThreadLocal.getLoginInfo().getUserSn();
		List<PanoUserReceiveAddressModel> address = receiveAddressService.listByUser(userSn);
		modelMap.put("addressList", address);
		modelMap.put("packageSns", packageSns);
		return "/member/address/check";
	}
	
	/**
	 * 进入到收货地址2
	 */
	@RequestMapping("/toaddress2")
	public String toaddress2(HttpServletRequest request) {
		Long userSn = LoginThreadLocal.getLoginInfo().getUserSn();
		List<PanoUserReceiveAddressModel> address = receiveAddressService.listByUser(userSn);
		request.setAttribute("addressList", address);
		return "/userside/address";
	}

}

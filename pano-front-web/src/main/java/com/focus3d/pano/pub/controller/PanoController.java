package com.focus3d.pano.pub.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.focustech.common.utils.HttpUtil;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping(value = "/fp")
public class PanoController extends AbstractPanoController {
	/**
	 * 
	 * *
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/720")
	public String to720(HttpServletRequest request,HttpSession session, ModelMap modelMap){
		String styleId = HttpUtil.sv(request, "styleId");
		String checkProjectSn = HttpUtil.sv(request, "projectId"); 
		modelMap.put("projectId", checkProjectSn);
		modelMap.put("styleId", styleId);
		return "/pub/720";
	}
}

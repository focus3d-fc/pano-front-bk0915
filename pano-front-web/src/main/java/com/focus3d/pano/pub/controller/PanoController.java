package com.focus3d.pano.pub.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.focus3d.pano.filter.LoginThreadLocal;
import com.focus3d.pano.model.PanoOrderShopcartModel;
import com.focus3d.pano.model.PanoProjectModel;
import com.focus3d.pano.project.service.PanoProjectService;
import com.focus3d.pano.shopcart.service.PanoOrderShopCartService;
import com.focustech.common.utils.EncryptUtil;
import com.focustech.common.utils.HttpUtil;
import com.focustech.common.utils.TCUtil;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping(value = "/fp")
public class PanoController extends AbstractPanoController {
	@Autowired
	private PanoOrderShopCartService<PanoOrderShopcartModel> orderShopCartService;
	@Autowired
	private PanoProjectService<PanoProjectModel> projectService;
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
	/**
	 * 
	 * *
	 * @throws Exception 
	 */
	@RequestMapping(value = "/fillshopcart")
	public void addHousePackageToShopcart(String projectSn, String houseId, String styleId, HttpServletResponse response) throws Exception {
		if(LoginThreadLocal.getLoginInfo() != null){
			long userSn = LoginThreadLocal.getLoginInfo().getUserSn();
			long currentProjectSn = EncryptUtil.decode(projectSn);
			PanoProjectModel project = projectService.getBySn(currentProjectSn);
			long currentStyleSn = TCUtil.lv(styleId);
			long currentHouseSn = EncryptUtil.decode(houseId);
			if(project != null && currentHouseSn > 0 && currentStyleSn > 0){
				//复制楼盘套餐数据到购车车
				orderShopCartService.copyFromHousePackage(userSn, currentProjectSn, currentHouseSn, currentStyleSn);
			}
		}
		JSONObject jo = new JSONObject();
		ajaxOutput(response, jo.toString());
	}
}

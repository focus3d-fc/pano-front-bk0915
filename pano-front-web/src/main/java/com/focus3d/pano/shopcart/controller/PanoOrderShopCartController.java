package com.focus3d.pano.shopcart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focus3d.pano.filter.LoginThreadLocal;
import com.focus3d.pano.model.PanoOrderShopcartModel;
import com.focus3d.pano.model.PanoProjectHouseModel;
import com.focus3d.pano.model.PanoProjectHousePackageModel;
import com.focus3d.pano.model.PanoProjectHouseStyleModel;
import com.focus3d.pano.model.PanoProjectModel;
import com.focus3d.pano.model.PanoProjectPackageTypeModel;
import com.focus3d.pano.model.PanoProjectStyleModel;
import com.focus3d.pano.project.service.PanoProjectHousePackageService;
import com.focus3d.pano.project.service.PanoProjectHouseStyleService;
import com.focus3d.pano.project.service.PanoProjectPackageTypeService;
import com.focus3d.pano.project.service.PanoProjectService;
import com.focus3d.pano.pub.controller.AbstractPanoController;
import com.focus3d.pano.shopcart.service.PanoOrderShopCartService;
import com.focustech.common.utils.EncryptUtil;
import com.focustech.common.utils.HttpUtil;
import com.focustech.common.utils.StringUtils;
import com.focustech.common.utils.TCUtil;

/**
 * 购物车 *
 * 
 * @author lihaijun
 * 
 */
@Controller
@RequestMapping(value = "/shopcart")
public class PanoOrderShopCartController extends AbstractPanoController {
	@Autowired
	private PanoOrderShopCartService<PanoOrderShopcartModel> orderShopCartService;
	@Autowired
	private PanoProjectHousePackageService<PanoProjectHousePackageModel> housePackageService;
	@Autowired
	private PanoProjectPackageTypeService<PanoProjectPackageTypeModel> packageTypeService;
	@Autowired
	private PanoProjectHouseStyleService<PanoProjectHouseStyleModel> houseStyleService;
	@Autowired
	private PanoProjectService<PanoProjectModel> projectService;

	/**
	 * 购物车列表 *
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(String checkProjectSn, String houseId, String styleId, ModelMap modelMap, HttpServletRequest request) throws Exception {
		Long userSn = LoginThreadLocal.getLoginInfo().getUserSn();
		long currentProjectSn = EncryptUtil.decode(checkProjectSn);
		PanoProjectModel project = projectService.getBySn(currentProjectSn);
		long currentStyleSn = TCUtil.lv(styleId);
		long currentHouseSn = EncryptUtil.decode(houseId);
		if(project != null && currentHouseSn > 0 && currentStyleSn > 0){
			//复制楼盘套餐数据到购车车
			orderShopCartService.copyFromHousePackage(userSn, currentProjectSn, currentHouseSn, currentStyleSn);
			//透视图状态信息
			setPerspectiveInfo(modelMap, request);
			//分组显示
			Map<String, PanoOrderShopcartListVo> groupMap = new HashMap<String, PanoOrderShopcartListVo>();
			List<PanoOrderShopcartModel> shopcartList = orderShopCartService.listByUser(userSn);
			for (PanoOrderShopcartModel shopcart : shopcartList) {
				Long housePackageSn = shopcart.getHousePackageSn();
				PanoProjectHousePackageModel housePackage = housePackageService.getDetail(housePackageSn);
				if (housePackage != null) {
					shopcart.setHousePackage(housePackage);
				}
				//分组
				PanoProjectHouseModel house = housePackage.getHouse();
				PanoProjectStyleModel style = housePackage.getStyle();
				Long houseSn = house.getSn();
				Long styleSn = style.getSn();
				String key = houseSn + "_" + styleSn;
				if(groupMap.containsKey(key)){
					groupMap.get(key).getShopcarts().add(shopcart);
					groupMap.get(key).setStyle(style);
				} else {
					PanoOrderShopcartListVo v = new PanoOrderShopcartListVo();
					v.setHouse(house);
					v.setStyle(style);
					v.getShopcarts().add(shopcart);
					groupMap.put(key, v);
				}
				
			}
			modelMap.put("styleId", styleId);
			modelMap.put("project", project);
			modelMap.put("houseGroupMap", groupMap);
		}
		return "/member/shopcart/list";
	}
	
	/**
	 * 
	 * *
	 * @param modelMap
	 * @param request
	 */
	private void setPerspectiveInfo(ModelMap modelMap, HttpServletRequest request) {
		//Long userSn = LoginThreadLocal.getLoginInfo().getUserSn();
		String packageTypeSn = HttpUtil.sv(request, "packageTypeSn");
		String houseStyleSn = HttpUtil.sv(request, "houseStyleSn");
		if(StringUtils.isNotEmpty(packageTypeSn)){
			PanoProjectPackageTypeModel packageTypeModel = packageTypeService.getBySn(TCUtil.lv(packageTypeSn));
			Long housePackageSn = packageTypeModel.getHousePackageSn();
			//PanoOrderShopcartModel shopcart = orderShopCartService.getUserShopcartPackage(userSn, housePackageSn);
			//PanoOrderShopcartDetailModel shopcartDetail = orderShopCartDetailService.getByAttribute(shopcart.getSn(), TCUtil.lv(packageTypeSn));
			//Long oldProductSn = shopcartDetail.getPackageProductSn();
			//更新购物车产品
			/*if(shopcartDetail != null && !TCUtil.sv(oldProductSn).equals(productSn)){
				shopcartDetail.setPackageProductSn(TCUtil.lv(productSn));
				orderShopCartDetailService.update(shopcartDetail);
			}*/
			PanoProjectHouseStyleModel houseStyle = houseStyleService.getBySn(TCUtil.lv(houseStyleSn));
			modelMap.put("styleId", houseStyle.getStyleSn());
			//购物车状态记录,返回到购物车时设置勾选等状态信息
			JSONObject rememberStatus = new JSONObject();
			rememberStatus.put("housePackageSn", housePackageSn);
			rememberStatus.put("packageTypeSn", packageTypeSn);
			modelMap.put("rememberStatus", rememberStatus);
		}
	}

	/**
	 * 添加套餐到购物车 *
	 * 
	 * @param packageEncryptSn
	 * @throws Exception
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public void add(String packageEncryptSn, HttpServletResponse response) throws Exception {
		if (StringUtils.isNotEmpty(packageEncryptSn)) {
			Long packageSn = EncryptUtil.decode(packageEncryptSn);
			int status = orderShopCartService.addOrDelete(packageSn);
			JSONObject jo = new JSONObject();
			jo.put("status", status);
			ajaxOutput(response, jo.toString());
		}
	}
}

package com.focus3d.pano.pub.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.focus3d.pano.common.controller.BaseController;
import com.focus3d.pano.model.panoSkin;
import com.focus3d.pano.usersside.service.UsersSideService;

public class AbstractPanoController extends BaseController {
	@Autowired
	private UsersSideService usersSideService;

	@ModelAttribute
	protected void setBottomIcon(HttpServletRequest request, ModelMap modelMap) {
		//获取导航图
		List<panoSkin> panoSkinList = usersSideService.list_selectPanoSkinList();
		String skinName="";
		long packageImg=0;
		long spaceImg = 0;
		long houseImg = 0;
		long homeImg = 0;
		for(int i = 0; i < panoSkinList.size(); i ++){
			panoSkin panoSkin = panoSkinList.get(i);
			skinName = panoSkin.getName();
			if(skinName.equals("套餐")){
				packageImg = panoSkin.getImg_sn();
			}else if(skinName.equals("房间")){
				spaceImg = panoSkin.getImg_sn();
			}else if(skinName.equals("户型")){
				houseImg = panoSkin.getImg_sn();
			}else if(skinName.equals("首页")){
				homeImg = panoSkin.getImg_sn();
			}
		}
		modelMap.put("img_tc",packageImg);
		modelMap.put("img_space",spaceImg);
		modelMap.put("img_house",houseImg);
		modelMap.put("img_home",homeImg);
	}
}

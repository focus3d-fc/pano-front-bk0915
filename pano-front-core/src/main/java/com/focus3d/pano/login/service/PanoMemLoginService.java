package com.focus3d.pano.login.service;

import net.sf.json.JSONObject;

import com.focus3d.pano.common.service.CommonService;
import com.focus3d.pano.login.constant.LoginTypeEnum;
import com.focus3d.pano.model.PanoMemLoginModel;
/**
 * 
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface PanoMemLoginService<T> extends CommonService<T> {

	PanoMemLoginModel getByName(String openId, LoginTypeEnum wx);

	void insertOrUpdate(PanoMemLoginModel memLoginModel, LoginTypeEnum type);
	
	void insertOrUpdate(JSONObject data, LoginTypeEnum type);
}

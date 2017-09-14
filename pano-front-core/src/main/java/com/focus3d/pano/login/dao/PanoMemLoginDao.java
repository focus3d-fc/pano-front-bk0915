package com.focus3d.pano.login.dao;

import org.springframework.stereotype.Repository;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.login.constant.LoginTypeEnum;
import com.focus3d.pano.model.PanoMemLoginModel;
import com.focus3d.pano.model.ibator.PanoMemLoginCriteria;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Repository
public class PanoMemLoginDao extends CommonDao<PanoMemLoginModel> {
	/**
	 * 
	 * *
	 * @param loginName
	 * @param type 
	 * @return
	 */
	public PanoMemLoginModel getByLoginName(String loginName, LoginTypeEnum type){
		PanoMemLoginCriteria criteria = new PanoMemLoginCriteria();
		criteria.createCriteria().andLoginNameEqualTo(loginName).andTypeEqualTo(type.getType());
		return selectFirstByExample(criteria, PanoMemLoginModel.class);
	}
}

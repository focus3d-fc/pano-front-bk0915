package com.focus3d.pano.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.common.service.impl.CommonServiceImpl;
import com.focus3d.pano.model.PanoMemUserModel;
import com.focus3d.pano.user.dao.PanoMemUserDao;
import com.focus3d.pano.user.service.PanoMemUserService;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Service
@Transactional
public class PanoMemUserServiceImpl extends CommonServiceImpl<PanoMemUserModel> implements PanoMemUserService<PanoMemUserModel> {
	@Autowired
	private PanoMemUserDao memUserDao;
	@Override
	public CommonDao<PanoMemUserModel> getDao() {
		return memUserDao;
	}
	
}

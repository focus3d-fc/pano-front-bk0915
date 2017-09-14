package com.focus3d.pano.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.common.service.impl.CommonServiceImpl;
import com.focus3d.pano.member.dao.PanoUserReceiveAddressDao;
import com.focus3d.pano.member.service.PanoUserReceiveAddressService;
import com.focus3d.pano.model.PanoMemUserModel;
import com.focus3d.pano.model.PanoUserReceiveAddressModel;
import com.focus3d.pano.user.service.PanoMemUserService;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Service
@Transactional
public class PanoUserReceiveAddressServiceImpl extends CommonServiceImpl<PanoUserReceiveAddressModel> implements PanoUserReceiveAddressService<PanoUserReceiveAddressModel> {
	@Autowired
	private PanoUserReceiveAddressDao receiveAddressDao;
	@Autowired
	private PanoMemUserService<PanoMemUserModel> memUserService;
	
	@Override
	public CommonDao<PanoUserReceiveAddressModel> getDao() {
		return receiveAddressDao;
	}

	@Override
	public List<PanoUserReceiveAddressModel> listByUser(long userSn) {
		List<PanoUserReceiveAddressModel> listByUser = receiveAddressDao.listByUser(userSn);
		for (PanoUserReceiveAddressModel address : listByUser) {
			PanoMemUserModel user = memUserService.getBySn(address.getUserSn());
			address.setUser(user);
		}
		return listByUser;
	}
}

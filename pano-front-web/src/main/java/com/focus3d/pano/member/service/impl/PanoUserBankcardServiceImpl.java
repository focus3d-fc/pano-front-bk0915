package com.focus3d.pano.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.common.service.impl.CommonServiceImpl;
import com.focus3d.pano.member.dao.PanoUserBankcardDao;
import com.focus3d.pano.member.service.PanoUserBankcardService;
import com.focus3d.pano.model.PanoUserBankcardModel;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Service
@Transactional
public class PanoUserBankcardServiceImpl extends CommonServiceImpl<PanoUserBankcardModel> implements PanoUserBankcardService<PanoUserBankcardModel> {
	@Autowired
	private PanoUserBankcardDao userBankcardDao;
	@Override
	public CommonDao<PanoUserBankcardModel> getDao() {
		return userBankcardDao;
	}
	@Override
	public List<PanoUserBankcardModel> listByUser(long userSn) {
		return userBankcardDao.listByUser(userSn);
	}
	@Override
	public PanoUserBankcardModel getByCardNo(long userSn, String cardNo) {
		return userBankcardDao.getByCardNo(userSn, cardNo);
	}

}

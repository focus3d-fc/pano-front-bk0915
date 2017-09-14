package com.focus3d.pano.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.common.service.impl.CommonServiceImpl;
import com.focus3d.pano.model.PanoOrderPackageDetailModel;
import com.focus3d.pano.order.dao.PanoOrderPackageDetailDao;
import com.focus3d.pano.order.service.PanoOrderPackageDetailService;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Service
@Transactional
public class PanoOrderPackageDetailServiceImpl extends CommonServiceImpl<PanoOrderPackageDetailModel> implements PanoOrderPackageDetailService<PanoOrderPackageDetailModel> {
	@Autowired
	private PanoOrderPackageDetailDao orderPackageDetailDao;
	@Override
	public CommonDao<PanoOrderPackageDetailModel> getDao() {
		return orderPackageDetailDao;
	}

}

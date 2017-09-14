package com.focus3d.pano.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.common.service.impl.CommonServiceImpl;
import com.focus3d.pano.model.PanoOrderPackageModel;
import com.focus3d.pano.order.dao.PanoOrderPackageDao;
import com.focus3d.pano.order.service.PanoOrderPackageService;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Service
@Transactional
public class PanoOrderPackageServiceImpl extends CommonServiceImpl<PanoOrderPackageModel> implements PanoOrderPackageService<PanoOrderPackageModel> {
	@Autowired
	private PanoOrderPackageDao orderPackageDao;
	@Override
	public CommonDao<PanoOrderPackageModel> getDao() {
		return orderPackageDao;
	}

}

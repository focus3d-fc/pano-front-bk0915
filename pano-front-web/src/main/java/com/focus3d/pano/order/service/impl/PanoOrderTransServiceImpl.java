package com.focus3d.pano.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.common.service.impl.CommonServiceImpl;
import com.focus3d.pano.model.PanoOrderTransModel;
import com.focus3d.pano.order.dao.PanoOrderTransDao;
import com.focus3d.pano.order.service.PanoOrderTransService;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Service
@Transactional
public class PanoOrderTransServiceImpl extends CommonServiceImpl<PanoOrderTransModel> implements PanoOrderTransService<PanoOrderTransModel> {
	@Autowired
	private PanoOrderTransDao orderTransDao;
	@Override
	public CommonDao<PanoOrderTransModel> getDao() {
		return orderTransDao;
	}

}

package com.focus3d.pano.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.common.service.impl.CommonServiceImpl;
import com.focus3d.pano.model.PanoProductModel;
import com.focus3d.pano.product.dao.PanoProductDao;
import com.focus3d.pano.product.service.PanoProductService;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Service
public class PanoProductServiceImpl extends CommonServiceImpl<PanoProductModel> implements PanoProductService<PanoProductModel> {
	@Autowired
	private PanoProductDao productDao;
	@Override
	public CommonDao<PanoProductModel> getDao() {
		return productDao;
	}
}

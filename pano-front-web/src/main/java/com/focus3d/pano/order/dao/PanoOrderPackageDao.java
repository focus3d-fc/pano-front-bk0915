package com.focus3d.pano.order.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.model.PanoOrderPackageModel;
import com.focus3d.pano.model.ibator.PanoOrderPackageCriteria;

/**
 * 
 * *
 * 
 * @author lihaijun
 * 
 */
@Repository
public class PanoOrderPackageDao extends CommonDao<PanoOrderPackageModel> {

	public List<PanoOrderPackageModel> listByOrder(Long orderSn) {
		PanoOrderPackageCriteria criteria = new PanoOrderPackageCriteria();
		criteria.createCriteria().andOrderSnEqualTo(orderSn);
		return selectByCriteria(criteria, PanoOrderPackageModel.class);
	}
}

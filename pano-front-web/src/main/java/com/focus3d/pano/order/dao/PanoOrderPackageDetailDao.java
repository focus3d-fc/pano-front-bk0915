package com.focus3d.pano.order.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.model.PanoOrderPackageDetailModel;
import com.focus3d.pano.model.ibator.PanoOrderPackageDetailCriteria;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Repository
public class PanoOrderPackageDetailDao extends CommonDao<PanoOrderPackageDetailModel> {
	/**
	 * 订单内套餐明细列表
	 * *
	 * @param housePackageSn 订单套餐sn
	 * @return
	 */
	public List<PanoOrderPackageDetailModel> listByOrderPackage(long orderPackageSn){
		PanoOrderPackageDetailCriteria criteria = new PanoOrderPackageDetailCriteria();
		criteria.createCriteria().andOrderPackageSnEqualTo(orderPackageSn);
		return selectByCriteria(criteria, PanoOrderPackageDetailModel.class);
	}
}

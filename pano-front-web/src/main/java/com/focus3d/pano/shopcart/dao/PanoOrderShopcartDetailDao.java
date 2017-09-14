package com.focus3d.pano.shopcart.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.model.PanoOrderShopcartDetailModel;
import com.focus3d.pano.model.ibator.PanoOrderShopcartDetailCriteria;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Repository
public class PanoOrderShopcartDetailDao extends CommonDao<PanoOrderShopcartDetailModel> {
	/**
	 * 购物车明细列表
	 * *
	 * @param shopcartSn 购物车sn
	 * @return
	 */
	public List<PanoOrderShopcartDetailModel> listByShopcart(long shopcartSn){
		PanoOrderShopcartDetailCriteria criteria = new PanoOrderShopcartDetailCriteria();
		criteria.createCriteria().andShopcartSnEqualTo(shopcartSn);
		return selectByCriteria(criteria, PanoOrderShopcartDetailModel.class);
	}
	/**
	 * 
	 * *
	 * @param shopcartSn
	 * @param packageTypeSn
	 * @param packageProductSn
	 * @return
	 */
	public PanoOrderShopcartDetailModel getByAttribute(long shopcartSn, long packageTypeSn) {
		PanoOrderShopcartDetailCriteria criteria = new PanoOrderShopcartDetailCriteria();
		criteria.createCriteria().andShopcartSnEqualTo(shopcartSn).andPackageTypeSnEqualTo(packageTypeSn);
		return selectFirstByExample(criteria, PanoOrderShopcartDetailModel.class);
	}
}

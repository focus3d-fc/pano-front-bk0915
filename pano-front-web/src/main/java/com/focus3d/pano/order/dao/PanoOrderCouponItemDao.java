package com.focus3d.pano.order.dao;

import org.springframework.stereotype.Repository;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.model.PanoOrderCouponItemModel;
import com.focus3d.pano.model.ibator.PanoOrderCouponItemCriteria;

/**
 * 
 * *
 * 
 * @author lihaijun
 * 
 */
@Repository
public class PanoOrderCouponItemDao extends CommonDao<PanoOrderCouponItemModel> {
	/**
	 * 获取优惠券码 *
	 * 
	 * @param codeNum
	 * @return
	 */
	public PanoOrderCouponItemModel getByCode(String codeNum) {
		PanoOrderCouponItemCriteria criteria = new PanoOrderCouponItemCriteria();
		criteria.createCriteria().andCodeNumEqualTo(codeNum);
		return selectFirstByExample(criteria, PanoOrderCouponItemModel.class);
	}

	/**
	 * 获取优惠券码 *
	 * 
	 * @param codeNum
	 * @return
	 */
	public PanoOrderCouponItemModel getByOrderSn(Long orderSn) {
		PanoOrderCouponItemCriteria criteria = new PanoOrderCouponItemCriteria();
		criteria.createCriteria().andOrderSnEqualTo(orderSn);
		return selectFirstByExample(criteria, PanoOrderCouponItemModel.class);
	}
}

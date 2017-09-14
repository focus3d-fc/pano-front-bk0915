package com.focus3d.pano.order.service;

import com.focus3d.pano.common.service.CommonService;
/**
 * 优惠券
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface PanoOrderCouponItemService<T> extends CommonService<T> {
	/**
	 * 查找优惠券
	 * *
	 * @param code 优惠券码
	 * @return
	 */
	T getByCode(String codeNum);
	
	T getByOrderSn(Long orderSn);
}

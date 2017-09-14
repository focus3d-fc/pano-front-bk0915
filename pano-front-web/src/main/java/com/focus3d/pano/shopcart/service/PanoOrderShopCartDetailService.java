package com.focus3d.pano.shopcart.service;

import com.focus3d.pano.common.service.CommonService;
/**
 * 
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface PanoOrderShopCartDetailService<T> extends CommonService<T> {
	/**
	 * 
	 * *
	 * @param packageTypeSn
	 * @param packageProductSn
	 * @return
	 */
	T getByAttribute(long shopcartSn, long packageTypeSn);
}

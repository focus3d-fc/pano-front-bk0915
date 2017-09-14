package com.focus3d.pano.shopcart.service;

import java.util.List;

import com.focus3d.pano.common.service.CommonService;
import com.focus3d.pano.model.PanoOrderShopcartModel;
/**
 * 
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface PanoOrderShopCartService<T> extends CommonService<T> {
	/**
	 * 获取用户购物车列表
	 * *
	 * @param userSn packageSn
	 * @return
	 */
	 PanoOrderShopcartModel getUserShopcartPackage(long userSn,long packageSn);
	/**
	 * 获取用户购物车列表
	 * *
	 * @param userSn
	 * @return
	 */
	List<T> listByUser(long userSn);
	/**
	 * 添加户型套餐到购物车,或者从购物车删除
	 * *
	 * @param housePackageSn 户型套餐sn
	 */
	int addOrDelete(long housePackageSn);
	/**
	 * 复制楼盘套餐信息到购物车
	 * *
	 * @param userSn
	 * @param projectSn
	 */
	void copyFromHousePackage(long userSn, long projectSn, long houseSn, long styleSn);
}

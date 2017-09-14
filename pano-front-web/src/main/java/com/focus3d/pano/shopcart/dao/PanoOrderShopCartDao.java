package com.focus3d.pano.shopcart.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.model.PanoOrderShopcartModel;
import com.focus3d.pano.model.ibator.PanoOrderShopcartCriteria;

/**
 * 
 * *
 * 
 * @author lihaijun
 * 
 */
@Repository
public class PanoOrderShopCartDao extends CommonDao<PanoOrderShopcartModel> {
	private static final String DELETE_SHOPCART_AND_DETAIL = "c_pano_order_shopcart.deleteShopcartAndDetail";
	private static final String INSERT_SHOPCART = "c_pano_order_shopcart.insertShopcart";
	private static final String INSERT_SHOPCART_DETAIL = "c_pano_order_shopcart.insertShopcartDetail";
	/**
	 * 获取用户购物车套餐 *
	 * 
	 * @param userSn
	 *            用户sn
	 * @return
	 */
	public PanoOrderShopcartModel getUserShopcartPackage(long userSn, long housePackageSn) {
		PanoOrderShopcartCriteria criteria = new PanoOrderShopcartCriteria();
		criteria.createCriteria().andUserSnEqualTo(userSn).andHousePackageSnEqualTo(housePackageSn);
		return selectFirstByExample(criteria, PanoOrderShopcartModel.class);
	}

	/**
	 * 获取用户购物车列表 *
	 * 
	 * @param userSn
	 *            用户sn
	 * @return
	 */
	public List<PanoOrderShopcartModel> listByUser(long userSn) {
		PanoOrderShopcartCriteria criteria = new PanoOrderShopcartCriteria();
		criteria.createCriteria().andUserSnEqualTo(userSn);
		return selectByCriteria(criteria, PanoOrderShopcartModel.class);
	}

	/**
	 * 获取购物车套餐信息 *
	 * 
	 * @param housePackageSn
	 *            户型套餐sn
	 * @param housePackageSn2 
	 * @return
	 */
	public PanoOrderShopcartModel getByHousePackage(long userSn, long housePackageSn) {
		PanoOrderShopcartCriteria criteria = new PanoOrderShopcartCriteria();
		criteria.createCriteria().andHousePackageSnEqualTo(housePackageSn).andUserSnEqualTo(userSn);
		return selectFirstByExample(criteria, PanoOrderShopcartModel.class);
	}
	/**
	 * 复制数据
	 * *
	 * @param userSn 当前用户sn
	 * @param projectSn 项目sn
	 */
	public void copyFromHousePackage(long userSn, long projectSn, long houseSn, long styleSn){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userSn", userSn);
		map.put("projectSn", projectSn);
		map.put("houseSn", houseSn);
		map.put("styleSn", styleSn);
		try {
			int delete = getSqlMapClient().delete(DELETE_SHOPCART_AND_DETAIL, map);
			if(delete >= 0){
				getSqlMapClient().insert(INSERT_SHOPCART, map);
				getSqlMapClient().insert(INSERT_SHOPCART_DETAIL, map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

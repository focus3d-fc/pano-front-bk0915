package com.focus3d.pano.shopcart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.common.service.impl.CommonServiceImpl;
import com.focus3d.pano.filter.LoginThreadLocal;
import com.focus3d.pano.model.PanoOrderShopcartDetailModel;
import com.focus3d.pano.model.PanoOrderShopcartModel;
import com.focus3d.pano.model.PanoProductModel;
import com.focus3d.pano.model.PanoProjectHousePackageModel;
import com.focus3d.pano.model.PanoProjectPackageProductModel;
import com.focus3d.pano.model.PanoProjectPackageTypeModel;
import com.focus3d.pano.product.dao.PanoProductDao;
import com.focus3d.pano.project.dao.PanoProjectHousePackageDao;
import com.focus3d.pano.project.dao.PanoProjectPackageProductDao;
import com.focus3d.pano.project.dao.PanoProjectPackageTypeDao;
import com.focus3d.pano.shopcart.dao.PanoOrderShopCartDao;
import com.focus3d.pano.shopcart.dao.PanoOrderShopcartDetailDao;
import com.focus3d.pano.shopcart.service.PanoOrderShopCartService;
import com.focustech.common.utils.ListUtils;
import com.focustech.common.utils.TCUtil;

/**
 * 
 * *
 * 
 * @author lihaijun
 * 
 */
@Service
@Transactional
public class PanoOrderShopCartServiceImpl extends CommonServiceImpl<PanoOrderShopcartModel> implements PanoOrderShopCartService<PanoOrderShopcartModel> {
	@Autowired
	private PanoOrderShopCartDao orderShopCartDao;
	@Autowired
	private PanoOrderShopcartDetailDao orderShopcartDetailDao;
	@Autowired
	private PanoProductDao productDao;
	@Autowired
	private PanoProjectPackageTypeDao packageTypeDao;
	@Autowired
	private PanoProjectPackageProductDao packageProductDao;
	@Autowired
	private PanoProjectHousePackageDao housePackageDao;

	@Override
	public CommonDao<PanoOrderShopcartModel> getDao() {
		return orderShopCartDao;
	}

	@Override
	public PanoOrderShopcartModel getUserShopcartPackage(long userSn, long packageSn) {
		PanoOrderShopcartModel shopcart = orderShopCartDao.getUserShopcartPackage(userSn, packageSn);
		if(shopcart == null) {
			return null;
		}
		List<PanoOrderShopcartDetailModel> shopcartDetails = orderShopcartDetailDao.listByShopcart(shopcart.getSn());
		if(shopcartDetails == null ){
			return shopcart;
		}
		// 设置购物车明细信息
		for (PanoOrderShopcartDetailModel shopcartDetail : shopcartDetails) {
			Long packageProductSn = shopcartDetail.getPackageProductSn();
			if (packageProductSn != null) {
				PanoProductModel packageProduct = productDao
						.getBySn(packageProductSn);
				if (packageProduct != null) {
					shopcartDetail.setPackageProduct(packageProduct);
				}
			}
			Long packageTypeSn = shopcartDetail.getPackageTypeSn();
			if (packageTypeSn != null) {
				PanoProjectPackageTypeModel packageType = packageTypeDao
						.getBySn(packageTypeSn);
				if (packageType != null) {
					shopcartDetail.setPackageType(packageType);
				}
			}
		}
		shopcart.setDetails(shopcartDetails);
		return shopcart;
	}

	@Override
	public List<PanoOrderShopcartModel> listByUser(long userSn) {
		List<PanoOrderShopcartModel> shopcarts = orderShopCartDao.listByUser(userSn);
		for (PanoOrderShopcartModel shopcart : shopcarts) {
			List<PanoOrderShopcartDetailModel> shopcartDetails = orderShopcartDetailDao.listByShopcart(shopcart.getSn());
			// 设置购物车明细信息
			for (PanoOrderShopcartDetailModel shopcartDetail : shopcartDetails) {
				Long productSn = shopcartDetail.getPackageProductSn();
				Long packageTypeSn = shopcartDetail.getPackageTypeSn();
				if(productSn != null && packageTypeSn != null){
					PanoProductModel product = productDao.getBySn(productSn);
					if (product != null) {
						shopcartDetail.setPackageProduct(product);
					}
					PanoProjectPackageTypeModel packageType = packageTypeDao.getBySn(packageTypeSn);
					if (packageType != null) {
						shopcartDetail.setPackageType(packageType);
					}
					PanoProjectPackageProductModel packageProduct = packageProductDao.getByPackageTypeAndProduct(packageTypeSn, productSn);
					if(packageProduct != null){
						shopcartDetail.setProductNum(TCUtil.iv(packageProduct.getProductNum()));
					}
				}
			}
			shopcart.setDetails(shopcartDetails);
		}
		return shopcarts;
	}

	@Override
	public int addOrDelete(long housePackageSn) {
		int status = 1;// 0-从购物车删除 ，1-添加到购物车
		Long userSn = LoginThreadLocal.getLoginInfo().getUserSn();
		PanoOrderShopcartModel shopcart = orderShopCartDao.getByHousePackage(userSn, housePackageSn);
		if (shopcart != null) {
			/*// 从购物车中删除套餐项
			List<PanoOrderShopcartDetailModel> shopcartDetails = orderShopcartDetailDao.listByShopcart(shopcart.getSn());
			for (PanoOrderShopcartDetailModel shopcartDetail : shopcartDetails) {
				orderShopcartDetailDao.deleteByKey(shopcartDetail);
			}
			orderShopCartDao.deleteByKey(shopcart);
			 */
			status = 0;
		} else {
			// 添加套餐项到购物车
			List<PanoOrderShopcartDetailModel> orderShopcartDetails = new ArrayList<PanoOrderShopcartDetailModel>();
			int housePackageNum = 0;
			// 套餐类别
			List<PanoProjectPackageTypeModel> housePackageTypeList = listPackageType(housePackageSn);
			for (PanoProjectPackageTypeModel housePackageType : housePackageTypeList) {
				// 取类别下第一条产品
				List<PanoProductModel> products = housePackageType.getProducts();
				if (ListUtils.isNotEmpty(products)) {
					PanoProductModel product = products.get(0);
					PanoOrderShopcartDetailModel shopcartDetail = new PanoOrderShopcartDetailModel();
					shopcartDetail.setPackageTypeSn(housePackageType.getSn());
					shopcartDetail.setPackageProductSn(product.getSn());
					orderShopcartDetails.add(shopcartDetail);
					housePackageNum++;
				}
			}
			if (ListUtils.isNotEmpty(orderShopcartDetails)) {
				PanoOrderShopcartModel shopcartModel = new PanoOrderShopcartModel();
				shopcartModel.setUserSn(userSn);
				shopcartModel.setHousePackageSn(housePackageSn);
				shopcartModel.setPurchaseNum(1);
				orderShopCartDao.insert(shopcartModel);
				Long shopcartSn = shopcartModel.getSn();
				for (PanoOrderShopcartDetailModel shopcartDetail : orderShopcartDetails) {
					shopcartDetail.setShopcartSn(shopcartSn);
					orderShopcartDetailDao.insert(shopcartDetail);
				}
			}
		}
		return status;
	}

	/**
	 * 
	 * *
	 * 
	 * @param packageType
	 */
	private List<PanoProjectPackageTypeModel> listPackageType(
			long housePackageSn) {
		List<PanoProjectPackageTypeModel> housePackageTypeList = packageTypeDao
				.listByHousePackage(housePackageSn);
		for (PanoProjectPackageTypeModel packageType : housePackageTypeList) {
			Long packageTypeSn = packageType.getSn();
			List<PanoProjectPackageProductModel> packageProducts = packageProductDao
					.listByPackageType(packageTypeSn);
			for (PanoProjectPackageProductModel packageProduct : packageProducts) {
				Long productSn = packageProduct.getProductSn();
				PanoProductModel product = productDao.getBySn(productSn);
				if (product != null) {
					packageType.getProducts().add(product);
				}
			}
		}
		return housePackageTypeList;
	}

	@Override
	public void copyFromHousePackage(long userSn, long projectSn, long houseSn, long styleSn) {
		List<PanoProjectHousePackageModel> housePackages = housePackageDao.listByProject(projectSn, houseSn, styleSn);
		List<PanoOrderShopcartModel> shopcarts = orderShopCartDao.listByUser(userSn);
		if(housePackages != null){
			boolean isCopy = false;
			for (PanoOrderShopcartModel shopcart : shopcarts) {
				Long housePackageSn = shopcart.getHousePackageSn();
				if(!findHousePackage(housePackageSn, housePackages)){
					isCopy = true;
					break;
				}
			}
			if(shopcarts.isEmpty() || isCopy){
				orderShopCartDao.copyFromHousePackage(userSn, projectSn, houseSn, styleSn);
			}
		}
	}
	/**
	 * 
	 * *
	 * @param housePackageSn
	 * @param housePackages
	 * @return
	 */
	private boolean findHousePackage(long housePackageSn, List<PanoProjectHousePackageModel> housePackages){
		for(PanoProjectHousePackageModel pk : housePackages){
			if(pk.getSn().equals(housePackageSn)){
				return true;
			}
		}
		return false;
	}

}

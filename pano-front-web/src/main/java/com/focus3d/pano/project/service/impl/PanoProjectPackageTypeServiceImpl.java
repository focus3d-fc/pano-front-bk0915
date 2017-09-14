package com.focus3d.pano.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.common.service.impl.CommonServiceImpl;
import com.focus3d.pano.model.PanoProductModel;
import com.focus3d.pano.model.PanoProjectPackageProductModel;
import com.focus3d.pano.model.PanoProjectPackageTypeModel;
import com.focus3d.pano.product.dao.PanoProductDao;
import com.focus3d.pano.project.dao.PanoProjectPackageProductDao;
import com.focus3d.pano.project.dao.PanoProjectPackageTypeDao;
import com.focus3d.pano.project.service.PanoProjectPackageTypeService;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Service
public class PanoProjectPackageTypeServiceImpl extends CommonServiceImpl<PanoProjectPackageTypeModel> implements PanoProjectPackageTypeService<PanoProjectPackageTypeModel> {
	@Autowired
	private PanoProjectPackageTypeDao packageTypeDao;
	@Autowired
	private PanoProjectPackageProductDao packageProductDao;
	@Autowired
	private PanoProductDao productDao;
	
	@Override
	public CommonDao<PanoProjectPackageTypeModel> getDao() {
		return packageTypeDao;
	}
	
	@Override
	public List<PanoProjectPackageTypeModel> listByHousePackage(long housePackageSn) {
		List<PanoProjectPackageTypeModel> listByHousePackage = packageTypeDao.listByHousePackage(housePackageSn);
		for (PanoProjectPackageTypeModel packageType : listByHousePackage) {
			addProducts(packageType);
		}
		return listByHousePackage;
	}

	/**
	 * 
	 * *
	 * @param packageType
	 */
	private void addProducts(PanoProjectPackageTypeModel packageType) {
		Long packageTypeSn = packageType.getSn();
		List<PanoProjectPackageProductModel> packageProducts = packageProductDao.listByPackageType(packageTypeSn);
		for (PanoProjectPackageProductModel packageProduct : packageProducts) {
			Long productSn = packageProduct.getProductSn();
			PanoProductModel product = productDao.getBySn(productSn);
			if(product != null){
				packageType.getProducts().add(product);
			}
		}
	}

}

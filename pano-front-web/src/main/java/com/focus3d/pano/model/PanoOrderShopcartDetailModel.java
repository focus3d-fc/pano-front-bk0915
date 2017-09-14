package com.focus3d.pano.model;

import com.focus3d.pano.common.model.CommonModel;
import com.focus3d.pano.model.ibator.PanoOrderShopcartDetail;
import com.focus3d.pano.model.ibator.PanoOrderShopcartDetailCriteria;
/**
 * 
 * *
 * @author lihaijun
 *
 */
public class PanoOrderShopcartDetailModel extends PanoOrderShopcartDetail<PanoOrderShopcartDetailModel, PanoOrderShopcartDetailCriteria> implements CommonModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PanoProductModel packageProduct;
	
	private PanoProjectPackageTypeModel packageType;
	
	private Integer productNum = 1;//产品组合数量

	public PanoProductModel getPackageProduct() {
		return packageProduct;
	}

	public void setPackageProduct(PanoProductModel packageProduct) {
		this.packageProduct = packageProduct;
	}

	public PanoProjectPackageTypeModel getPackageType() {
		return packageType;
	}

	public void setPackageType(PanoProjectPackageTypeModel packageType) {
		this.packageType = packageType;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}
}

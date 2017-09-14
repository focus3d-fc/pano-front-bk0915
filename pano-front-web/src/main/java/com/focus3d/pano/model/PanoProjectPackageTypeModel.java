package com.focus3d.pano.model;

import java.util.ArrayList;
import java.util.List;

import com.focus3d.pano.common.model.CommonModel;
import com.focus3d.pano.model.ibator.PanoProjectPackageType;
import com.focus3d.pano.model.ibator.PanoProjectPackageTypeCriteria;
/**
 * 
 * *
 * @author lihaijun
 *
 */
public class PanoProjectPackageTypeModel extends PanoProjectPackageType<PanoProjectPackageTypeModel, PanoProjectPackageTypeCriteria> implements CommonModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<PanoProductModel> products = new ArrayList<PanoProductModel>();

	public List<PanoProductModel> getProducts() {
		return products;
	}

	public void setProducts(List<PanoProductModel> products) {
		this.products = products;
	}
}

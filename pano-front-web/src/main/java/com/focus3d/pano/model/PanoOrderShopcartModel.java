package com.focus3d.pano.model;

import java.util.ArrayList;
import java.util.List;

import com.focus3d.pano.common.model.CommonModel;
import com.focus3d.pano.model.ibator.PanoOrderShopcart;
import com.focus3d.pano.model.ibator.PanoOrderShopcartCriteria;
/**
 * 
 * *
 * @author lihaijun
 *
 */
public class PanoOrderShopcartModel extends PanoOrderShopcart<PanoOrderShopcartModel, PanoOrderShopcartCriteria> implements CommonModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PanoProjectHousePackageModel housePackage;
	
	private List<PanoOrderShopcartDetailModel> details = new ArrayList<PanoOrderShopcartDetailModel>();

	public PanoProjectHousePackageModel getHousePackage() {
		return housePackage;
	}

	public void setHousePackage(PanoProjectHousePackageModel housePackage) {
		this.housePackage = housePackage;
	}

	public List<PanoOrderShopcartDetailModel> getDetails() {
		return details;
	}

	public void setDetails(List<PanoOrderShopcartDetailModel> details) {
		this.details = details;
	}

	public Integer getTotalProductNum() {
		int num = 0;
		for(PanoOrderShopcartDetailModel detail : details){
			num += detail.getProductNum();
		}
		return num;
	}

}

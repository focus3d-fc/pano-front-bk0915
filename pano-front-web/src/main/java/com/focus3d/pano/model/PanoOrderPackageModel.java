package com.focus3d.pano.model;

import java.util.ArrayList;
import java.util.List;

import com.focus3d.pano.common.model.CommonModel;
import com.focus3d.pano.model.ibator.PanoOrderPackage;
import com.focus3d.pano.model.ibator.PanoOrderPackageCriteria;

/**
 * 
 * *
 * 
 * @author lihaijun
 * 
 */
public class PanoOrderPackageModel extends PanoOrderPackage<PanoOrderPackageModel, PanoOrderPackageCriteria> implements CommonModel {
	private static final long serialVersionUID = 1L;
	
	private PanoProjectHousePackageModel housePackage;
	
	private List<PanoOrderPackageDetailModel> details = new ArrayList<PanoOrderPackageDetailModel>();
	
	public PanoProjectHousePackageModel getHousePackage() {
		return housePackage;
	}
	public void setHousePackage(PanoProjectHousePackageModel housePackage) {
		this.housePackage = housePackage;
	}
	public List<PanoOrderPackageDetailModel> getDetails() {
		return details;
	}
	public void setDetails(List<PanoOrderPackageDetailModel> details) {
		this.details = details;
	}

	public Integer getTotalProductNum() {
		int num = 0;
		for(PanoOrderPackageDetailModel detail : details){
			num += detail.getProductNum();
		}
		return num;
	}

}

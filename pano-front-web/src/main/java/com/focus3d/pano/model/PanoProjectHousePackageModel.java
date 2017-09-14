package com.focus3d.pano.model;

import com.focus3d.pano.common.model.CommonModel;
import com.focus3d.pano.model.ibator.PanoProjectHousePackage;
import com.focus3d.pano.model.ibator.PanoProjectHousePackageCriteria;
/**
 * 
 * *
 * @author lihaijun
 *
 */
public class PanoProjectHousePackageModel extends PanoProjectHousePackage<PanoProjectHousePackageModel, PanoProjectHousePackageCriteria> implements CommonModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private PanoProjectModel project;//楼盘
	
	private PanoProjectHouseModel house;//户型
	
	private PanoProjectStyleModel style;//风格
	

	public PanoProjectHouseModel getHouse() {
		return house;
	}

	public void setHouse(PanoProjectHouseModel house) {
		this.house = house;
	}


	public PanoProjectStyleModel getStyle() {
		return style;
	}

	public void setStyle(PanoProjectStyleModel style) {
		this.style = style;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PanoProjectModel getProject() {
		return project;
	}

	public void setProject(PanoProjectModel project) {
		this.project = project;
	}
}

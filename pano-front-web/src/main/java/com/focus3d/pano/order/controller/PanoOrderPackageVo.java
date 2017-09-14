package com.focus3d.pano.order.controller;

import java.util.ArrayList;
import java.util.List;

import com.focus3d.pano.model.PanoOrderPackageModel;
import com.focus3d.pano.model.PanoProjectHouseModel;
import com.focus3d.pano.model.PanoProjectStyleModel;

/**
 * 
 * *
 * @author lihaijun
 *
 */
public class PanoOrderPackageVo {
	private PanoProjectHouseModel house;
	private PanoProjectStyleModel style;
	private List<PanoOrderPackageModel> orderPackages = new ArrayList<PanoOrderPackageModel>();
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
	public List<PanoOrderPackageModel> getOrderPackages() {
		return orderPackages;
	}
	public void setOrderPackages(List<PanoOrderPackageModel> orderPackages) {
		this.orderPackages = orderPackages;
	}
}

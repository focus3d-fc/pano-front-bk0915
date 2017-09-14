package com.focus3d.pano.shopcart.controller;

import java.util.ArrayList;
import java.util.List;

import com.focus3d.pano.model.PanoOrderShopcartModel;
import com.focus3d.pano.model.PanoProjectHouseModel;
import com.focus3d.pano.model.PanoProjectStyleModel;

/**
 * 
 * *
 * @author lihaijun
 *
 */
public class PanoOrderShopcartListVo {
	private PanoProjectHouseModel house;
	private PanoProjectStyleModel style;
	private List<PanoOrderShopcartModel> shopcarts = new ArrayList<PanoOrderShopcartModel>();
	
	public PanoProjectHouseModel getHouse() {
		return house;
	}
	public void setHouse(PanoProjectHouseModel house) {
		this.house = house;
	}
	public List<PanoOrderShopcartModel> getShopcarts() {
		return shopcarts;
	}
	public void setShopcarts(List<PanoOrderShopcartModel> shopcarts) {
		this.shopcarts = shopcarts;
	}
	public PanoProjectStyleModel getStyle() {
		return style;
	}
	public void setStyle(PanoProjectStyleModel style) {
		this.style = style;
	}
	
}

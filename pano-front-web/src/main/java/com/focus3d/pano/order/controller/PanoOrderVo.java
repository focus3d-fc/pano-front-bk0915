package com.focus3d.pano.order.controller;

import java.util.ArrayList;
import java.util.List;

import com.focus3d.pano.model.PanoOrderModel;
import com.focus3d.pano.model.PanoProjectModel;
/**
 * 
 * *
 * @author lihaijun
 *
 */
public class PanoOrderVo {
	private PanoProjectModel project;
	//订单下面套餐集合
	private List<PanoOrderModel> orders = new ArrayList<PanoOrderModel>();
	
	public PanoProjectModel getProject() {
		return project;
	}
	public void setProject(PanoProjectModel project) {
		this.project = project;
	}
	public List<PanoOrderModel> getOrders() {
		return orders;
	}
	public void setOrders(List<PanoOrderModel> orders) {
		this.orders = orders;
	}
}

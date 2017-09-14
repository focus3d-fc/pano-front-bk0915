package com.focus3d.pano.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.focus3d.pano.common.model.CommonModel;
import com.focus3d.pano.model.ibator.PanoOrder;
import com.focus3d.pano.model.ibator.PanoOrderCriteria;
import com.focus3d.pano.order.controller.PanoOrderPackageVo;

public class PanoOrderModel extends PanoOrder<PanoOrderModel, PanoOrderCriteria> implements CommonModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//订单下面套餐集合
	private List<PanoOrderPackageModel> orderPackages = new ArrayList<PanoOrderPackageModel>();
	//收获地址
	private PanoUserReceiveAddressModel address;
	//优惠券
	private PanoOrderCouponItemModel couponItem;
	//子订单
	private PanoOrderModel childrenOrder;
	
	private Map<String, PanoOrderPackageVo> packageGropMap;

	public PanoOrderModel getChildrenOrder() {
		return childrenOrder;
	}

	public void setChildrenOrder(PanoOrderModel childrenOrder) {
		this.childrenOrder = childrenOrder;
	}

	public PanoOrderCouponItemModel getCouponItem() {
		return couponItem;
	}

	public void setCouponItem(PanoOrderCouponItemModel couponItem) {
		this.couponItem = couponItem;
	}

	public List<PanoOrderPackageModel> getOrderPackages() {
		return orderPackages;
	}

	public void setOrderPackages(List<PanoOrderPackageModel> orderPackages) {
		this.orderPackages = orderPackages;
	}

	public PanoUserReceiveAddressModel getAddress() {
		return address;
	}

	public void setAddress(PanoUserReceiveAddressModel address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		return 6 * this.getSn().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof PanoOrderModel){
			PanoOrderModel order = (PanoOrderModel)obj;
			return order.getSn().equals(this.getSn());
		}
		return false;
	}

	public Map<String, PanoOrderPackageVo> getPackageGropMap() {
		return packageGropMap;
	}

	public void setPackageGropMap(Map<String, PanoOrderPackageVo> packageGropMap) {
		this.packageGropMap = packageGropMap;
	}
}

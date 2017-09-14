package com.focus3d.pano.model;

import java.math.BigDecimal;

import com.focus3d.pano.common.model.CommonModel;
import com.focus3d.pano.model.ibator.PanoOrderCouponItem;
import com.focus3d.pano.model.ibator.PanoOrderCouponItemCriteria;
/**
 * 
 * *
 * @author lihaijun
 *
 */
public class PanoOrderCouponItemModel extends PanoOrderCouponItem<PanoOrderCouponItemModel, PanoOrderCouponItemCriteria> implements CommonModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private BigDecimal priceDiscount;//优惠价
	
	private int status;//0-可用 1-未生效 2-过期 3-已被使用过

	public BigDecimal getPriceDiscount() {
		return priceDiscount;
	}

	public void setPriceDiscount(BigDecimal priceDiscount) {
		this.priceDiscount = priceDiscount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}

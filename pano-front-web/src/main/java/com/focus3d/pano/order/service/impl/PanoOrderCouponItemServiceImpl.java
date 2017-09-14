package com.focus3d.pano.order.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focus3d.pano.common.dao.CommonDao;
import com.focus3d.pano.common.service.impl.CommonServiceImpl;
import com.focus3d.pano.model.PanoOrderCouponItemModel;
import com.focus3d.pano.model.PanoOrderCouponModel;
import com.focus3d.pano.order.dao.PanoOrderCouponDao;
import com.focus3d.pano.order.dao.PanoOrderCouponItemDao;
import com.focus3d.pano.order.service.PanoOrderCouponItemService;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Service
@Transactional
public class PanoOrderCouponItemServiceImpl extends CommonServiceImpl<PanoOrderCouponItemModel> implements PanoOrderCouponItemService<PanoOrderCouponItemModel> {
	@Autowired
	private PanoOrderCouponItemDao orderCouponItemDao;
	@Autowired
	private PanoOrderCouponDao orderCouponDao;
	
	@Override
	public CommonDao<PanoOrderCouponItemModel> getDao() {
		return orderCouponItemDao;
	}
	@Override
	public PanoOrderCouponItemModel getByCode(String codeNum) {
		PanoOrderCouponItemModel couponItem = orderCouponItemDao.getByCode(codeNum);
		if(couponItem != null){
			Long couponSn = couponItem.getCouponSn();
			PanoOrderCouponModel coupon = orderCouponDao.getBySn(couponSn);
			if(coupon != null){
				int status = 0;
				try {
					Date startDate = coupon.getStartDate();
					Date endDate = coupon.getEndDate();
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					String format = df.format(new Date());
					Date now = df.parse(format);
					if(now.compareTo(startDate) < 0){
						status = 1;//未生效
					} else if(now.compareTo(endDate) > 0){
						status = 2;//已过期
					} else if(couponItem.getCodeStatus() == 1){
						status = 3;//已被使用过
					}
					couponItem.setStatus(status);
					couponItem.setPriceDiscount(coupon.getPriceDiscount());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return couponItem;
			}
		}
		return null;
	}
	@Override
	public PanoOrderCouponItemModel getByOrderSn(Long orderSn) {
		return orderCouponItemDao.getByOrderSn(orderSn);
	}
}

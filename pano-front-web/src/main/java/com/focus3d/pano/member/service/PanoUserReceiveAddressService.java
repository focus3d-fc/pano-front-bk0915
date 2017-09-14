package com.focus3d.pano.member.service;

import java.util.List;

import com.focus3d.pano.common.service.CommonService;
/**
 * 收获地址
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface PanoUserReceiveAddressService<T> extends CommonService<T> {
	/**
	 * 获取用户收货地址
	 * *
	 * @param userSn
	 * @return
	 */
	List<T> listByUser(long userSn);
	
}

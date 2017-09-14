package com.focus3d.pano.member.service;

import java.util.List;

import com.focus3d.pano.common.service.CommonService;
/**
 * 
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface PanoUserBankcardService<T> extends CommonService<T> {
	/**
	 * 用户银行卡列表
	 * *
	 * @param userSn
	 * @return
	 */
	List<T> listByUser(long userSn);
	/**
	 * 获取用户银行卡信息
	 * *
	 * @param userSn
	 * @param cardNo
	 * @return
	 */
	T getByCardNo(long userSn, String cardNo);
}

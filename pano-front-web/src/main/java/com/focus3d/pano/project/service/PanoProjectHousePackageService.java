package com.focus3d.pano.project.service;

import java.util.List;

import com.focus3d.pano.common.service.CommonService;
/**
 * 户型套餐
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface PanoProjectHousePackageService<T> extends CommonService<T> {
	/**
	 * 获取套餐详细信息
	 * *
	 * @param housePackageSn 套餐sn
	 * @return
	 */
	T getDetail(long housePackageSn);
	/**
	 * 
	 * *
	 * @param projectSn
	 * @return
	 */
	List<T> listByProject(long projectSn, long houseSn, long styleSn);
}

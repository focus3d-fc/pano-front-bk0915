package com.focus3d.pano.project.service;

import java.util.List;

import com.focus3d.pano.common.service.CommonService;
/**
 * 
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface PanoProjectPackageTypeService<T> extends CommonService<T>{

	List<T> listByHousePackage(long housePackageSn);
}

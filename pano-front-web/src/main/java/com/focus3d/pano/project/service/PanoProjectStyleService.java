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
public interface PanoProjectStyleService<T> extends CommonService<T> {
	/**
	 * 
	 * *
	 * @param projectSn
	 * @return
	 */
	List<T> listByProject(long projectSn);
}

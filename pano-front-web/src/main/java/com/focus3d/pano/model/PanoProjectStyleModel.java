package com.focus3d.pano.model;

import com.focus3d.pano.common.model.CommonModel;
import com.focus3d.pano.model.ibator.PanoProjectStyle;
import com.focus3d.pano.model.ibator.PanoProjectStyleCriteria;
/**
 * 
 * *
 * @author lihaijun
 *
 */
public class PanoProjectStyleModel extends PanoProjectStyle<PanoProjectStyleModel, PanoProjectStyleCriteria> implements CommonModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

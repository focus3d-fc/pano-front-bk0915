package com.focus3d.pano.model;

import com.focus3d.pano.common.model.CommonModel;
import com.focus3d.pano.model.ibator.PanoUserReceiveAddress;
import com.focus3d.pano.model.ibator.PanoUserReceiveAddressCriteria;
/**
 * 
 * *
 * @author lihaijun
 *
 */
public class PanoUserReceiveAddressModel extends PanoUserReceiveAddress<PanoUserReceiveAddressModel, PanoUserReceiveAddressCriteria> implements CommonModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PanoMemUserModel user;

	public PanoMemUserModel getUser() {
		return user;
	}

	public void setUser(PanoMemUserModel user) {
		this.user = user;
	}
	

}

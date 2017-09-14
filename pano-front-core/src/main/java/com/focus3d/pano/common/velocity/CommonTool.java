package com.focus3d.pano.common.velocity;

import com.focustech.common.utils.EncryptUtil;

public class CommonTool {
	/**
	 * 
	 * *
	 * @param value
	 * @return
	 */
	public String encrypt(Long value){
		return EncryptUtil.encode(value);
	}
}

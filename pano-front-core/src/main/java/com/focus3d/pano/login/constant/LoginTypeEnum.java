package com.focus3d.pano.login.constant;
/**
 * 
 * *
 * @author lihaijun
 *
 */
public enum LoginTypeEnum {

	WX(1),
	MOBILE(2),
	EMAIL(3);
	
	int type;
	
	LoginTypeEnum(int type){
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}

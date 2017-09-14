package com.focus3d.pano.filter;

import com.focus3d.pano.model.PanoMemLoginModel;



/**
 *
 * *
 * @author lihaijun
 *
 */
public class LoginThreadLocal {

	public static final ThreadLocal<RequestMessageCookie> msgThreadLocal = new ThreadLocal<RequestMessageCookie>();
	public static final ThreadLocal<PanoMemLoginModel> loginInfoThreadLocal = new ThreadLocal<PanoMemLoginModel>();

	public static RequestMessageCookie getMessageCookie(){
		RequestMessageCookie requestMessageCookie = msgThreadLocal.get();
		if(requestMessageCookie == null){
			requestMessageCookie = new RequestMessageCookie();
			msgThreadLocal.set(requestMessageCookie);
		}
		return requestMessageCookie;
	}

	public static PanoMemLoginModel getLoginInfo(){
		return loginInfoThreadLocal.get();
	}

	public static void setLoginInfo(PanoMemLoginModel loginInfo){
		if(loginInfo != null){
			loginInfoThreadLocal.set(loginInfo);
		}
	}
	public static void setLoginInfo(Object loginInfo){
		if(loginInfo != null && loginInfo instanceof PanoMemLoginModel){
			loginInfoThreadLocal.set((PanoMemLoginModel)loginInfo);
		} else {
			loginInfoThreadLocal.remove();
		}
	}

	public static void cleanAll(){
		msgThreadLocal.remove();
		loginInfoThreadLocal.remove();
	}
}

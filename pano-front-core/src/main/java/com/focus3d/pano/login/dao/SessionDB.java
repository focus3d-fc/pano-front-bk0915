package com.focus3d.pano.login.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.focus3d.pano.model.PanoMemLoginModel;

/**
 * 
 * *
 * @author lihaijun
 *
 */
public class SessionDB {
	//本地会话
	private static Map<String, PanoMemLoginModel> sessionMap = new ConcurrentHashMap<String, PanoMemLoginModel>();
	private static Map<String, Object> tempSessionMap = new ConcurrentHashMap<String, Object>();
	
	public static void addSession(String sessionId, PanoMemLoginModel loginInfo){
		if(!sessionMap.containsKey(sessionId)){
			sessionMap.put(sessionId, loginInfo);
		}
	}
	
	public static PanoMemLoginModel get(String sessionId){
		PanoMemLoginModel panoLoginModel = sessionMap.get(sessionId);
		return panoLoginModel;
	}
	
	public static void remove(String sessionId){
		if(sessionMap.containsKey(sessionId)){
			sessionMap.remove(sessionId);
		}
	}
	/**
	 * 
	 * *
	 * @param sessionId
	 * @param obj
	 */
	public static void addTempSession(String sessionId, Object obj){
		if(!tempSessionMap.containsKey(sessionId)){
			tempSessionMap.put(sessionId, obj);
		}
	}
	/**
	 * *
	 * @param sessionId
	 * @return
	 */
	public static Object getTemp(String sessionId){
		return tempSessionMap.get(sessionId);
	}
	/**
	 * *
	 * @param sessionId
	 */
	public static void removeTemp(String sessionId){
		if(tempSessionMap.containsKey(sessionId)){
			tempSessionMap.remove(sessionId);
		}
	}
}

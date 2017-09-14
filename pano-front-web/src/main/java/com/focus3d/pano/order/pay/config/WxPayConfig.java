package com.focus3d.pano.order.pay.config;
/**
 * 
 * *
 * @author lihaijun
 *
 */
public class WxPayConfig extends CommonPayConfig{
	//公众号APP ID
	public static String APPID = "wxed31115f33aab720";
	//公众号appsecret
	public static String APPSECRET = "4bfa05cab2a336a89c48167589717366";
	//微信商户号
	public static String MCHID = "1486464992";
	//微信商户KEY
	public static String MCHKEY = "34dfgy57644534fdhkolu797dgdsghut";
	//支付异步通知
	public static String NOTIFY_URL = "http://"+DOMAIN+"/order/wxpaynotify";
	//支付同步通知
	public static String RETURN_URL = "http://"+DOMAIN+"/order/wxpayreturn";
}

package com.focus3d.pano.order.pay.config;
/**
 * 连连认证支付参数
 * *
 * @author lihaijun
 *
 */
public class LianAuthPayConfig extends CommonPayConfig{
	//商户号
	public static String OID_PARTNER = "201708290000844702";
	//MD5 KEY
	public static String MD5_KEY = "dfs34324dsjflkj0090i09803osdflj0949h";
	//连连公钥
	public static String PUB_KEY = "http://www.3d-focus.com";
	//商户私钥
	public static String OID_PRI_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSS/DiwdCf/aZsxxcacDnooGph3d2JOj5GXWi+q3gznZauZjkNP8SKl3J2liP0O6rU/Y/29+IUe+GTMhMOFJuZm1htAtKiu5ekW0GlBMWxf4FPkYlQkPE0FtaoMP3gYfh+OwI+fIRrpW3ySn3mScnc6Z700nU/VYrRkfcSCbSnRwIDAQAB";
	//连连支付WEB收银台支付服务地址
	public static String PAY_URL = "https://wap.lianlianpay.com/authpay.htm";
	//业务类型，连连支付根据商户业务为商户开设的业务类型； （101001：虚拟商品销售、109001：实物商品销售、108001：外部账户充值）
	public static String BUSI_PARTNER = "101001";
	//接收异步通知地址
	public static String NOTIFY_URL = "http://" + DOMAIN + "/order/lianpaynotify"; 
	//支付结束后返回地址
	public static String RETURN_URL = "http://" + DOMAIN + "/order/lianpayreturn";
	
}

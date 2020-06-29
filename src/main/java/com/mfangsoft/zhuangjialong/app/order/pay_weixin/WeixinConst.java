package com.mfangsoft.zhuangjialong.app.order.pay_weixin;

public class WeixinConst {

	public final static String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public final static String appid = "wxce8c5c53efcfe5e6";

	public final static String mch_id = "1413237302";

	public final static String key = "b3c85bc404564c4d07a46b68ad670122";

	public final static String notify_url = "https://www.zjial.com/shop-web/app/WXRnotify";

	public final static String notify_deposit_url = "https://www.zjial.com/shop-web/app/WXRnotifyForDeposit";//

	public final static String notify_cash_url = "https://www.zjial.com/shop-web/app/WXRnotifyForCash";//

	public final static String notify_cash_url_new = "https://www.zjial.com/shop-web/app/WXRnotifyForCashNew";//

	public final static String notify_constract = "https://www.zjial.com/shop-web/app/WXRnotifyForConstract";//

	public final static String notify_coupons = "https://www.zjial.com/shop-web/app/WXRnotifyForCoupons";//

	///////////////// debug///////////////////////////

	public final static String notify_url_debug = "http://139.196.154.83:8081/shop-web/app/WXRnotify";

	public final static String notify_deposit_url_debug = "http://139.196.154.83:8081/shop-web/app/WXRnotifyForDeposit";//

	public final static String notify_cash_url_debug = "http://139.196.154.83:8081/shop-web/app/WXRnotifyForCash";//

	public final static String notify_cash_url_new_debug = "http://139.196.154.83:8081/shop-web/app/WXRnotifyForCashNew";//

	public final static String notify_constract_debug = "http://139.196.154.83:8081/shop-web/app/WXRnotifyForConstract";//

	public final static String notify_coupons_debug = "http://139.196.154.83:8081/shop-web/app/WXRnotifyForCoupons";//
	////////////////// debug/////////////////////////

	public final static String trade_type = "APP";

	public final static String body = "装甲龙APP";

	//////////////////////// 公众号支付////////////////////////////////////////
	public final static String appidH5 = "wxebc7f519e90620c9";

	public final static String mch_idH5 = "1410925302";

	public final static String keyH5 = "70243a84f20eb6e156199883c51f8af7";
	
	public final static String trade_typeH5 = "JSAPI";
	
	public final static String bodyH5 = "装甲龙公众号";
	
	public final static String secretH5 = "1bd00b915addd90d2cbc8b3c0853ac23";
	
	public final static String urlForOpenidH5 = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ appidH5 +"&secret="+ secretH5 +"&code=CODE&grant_type=authorization_code";
	
	
}

package com.mfangsoft.zhuangjialong.app.order.pay_ali.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 */

public class AlipayConfig {

	public static String requesturl = "https://openapi.alipay.com/gateway.do";
	
	public static String app_id = "2016110102466050";
	
	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String partner = "2088421986395937";
	
	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String seller_id = "shbgkj@sina.com";

	//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	public static String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMO2nuP7aZjfKIzJyooW7s3LNQ0hQ3FoyUJzLKWpS5RzlSWpTbTjvMN1NLFFWdGhecY6E+rSuST1/xvU6hoEyZrdCzu9PmzLONL1Cr6zKWNr9v5DFDmpEww6cnUpd8TKslgwb+MsVnFsqC301+5nanGMA/sz2nhSc4o3YLMKFOB9AgMBAAECgYEAo46NkLc901zDdYz575cksOUei799rlxEvXylFji6CfDh9txbLU2ZJbtgcrkjS9ZnfypwFLFPury+GpM/pMjVdVhHLcxs7O+QgG0w2xQIwgVXLvP17FhPBHsJnHeXdjN1N6gfIm+ubGyvk2bz2HtNa0Q2trnR+9KH+BDiOv9f7WECQQDvZIIiuoY9SdVC4JtzsXamo+KeCLIbzFF/Xn0KdiN7cduSkl8p7D4GOKU4qw8F6HAKDpBZpZIUmIqTLUj9JoBZAkEA0UpkFjDJkUwDB9dILwyAsHjg7oOrHwshRcnYAP+coY/sg87HsT+YXKJJMk36Ta9jtYsALQGhR/UT5TLUnnF8xQJAHByha5Kb0PaqpH/bwWpFlKwKIKTExZ7osxxVcrUM1aI5Nq0Zn8KuRezA+jacPQVeos+zA9P9o42GOGJI//FVgQJAYefM/lyATsoiOM7K48eTg/poLuvFZ1ZLkFwmAgGRgjrTx6052X8IhX0dhLzZtPGxUUHfKS60BfduATBQKnaoiQJAY1onXTy1K77UnS/iZ0I/o+YeFaBNsR5Fyj6VnpNu+1ZyKSYUj4ZqGGUIJ3IjY7PCqmyXf8VqL0DY4zmnUGq6tA==";
	
	// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "https://www.zjial.com/shop-web/app/ALIXYZnotify";

	// 支付尾款
	public static String notify_deposit_url = "https://www.zjial.com/shop-web/app/ALIXYZnotifyForDeposit";
	
	// 支付虚拟券
	public static String notify_cash_url = "https://www.zjial.com/shop-web/app/ALIXYZnotifyForCash";
	
	// 支付虚拟券新
	public static String notify_cash_new_url = "https://www.zjial.com/shop-web/app/ALIXYZnotifyForCashNew";
	
	// 支付施工预约
	public static String notify_constract_url = "https://www.zjial.com/shop-web/app/ALIXYZnotifyForConstruct";
	
	// 支付施工预约
	public static String notify_coupons_url = "https://www.zjial.com/shop-web/app/ALIXYZnotifyForCoupons";
	
	///////////////////////////////////////////////////////////
	public static String notify_url_debug = "http://139.196.154.83:8081/shop-web/app/ALIXYZnotify";

	// 支付尾款
	public static String notify_deposit_url_debug = "http://139.196.154.83:8081/shop-web/app/ALIXYZnotifyForDeposit";
	
	// 支付虚拟券
	public static String notify_cash_url_debug = "http://139.196.154.83:8081/shop-web/app/ALIXYZnotifyForCash";
	
	// 支付虚拟券新
	public static String notify_cash_new_url_debug = "http://139.196.154.83:8081/shop-web/app/ALIXYZnotifyForCashNew";
	
	// 支付施工预约
	public static String notify_constract_url_debug = "http://139.196.154.83:8081/shop-web/app/ALIXYZnotifyForConstruct";
	
	// 支付施工预约
	public static String notify_coupons_url_debug = "http://139.196.154.83:8081/shop-web/app/ALIXYZnotifyForCoupons";
		
	///////////////////////////////////////////////////////////
	
	// 签名方式
	public static String sign_type = "RSA";
	
	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path = "F:\\";
		
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
		
	// 支付类型 ，无需修改
	public static String payment_type = "1";
		
	// 调用的接口名，无需修改
	public static String service = "mobile.securitypay.pay";

	public static String subject = "装甲龙APP";
	
	public static String body = "装甲龙APP";
	
//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
//↓↓↓↓↓↓↓↓↓↓ 请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	
	// 防钓鱼时间戳  若要使用请调用类文件submit中的query_timestamp函数
	public static String anti_phishing_key = "";
	
	// 客户端的IP地址 非局域网的外网IP地址，如：221.0.0.1
	public static String exter_invoke_ip = "";
		
//↑↑↑↑↑↑↑↑↑↑请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
}


package com.mfangsoft.zhuangjialong.app.order.pay_ali;


import com.mfangsoft.zhuangjialong.app.order.pay_ali.config.AlipayConfig;
import com.mfangsoft.zhuangjialong.app.order.pay_ali.sign.RSA;

public class AliPayWorker {
//public static void main(String[] args) {
//	try {
//		new AliPayWorker().pay();
//	} catch (AlipayApiException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//}
//	public void pay() throws AlipayApiException{
//		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.requesturl,AlipayConfig.partner
//				,AlipayConfig.private_key, "json", AlipayConfig.input_charset, AlipayConfig.alipay_public_key);
//		AlipayTradePayRequest request = new AlipayTradePayRequest();
//		
//		AlipayCore  alipayCore = new AlipayCore();
//		
//		request.setBizContent(new Gson().toJson(getOrderInfo("5.00", "454541521141141")));
//		request.setNotifyUrl(AlipayConfig.notify_url);
//		
////		request.setBizContent("{
////		    "out_trade_no":"20150320010101001",
////		    "scene":"bar_code,wave_code",
////		    "auth_code":"28763443825664394",
////		    "seller_id":"2088102146225135",
////		    "total_amount":88.88,
////		    "discountable_amount":8.88,
////		    "undiscountable_amount":80.00,
////		    "subject":"Iphone6 16G",
////		    "body":"Iphone6 16G",
////		      "goods_detail":[{
////		                "goods_id":"apple-01",
////		        "alipay_goods_id":"20010001",
////		        "goods_name":"ipad",
////		        "quantity":1,
////		        "price":2000,
////		        "goods_category":"34543238",
////		        "body":"特价手机"
////		        }],
////		    "operator_id":"yx_001",
////		    "store_id":"NJ_001",
////		    "terminal_id":"NJ_T_001",
////		    "alipay_store_id":"2016041400077000000003314986",
////		    "extend_params":{
////		      "sys_service_provider_id":"2088511833207846",
////		      "hb_fq_num":"3",
////		      "hb_fq_seller_percent":"100"
////		    },
////		    "timeout_express":"90m",
////		    "royalty_info":{
////		      "royalty_type":"ROYALTY",
////		        "royalty_detail_infos":[{
////		                    "serial_no":1,
////		          "trans_in_type":"userId",
////		          "batch_no":"123",
////		          "out_relation_id":"20131124001",
////		          "trans_out_type":"userId",
////		          "trans_out":"2088101126765726",
////		          "trans_in":"2088101126708402",
////		          "amount":0.1,
////		          "desc":"分账测试1"
////		          }]
////		    }
////		  }");
//		AlipayTradePayResponse response = alipayClient.execute(request);
//		System.out.println("-------------" + response.getBody());
//		System.out.println("-------------" + response.getMsg());
//		System.out.println("-------------" + response.getSubCode());
//	}
	
/**
 * create the order info. 创建订单信息
 * 
 */
	public static String getOrderInfo(String price, String code) {

	// 签约合作者身份ID
	String orderInfo = "partner=" + "\"" + AlipayConfig.partner + "\"";

	// 签约卖家支付宝账号
	orderInfo += "&seller_id=" + "\"" + AlipayConfig.seller_id + "\"";

	// 商户网站唯一订单号
	orderInfo += "&out_trade_no=" + "\"" + code + "\"";

	// 商品名称
	orderInfo += "&subject=" + "\"" + AlipayConfig.subject + "\"";

	// 商品详情
	orderInfo += "&body=" + "\"" + AlipayConfig.body + "\"";

	// 商品金额
	orderInfo += "&total_fee=" + "\"" + price + "\"";

	// 服务器异步通知页面路径
	orderInfo += "&notify_url=" + "\"" + AlipayConfig.notify_url + "\"";

	// 服务接口名称， 固定值
	orderInfo += "&service=\"mobile.securitypay.pay\"";

	// 支付类型， 固定值
	orderInfo += "&payment_type=\"1\"";

	// 参数编码， 固定值
	orderInfo += "&_input_charset=\"utf-8\"";

	// 设置未付款交易的超时时间
	// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
	// 取值范围：1m～15d。
	// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
	// 该参数数值不接受小数点，如1.5h，可转换为90m。
//	orderInfo += "&it_b_pay=\"2m\"";

	// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
	// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

	// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
	orderInfo += "&return_url=\"m.alipay.com\"";

	return orderInfo;
}

/**
 * sign the order info. 对订单信息进行签名
 * 
 * @param content
 *            待签名订单信息
 */
private String sign(String content) {
	return RSA.sign(content, AlipayConfig.private_key, AlipayConfig.input_charset);
}

/**
 * get the sign type we use. 获取签名方式
 * 
 */
private String getSignType() {
	return "sign_type=\"RSA\"";
}

}

package com.mfangsoft.zhuangjialong.app.order.pay_weixin;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.mfangsoft.zhuangjialong.app.order.util.OrderManager;
import com.mfangsoft.zhuangjialong.common.utils.HttpRequest;
import com.mfangsoft.zhuangjialong.common.utils.MD5Util;
import com.mfangsoft.zhuangjialong.common.utils.PropUtis;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class WPay {

	/**
	 * 调统一下单API
	 * @param orderInfo
	 * @return
	 */
	public static String httpOrder(String orderInfo) {
	    
	    try {
	        HttpURLConnection conn = (HttpURLConnection) new URL(WeixinConst.url).openConnection();
	        //加入数据  
	        conn.setRequestMethod("POST");
	        conn.setDoOutput(true);
	           
	        BufferedOutputStream buffOutStr = new BufferedOutputStream(conn.getOutputStream());  
	        buffOutStr.write(orderInfo.getBytes());
	        buffOutStr.flush();
	        buffOutStr.close();
	           
	        //获取输入流  
	        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
	           
	        String line = null;  
	        StringBuffer sb = new StringBuffer();  
	        while((line = reader.readLine())!= null){  
	            sb.append(line);  
	        }  
	         
	        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));//说明3(见文末)
	        //将请求返回的内容通过xStream转换为UnifiedOrderRespose对象
	        xStream.alias("xml", UnifiedOrderRespose.class);
	        UnifiedOrderRespose unifiedOrderRespose = (UnifiedOrderRespose) xStream.fromXML(sb.toString());
	        String result = (new String(unifiedOrderRespose.getReturn_msg().getBytes(),"UTF-8")).trim();
	        System.out.println("-----------------------------------" + result + "|" + unifiedOrderRespose.getReturn_code() + "|" + unifiedOrderRespose.getResult_code() + "|" + unifiedOrderRespose.getErr_code() + "|" + unifiedOrderRespose.getErr_code_des());
	        //根据微信文档return_code 和result_code都为SUCCESS的时候才会返回code_url
	        if(null!=unifiedOrderRespose 
	                && "SUCCESS".equals(unifiedOrderRespose.getReturn_code())
	                && "SUCCESS".equals(unifiedOrderRespose.getResult_code())){
	            return unifiedOrderRespose.getPrepay_id();
	        }else{
	            return null;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	/**
	 * 生成订单
	 * @param orderId
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String createOrderInfo(UnifiedOrderRequest unifiedOrderRequest,String groupOrderId, String price_total) throws UnsupportedEncodingException {
	    //生成订单对象
//	    UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
	    unifiedOrderRequest.setAppid(WeixinConst.appid);//公众账号ID
	    unifiedOrderRequest.setMch_id(WeixinConst.mch_id);//商户号
	    
	    unifiedOrderRequest.setNonce_str(OrderManager.makeUUID());//随机字符串 
	    unifiedOrderRequest.setBody(WeixinConst.body);//商品描述
	    unifiedOrderRequest.setOut_trade_no(groupOrderId);//商户订单号
	    unifiedOrderRequest.setTotal_fee(price_total);  //金额
//	    unifiedOrderRequest.setSpbill_create_ip("118.145.20.1");//终端IP
	    if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
	    	unifiedOrderRequest.setNotify_url(WeixinConst.notify_url_debug);//通知地址
		}else{
			unifiedOrderRequest.setNotify_url(WeixinConst.notify_url);//通知地址
		}
	    unifiedOrderRequest.setTrade_type(WeixinConst.trade_type);//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付
	    unifiedOrderRequest.setSign(createSign(unifiedOrderRequest,WeixinConst.key));//签名
	    //将订单对象转为xml格式
	    XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_"))); //
	    xStream.alias("xml", UnifiedOrderRequest.class);//根元素名需要是xml
	    return xStream.toXML(unifiedOrderRequest);
	}

	/**
	 * 生成订单
	 * @param orderId
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String createOrderInfoForDeposit(UnifiedOrderRequest unifiedOrderRequest,String orderCode, String price_total) throws UnsupportedEncodingException {
	    //生成订单对象
//	    UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
	    unifiedOrderRequest.setAppid(WeixinConst.appid);//公众账号ID
	    unifiedOrderRequest.setMch_id(WeixinConst.mch_id);//商户号
	    
	    unifiedOrderRequest.setNonce_str(OrderManager.makeUUID());//随机字符串 
	    unifiedOrderRequest.setBody(WeixinConst.body);//商品描述
	    unifiedOrderRequest.setOut_trade_no(orderCode);//商户订单号
	    unifiedOrderRequest.setTotal_fee(price_total);  //金额
//	    unifiedOrderRequest.setSpbill_create_ip("118.145.20.1");//终端IP
	    if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
	    	unifiedOrderRequest.setNotify_url(WeixinConst.notify_deposit_url_debug);//通知地址
		}else{
			unifiedOrderRequest.setNotify_url(WeixinConst.notify_deposit_url);//通知地址
		}
	    unifiedOrderRequest.setTrade_type(WeixinConst.trade_type);//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付
	    unifiedOrderRequest.setSign(createSign(unifiedOrderRequest,WeixinConst.key));//签名
	    //将订单对象转为xml格式
	    XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_"))); //
	    xStream.alias("xml", UnifiedOrderRequest.class);//根元素名需要是xml
	    return xStream.toXML(unifiedOrderRequest);
	}
	
	/**
	 * 生成订单
	 * @param orderId
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String createOrderInfoForCash(UnifiedOrderRequest unifiedOrderRequest,String orderCode, String price_total) throws UnsupportedEncodingException {
	    //生成订单对象
//	    UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
	    unifiedOrderRequest.setAppid(WeixinConst.appid);//公众账号ID
	    unifiedOrderRequest.setMch_id(WeixinConst.mch_id);//商户号
	    
	    unifiedOrderRequest.setNonce_str(OrderManager.makeUUID());//随机字符串 
	    unifiedOrderRequest.setBody(WeixinConst.body);//商品描述
	    unifiedOrderRequest.setOut_trade_no(orderCode);//商户订单号
	    unifiedOrderRequest.setTotal_fee(price_total);  //金额
//	    unifiedOrderRequest.setSpbill_create_ip("118.145.20.1");//终端IP
	    if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
	    	unifiedOrderRequest.setNotify_url(WeixinConst.notify_cash_url_debug);//通知地址
		}else{
			unifiedOrderRequest.setNotify_url(WeixinConst.notify_cash_url);//通知地址
		}
	    unifiedOrderRequest.setTrade_type(WeixinConst.trade_type);//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付
	    unifiedOrderRequest.setSign(createSign(unifiedOrderRequest,WeixinConst.key));//签名
	    //将订单对象转为xml格式
	    XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_"))); //
	    xStream.alias("xml", UnifiedOrderRequest.class);//根元素名需要是xml
	    return xStream.toXML(unifiedOrderRequest);
	}
	
	/**
	 * 生成订单
	 * @param orderId
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String createOrderInfoForCashNew(UnifiedOrderRequest unifiedOrderRequest,String orderCode, String price_total) throws UnsupportedEncodingException {
	    //生成订单对象
//	    UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
	    unifiedOrderRequest.setAppid(WeixinConst.appid);//公众账号ID
	    unifiedOrderRequest.setMch_id(WeixinConst.mch_id);//商户号
	    
	    unifiedOrderRequest.setNonce_str(OrderManager.makeUUID());//随机字符串 
	    unifiedOrderRequest.setBody(WeixinConst.body);//商品描述
	    unifiedOrderRequest.setOut_trade_no(orderCode);//商户订单号
	    unifiedOrderRequest.setTotal_fee(price_total);  //金额
//	    unifiedOrderRequest.setSpbill_create_ip("118.145.20.1");//终端IP
	    if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
	    	unifiedOrderRequest.setNotify_url(WeixinConst.notify_cash_url_new_debug);//通知地址
		}else{
			unifiedOrderRequest.setNotify_url(WeixinConst.notify_cash_url_new);//通知地址
		}
	    unifiedOrderRequest.setTrade_type(WeixinConst.trade_type);//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付
	    unifiedOrderRequest.setSign(createSign(unifiedOrderRequest,WeixinConst.key));//签名
	    //将订单对象转为xml格式
	    XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_"))); //
	    xStream.alias("xml", UnifiedOrderRequest.class);//根元素名需要是xml
	    return xStream.toXML(unifiedOrderRequest);
	}
	
	/**
	 * 生成订单
	 * @param orderId
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String createOrderInfoForConstrat(UnifiedOrderRequest unifiedOrderRequest,String orderCode, String price_total) throws UnsupportedEncodingException {
	    //生成订单对象
//	    UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
	    unifiedOrderRequest.setAppid(WeixinConst.appid);//公众账号ID
	    unifiedOrderRequest.setMch_id(WeixinConst.mch_id);//商户号
	    
	    unifiedOrderRequest.setNonce_str(OrderManager.makeUUID());//随机字符串 
	    unifiedOrderRequest.setBody(WeixinConst.body);//商品描述
	    unifiedOrderRequest.setOut_trade_no(orderCode);//商户订单号
	    unifiedOrderRequest.setTotal_fee(price_total);  //金额
//	    unifiedOrderRequest.setSpbill_create_ip("118.145.20.1");//终端IP
	    if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
	    	unifiedOrderRequest.setNotify_url(WeixinConst.notify_constract_debug);//通知地址
		}else{
			unifiedOrderRequest.setNotify_url(WeixinConst.notify_constract);//通知地址
		}
	    unifiedOrderRequest.setTrade_type(WeixinConst.trade_type);//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付
	    unifiedOrderRequest.setSign(createSign(unifiedOrderRequest,WeixinConst.key));//签名
	    //将订单对象转为xml格式
	    XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_"))); //
	    xStream.alias("xml", UnifiedOrderRequest.class);//根元素名需要是xml
	    return xStream.toXML(unifiedOrderRequest);
	}
	
	/**
	 * 生成订单
	 * @param orderId
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String createOrderInfoForCoupons(UnifiedOrderRequest unifiedOrderRequest,String orderCode, String price_total) throws UnsupportedEncodingException {
	    //生成订单对象
//	    UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
	    unifiedOrderRequest.setAppid(WeixinConst.appid);//公众账号ID
	    unifiedOrderRequest.setMch_id(WeixinConst.mch_id);//商户号
	    
	    unifiedOrderRequest.setNonce_str(OrderManager.makeUUID());//随机字符串 
	    unifiedOrderRequest.setBody(WeixinConst.body);//商品描述
	    unifiedOrderRequest.setOut_trade_no(orderCode);//商户订单号
	    unifiedOrderRequest.setTotal_fee(price_total);  //金额
//	    unifiedOrderRequest.setSpbill_create_ip("118.145.20.1");//终端IP
	    if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
	    	unifiedOrderRequest.setNotify_url(WeixinConst.notify_coupons_debug);//通知地址
		}else{
			unifiedOrderRequest.setNotify_url(WeixinConst.notify_coupons);//通知地址
		}
	    unifiedOrderRequest.setTrade_type(WeixinConst.trade_type);//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付
	    unifiedOrderRequest.setSign(createSign(unifiedOrderRequest,WeixinConst.key));//签名
	    //将订单对象转为xml格式
	    XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_"))); //
	    xStream.alias("xml", UnifiedOrderRequest.class);//根元素名需要是xml
	    return xStream.toXML(unifiedOrderRequest);
	}
	
	/**
	 * 生成订单
	 * @param orderId
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String createOrderInfoH5(UnifiedOrderRequest unifiedOrderRequest,String groupOrderId, String price_total, String notify_url, String notify_url_debug) throws UnsupportedEncodingException {
	    //生成订单对象
//	    UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
	    unifiedOrderRequest.setAppid(WeixinConst.appidH5);//公众账号ID
	    unifiedOrderRequest.setMch_id(WeixinConst.mch_idH5);//商户号
	    
	    unifiedOrderRequest.setNonce_str(OrderManager.makeUUID());//随机字符串 
	    unifiedOrderRequest.setBody(WeixinConst.bodyH5);//商品描述
	    unifiedOrderRequest.setOut_trade_no(groupOrderId);//商户订单号
	    unifiedOrderRequest.setTotal_fee(price_total);  //金额
//	    unifiedOrderRequest.setSpbill_create_ip("118.145.20.1");//终端IP
	    
	    String json_result = HttpRequest.sendPost(WeixinConst.urlForOpenidH5.replace("CODE", unifiedOrderRequest.getOpenid()), "");
	    String openid =  getData(json_result).get("openid");
	    unifiedOrderRequest.setOpenid(openid);
	    if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
	    	unifiedOrderRequest.setNotify_url(notify_url_debug);//通知地址
		}else{
			unifiedOrderRequest.setNotify_url(notify_url);//通知地址
		}
	    
	    unifiedOrderRequest.setTrade_type(WeixinConst.trade_typeH5);//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付
	    unifiedOrderRequest.setSign(createSign(unifiedOrderRequest,WeixinConst.keyH5));//签名
	    //将订单对象转为xml格式
	    XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_"))); //
	    xStream.alias("xml", UnifiedOrderRequest.class);//根元素名需要是xml
	    return xStream.toXML(unifiedOrderRequest);
	}
	
	public static Map<String,String> getData(String str) {
		 String sb = str.substring(1, str.length() - 1);
		String[] name = sb.split("\\\",\\\"");
		String[] nn = null;
		Map<String,String> map = new HashMap<>();
		for (String aName : name) {
				nn = aName.split("\\\":\\\"");
				map.put(nn[0].replace("\"", ""), nn[1].replace("\"", ""));
		}
		return map;
	}
	
	/**
	 * 生成签名
	 * 
	 * @param appid_value
	 * @param mch_id_value
	 * @param productId
	 * @param nonce_str_value
	 * @param trade_type 
	 * @param notify_url 
	 * @param spbill_create_ip 
	 * @param total_fee 
	 * @param out_trade_no 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String createSign(UnifiedOrderRequest unifiedOrderRequest, String key) throws UnsupportedEncodingException{
	    //根据规则创建可排序的map集合
	    SortedMap<String, String> packageParams = new TreeMap<String, String>();
	    packageParams.put("appid", unifiedOrderRequest.getAppid());
	    packageParams.put("attach", unifiedOrderRequest.getAttach());
	    packageParams.put("body", unifiedOrderRequest.getBody());
	    packageParams.put("mch_id", unifiedOrderRequest.getMch_id());
	    packageParams.put("nonce_str", unifiedOrderRequest.getNonce_str());
	    packageParams.put("notify_url", unifiedOrderRequest.getNotify_url());
	    if(unifiedOrderRequest.getOpenid() != null){
	    	packageParams.put("openid", unifiedOrderRequest.getOpenid());
	    }
	    packageParams.put("out_trade_no", unifiedOrderRequest.getOut_trade_no());
	    packageParams.put("spbill_create_ip", unifiedOrderRequest.getSpbill_create_ip());
	    packageParams.put("trade_type", unifiedOrderRequest.getTrade_type());
	    packageParams.put("total_fee", unifiedOrderRequest.getTotal_fee());
	 
	    StringBuffer sb = new StringBuffer();
	    Set es = packageParams.entrySet();//字典序
	    Iterator it = es.iterator();
	    while (it.hasNext()) {
	        Map.Entry entry = (Map.Entry) it.next();
	        String k = (String) entry.getKey();
	        String v = (String) entry.getValue();
	        //为空不参与签名、参数名区分大小写
	        if (null != v && !"".equals(v) && !"sign".equals(k)
	                && !"key".equals(k)) {
	            sb.append(k + "=" + v + "&");
	        }
	    }
	    sb.append("key=" + key);
	    String sign = MD5Util.MD5(sb.toString())
	            .toUpperCase();
	    return sign;
	}
	
	/**
	 * 生成签名H5
	 * 
	 * @param appid_value
	 * @param mch_id_value
	 * @param productId
	 * @param nonce_str_value
	 * @param trade_type 
	 * @param notify_url 
	 * @param spbill_create_ip 
	 * @param total_fee 
	 * @param out_trade_no 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String createSignH5(UnifiedOrderRequest unifiedOrderRequest, String key, String prepay_id) throws UnsupportedEncodingException{
	    //根据规则创建可排序的map集合
		String sign = null;
		try{
	    SortedMap<String, String> packageParams = new TreeMap<String, String>();
	    packageParams.put("appId", unifiedOrderRequest.getAppid());
	    
	    String nonceStr = OrderManager.makeUUID();
	    packageParams.put("nonceStr", nonceStr);
	    unifiedOrderRequest.setNonce_str(nonceStr);
	    
	    packageParams.put("package", "prepay_id="+prepay_id);
	    packageParams.put("signType", "MD5");
	    
	    String timestamp = StringUtils.substring(System.currentTimeMillis() + "", 0, 10);
	    packageParams.put("timeStamp", timestamp);
	    unifiedOrderRequest.setTimestamp(timestamp);
	    
	    StringBuffer sb = new StringBuffer();
	    Set es = packageParams.entrySet();//字典序
	    Iterator it = es.iterator();
	    while (it.hasNext()) {
	        Map.Entry entry = (Map.Entry) it.next();
	        String k = (String) entry.getKey();
	        String v = (String) entry.getValue();
	        //为空不参与签名、参数名区分大小写
	        if (null != v && !"".equals(v) && !"sign".equals(k) 
	                && !"key".equals(k)) {
	            sb.append(k + "=" + v + "&");
	        }
	    }
	    sb.append("key=" + key);
	    sign = MD5Util.MD5(sb.toString())
	            .toUpperCase();
		}catch(Exception e){
			e.printStackTrace();
		}
	    return sign;
	}
	
	/**
	 * 生成Response签名
	 * 
	 * @param appid_value
	 * @param mch_id_value
	 * @param productId
	 * @param nonce_str_value
	 * @param trade_type 
	 * @param notify_url 
	 * @param spbill_create_ip 
	 * @param total_fee 
	 * @param out_trade_no 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static boolean verifyResponse(Map<String, String> packageParams, String key){
	    //根据规则创建可排序的map集合
		String sign = packageParams.get("sign");
		packageParams = paraFilter(packageParams);
		String varifyparams = createLinkStringForVerify(packageParams);
		varifyparams = varifyparams + "&" +  "key=" + key;
		String signNew = MD5Util.MD5(varifyparams)
	            .toUpperCase();//MD5加密
		if(sign.equals(signNew)){
			return true;
		}else{
			return false;
		}
		
	}
	
	   /** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            Object value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value.toString());
        }
        return result;
    }
    
    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkStringForVerify(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
}

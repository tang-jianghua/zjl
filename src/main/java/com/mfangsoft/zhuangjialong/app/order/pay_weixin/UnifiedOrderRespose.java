package com.mfangsoft.zhuangjialong.app.order.pay_weixin;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value=Include.NON_NULL)
public class UnifiedOrderRespose {
	private String return_code;             //返回状态码
    private String return_msg;              //返回信息
    private String appid;                   //公众账号ID
    private String mch_id;                  //商户号
    private String device_info;             //设备号
    private String nonce_str;               //随机字符串
    private String sign;                    //签名
    private String result_code;             //业务结果
    private String err_code;                //错误代码
    private String err_code_des;            //<span style="white-space:pre">  </span>//错误代码描述
    private String trade_type;              //交易类型
    private String prepay_id;               //预支付交易会话标识
    private String code_url;                //二维码链接
    private String out_trade_no;        //商户订单号
    private String total_fee;           //总金额，单位分
    private String bank_type;           //总金额，单位分
    private String openid;
    private String is_subscribe;
    private String fee_type;
    private String cash_fee;
    private String cash_fee_type;
    private String coupon_fee;
    private String coupon_count;
    private String coupon_id_$n;
    private String coupon_fee_$n;
    private String transaction_id;
    private String attach;
    private String time_end;
    
	public String getBank_type() {
		return bank_type;
	}
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getIs_subscribe() {
		return is_subscribe;
	}
	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getCash_fee() {
		return cash_fee;
	}
	public void setCash_fee(String cash_fee) {
		this.cash_fee = cash_fee;
	}
	public String getCash_fee_type() {
		return cash_fee_type;
	}
	public void setCash_fee_type(String cash_fee_type) {
		this.cash_fee_type = cash_fee_type;
	}
	public String getCoupon_fee() {
		return coupon_fee;
	}
	public void setCoupon_fee(String coupon_fee) {
		this.coupon_fee = coupon_fee;
	}
	public String getCoupon_count() {
		return coupon_count;
	}
	public void setCoupon_count(String coupon_count) {
		this.coupon_count = coupon_count;
	}
	public String getCoupon_id_$n() {
		return coupon_id_$n;
	}
	public void setCoupon_id_$n(String coupon_id_num) {
		this.coupon_id_$n = StringUtils.replaceOnce("$n", "coupon_id_$n", coupon_id_num);
	}
	public String getCoupon_fee_$n() {
		return coupon_fee_$n;
	}
	public void setCoupon_fee_$n(String coupon_fee_$n) {
		this.coupon_fee_$n = StringUtils.replaceOnce("$n", "coupon_fee_$n", coupon_fee_$n);
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_code_des() {
		return err_code_des;
	}
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getPrepay_id() {
		return prepay_id;
	}
	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}
	public String getCode_url() {
		return code_url;
	}
	public void setCode_url(String code_url) {
		this.code_url = code_url;
	}
    
    public String toString(){
    	StringBuilder builder = new StringBuilder();
    	
   	 builder.append("return_code" + "   " + return_code + "\r\n"); 
   	 builder.append("return_msg"  + "   " + return_msg + "\r\n");   
   	 builder.append("appid"       + "   " + appid + "\r\n");        
   	 builder.append("mch_id"      + "   " + mch_id + "\r\n");       
   	 builder.append("device_info" + "   " + device_info + "\r\n");  
   	 builder.append("nonce_str"   + "   " + nonce_str + "\r\n");    
   	 builder.append("sign"       + "   " + sign + "\r\n");         
   	 builder.append("result_code" + "   " + result_code + "\r\n");  
   	 builder.append("err_code"   + "   " + err_code + "\r\n");     
   	 builder.append("err_code_des"+ "   " + err_code_des + "\r\n"); 
   	 builder.append("trade_type"  + "   " + trade_type + "\r\n");   
   	 builder.append("prepay_id"   + "   " + prepay_id + "\r\n");    
   	 builder.append("code_url"    + "   " + code_url + "\r\n");     
   	 builder.append("out_trade_no"+ "   " + out_trade_no + "\r\n"); 
   	 builder.append("total_fee"   + "   " + total_fee + "\r\n"); 
   	 builder.append("bank_type" + "   " + bank_type + "\r\n"); 		
   	 builder.append("openid" + "   " + openid + "\r\n"); 
   	 builder.append("is_subscribe" + "   " + is_subscribe + "\r\n"); 
   	 builder.append("fee_type" + "   " + fee_type + "\r\n"); 
   	 builder.append("cash_fee" + "   " + cash_fee + "\r\n"); 
   	 builder.append("cash_fee_type" + "   " + cash_fee_type + "\r\n"); 
   	 builder.append("coupon_fee" + "   " + coupon_fee + "\r\n"); 
   	 builder.append("coupon_count" + "   " + coupon_count + "\r\n"); 
   	 builder.append("coupon_id_$n" + "   " + coupon_id_$n + "\r\n"); 
   	 builder.append("coupon_fee_$n" + "   " + coupon_fee_$n + "\r\n"); 
   	 builder.append("transaction_id" + "   " + transaction_id + "\r\n"); 
   	 builder.append("attach" + "   " + attach + "\r\n"); 
   	 builder.append("time_end" + "   " + time_end + "\r\n"); 
    	return builder.toString();
    }
}

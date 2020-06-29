package com.mfangsoft.zhuangjialong.app.sendsms.service;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年9月20日
* 
*/

public interface SendSMSService {
  
	/*
	 * 发送单个手机号
	 * 
	 * param:
	 *phone  要发送的 手机号,只发一个号码：13800000001。发多个号码：13800000001,13800000002,...N 。使用半角逗号分隔。
	 *content 要发送的短信内容，特别注意：签名必须设置，网页验证码应用需要加添加【图形识别码】。 
	 */
	public  boolean sendSMS(String phone,String message,String message_suf);
}

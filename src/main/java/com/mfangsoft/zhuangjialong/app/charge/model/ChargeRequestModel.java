package com.mfangsoft.zhuangjialong.app.charge.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年10月27日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class ChargeRequestModel {
	
	private String action;//请求名
	
	private String username;//用户名(签名)
	
	private String name_to_package;//套餐数额(签名)
	
	private String orderid;//订单 ID(签名) 
	
	private String timestamp;//时间戳(签名)
	
	private String sign;//签名

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the name_to_package
	 */
	public String getName_to_package() {
		return name_to_package;
	}

	/**
	 * @param name_to_package the name_to_package to set
	 */
	public void setName_to_package(String name_to_package) {
		this.name_to_package = name_to_package;
	}

	/**
	 * @return the orderid
	 */
	public String getOrderid() {
		return orderid;
	}

	/**
	 * @param orderid the orderid to set
	 */
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * @param sign the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
       
}

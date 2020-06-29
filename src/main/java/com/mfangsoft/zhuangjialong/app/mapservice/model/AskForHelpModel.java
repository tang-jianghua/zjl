package com.mfangsoft.zhuangjialong.app.mapservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年12月15日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class AskForHelpModel extends BaseAskhelpEntity{

	private String head_url;//用户头像

	private Double distance;//距离
	
	private String user_name;

	private String easemob_account;//环信账号
	
	private String phone;//联系方式
	
	

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the easemob_account
	 */
	public String getEasemob_account() {
		return easemob_account;
	}

	/**
	 * @param easemob_account the easemob_account to set
	 */
	public void setEasemob_account(String easemob_account) {
		this.easemob_account = easemob_account;
	}

	/**
	 * @return the user_name
	 */
	public String getUser_name() {
		return user_name;
	}

	/**
	 * @param user_name the user_name to set
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	/**
	 * @return the distance
	 */
	public Double getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
	}

	/**
	 * @return the head_url
	 */
	public String getHead_url() {
		return head_url;
	}

	/**
	 * @param head_url the head_url to set
	 */
	public void setHead_url(String head_url) {
		this.head_url = head_url;
	}
	
	
}

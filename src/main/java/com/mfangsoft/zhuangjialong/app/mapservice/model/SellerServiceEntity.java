package com.mfangsoft.zhuangjialong.app.mapservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年12月22日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class SellerServiceEntity {

	private Integer id;//服务id
	
	private String head_img;//头像
	
	private String seller_name;//联系方式

	private String phone;//联系方式

	private String easemob_account;//环信账号
	 
	
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
	 * @return the seller_name
	 */
	public String getSeller_name() {
		return seller_name;
	}

	/**
	 * @param seller_name the seller_name to set
	 */
	public void setSeller_name(String seller_name) {
		this.seller_name = seller_name;
	}

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
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the head_img
	 */
	public String getHead_img() {
		return head_img;
	}

	/**
	 * @param head_img the head_img to set
	 */
	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}
	
	
}

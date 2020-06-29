package com.mfangsoft.zhuangjialong.integration.shop.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年12月26日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class ShopGuiderModel extends BaseShopGuiderEntity{

	
	private String name;//卖家昵称
	
	private String account;//卖家账户
	
	private String phone;//卖家电话
	
	private String principal;//合伙人名称

	private Integer state;//账号状态
	
	private String brand_name;//品牌名称
	
	private String shop_name;//店铺名称
	
	private String expand_name;//推广人姓名
	
	private Integer invite_count;//推广数
	
	private Integer certification_state;//店铺导购认证状态
	
	private String password;//密码
	
	private Long certification_info_id;//认证信息id
	
	private String no_pass_reason;//未通过原因
	
	
	/**
	 * @return the no_pass_reason
	 */
	public String getNo_pass_reason() {
		return no_pass_reason;
	}

	/**
	 * @param no_pass_reason the no_pass_reason to set
	 */
	public void setNo_pass_reason(String no_pass_reason) {
		this.no_pass_reason = no_pass_reason;
	}

	/**
	 * @return the certification_info_id
	 */
	public Long getCertification_info_id() {
		return certification_info_id;
	}

	/**
	 * @param certification_info_id the certification_info_id to set
	 */
	public void setCertification_info_id(Long certification_info_id) {
		this.certification_info_id = certification_info_id;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
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
	 * @return the principal
	 */
	public String getPrincipal() {
		return principal;
	}

	/**
	 * @param principal the principal to set
	 */
	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * @return the brand_name
	 */
	public String getBrand_name() {
		return brand_name;
	}

	/**
	 * @param brand_name the brand_name to set
	 */
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	/**
	 * @return the shop_name
	 */
	public String getShop_name() {
		return shop_name;
	}

	/**
	 * @param shop_name the shop_name to set
	 */
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	/**
	 * @return the expand_name
	 */
	public String getExpand_name() {
		return expand_name;
	}

	/**
	 * @param expand_name the expand_name to set
	 */
	public void setExpand_name(String expand_name) {
		this.expand_name = expand_name;
	}

	/**
	 * @return the invite_count
	 */
	public Integer getInvite_count() {
		return invite_count;
	}

	/**
	 * @param invite_count the invite_count to set
	 */
	public void setInvite_count(Integer invite_count) {
		this.invite_count = invite_count;
	}

	/**
	 * @return the certification_state
	 */
	public Integer getCertification_state() {
		return certification_state;
	}

	/**
	 * @param certification_state the certification_state to set
	 */
	public void setCertification_state(Integer certification_state) {
		this.certification_state = certification_state;
	}

	
	
}

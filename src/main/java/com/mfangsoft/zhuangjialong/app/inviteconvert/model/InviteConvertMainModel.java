package com.mfangsoft.zhuangjialong.app.inviteconvert.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年10月31日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class InviteConvertMainModel {
        
	private String charge_rule;//兑换话费规则
	
	private int invite_no;//邀请数
	
	private List<InviteProductEntity> products;//可兑换的产品

	private List<CustomerEntity> customers;
	 
	
	/**
	 * @return the customers
	 */
	public List<CustomerEntity> getCustomers() {
		return customers;
	}

	/**
	 * @param customers the customers to set
	 */
	public void setCustomers(List<CustomerEntity> customers) {
		this.customers = customers;
	}

	/**
	 * @return the charge_rule
	 */
	public String getCharge_rule() {
		return charge_rule;
	}

	/**
	 * @param charge_rule the charge_rule to set
	 */
	public void setCharge_rule(String charge_rule) {
		this.charge_rule = charge_rule;
	}

	/**
	 * @return the invite_no
	 */
	public int getInvite_no() {
		return invite_no;
	}

	/**
	 * @param invite_no the invite_no to set
	 */
	public void setInvite_no(int invite_no) {
		this.invite_no = invite_no;
	}

	/**
	 * @return the products
	 */
	public List<InviteProductEntity> getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(List<InviteProductEntity> products) {
		this.products = products;
	}
	
	
	
}

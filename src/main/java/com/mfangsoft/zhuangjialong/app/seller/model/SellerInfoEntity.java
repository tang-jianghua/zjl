package com.mfangsoft.zhuangjialong.app.seller.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value=Include.NON_NULL)
public class SellerInfoEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long seller_id;
	private String ali_account;
	private Long construct_info_id;
	private Double commission_rate;
	private Long expand_id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(Long seller_id) {
		this.seller_id = seller_id;
	}
	public String getAli_account() {
		return ali_account;
	}
	public void setAli_account(String ali_account) {
		this.ali_account = ali_account;
	}
	public Long getConstruct_info_id() {
		return construct_info_id;
	}
	public void setConstruct_info_id(Long construct_info_id) {
		this.construct_info_id = construct_info_id;
	}
	public Double getCommission_rate() {
		return commission_rate;
	}
	public void setCommission_rate(Double commission_rate) {
		this.commission_rate = commission_rate;
	}
	public Long getExpand_id() {
		return expand_id;
	}
	public void setExpand_id(Long expand_id) {
		this.expand_id = expand_id;
	}
}

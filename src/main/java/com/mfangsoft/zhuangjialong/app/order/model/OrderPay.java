package com.mfangsoft.zhuangjialong.app.order.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class OrderPay {
	private String preOrderStr;//提交订单前奏代号
	private Long customer_id;
	private Long customer_address_id;
	private Long customer_pay_id;
	private List<OrderEntity> orders;
	private ArrayList<String> state;
	private Integer page;
	private Integer pageSize;

	public String getPreOrderStr() {
		return preOrderStr;
	}

	public void setPreOrderStr(String preOrderStr) {
		this.preOrderStr = preOrderStr;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}

	public Long getCustomer_address_id() {
		return customer_address_id;
	}

	public void setCustomer_address_id(Long customer_address_id) {
		this.customer_address_id = customer_address_id;
	}

	public Long getCustomer_pay_id() {
		return customer_pay_id;
	}

	public void setCustomer_pay_id(Long customer_pay_id) {
		this.customer_pay_id = customer_pay_id;
	}

	public List<OrderEntity> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderEntity> orders) {
		this.orders = orders;
	}

	public ArrayList<String> getState() {
		return state;
	}

	public void setState(ArrayList<String> state) {
		this.state = state;
	}

}

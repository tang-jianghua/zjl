package com.mfangsoft.zhuangjialong.app.order.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class OrderJsonModel {

	private String order_code;

	private double order_price;
	
	private boolean isZeroPriceOrder;

	public boolean isZeroPriceOrder() {
		return isZeroPriceOrder;
	}

	public void setZeroPriceOrder(boolean isZeroPriceOrder) {
		this.isZeroPriceOrder = isZeroPriceOrder;
	}

	public OrderJsonModel() {

	}

	public OrderJsonModel(String order_code, double order_price) {
		this.order_code = order_code;
		this.order_price = order_price;
	}

	public String getOrder_code() {
		return order_code;
	}

	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}

	public double getOrder_price() {
		return order_price;
	}

	public void setOrder_price(double order_price) {
		this.order_price = order_price;
	}

}

package com.mfangsoft.zhuangjialong.integration.seller.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Order {

	private Long id;

	private String code;
	private Double order_price;
	private Boolean isdeposit;
	private Double deposit_price;
	private Double totalprice;
	private Double total_price;
	private Integer shifu_type;
	private Double fixed_money;
	private Double pre_money;
	private String trade_data;
	private Long customer_id;
	private String receiver_name;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date create_time;
	private List<Product> order_products;

	private Long shop_id;
	private String shop_phone_num;
	private String shop_name;

	private Integer pay_state;
	private Integer order_state_code;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date pay_time;

	private String seller_name;
	private String brand_name;
	private String enterprise_name;
	private String principal;

	private String state;
	private Double money_platform;
	private Double amount;
	
	private String cash_info;
	
	private String level;
	
	
	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getOrder_price() {
		return order_price;
	}

	public void setOrder_price(Double order_price) {
		this.order_price = order_price;
	}

	public Boolean getIsdeposit() {
		return isdeposit;
	}

	public void setIsdeposit(Boolean isdeposit) {
		this.isdeposit = isdeposit;
	}

	public Double getDeposit_price() {
		return deposit_price;
	}

	public void setDeposit_price(Double deposit_price) {
		this.deposit_price = deposit_price;
	}

	public Double getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(Double totalprice) {
		this.totalprice = totalprice;
	}

	public Double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Double total_price) {
		this.total_price = total_price;
	}

	public Integer getShifu_type() {
		return shifu_type;
	}

	public void setShifu_type(Integer shifu_type) {
		this.shifu_type = shifu_type;
	}

	public Double getFixed_money() {
		return fixed_money;
	}

	public void setFixed_money(Double fixed_money) {
		this.fixed_money = fixed_money;
	}

	public Double getPre_money() {
		return pre_money;
	}

	public void setPre_money(Double pre_money) {
		this.pre_money = pre_money;
	}

	public String getTrade_data() {
		return trade_data;
	}

	public void setTrade_data(String trade_data) {
		this.trade_data = trade_data;
	}

	public Long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}

	public String getReceiver_name() {
		return receiver_name;
	}

	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public List<Product> getOrder_products() {
		return order_products;
	}

	public void setOrder_products(List<Product> order_products) {
		this.order_products = order_products;
	}

	public Long getShop_id() {
		return shop_id;
	}

	public void setShop_id(Long shop_id) {
		this.shop_id = shop_id;
	}

	public String getShop_phone_num() {
		return shop_phone_num;
	}

	public void setShop_phone_num(String shop_phone_num) {
		this.shop_phone_num = shop_phone_num;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public Integer getPay_state() {
		return pay_state;
	}

	public void setPay_state(Integer pay_state) {
		this.pay_state = pay_state;
	}

	public Integer getOrder_state_code() {
		return order_state_code;
	}

	public void setOrder_state_code(Integer order_state_code) {
		this.order_state_code = order_state_code;
	}

	public Date getPay_time() {
		return pay_time;
	}

	public void setPay_time(Date pay_time) {
		this.pay_time = pay_time;
	}

	public String getSeller_name() {
		return seller_name;
	}

	public void setSeller_name(String seller_name) {
		this.seller_name = seller_name;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getEnterprise_name() {
		return enterprise_name;
	}

	public void setEnterprise_name(String enterprise_name) {
		this.enterprise_name = enterprise_name;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCash_info() {
		return cash_info;
	}

	public void setCash_info(String cash_info) {
		this.cash_info = cash_info;
	}

	public Double getMoney_platform() {
		return money_platform;
	}

	public void setMoney_platform(Double money_platform) {
		this.money_platform = money_platform;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}

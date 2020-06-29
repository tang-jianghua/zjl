package com.mfangsoft.zhuangjialong.app.order.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerAddress;

/**
 * @description：订单模型
 * @author：Liyanchen @date：2016年5月27日
 * 
 */
@JsonInclude(value = Include.NON_NULL)
public class Order {
	private String order_code;
	private Long customer_id;
	private Long shop_id;
	private String shop_name;
	private String shop_phone_num;
	private Long customer_address_id;
	private String memo;
	private Long customer_pay_id;
	private Long promotion_id;
	private Long coupons_id;
	private Long brand_id;
	private Long citypartner_id;
	private Long redbag_id;
	private Double order_price;
	private Boolean isdeposit;
	private Double deposit_price;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date create_time;
	private String region_code;
	private CustomerAddress address;
	private Long exshopper_id;
	private List<OrderProduct> products;
	private Integer pay_state;
	private Integer order_state_code;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date pay_time;
	private String trade_data;

	public String getShop_phone_num() {
		return shop_phone_num;
	}

	public void setShop_phone_num(String shop_phone_num) {
		this.shop_phone_num = shop_phone_num;
	}

	public Long getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(Long brand_id) {
		this.brand_id = brand_id;
	}

	public Long getCitypartner_id() {
		return citypartner_id;
	}

	public void setCitypartner_id(Long citypartner_id) {
		this.citypartner_id = citypartner_id;
	}

	/**
	 * @return the order_code
	 */
	public String getOrder_code() {
		return order_code;
	}

	/**
	 * @param order_code
	 *            the order_code to set
	 */
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}

	/**
	 * @return the customer_id
	 */
	public Long getCustomer_id() {
		return customer_id;
	}

	/**
	 * @param customer_id
	 *            the customer_id to set
	 */
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}

	/**
	 * @return the shop_id
	 */
	public Long getShop_id() {
		return shop_id;
	}

	/**
	 * @param shop_id
	 *            the shop_id to set
	 */
	public void setShop_id(Long shop_id) {
		this.shop_id = shop_id;
	}

	/**
	 * @return the shop_name
	 */
	public String getShop_name() {
		return shop_name;
	}

	/**
	 * @param shop_name
	 *            the shop_name to set
	 */
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	/**
	 * @return the customer_address_id
	 */
	public Long getCustomer_address_id() {
		return customer_address_id;
	}

	/**
	 * @param customer_address_id
	 *            the customer_address_id to set
	 */
	public void setCustomer_address_id(Long customer_address_id) {
		this.customer_address_id = customer_address_id;
	}

	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo
	 *            the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return the customer_pay_id
	 */
	public Long getCustomer_pay_id() {
		return customer_pay_id;
	}

	/**
	 * @param customer_pay_id
	 *            the customer_pay_id to set
	 */
	public void setCustomer_pay_id(Long customer_pay_id) {
		this.customer_pay_id = customer_pay_id;
	}

	/**
	 * @return the coupons_id
	 */
	public Long getCoupons_id() {
		return coupons_id;
	}

	/**
	 * @param coupons_id
	 *            the coupons_id to set
	 */
	public void setCoupons_id(Long coupons_id) {
		this.coupons_id = coupons_id;
	}

	/**
	 * @return the redbag_id
	 */
	public Long getRedbag_id() {
		return redbag_id;
	}

	/**
	 * @param redbag_id
	 *            the redbag_id to set
	 */
	public void setRedbag_id(Long redbag_id) {
		this.redbag_id = redbag_id;
	}

	/**
	 * @return the order_price
	 */
	public Double getOrder_price() {
		return order_price;
	}

	/**
	 * @param order_price
	 *            the order_price to set
	 */
	public void setOrder_price(Double order_price) {
		this.order_price = order_price;
	}

	/**
	 * @return the create_time
	 */
	public Date getCreate_time() {
		return create_time;
	}

	/**
	 * @param create_time
	 *            the create_time to set
	 */
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	/**
	 * @return the region_code
	 */
	public String getRegion_code() {
		return region_code;
	}

	/**
	 * @param region_code
	 *            the region_code to set
	 */
	public void setRegion_code(String region_code) {
		this.region_code = region_code;
	}

	/**
	 * @return the address
	 */
	public CustomerAddress getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(CustomerAddress address) {
		this.address = address;
	}

	public Long getExshopper_id() {
		return exshopper_id;
	}

	public void setExshopper_id(Long exshopper_id) {
		this.exshopper_id = exshopper_id;
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

	public String getTrade_data() {
		return trade_data;
	}

	public void setTrade_data(String trade_data) {
		this.trade_data = trade_data;
	}

	public List<OrderProduct> getProducts() {
		return products;
	}

	public void setProducts(List<OrderProduct> products) {
		this.products = products;
	}

	public Long getPromotion_id() {
		return promotion_id;
	}

	public void setPromotion_id(Long promotion_id) {
		this.promotion_id = promotion_id;
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

}

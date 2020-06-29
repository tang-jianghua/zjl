package com.mfangsoft.zhuangjialong.app.coupons.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.BaseBrandCouponsEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月20日
* 
*/

@JsonInclude(value=Include.NON_NULL)
public class BrandCouponsModel extends BaseBrandCouponsEntity{
	
	private String spbill_create_ip;
	private String openid;
    private  Long coupons_id;
    private Long customer_id;
    private Boolean isused;
    private Date recive_time;
	private Date use_time;
	private Long brand_id;
	@JsonFormat(pattern="yyyy.MM.dd",timezone="GMT+8")
	private Date c_start;
	@JsonFormat(pattern="yyyy.MM.dd",timezone="GMT+8")
	private Date c_end;
	private Boolean enable_buy;//是否可以购买
	private String convert_code;
	private Integer count;
	
	/**
	 * @return the convert_code
	 */
	public String getConvert_code() {
		return convert_code;
	}
	/**
	 * @param convert_code the convert_code to set
	 */
	public void setConvert_code(String convert_code) {
		this.convert_code = convert_code;
	}
	/**
	 * @return the brand_id
	 */
	public Long getBrand_id() {
		return brand_id;
	}
	/**
	 * @param brand_id the brand_id to set
	 */
	public void setBrand_id(Long brand_id) {
		this.brand_id = brand_id;
	}
	/**
	 * @return the enable_buy
	 */
	public Boolean getEnable_buy() {
		return enable_buy;
	}
	/**
	 * @param enable_buy the enable_buy to set
	 */
	public void setEnable_buy(Boolean enable_buy) {
		this.enable_buy = enable_buy;
	}
	/**
	 * @return the c_start
	 */
	public Date getC_start() {
		return c_start;
	}
	/**
	 * @param c_start the c_start to set
	 */
	public void setC_start(Date c_start) {
		this.c_start = c_start;
	}
	/**
	 * @return the c_end
	 */
	public Date getC_end() {
		return c_end;
	}
	/**
	 * @param c_end the c_end to set
	 */
	public void setC_end(Date c_end) {
		this.c_end = c_end;
	}
	/**
	 * @return the coupons_id
	 */
	public Long getCoupons_id() {
		return coupons_id;
	}
	/**
	 * @param coupons_id the coupons_id to set
	 */
	public void setCoupons_id(Long coupons_id) {
		this.coupons_id = coupons_id;
	}
	/**
	 * @return the customer_id
	 */
	public Long getCustomer_id() {
		return customer_id;
	}
	/**
	 * @param customer_id the customer_id to set
	 */
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}
	
	/**
	 * @return the isused
	 */
	public Boolean getIsused() {
		return isused;
	}
	/**
	 * @param isused the isused to set
	 */
	public void setIsused(Boolean isused) {
		this.isused = isused;
	}
	/**
	 * @return the recive_time
	 */
	public Date getRecive_time() {
		return recive_time;
	}
	/**
	 * @param recive_time the recive_time to set
	 */
	public void setRecive_time(Date recive_time) {
		this.recive_time = recive_time;
	}
	/**
	 * @return the use_time
	 */
	public Date getUse_time() {
		return use_time;
	}
	/**
	 * @param use_time the use_time to set
	 */
	public void setUse_time(Date use_time) {
		this.use_time = use_time;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	
	
	
}

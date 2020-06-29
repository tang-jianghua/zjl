package com.mfangsoft.zhuangjialong.app.personalCenter.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：优惠卷
* @author：Jianghua.Tang 
* @date：2016年5月27日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class Coupons {
    private String coupons_url;
    private String name;
    private Double denomination;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date starttime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endtime;
    private String expression;
    private Integer customer_id;
    private Integer brand_id;
    private Integer state;
    
    
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
	 * @return the denomination
	 */
	public Double getDenomination() {
		return denomination;
	}

	/**
	 * @param denomination the denomination to set
	 */
	public void setDenomination(Double denomination) {
		this.denomination = denomination;
	}

	/**
	 * @return the starttime
	 */
	public Date getStarttime() {
		return starttime;
	}

	/**
	 * @param starttime the starttime to set
	 */
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	/**
	 * @return the endtime
	 */
	public Date getEndtime() {
		return endtime;
	}

	/**
	 * @param endtime the endtime to set
	 */
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	/**
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * @param expression the expression to set
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}

	/**
	 * @return the customer_id
	 */
	public Integer getCustomer_id() {
		return customer_id;
	}

	/**
	 * @param customer_id the customer_id to set
	 */
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

	/**
	 * @return the brand_id
	 */
	public Integer getBrand_id() {
		return brand_id;
	}

	/**
	 * @param brand_id the brand_id to set
	 */
	public void setBrand_id(Integer brand_id) {
		this.brand_id = brand_id;
	}

	/**
	 * @return the coupons_url
	 */
	public String getCoupons_url() {
		return coupons_url;
	}

	/**
	 * @param coupons_url the coupons_url to set
	 */
	public void setCoupons_url(String coupons_url) {
		this.coupons_url = coupons_url;
	}
    
}

package com.mfangsoft.zhuangjialong.integration.newcoupons.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class CustomerCouponsModel extends BaseCustomerCouponsEntity {

	Integer regbagType;

	private Long brand_id;
	private String name;
	private String imgurl;
	private Integer surplus_count;
	private Integer total_count;
	private Double step_value;
	/**
	 * 卷类型1：红包 2：优惠卷 column type
	 *
	 * @MLTH_generated
	 */
	private Integer type;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date start_time;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date end_time;

	/**
	 * 1:启用，2停用 column state
	 *
	 * @MLTH_generated
	 */
	private Integer state;
	private Double value;
	private Long user_id;
	private Integer note;
	private Integer scope_type;
	private String brand_name;

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public Integer getScope_type() {
		return scope_type;
	}

	public void setScope_type(Integer scope_type) {
		this.scope_type = scope_type;
	}

	public Integer getNote() {
		return note;
	}

	public void setNote(Integer note) {
		this.note = note;
	}

	public Integer getRegbagType() {
		return regbagType;
	}

	public void setRegbagType(Integer regbagType) {
		this.regbagType = regbagType;
	}

	public Long getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(Long brand_id) {
		this.brand_id = brand_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public Integer getSurplus_count() {
		return surplus_count;
	}

	public void setSurplus_count(Integer surplus_count) {
		this.surplus_count = surplus_count;
	}

	public Integer getTotal_count() {
		return total_count;
	}

	public void setTotal_count(Integer total_count) {
		this.total_count = total_count;
	}

	public Double getStep_value() {
		return step_value;
	}

	public void setStep_value(Double step_value) {
		this.step_value = step_value;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

}

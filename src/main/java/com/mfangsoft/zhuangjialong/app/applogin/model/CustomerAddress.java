package com.mfangsoft.zhuangjialong.app.applogin.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 消费者地址表 table t_app_customer_address
 *
 * @MLTH_generated do_not_delete_during_merge
 */
@JsonInclude(value = Include.NON_NULL)
public class CustomerAddress implements Serializable {

	private Integer isselfregion_area; // 0 非 1 是本市
	/**
	 * 消费者地址id column id
	 *
	 * @MLTH_generated
	 */
	private Long id;

	/**
	 * 消费者id column customer_id
	 *
	 * @MLTH_generated
	 */
	private Long customer_id;

	/**
	 * 区域代码 column region_code
	 *
	 * @MLTH_generated
	 */
	private String region_code;

	/**
	 * 地址详情 column address_details
	 *
	 * @MLTH_generated
	 */
	private String address_details;

	/**
	 * 收货人姓名 column receiver_name
	 *
	 * @MLTH_generated
	 */
	private String receiver_name;

	/**
	 * 联系电话 column phone_num
	 *
	 * @MLTH_generated
	 */
	private String phone_num;

	/**
	 * 1：默认，0：非默认 column state
	 *
	 * @MLTH_generated
	 */
	private Boolean state;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database table t_app_customer_address
	 *
	 * @MLTH_generated
	 */
	private static final long serialVersionUID = 1L;

	public Integer getIsselfregion_area() {
		return isselfregion_area;
	}

	public void setIsselfregion_area(Integer isselfregion_area) {
		this.isselfregion_area = isselfregion_area;
	}

	/**
	 * 消费者地址id
	 * 
	 * @return the value of BIGINT
	 *
	 * @MLTH_generated
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 消费者地址id
	 * 
	 * @param id
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 消费者id
	 * 
	 * @return the value of BIGINT
	 *
	 * @MLTH_generated
	 */
	public Long getCustomer_id() {
		return customer_id;
	}

	/**
	 * 消费者id
	 * 
	 * @param customer_id
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}

	/**
	 * 区域代码
	 * 
	 * @return the value of VARCHAR
	 *
	 * @MLTH_generated
	 */
	public String getRegion_code() {
		return region_code;
	}

	/**
	 * 区域代码
	 * 
	 * @param region_code
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setRegion_code(String region_code) {
		this.region_code = region_code == null ? null : region_code.trim();
	}

	/**
	 * 地址详情
	 * 
	 * @return the value of VARCHAR
	 *
	 * @MLTH_generated
	 */
	public String getAddress_details() {
		return address_details;
	}

	/**
	 * 地址详情
	 * 
	 * @param address_details
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setAddress_details(String address_details) {
		this.address_details = address_details == null ? null : address_details.trim();
	}

	/**
	 * 收货人姓名
	 * 
	 * @return the value of VARCHAR
	 *
	 * @MLTH_generated
	 */
	public String getReceiver_name() {
		return receiver_name;
	}

	/**
	 * 收货人姓名
	 * 
	 * @param receiver_name
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name == null ? null : receiver_name.trim();
	}

	/**
	 * 联系电话
	 * 
	 * @return the value of VARCHAR
	 *
	 * @MLTH_generated
	 */
	public String getPhone_num() {
		return phone_num;
	}

	/**
	 * 联系电话
	 * 
	 * @param phone_num
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num == null ? null : phone_num.trim();
	}

	/**
	 * 1：默认，0：非默认
	 * 
	 * @return the value of INTEGER
	 *
	 * @MLTH_generated
	 */
	public Boolean getState() {
		return state;
	}

	/**
	 * 1：默认，0：非默认
	 * 
	 * @param state
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setState(Boolean state) {
		this.state = state;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(id);
		sb.append(", customer_id=").append(customer_id);
		sb.append(", region_code=").append(region_code);
		sb.append(", address_details=").append(address_details);
		sb.append(", receiver_name=").append(receiver_name);
		sb.append(", phone_num=").append(phone_num);
		sb.append(", state=").append(state);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}
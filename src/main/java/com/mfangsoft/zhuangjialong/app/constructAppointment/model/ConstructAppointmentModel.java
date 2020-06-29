package com.mfangsoft.zhuangjialong.app.constructAppointment.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：施工预约model
* @author：Jianghua.Tang 
* @date：2016年7月5日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class ConstructAppointmentModel {

    private Long id;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date create_time;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date start_time;

    private Long construct_id;
    
    private String team_name;
    
    private String head_img;
    
    private String construct_name;
    
    private String phone;
    
    private String state_code;
    
    private String state;
    
    private Long customer_id;
    
    private String customer_name;
    
    private String head_url;
    
    private String  customer_phone;
    
    private Long address_id;
    
    private String region_code;
    
    private String address_details;
    
    private String receive_address;
    
    private String receive_phone;
    
    private String receiver;
    
    private String needs;

    private Double construct_data;

    private String construct_data_str;
    
    private Integer score;
    
    private String service_type;
    
    private String seller_phone;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date state_time;
    
    private List<ConstructStateRelation> constructStateRelationList;
    ////////////////////
    
    private String name;
    private Double money;
    private String account;
    private String principal;
    private Long partner_id;
    private Long enterprise_id;
    
	public Long getEnterprise_id() {
		return enterprise_id;
	}

	public void setEnterprise_id(Long enterprise_id) {
		this.enterprise_id = enterprise_id;
	}

	public Long getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(Long partner_id) {
		this.partner_id = partner_id;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getState_code() {
		return state_code;
	}

	public void setState_code(String state_code) {
		this.state_code = state_code;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public Date getState_time() {
		return state_time;
	}

	public void setState_time(Date state_time) {
		this.state_time = state_time;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the create_time
	 */
	public Date getCreate_time() {
		return create_time;
	}

	/**
	 * @param create_time the create_time to set
	 */
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	/**
	 * @return the start_time
	 */
	public Date getStart_time() {
		return start_time;
	}

	/**
	 * @param start_time the start_time to set
	 */
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	/**
	 * @return the construct_id
	 */
	public Long getConstruct_id() {
		return construct_id;
	}

	/**
	 * @param construct_id the construct_id to set
	 */
	public void setConstruct_id(Long construct_id) {
		this.construct_id = construct_id;
	}

	/**
	 * @return the team_name
	 */
	public String getTeam_name() {
		return team_name;
	}

	/**
	 * @param team_name the team_name to set
	 */
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	/**
	 * @return the head_img
	 */
	public String getHead_img() {
		return head_img;
	}

	/**
	 * @param head_img the head_img to set
	 */
	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}

	/**
	 * @return the construct_name
	 */
	public String getConstruct_name() {
		return construct_name;
	}

	/**
	 * @param construct_name the construct_name to set
	 */
	public void setConstruct_name(String construct_name) {
		this.construct_name = construct_name;
	}

	 
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
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
	 * @return the address_id
	 */
	public Long getAddress_id() {
		return address_id;
	}

	/**
	 * @param address_id the address_id to set
	 */
	public void setAddress_id(Long address_id) {
		this.address_id = address_id;
	}

	/**
	 * @return the receive_address
	 */
	public String getReceive_address() {
		return receive_address;
	}

	/**
	 * @param receive_address the receive_address to set
	 */
	public void setReceive_address(String receive_address) {
		this.receive_address = receive_address;
	}

	/**
	 * @return the receive_phone
	 */
	public String getReceive_phone() {
		return receive_phone;
	}

	/**
	 * @param receive_phone the receive_phone to set
	 */
	public void setReceive_phone(String receive_phone) {
		this.receive_phone = receive_phone;
	}

	/**
	 * @return the receiver
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return the needs
	 */
	public String getNeeds() {
		return needs;
	}

	/**
	 * @param needs the needs to set
	 */
	public void setNeeds(String needs) {
		this.needs = needs;
	}

	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * @return the region_code
	 */
	public String getRegion_code() {
		return region_code;
	}

	/**
	 * @param region_code the region_code to set
	 */
	public void setRegion_code(String region_code) {
		this.region_code = region_code;
	}

	/**
	 * @return the address_details
	 */
	public String getAddress_details() {
		return address_details;
	}

	/**
	 * @param address_details the address_details to set
	 */
	public void setAddress_details(String address_details) {
		this.address_details = address_details;
	}

	public String getCustomer_phone() {
		return customer_phone;
	}

	public void setCustomer_phone(String customer_phone) {
		this.customer_phone = customer_phone;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public String getSeller_phone() {
		return seller_phone;
	}

	public void setSeller_phone(String seller_phone) {
		this.seller_phone = seller_phone;
	}

	public List<ConstructStateRelation> getConstructStateRelationList() {
		return constructStateRelationList;
	}

	public void setConstructStateRelationList(List<ConstructStateRelation> constructStateRelationList) {
		this.constructStateRelationList = constructStateRelationList;
	}

	public String getHead_url() {
		return head_url;
	}

	public void setHead_url(String head_url) {
		this.head_url = head_url;
	}

	private Double total_price;

	/**
	 * @return the total_price
	 */
	public Double getTotal_price() {
		return total_price;
	}

	/**
	 * @param total_price the total_price to set
	 */
	public void setTotal_price(Double total_price) {
		this.total_price = total_price;
	}

	public String getConstruct_data_str() {
		return construct_data_str;
	}

	public void setConstruct_data_str(String construct_data_str) {
		this.construct_data_str = construct_data_str;
	}

	public void setConstruct_data(Double construct_data) {
		this.construct_data = construct_data;
	}

	public Double getConstruct_data() {
		return construct_data;
	}

	
	
	
}

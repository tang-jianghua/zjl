package com.mfangsoft.zhuangjialong.app.applogin.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
* @description：消费者模型
* @author：Jianghua.Tang 
* @date：2016年5月30日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class Customer {
     private Long customer_id;
     private String customer_name;
     private String customer_head;
     private Integer customer_point;
     private String customer_phone;
     private String account;
     private String pwd;
     private String  vcode;
     private String email;
     private String gender;
     @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
     private Date birthday;
     private String new_phone;
     private String new_email;
     private String new_pwd;
     private String token;
     private String key;
     private String invite_code;
     private Integer invite_count;
     private String region_code;
     private String flow;
     private Boolean flow_used_state;
     private String convert_code;
     
     
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
	 * @return the flow_used_state
	 */
	public Boolean getFlow_used_state() {
		return flow_used_state;
	}
	/**
	 * @return the flow_used_state
	 */
	public Boolean isFlow_used_state() {
		return flow_used_state;
	}
	/**
	 * @param flow_used_state the flow_used_state to set
	 */
	public void setFlow_used_state(Boolean flow_used_state) {
		this.flow_used_state = flow_used_state;
	}
	/**
	 * @return the flow
	 */
	public String getFlow() {
		return flow;
	}
	/**
	 * @param flow the flow to set
	 */
	public void setFlow(String flow) {
		this.flow = flow;
	}
	public String getRegion_code() {
		return region_code;
	}
	public void setRegion_code(String region_code) {
		this.region_code = region_code;
	}
	public Integer getInvite_count() {
		return invite_count;
	}
	public void setInvite_count(Integer invite_count) {
		this.invite_count = invite_count;
	}
	public String getInvite_code() {
		return invite_code;
	}
	public void setInvite_code(String invite_code) {
		this.invite_code = invite_code;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	/**
	 * @return the new_pwd
	 */
	public String getNew_pwd() {
		return new_pwd;
	}
	/**
	 * @param new_pwd the new_pwd to set
	 */
	public void setNew_pwd(String new_pwd) {
		this.new_pwd = new_pwd;
	}
	/**
	 * @return the new_email
	 */
	public String getNew_email() {
		return new_email;
	}
	/**
	 * @param new_email the new_email to set
	 */
	public void setNew_email(String new_email) {
		this.new_email = new_email;
	}
	/**
	 * @return the new_phone
	 */
	public String getNew_phone() {
		return new_phone;
	}
	/**
	 * @param new_phone the new_phone to set
	 */
	public void setNew_phone(String new_phone) {
		this.new_phone = new_phone;
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
	 * @return the customer_name
	 */
	public String getCustomer_name() {
		return customer_name;
	}
	/**
	 * @param customer_name the customer_name to set
	 */
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	/**
	 * @return the customer_head
	 */
	public String getCustomer_head() {
		return customer_head;
	}
	/**
	 * @param customer_head the customer_head to set
	 */
	public void setCustomer_head(String customer_head) {
		this.customer_head = customer_head;
	}
	

	/**
	 * @return the customer_point
	 */
	public Integer getCustomer_point() {
		return customer_point;
	}
	/**
	 * @param customer_point the customer_point to set
	 */
	public void setCustomer_point(Integer customer_point) {
		this.customer_point = customer_point;
	}
	/**
	 * @return the customer_phone
	 */
	public String getCustomer_phone() {
		return customer_phone;
	}
	/**
	 * @param customer_phone the customer_phone to set
	 */
	public void setCustomer_phone(String customer_phone) {
		this.customer_phone = customer_phone;
	}
	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}
	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	/**
	 * @return the vcode
	 */
	public String getVcode() {
		return vcode;
	}
	/**
	 * @param vcode the vcode to set
	 */
	public void setVcode(String vcode) {
		this.vcode = vcode;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String vkey;


	/**
	 * @return the vkey
	 */
	public String getVkey() {
		return vkey;
	}
	/**
	 * @param vkey the vkey to set
	 */
	public void setVkey(String vkey) {
		this.vkey = vkey;
	}
	
     
}

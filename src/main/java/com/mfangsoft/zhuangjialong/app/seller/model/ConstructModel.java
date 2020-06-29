package com.mfangsoft.zhuangjialong.app.seller.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月4日
* 
*/


@JsonInclude(value=Include.NON_NULL)
public class ConstructModel {
         
	private String vcode;
	private String search_content;
	private Long id;
	private String account;
	private String password;
	private String head_img;
	private String name;
	private String phone;
	private String service_region;
	private String service_code;
	private String service_type;
	private String region_code;
	private String region_name;
	private String address_details;
	private String address;
	private Double unit_price;
	private String unit_name;
	private Integer certification_state;
	private String construct_about;//施工简介
	
	private String idcard_front;
	private String idcard_back;
	private String idcard_hold;
	private String qualification_one;
	private String qualification_two;
	private String qualification_three;
	private Long expand_id;
	private Double commission_rate;
	private String ali_account;
	private Long partner_id;
	private String invite_code;//邀请码
	private Date create_time;
	private Integer score;
	private String unit;
	private String team_name;
	private Integer break_off_state;
	private Double draw_money;
	private String phone_num;
	
    private String info;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updatetime;

	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getUnit_name() {
		return unit_name;
	}
	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}
	public Double getDraw_money() {
		return draw_money;
	}
	public void setDraw_money(Double draw_money) {
		this.draw_money = draw_money;
	}
	public String getVcode() {
		return vcode;
	}
	public void setVcode(String vcode) {
		this.vcode = vcode;
	}
	public Integer getBreak_off_state() {
		return break_off_state;
	}
	public void setBreak_off_state(Integer break_off_state) {
		this.break_off_state = break_off_state;
	}
	public String getRegion_name() {
		return region_name;
	}
	public void setRegion_name(String region_name) {
		this.region_name = region_name;
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
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
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
	 * @return the search_content
	 */
	public String getSearch_content() {
		return search_content;
	}
	/**
	 * @param search_content the search_content to set
	 */
	public void setSearch_content(String search_content) {
		this.search_content = search_content;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the service_region
	 */
	public String getService_region() {
		return service_region;
	}
	/**
	 * @param service_region the service_region to set
	 */
	public void setService_region(String service_region) {
		this.service_region = service_region;
	}
	/**
	 * @return the service_code
	 */
	public String getService_code() {
		return service_code;
	}
	/**
	 * @param service_code the service_code to set
	 */
	public void setService_code(String service_code) {
		this.service_code = service_code;
	}
	/**
	 * @return the service_type
	 */
	public String getService_type() {
		return service_type;
	}
	/**
	 * @param service_type the service_type to set
	 */
	public void setService_type(String service_type) {
		this.service_type = service_type;
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
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the unit_price
	 */
	public Double getUnit_price() {
		return unit_price;
	}
	/**
	 * @param unit_price the unit_price to set
	 */
	public void setUnit_price(Double unit_price) {
		this.unit_price = unit_price;
	}
	/**
	 * @return the certification_state
	 */
	public Integer getCertification_state() {
		return certification_state;
	}
	/**
	 * @param certification_state the certification_state to set
	 */
	public void setCertification_state(Integer certification_state) {
		this.certification_state = certification_state;
	}
	/**
	 * @return the construct_about
	 */
	public String getConstruct_about() {
		return construct_about;
	}
	/**
	 * @param construct_about the construct_about to set
	 */
	public void setConstruct_about(String construct_about) {
		this.construct_about = construct_about;
	}
	/**
	 * @return the idcard_front
	 */
	public String getIdcard_front() {
		return idcard_front;
	}
	/**
	 * @param idcard_front the idcard_front to set
	 */
	public void setIdcard_front(String idcard_front) {
		this.idcard_front = idcard_front;
	}
	/**
	 * @return the idcard_back
	 */
	public String getIdcard_back() {
		return idcard_back;
	}
	/**
	 * @param idcard_back the idcard_back to set
	 */
	public void setIdcard_back(String idcard_back) {
		this.idcard_back = idcard_back;
	}
	/**
	 * @return the idcard_hold
	 */
	public String getIdcard_hold() {
		return idcard_hold;
	}
	/**
	 * @param idcard_hold the idcard_hold to set
	 */
	public void setIdcard_hold(String idcard_hold) {
		this.idcard_hold = idcard_hold;
	}
	/**
	 * @return the qualification_one
	 */
	public String getQualification_one() {
		return qualification_one;
	}
	/**
	 * @param qualification_one the qualification_one to set
	 */
	public void setQualification_one(String qualification_one) {
		this.qualification_one = qualification_one;
	}
	/**
	 * @return the qualification_two
	 */
	public String getQualification_two() {
		return qualification_two;
	}
	/**
	 * @param qualification_two the qualification_two to set
	 */
	public void setQualification_two(String qualification_two) {
		this.qualification_two = qualification_two;
	}
	/**
	 * @return the qualification_three
	 */
	public String getQualification_three() {
		return qualification_three;
	}
	/**
	 * @param qualification_three the qualification_three to set
	 */
	public void setQualification_three(String qualification_three) {
		this.qualification_three = qualification_three;
	}
	/**
	 * @return the expand_id
	 */
	public Long getExpand_id() {
		return expand_id;
	}
	/**
	 * @param expand_id the expand_id to set
	 */
	public void setExpand_id(Long expand_id) {
		this.expand_id = expand_id;
	}
	/**
	 * @return the commission_rate
	 */
	public Double getCommission_rate() {
		return commission_rate;
	}
	/**
	 * @param commission_rate the commission_rate to set
	 */
	public void setCommission_rate(Double commission_rate) {
		this.commission_rate = commission_rate;
	}
	/**
	 * @return the ali_account
	 */
	public String getAli_account() {
		return ali_account;
	}
	/**
	 * @param ali_account the ali_account to set
	 */
	public void setAli_account(String ali_account) {
		this.ali_account = ali_account;
	}
	/**
	 * @return the partner_id
	 */
	public Long getPartner_id() {
		return partner_id;
	}
	/**
	 * @param partner_id the partner_id to set
	 */
	public void setPartner_id(Long partner_id) {
		this.partner_id = partner_id;
	}
	/**
	 * @return the invite_code
	 */
	public String getInvite_code() {
		return invite_code;
	}
	/**
	 * @param invite_code the invite_code to set
	 */
	public void setInvite_code(String invite_code) {
		this.invite_code = invite_code;
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
	
	
}

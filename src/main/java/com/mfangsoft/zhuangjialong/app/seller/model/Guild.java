package com.mfangsoft.zhuangjialong.app.seller.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(value=Include.NON_NULL)
public class Guild {

	private Long id;
	private String account;
	private String password;
	private String mcode;
	private String head_img;
	private String name;
	private String phone;
	private Integer state;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date create_time;
	private Double commission_rate;
	private String ali_account;
	private Long partner_id;
	private String invite_code;//邀请码

    private String new_phone;
    private String new_email;
    private String new_password;
    private String token;
    private String key;
    
	private Long expand_id;
	private String principal;
    private Integer invite_count;
    
    private Integer seller_type;
    private String service_type;
    private Double unit_price;
    private String unit;
    
    private String certification_state;
    private String info;
    private String imageStr;
    private String vKey;
    
	public String getImageStr() {
		return imageStr;
	}

	public void setImageStr(String imageStr) {
		this.imageStr = imageStr;
	}

	public String getvKey() {
		return vKey;
	}

	public void setvKey(String vKey) {
		this.vKey = vKey;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getMcode() {
		return mcode;
	}

	public String getCertification_state() {
		return certification_state;
	}

	public void setCertification_state(String certification_state) {
		this.certification_state = certification_state;
	}

	public void setMcode(String mcode) {
		this.mcode = mcode;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public Double getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(Double unit_price) {
		this.unit_price = unit_price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public Integer getSeller_type() {
		return seller_type;
	}

	public void setSeller_type(Integer seller_type) {
		this.seller_type = seller_type;
	}

	public String getNew_phone() {
		return new_phone;
	}

	public void setNew_phone(String new_phone) {
		this.new_phone = new_phone;
	}

	public String getNew_email() {
		return new_email;
	}

	public void setNew_email(String new_email) {
		this.new_email = new_email;
	}

	public String getNew_password() {
		return new_password;
	}

	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getInvite_count() {
		return invite_count;
	}

	public void setInvite_count(Integer invite_count) {
		this.invite_count = invite_count;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHead_img() {
		return head_img;
	}

	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Double getCommission_rate() {
		return commission_rate;
	}

	public void setCommission_rate(Double commission_rate) {
		this.commission_rate = commission_rate;
	}

	public String getAli_account() {
		return ali_account;
	}

	public void setAli_account(String ali_account) {
		this.ali_account = ali_account;
	}

	public Long getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(Long partner_id) {
		this.partner_id = partner_id;
	}

	public String getInvite_code() {
		return invite_code;
	}

	public void setInvite_code(String invite_code) {
		this.invite_code = invite_code;
	}

	public Long getExpand_id() {
		return expand_id;
	}

	public void setExpand_id(Long expand_id) {
		this.expand_id = expand_id;
	}
}

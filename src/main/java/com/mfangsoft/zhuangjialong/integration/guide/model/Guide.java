package com.mfangsoft.zhuangjialong.integration.guide.model;

import java.util.Date;

public class Guide {
	
	
	private String name;
	
	private String user_account;
	
	private String phonenum;
	
	private String extension_name;
	
	private String pay_account;
	
	
	private Integer extend_count;
	
	
	private Double Income;
	
	private Date  create_time;
	
	private  Integer state;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUser_account() {
		return user_account;
	}

	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getExtension_name() {
		return extension_name;
	}

	public void setExtension_name(String extension_name) {
		this.extension_name = extension_name;
	}

	public String getPay_account() {
		return pay_account;
	}

	public void setPay_account(String pay_account) {
		this.pay_account = pay_account;
	}

	public Integer getExtend_count() {
		return extend_count;
	}

	public void setExtend_count(Integer extend_count) {
		this.extend_count = extend_count;
	}

	public Double getIncome() {
		return Income;
	}

	public void setIncome(Double income) {
		Income = income;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	

}

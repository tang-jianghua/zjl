package com.mfangsoft.zhuangjialong.integration.extension.model;

import java.util.Date;

/**
 * 推广员
 * @author Administrator
 *
 */
public class Extension{
	
	private String name;
	
	private String phonenum;
	
	private String partner_name;
	
	private Integer extend_count;
	
	private Date create_time;
	
	private Integer state;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getPartner_name() {
		return partner_name;
	}

	public void setPartner_name(String partner_name) {
		this.partner_name = partner_name;
	}

	public Integer getExtend_count() {
		return extend_count;
	}

	public void setExtend_count(Integer extend_count) {
		this.extend_count = extend_count;
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

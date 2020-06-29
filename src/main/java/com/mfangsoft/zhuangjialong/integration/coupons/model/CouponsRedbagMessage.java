package com.mfangsoft.zhuangjialong.integration.coupons.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 消息模板
 * @author Administrator
 *
 */
@JsonInclude(value = Include.NON_NULL)
public class CouponsRedbagMessage {
	
	private Long customer_id;
	private String imgurl;
	private String name;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date end_time;
	private Integer value;
	private String pushstr;
	private String platform;
	private Integer type;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public String getPushstr() {
		return pushstr;
	}
	public void setPushstr(String pushstr) {
		this.pushstr = pushstr;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	
}

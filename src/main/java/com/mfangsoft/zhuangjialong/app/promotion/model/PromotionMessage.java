package com.mfangsoft.zhuangjialong.app.promotion.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value=Include.NON_NULL)
public class PromotionMessage {

	private Long customer_id;
private Long pid;
private Long time_id;
	
	private String pushstr;
	private String platform;
	private String title;
	private String pstart_time;
	
	public Long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPstart_time() {
		return pstart_time;
	}
	public void setPstart_time(String pstart_time) {
		this.pstart_time = pstart_time;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public Long getTime_id() {
		return time_id;
	}
	public void setTime_id(Long time_id) {
		this.time_id = time_id;
	}
	
}

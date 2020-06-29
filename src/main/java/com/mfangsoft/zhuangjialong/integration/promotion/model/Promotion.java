package com.mfangsoft.zhuangjialong.integration.promotion.model;

import java.util.Date;
import java.util.List;

public class Promotion {

	private String name;
	
	private String content;
	
	private Date create_time;
	
	private Date registion_start_date;
	
	private Date registion_end_date;
	
	private Date start_date;
	
	private Date end_date;
	
	private String imgurl;
	
	private  List<String> range;
	
	private Integer range_type;

	public List<String> getRange() {
		return range;
	}

	public void setRange(List<String> range) {
		this.range = range;
	}

	public Integer getRange_type() {
		return range_type;
	}

	public void setRange_type(Integer range_type) {
		this.range_type = range_type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getRegistion_start_date() {
		return registion_start_date;
	}

	public void setRegistion_start_date(Date registion_start_date) {
		this.registion_start_date = registion_start_date;
	}

	public Date getRegistion_end_date() {
		return registion_end_date;
	}

	public void setRegistion_end_date(Date registion_end_date) {
		this.registion_end_date = registion_end_date;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	
	
	
}

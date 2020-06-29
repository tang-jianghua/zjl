package com.mfangsoft.zhuangjialong.app.personalCenter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value=Include.NON_NULL)
public class CustomerPointType {
	
	private Integer type;
	private String point_title;
	private Integer point;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getPoint_title() {
		return point_title;
	}
	public void setPoint_title(String point_title) {
		this.point_title = point_title;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	
	

}

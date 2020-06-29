package com.mfangsoft.zhuangjialong.integration.coupons.model;

public class Coupons {
	
	private String mame;	
	
	private Integer denomination;
	
	private String imgurl;	
	
	private String start_time;	
	
	private String end_time;	
	
	private String expression;	
	
	private Long brand_id;

	public String getMame() {
		return mame;
	}

	public void setMame(String mame) {
		this.mame = mame;
	}

	public Integer getDenomination() {
		return denomination;
	}

	public void setDenomination(Integer denomination) {
		this.denomination = denomination;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Long getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(Long brand_id) {
		this.brand_id = brand_id;
	}	


}

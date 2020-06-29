package com.mfangsoft.zhuangjialong.integration.introduce.model;

public class IntroduceProduct {
	
	private Long introduce_product_id;
    
    private Long product_id;
    
    private String product_title;
    
    private String imgurl;
    
    private String brand_name;

	public Long getIntroduce_product_id() {
		return introduce_product_id;
	}

	public void setIntroduce_product_id(Long introduce_product_id) {
		this.introduce_product_id = introduce_product_id;
	}

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}

	public String getProduct_title() {
		return product_title;
	}

	public void setProduct_title(String product_title) {
		this.product_title = product_title;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
    
    
}

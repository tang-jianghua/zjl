package com.mfangsoft.zhuangjialong.app.promotion.model;

import java.util.List;

import com.mfangsoft.zhuangjialong.integration.promotion.model.BrandProduct;

public class PromotionDetailParam {

	private String class_name;
	
	private List<BrandProduct> list;

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public List<BrandProduct> getList() {
		return list;
	}

	public void setList(List<BrandProduct> list) {
		this.list = list;
	}
	
	
}

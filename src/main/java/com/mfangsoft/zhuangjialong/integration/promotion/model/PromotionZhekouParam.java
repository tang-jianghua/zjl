package com.mfangsoft.zhuangjialong.integration.promotion.model;

import java.util.List;

public class PromotionZhekouParam extends PromotionZhekouEntity {

	private String brand_name;
	private List<PromotionZhekouProductEntity> zhekouProductEntity;
	
	public List<PromotionZhekouProductEntity> getZhekouProductEntity() {
		return zhekouProductEntity;
	}

	public void setZhekouProductEntity(List<PromotionZhekouProductEntity> zhekouProductEntity) {
		this.zhekouProductEntity = zhekouProductEntity;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	
	
	
	
}

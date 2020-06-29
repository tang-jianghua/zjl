package com.mfangsoft.zhuangjialong.integration.introduce.model;

import java.util.List;

public class IntroduceClass {

	private Long introduce_id;
	
    private Long class_id;
    
    private String class_name;
	
    private String materials_name;

    private List<IntroduceProduct> products;
    
	public Long getIntroduce_id() {
		return introduce_id;
	}

	public void setIntroduce_id(Long introduce_id) {
		this.introduce_id = introduce_id;
	}

	public Long getClass_id() {
		return class_id;
	}

	public void setClass_id(Long class_id) {
		this.class_id = class_id;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String getMaterials_name() {
		return materials_name;
	}

	public void setMaterials_name(String materials_name) {
		this.materials_name = materials_name;
	}

	public List<IntroduceProduct> getProducts() {
		return products;
	}

	public void setProducts(List<IntroduceProduct> products) {
		this.products = products;
	}
    
    
}

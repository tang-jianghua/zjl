package com.mfangsoft.zhuangjialong.integration.newproduct.model;

import java.util.List;

public class BrandProdAttrValueEntity extends BaseBrandProdAttrValueEntity{
	
	
	
	
	private String value_name;
	
	private ClassAttrEntity attrEntity;

	public String getValue_name() {
		return value_name;
	}

	public void setValue_name(String value_name) {
		this.value_name = value_name;
	}

	public ClassAttrEntity getAttrEntity() {
		return attrEntity;
	}

	public void setAttrEntity(ClassAttrEntity attrEntity) {
		this.attrEntity = attrEntity;
	}

	
	

}

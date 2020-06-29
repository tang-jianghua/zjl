package com.mfangsoft.zhuangjialong.integration.entnewproduct.model;

import java.io.Serializable;

import com.mfangsoft.zhuangjialong.integration.newproduct.model.ClassAttrEntity;


public class EntProdAttrValueEntity extends BaseEntProdAttrValueEntity {
	
	private String value_name;

	public String getValue_name() {
		return value_name;
	}

	public void setValue_name(String value_name) {
		this.value_name = value_name;
	}
	
	private ClassAttrEntity attrEntity;

	public ClassAttrEntity getAttrEntity() {
		return attrEntity;
	}

	public void setAttrEntity(ClassAttrEntity attrEntity) {
		this.attrEntity = attrEntity;
	}
  
}
package com.mfangsoft.zhuangjialong.integration.introduce.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class IntroduceProductParam {

	private Long class_id;
	private Long[] product_id_array;
	public Long getClass_id() {
		return class_id;
	}
	public void setClass_id(Long class_id) {
		this.class_id = class_id;
	}
	public Long[] getProduct_id_array() {
		return product_id_array;
	}
	public void setProduct_id_array(Long[] product_id_array) {
		this.product_id_array = product_id_array;
	}
	
	
}

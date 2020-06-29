package com.mfangsoft.zhuangjialong.integration.promotion.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value=Include.NON_NULL)
public class PromotionType {
	
	private Long promotion_id;
	
	private String promotion_type;
	
	private Integer is_group;

	public Long getPromotion_id() {
		return promotion_id;
	}

	public void setPromotion_id(Long promotion_id) {
		this.promotion_id = promotion_id;
	}

	public String getPromotion_type() {
		return promotion_type;
	}

	public void setPromotion_type(String promotion_type) {
		this.promotion_type = promotion_type;
	}

	public Integer getIs_group() {
		return is_group;
	}

	public void setIs_group(Integer is_group) {
		this.is_group = is_group;
	}

}

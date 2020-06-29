package com.mfangsoft.zhuangjialong.app.promotion.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.product.model.Product;

/**
* @description
* @author：Liyanchen
* @date：2016年6月12日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class PromotionProduct implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String promotion_type;
    
	public String getPromotion_type() {
		return promotion_type;
	}
	public void setPromotion_type(String promotion_type) {
		this.promotion_type = promotion_type;
	}
	
}

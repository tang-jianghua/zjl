package com.mfangsoft.zhuangjialong.integration.promotion.model;

import java.util.List;

import com.mfangsoft.zhuangjialong.app.product.model.Product;

public class UnionPromotionParam extends UnionPromotion {

	private List<Product> product;
	private boolean usedFlag;
	
	public boolean getUsedFlag() {
		return usedFlag;
	}

	public void setUsedFlag(boolean usedFlag) {
		this.usedFlag = usedFlag;
	}

	public List<Product> getProduct() {
		return product;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}
	
	
	
}
	
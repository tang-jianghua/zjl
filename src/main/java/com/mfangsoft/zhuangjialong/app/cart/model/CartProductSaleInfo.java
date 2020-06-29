package com.mfangsoft.zhuangjialong.app.cart.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class CartProductSaleInfo {

	private Long stock;
	private Double price;
	private String image_url;
	private String colorName;
	private Long material_package;

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public Long getMaterial_package() {
		return material_package;
	}

	public void setMaterial_package(Long material_package) {
		this.material_package = material_package;
	}

}

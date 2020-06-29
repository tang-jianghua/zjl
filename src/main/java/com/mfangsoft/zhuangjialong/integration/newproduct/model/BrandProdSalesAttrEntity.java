package com.mfangsoft.zhuangjialong.integration.newproduct.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class BrandProdSalesAttrEntity extends BaseBrandProdSalesAttrEntity {


	private List<BaseBrandProdSalesAttrEntity> standardArray;

	private String color_model;
	
	

	/**
	 * @return the color_model
	 */
	public String getColor_model() {
		return color_model;
	}

	/**
	 * @param color_model the color_model to set
	 */
	public void setColor_model(String color_model) {
		this.color_model = color_model;
	}

	public List<BaseBrandProdSalesAttrEntity> getStandardArray() {
		return standardArray;
	}

	public void setStandardArray(List<BaseBrandProdSalesAttrEntity> standardArray) {
		this.standardArray = standardArray;
	}

}

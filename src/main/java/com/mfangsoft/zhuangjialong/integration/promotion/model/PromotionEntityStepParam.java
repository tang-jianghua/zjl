package com.mfangsoft.zhuangjialong.integration.promotion.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.product.model.Product;
import com.mfangsoft.zhuangjialong.app.promotion.model.PromotionDetailParam;
import com.mfangsoft.zhuangjialong.common.utils.NumberUtil;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;

@JsonInclude(value=Include.NON_NULL)
public class PromotionEntityStepParam extends PromotionEntity {

	private static final long serialVersionUID = 6014178903478997692L;

	private List<PromotionStepConditionEntity> stepConditionEntityList;

	private List<Long> product_id_list;
	
	private List<BrandProduct> brandProduct; 
	
	private List<Product> ProductList;
	
	private Integer[] stateList;
	
	private Long brand_id_long;
	
	private Long partner_id_long;
	
	private Long enterprise_id;

	private List<String> imgs_list;
	
	private List<PromotionDetailParam> class_brand;

	public List<PromotionDetailParam> getClass_brand() {
		return class_brand;
	}

	public void setClass_brand(List<PromotionDetailParam> class_brand) {
		this.class_brand = class_brand;
	}

	public List<String> getImgs_list() {
		return imgs_list;
	}

	public void setImgs_list(List<String> imgs_list) {
		this.imgs_list = imgs_list;
	}

	public Long getEnterprise_id() {
		return enterprise_id;
	}

	public void setEnterprise_id(Long enterprise_id) {
		this.enterprise_id = enterprise_id;
	}

	public Long getPartner_id_long() {
		return partner_id_long;
	}

	public void setPartner_id_long(Long partner_id_long) {
		this.partner_id_long = partner_id_long;
	}

	public Long getBrand_id_long() {
		return brand_id_long;
	}

	public void setBrand_id_long(Long brand_id_long) {
		this.brand_id_long = brand_id_long;
	}

	public Integer[] getStateList() {
		return stateList;
	}

	public void setStateList(Integer[] stateList) {
		this.stateList = stateList;
	}

	public List<BrandProduct> getBrandProduct() {
		return brandProduct;
	}

	public void setBrandProduct(List<BrandProduct> brandProduct) {
		this.brandProduct = brandProduct;
	}

	public List<PromotionStepConditionEntity> getStepConditionEntityList() {
		return stepConditionEntityList;
	}

	public void setStepConditionEntityList(List<PromotionStepConditionEntity> stepConditionEntityList) {
		this.stepConditionEntityList = stepConditionEntityList;
	}

	public List<Long> getProduct_id_list() {
		return product_id_list;
	}

	public void setProduct_id_list(List<Long> product_id_list) {
		this.product_id_list = product_id_list;
	}

	public List<Product> getProductList() {
		return ProductList;
	}

	public void setProductList(List<Product> productList) {
		ProductList = productList;
	}
	
	public boolean condition(Integer num, Double price){
		
		Double total = num * price;
		
		Collections.sort(stepConditionEntityList,new Comparator<PromotionStepConditionEntity>() {
			@Override
			public int compare(PromotionStepConditionEntity o1, PromotionStepConditionEntity o2) {
				return o1.getValue1() < o2.getValue1()? 1 : -1;//降序
			}
		});
		
		if(stepConditionEntityList != null && stepConditionEntityList.size() > 0){
			for(PromotionStepConditionEntity con : stepConditionEntityList){
				// 0秒杀 1 满额折扣 2 满减折扣 3满量折扣
				if(super.getType().equals(1)){
					if(total >= con.getValue1()){
						return true;
					}
				}
				if(super.getType().equals(2)){
					if(total >= con.getValue1()){
						return true;
					}
				}
				if(super.getType().equals(3)){
					if(num >= con.getValue1() && num < con.getValue2()){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 返回打折总价，或原总价
	 */
	public PriceModel stepConditionOfProduct(Integer num, Double price) {
		Double total = num * price;
		
		Collections.sort(stepConditionEntityList,new Comparator<PromotionStepConditionEntity>() {
			@Override
			public int compare(PromotionStepConditionEntity o1, PromotionStepConditionEntity o2) {
				return o1.getValue1() < o2.getValue1()? 1 : -1;//降序
			}
		});
		Double new_price = 0D;
		Double total_price = 0D;
		PriceModel modle = new PriceModel();
		modle.setPrice(price);
		modle.setTotal_price(price * num);
		
		if(stepConditionEntityList != null && stepConditionEntityList.size() > 0){
			for(PromotionStepConditionEntity con : stepConditionEntityList){
				// 0秒杀 1 满额折扣 2 满减折扣 3满量折扣
				if(super.getType().equals(1)){
					if(total >= con.getValue1()){
						new_price = NumberUtil.round(price * con.getDiscount() < 0 ? 0 : price * con.getDiscount(), 2, BigDecimal.ROUND_HALF_DOWN);
						total_price = NumberUtil.round(new_price * num , 2, BigDecimal.ROUND_HALF_DOWN);
						modle.setPrice(new_price);
						modle.setTotal_price(total_price);
						break;
					}
				}
				if(super.getType().equals(2)){
					if(total >= con.getValue1()){
						total =  total - con.getDiscount() > 0? total - con.getDiscount() : 0;
						
						Double p= 0D;
						p = (price - con.getDiscount()/num) > 0? (price - con.getDiscount()/num) : 0;
						
						new_price = NumberUtil.round(p, 2, BigDecimal.ROUND_HALF_DOWN);
						total_price = NumberUtil.round(total , 2, BigDecimal.ROUND_HALF_DOWN);
						
						modle.setPrice(new_price);
						modle.setTotal_price(total);
						break;
					}
				}
				if(super.getType().equals(3)){
					if(num >= con.getValue1() && num < con.getValue2()){
						new_price =  NumberUtil.round(price * con.getDiscount() < 0 ? 0 : price * con.getDiscount(), 2, BigDecimal.ROUND_HALF_DOWN);
						total_price = NumberUtil.round(new_price * num , 2, BigDecimal.ROUND_HALF_DOWN);
						modle.setPrice(new_price);
						modle.setTotal_price(total_price);
						break;
					}
				}
			}
		}
		
		
		return modle;
	}
	
}

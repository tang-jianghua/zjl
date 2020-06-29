package com.mfangsoft.zhuangjialong.app.main.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.integration.introduce.model.IntroduceClassEntity;
import com.mfangsoft.zhuangjialong.integration.introduce.model.IntroduceProductEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年12月12日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class IntroduceProductModel extends IntroduceClassEntity{

	private List<IntroduceProductEntity> productEntities;//产品数组
	
	private List<Map<String, Object>> products;//产品数组
	
	private String appshow_imgurl;//品类图
	
	private String bg_color;//背景色调
	
	

	/**
	 * @return the appshow_imgurl
	 */
	public String getAppshow_imgurl() {
		return appshow_imgurl;
	}

	/**
	 * @param appshow_imgurl the appshow_imgurl to set
	 */
	public void setAppshow_imgurl(String appshow_imgurl) {
		this.appshow_imgurl = appshow_imgurl;
	}

	/**
	 * @return the bg_color
	 */
	public String getBg_color() {
		return bg_color;
	}

	/**
	 * @param bg_color the bg_color to set
	 */
	public void setBg_color(String bg_color) {
		this.bg_color = bg_color;
	}

	/**
	 * @return the productEntities
	 */
	public List<IntroduceProductEntity> getProductEntities() {
		return productEntities;
	}

	/**
	 * @param productEntities the productEntities to set
	 */
	public void setProductEntities(List<IntroduceProductEntity> productEntities) {
		this.productEntities = productEntities;
	}

	/**
	 * @return the products
	 */
	public List<Map<String, Object>> getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(List<Map<String, Object>> products) {
		this.products = products;
	}
	
	
}

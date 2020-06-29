package com.mfangsoft.zhuangjialong.app.pointmall.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerPointEntity;
import com.mfangsoft.zhuangjialong.common.model.Page;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年9月22日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class PointMallModel {
	private Long customer_id;
	private String region_code;
	private String bg_img;
	private Integer point;
	private Page<PointProductModel> products;
	private Page<CustomerPointEntity> points;
	
	
	/**
	 * @return the points
	 */
	public Page<CustomerPointEntity> getPoints() {
		return points;
	}
	/**
	 * @param points the points to set
	 */
	public void setPoints(Page<CustomerPointEntity> points) {
		this.points = points;
	}
	/**
	 * @return the customer_id
	 */
	public Long getCustomer_id() {
		return customer_id;
	}
	/**
	 * @param customer_id the customer_id to set
	 */
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}
	/**
	 * @return the region_code
	 */
	public String getRegion_code() {
		return region_code;
	}
	/**
	 * @param region_code the region_code to set
	 */
	public void setRegion_code(String region_code) {
		this.region_code = region_code;
	}
	/**
	 * @return the bg_img
	 */
	public String getBg_img() {
		return bg_img;
	}
	/**
	 * @param bg_img the bg_img to set
	 */
	public void setBg_img(String bg_img) {
		this.bg_img = bg_img;
	}
	/**
	 * @return the point
	 */
	public Integer getPoint() {
		return point;
	}
	/**
	 * @param point the point to set
	 */
	public void setPoint(Integer point) {
		this.point = point;
	}
	/**
	 * @return the products
	 */
	public Page<PointProductModel> getProducts() {
		return products;
	}
	/**
	 * @param products the products to set
	 */
	public void setProducts(Page<PointProductModel> products) {
		this.products = products;
	}
	
	
	
	
}

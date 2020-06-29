package com.mfangsoft.zhuangjialong.app.mapservice.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年12月15日
* 
*/
/**
* @description：
* @author：Jianghua.Tang 
* @date：2017年1月11日
* 
*/
/**
* @description：
* @author：Jianghua.Tang 
* @date：2017年1月11日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class SellerServiceModel extends BaseSellerServiceEntity{

	private String seller_name;//卖家名称
	
	private String head_img;//卖家头像
	
	private String brand_name;//品牌名称
	
	private String class_name;//品类名称
	
	private String shop_name;//店铺名称
	
	private String team_name;//施工队名称
	
	private String construct_type;//施工类型

	private Double distance;//距离
	
	private Long partner_id;//城市合伙人
	
	private String address;//地址
	
	private String brand_url;//品牌logo
	
	private List<SellerServiceEntity> services;//服务
	
	private String current_lbs;//临时经纬度
	
	private String phone;//联系方式


	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the current_lbs
	 */
	public String getCurrent_lbs() {
		return current_lbs;
	}

	/**
	 * @param current_lbs the current_lbs to set
	 */
	public void setCurrent_lbs(String current_lbs) {
		this.current_lbs = current_lbs;
	}

	/**
	 * @return the brand_url
	 */
	public String getBrand_url() {
		return brand_url;
	}

	/**
	 * @param brand_url the brand_url to set
	 */
	public void setBrand_url(String brand_url) {
		this.brand_url = brand_url;
	}

	/**
	 * @return the services
	 */
	public List<SellerServiceEntity> getServices() {
		return services;
	}

	/**
	 * @param services the services to set
	 */
	public void setServices(List<SellerServiceEntity> services) {
		this.services = services;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the partner_id
	 */
	public Long getPartner_id() {
		return partner_id;
	}

	/**
	 * @param partner_id the partner_id to set
	 */
	public void setPartner_id(Long partner_id) {
		this.partner_id = partner_id;
	}


	/**
	 * @return the distance
	 */
	public Double getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
	}

	/**
	 * @return the construct_type
	 */
	public String getConstruct_type() {
		return construct_type;
	}

	/**
	 * @param construct_type the construct_type to set
	 */
	public void setConstruct_type(String construct_type) {
		this.construct_type = construct_type;
	}

	/**
	 * @return the team_name
	 */
	public String getTeam_name() {
		return team_name;
	}

	/**
	 * @param team_name the team_name to set
	 */
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	/**
	 * @return the seller_name
	 */
	public String getSeller_name() {
		return seller_name;
	}

	/**
	 * @param seller_name the seller_name to set
	 */
	public void setSeller_name(String seller_name) {
		this.seller_name = seller_name;
	}

	/**
	 * @return the head_img
	 */
	public String getHead_img() {
		return head_img;
	}

	/**
	 * @param head_img the head_img to set
	 */
	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}

	/**
	 * @return the brand_name
	 */
	public String getBrand_name() {
		return brand_name;
	}

	/**
	 * @param brand_name the brand_name to set
	 */
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	/**
	 * @return the class_name
	 */
	public String getClass_name() {
		return class_name;
	}

	/**
	 * @param class_name the class_name to set
	 */
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	/**
	 * @return the shop_name
	 */
	public String getShop_name() {
		return shop_name;
	}

	/**
	 * @param shop_name the shop_name to set
	 */
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	
	
}

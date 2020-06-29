package com.mfangsoft.zhuangjialong.app.shop.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.product.model.Product;

/**
* @description：店铺模型
* @author：Jianghua.Tang 
* @date：2016年5月26日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class Shop {
   
	private String shop_name;
   private String address;
   private String telephone;
   private Double distance;
   private String brand_name;
   private Long shop_id;
   private List<Product> products;
   private Long brand_id;
   private String shop_logo;
   private Double shop_longitude;
   private Double shop_latitude;
   private Double longitude;
   private Double latitude;
   private String region_code;
   private String lbs;
   

/**
 * @return the lbs
 */
public String getLbs() {
	return lbs;
}
/**
 * @param lbs the lbs to set
 */
public void setLbs(String lbs) {
	this.lbs = lbs;
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
 * @return the shop_longitude
 */
public Double getShop_longitude() {
	return shop_longitude;
}
/**
 * @param shop_longitude the shop_longitude to set
 */
public void setShop_longitude(Double shop_longitude) {
	this.shop_longitude = shop_longitude;
}
/**
 * @return the shop_latitude
 */
public Double getShop_latitude() {
	return shop_latitude;
}
/**
 * @param shop_latitude the shop_latitude to set
 */
public void setShop_latitude(Double shop_latitude) {
	this.shop_latitude = shop_latitude;
}
/**
 * @return the longitude
 */
public Double getLongitude() {
	return longitude;
}
/**
 * @param longitude the longitude to set
 */
public void setLongitude(Double longitude) {
	this.longitude = longitude;
}
/**
 * @return the latitude
 */
public Double getLatitude() {
	return latitude;
}
/**
 * @param latitude the latitude to set
 */
public void setLatitude(Double latitude) {
	this.latitude = latitude;
}
/**
 * @return the shop_logo
 */
public String getShop_logo() {
	return shop_logo;
}
/**
 * @param shop_logo the shop_logo to set
 */
public void setShop_logo(String shop_logo) {
	this.shop_logo = shop_logo;
}



/**
 * @return the brand_id
 */
public Long getBrand_id() {
	return brand_id;
}
/**
 * @param brand_id the brand_id to set
 */
public void setBrand_id(Long brand_id) {
	this.brand_id = brand_id;
}
/**
 * @return the shop_id
 */
public Long getShop_id() {
	return shop_id;
}
/**
 * @param shop_id the shop_id to set
 */
public void setShop_id(Long shop_id) {
	this.shop_id = shop_id;
}
/**
 * @return the products
 */
public List<Product> getProducts() {
	return products;
}
/**
 * @param products the products to set
 */
public void setProducts(List<Product> products) {
	this.products = products;
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
 * @return the telephone
 */
public String getTelephone() {
	return telephone;
}
/**
 * @param telephone the telephone to set
 */
public void setTelephone(String telephone) {
	this.telephone = telephone;
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

   
}

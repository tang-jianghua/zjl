package com.mfangsoft.zhuangjialong.app.brand.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.coupons.model.BrandCouponsModel;
import com.mfangsoft.zhuangjialong.app.product.model.ListProductModel;
import com.mfangsoft.zhuangjialong.app.product.model.ProductListModel;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandBannerEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月18日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class BrandModel {
	private Long brand_id;
    private String brand_name;
    private String brand_image;
    private List<BrandCouponsModel> coupons;
    private List<BrandCouponsModel> redbags;
    private List<BrandBannerEntity> banner;
    private String package_url;
    private  Integer collect_num;
    private Double quality;
    private Double service;
    private Integer state;
    private Integer type;
    private String first_letter;
    private String bg_image;
    private List<Map<String, Object>> products;
    private List<ProductListModel> productList;
    private String hot_line;
    private Integer redbag_num;
    private Integer counpons_num;
    private String brand_main_url;
    
    
    
	/**
	 * @return the brand_main_url
	 */
	public String getBrand_main_url() {
		return brand_main_url;
	}
	/**
	 * @param brand_main_url the brand_main_url to set
	 */
	public void setBrand_main_url(String brand_main_url) {
		this.brand_main_url = brand_main_url;
	}
	/**
	 * @return the redbag_num
	 */
	public Integer getRedbag_num() {
		return redbag_num;
	}
	/**
	 * @param redbag_num the redbag_num to set
	 */
	public void setRedbag_num(Integer redbag_num) {
		this.redbag_num = redbag_num;
	}
	/**
	 * @return the counpons_num
	 */
	public Integer getCounpons_num() {
		return counpons_num;
	}
	/**
	 * @param counpons_num the counpons_num to set
	 */
	public void setCounpons_num(Integer counpons_num) {
		this.counpons_num = counpons_num;
	}
	/**
	 * @return the hot_line
	 */
	public String getHot_line() {
		return hot_line;
	}
	/**
	 * @param hot_line the hot_line to set
	 */
	public void setHot_line(String hot_line) {
		this.hot_line = hot_line;
	}
	/**
	 * @return the productList
	 */
	public List<ProductListModel> getProductList() {
		return productList;
	}
	/**
	 * @param productList the productList to set
	 */
	public void setProductList(List<ProductListModel> productList) {
		this.productList = productList;
	}
	/**
	 * @return the bg_image
	 */
	public String getBg_image() {
		return bg_image;
	}
	/**
	 * @param bg_image the bg_image to set
	 */
	public void setBg_image(String bg_image) {
		this.bg_image = bg_image;
	}
	/**
	 * @return the first_letter
	 */
	public String getFirst_letter() {
		return first_letter;
	}
	/**
	 * @param first_letter the first_letter to set
	 */
	public void setFirst_letter(String first_letter) {
		this.first_letter = first_letter;
	}
	/**
	 * @return the redbags
	 */
	public List<BrandCouponsModel> getRedbags() {
		return redbags;
	}
	/**
	 * @param redbags the redbags to set
	 */
	public void setRedbags(List<BrandCouponsModel> redbags) {
		this.redbags = redbags;
	}
	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
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
	 * @return the brand_image
	 */
	public String getBrand_image() {
		return brand_image;
	}
	/**
	 * @param brand_image the brand_image to set
	 */
	public void setBrand_image(String brand_image) {
		this.brand_image = brand_image;
	}
	

	/**
	 * @return the coupons
	 */
	public List<BrandCouponsModel> getCoupons() {
		return coupons;
	}
	/**
	 * @param coupons the coupons to set
	 */
	public void setCoupons(List<BrandCouponsModel> coupons) {
		this.coupons = coupons;
	}
	/**
	 * @return the banner
	 */
	public List<BrandBannerEntity> getBanner() {
		return banner;
	}
	/**
	 * @param banner the banner to set
	 */
	public void setBanner(List<BrandBannerEntity> banner) {
		this.banner = banner;
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
	/**
	 * @return the package_url
	 */
	public String getPackage_url() {
		return package_url;
	}
	/**
	 * @param package_url the package_url to set
	 */
	public void setPackage_url(String package_url) {
		this.package_url = package_url;
	}
	/**
	 * @return the collect_num
	 */
	public Integer getCollect_num() {
		return collect_num;
	}
	/**
	 * @param collect_num the collect_num to set
	 */
	public void setCollect_num(Integer collect_num) {
		this.collect_num = collect_num;
	}
	/**
	 * @return the quality
	 */
	public Double getQuality() {
		return quality;
	}
	/**
	 * @param quality the quality to set
	 */
	public void setQuality(Double quality) {
		this.quality = quality;
	}
	/**
	 * @return the service
	 */
	public Double getService() {
		return service;
	}
	/**
	 * @param service the service to set
	 */
	public void setService(Double service) {
		this.service = service;
	}
	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}
    
}

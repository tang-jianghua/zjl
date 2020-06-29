package com.mfangsoft.zhuangjialong.app.product.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.promotion.model.Promotion_type;

/**
* @description：首页热门产品
* @author：Jianghua.Tang 
* @date：2016年6月8日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class ListProductModel {
	  private Integer collect_id; 
      private Long product_id;
      private String image_url;
      private String product_name;
      private Long sale_num;
      private Double min_price;
      private Double min_prom_price;
      private List<Promotion_type> promotion_types;
      private Date new_time;
      
	/**
	 * @return the new_time
	 */
	public Date getNew_time() {
		return new_time;
	}
	/**
	 * @param new_time the new_time to set
	 */
	public void setNew_time(Date new_time) {
		this.new_time = new_time;
	}
	/**
	 * @return the collect_id
	 */
	public Integer getCollect_id() {
		return collect_id;
	}
	/**
	 * @param collect_id the collect_id to set
	 */
	public void setCollect_id(Integer collect_id) {
		this.collect_id = collect_id;
	}
	/**
	 * @return the product_id
	 */
	public Long getProduct_id() {
		return product_id;
	}
	/**
	 * @param product_id the product_id to set
	 */
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	/**
	 * @return the image_url
	 */
	public String getImage_url() {
		return image_url;
	}
	/**
	 * @param image_url the image_url to set
	 */
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	/**
	 * @return the product_name
	 */
	public String getProduct_name() {
		return product_name;
	}
	/**
	 * @param product_name the product_name to set
	 */
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	/**
	 * @return the sale_num
	 */
	public Long getSale_num() {
		return sale_num;
	}
	/**
	 * @param sale_num the sale_num to set
	 */
	public void setSale_num(Long sale_num) {
		this.sale_num = sale_num;
	}
	/**
	 * @return the min_price
	 */
	public Double getMin_price() {
		return min_price;
	}
	/**
	 * @param min_price the min_price to set
	 */
	public void setMin_price(Double min_price) {
		this.min_price = min_price;
	}
	/**
	 * @return the min_prom_price
	 */
	public Double getMin_prom_price() {
		return min_prom_price;
	}
	/**
	 * @param min_prom_price the min_prom_price to set
	 */
	public void setMin_prom_price(Double min_prom_price) {
		this.min_prom_price = min_prom_price;
	}

	/**
	 * @return the promotion_types
	 */
	public List<Promotion_type> getPromotion_types() {
		return promotion_types;
	}
	/**
	 * @param promotion_types the promotion_types to set
	 */
	public void setPromotion_types(List<Promotion_type> promotion_types) {
		this.promotion_types = promotion_types;
	}
      
}

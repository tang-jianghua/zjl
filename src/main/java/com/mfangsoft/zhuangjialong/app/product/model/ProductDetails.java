package com.mfangsoft.zhuangjialong.app.product.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.evaluation.model.EvaluationModel;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月22日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class ProductDetails implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1505613120868267331L;
	
	private Long id;
      private String product_name;
      private Double min_price;
      private Double max_price;
      private List<Integer> promotion_types;
      private String min_promotion_price;
      private Double max_promotion_price;
      private Long sales_num;
      private String unit;
      private Long evaluation_num;
      private EvaluationModel evaluationModel;
      private Long class_id;
      private Long brand_id;
      private Integer collect_state;
      private String hot_line;
      private String price;
      private String new_price;
      private Long product_id;
      private List<String> images;
      private EvaluationModel evaluation;
      private Integer union_promotion_id;
      private String union_title;
      private Integer limit_num;
	  private Integer person_product_num;
      private String product_detail_url;
      
      private Integer sell_type;
      private String sell_url;
	  
	public String getNew_price() {
		return new_price;
	}
	public void setNew_price(String new_price) {
		this.new_price = new_price;
	}
	/**
	 * @return the product_detail_url
	 */
	public String getProduct_detail_url() {
		return product_detail_url;
	}
	/**
	 * @param product_detail_url the product_detail_url to set
	 */
	public void setProduct_detail_url(String product_detail_url) {
		this.product_detail_url = product_detail_url;
	}
	/**
	 * @return the person_product_num
	 */
	public Integer getPerson_product_num() {
		return person_product_num;
	}
	/**
	 * @param person_product_num the person_product_num to set
	 */
	public void setPerson_product_num(Integer person_product_num) {
		this.person_product_num = person_product_num;
	}
	/**
	 * @return the limit_num
	 */
	public Integer getLimit_num() {
		return limit_num;
	}
	/**
	 * @param limit_num the limit_num to set
	 */
	public void setLimit_num(Integer limit_num) {
		this.limit_num = limit_num;
	}
	/**
	 * @return the union_promotion_id
	 */
	public Integer getUnion_promotion_id() {
		return union_promotion_id;
	}
	/**
	 * @param union_promotion_id the union_promotion_id to set
	 */
	public void setUnion_promotion_id(Integer union_promotion_id) {
		this.union_promotion_id = union_promotion_id;
	}
	/**
	 * @return the union_title
	 */
	public String getUnion_title() {
		return union_title;
	}
	/**
	 * @param union_title the union_title to set
	 */
	public void setUnion_title(String union_title) {
		this.union_title = union_title;
	}
	/**
	 * @return the evaluation
	 */
	public EvaluationModel getEvaluation() {
		return evaluation;
	}
	/**
	 * @param evaluation the evaluation to set
	 */
	public void setEvaluation(EvaluationModel evaluation) {
		this.evaluation = evaluation;
	}
	/**
	 * @return the images
	 */
	public List<String> getImages() {
		return images;
	}
	/**
	 * @param images the images to set
	 */
	public void setImages(List<String> images) {
		this.images = images;
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
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
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
	 * @return the collect_state
	 */
	public Integer getCollect_state() {
		return collect_state;
	}
	/**
	 * @param collect_state the collect_state to set
	 */
	public void setCollect_state(Integer collect_state) {
		this.collect_state = collect_state;
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
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the max_price
	 */
	public Double getMax_price() {
		return max_price;
	}
	/**
	 * @param max_price the max_price to set
	 */
	public void setMax_price(Double max_price) {
		this.max_price = max_price;
	}
	
	/**
	 * @return the promotion_types
	 */
	public List<Integer> getPromotion_types() {
		return promotion_types;
	}
	/**
	 * @param promotion_types the promotion_types to set
	 */
	public void setPromotion_types(List<Integer> promotion_types) {
		this.promotion_types = promotion_types;
	}
	
	public String getMin_promotion_price() {
		return min_promotion_price;
	}
	public void setMin_promotion_price(String min_promotion_price) {
		this.min_promotion_price = min_promotion_price;
	}
	/**
	 * @return the max_promotion_price
	 */
	public Double getMax_promotion_price() {
		return max_promotion_price;
	}
	/**
	 * @param max_promotion_price the max_promotion_price to set
	 */
	public void setMax_promotion_price(Double max_promotion_price) {
		this.max_promotion_price = max_promotion_price;
	}
	/**
	 * @return the sales_num
	 */
	public Long getSales_num() {
		return sales_num;
	}
	/**
	 * @param sales_num the sales_num to set
	 */
	public void setSales_num(Long sales_num) {
		this.sales_num = sales_num;
	}
	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * @return the evaluation_num
	 */
	public Long getEvaluation_num() {
		return evaluation_num;
	}
	/**
	 * @param evaluation_num the evaluation_num to set
	 */
	public void setEvaluation_num(Long evaluation_num) {
		this.evaluation_num = evaluation_num;
	}
	/**
	 * @return the evaluationModel
	 */
	public EvaluationModel getEvaluationModel() {
		return evaluationModel;
	}
	/**
	 * @param evaluationModel the evaluationModel to set
	 */
	public void setEvaluationModel(EvaluationModel evaluationModel) {
		this.evaluationModel = evaluationModel;
	}
	/**
	 * @return the class_id
	 */
	public Long getClass_id() {
		return class_id;
	}
	/**
	 * @param class_id the class_id to set
	 */
	public void setClass_id(Long class_id) {
		this.class_id = class_id;
	}
	public Integer getSell_type() {
		return sell_type;
	}
	public void setSell_type(Integer sell_type) {
		this.sell_type = sell_type;
	}
	public String getSell_url() {
		return sell_url;
	}
	public void setSell_url(String sell_url) {
		this.sell_url = sell_url;
	}
      
}

package com.mfangsoft.zhuangjialong.app.order.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value=Include.NON_NULL)
public class Salespropiety implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
    private Long id;

    private String color;

    private Double price;
    
    private Double old_price_sum;
    private Double unionPromotionPrice_sum;
    private Double percent;
    private Double percent_price;
    
    private String color_model;
    
    private String key_id;

    private String model;

    private String color_img;

    private Long stock;

    private Long product_id;

    private Integer material_package;
    private String standard;
    
    private String product_unit;
    
    
    public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public Double getPercent_price() {
		return percent_price;
	}

	public void setPercent_price(Double percent_price) {
		this.percent_price = percent_price;
	}

	public Double getOld_price_sum() {
		return old_price_sum;
	}

	public void setOld_price_sum(Double old_price_sum) {
		this.old_price_sum = old_price_sum;
	}

	public Double getUnionPromotionPrice_sum() {
		return unionPromotionPrice_sum;
	}

	public void setUnionPromotionPrice_sum(Double unionPromotionPrice_sum) {
		this.unionPromotionPrice_sum = unionPromotionPrice_sum;
	}

	public String getProduct_unit() {
		return product_unit;
	}

	public void setProduct_unit(String product_unit) {
		this.product_unit = product_unit;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getColor_img() {
		return color_img;
	}

	public void setColor_img(String color_img) {
		this.color_img = color_img;
	}

	//活动价
    private Double new_price;
  //活动价
    private Double sale_price;
    
	public Double getSale_price() {
		return sale_price;
	}

	public void setSale_price(Double sale_price) {
		this.sale_price = sale_price;
	}

	public Double getNew_price() {
		return new_price;
	}
	
	public void setNew_price(Double new_price) {
		this.new_price = new_price;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}

	public Integer getMaterial_package() {
		return material_package;
	}

	public void setMaterial_package(Integer material_package) {
		this.material_package = material_package;
	}
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

		/**
		 * @return the key_id
		 */
		public String getKey_id() {
			return key_id;
		}

		/**
		 * @param key_id the key_id to set
		 */
		public void setKey_id(String key_id) {
			this.key_id = key_id;
		}

	    public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}
}		

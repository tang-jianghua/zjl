package com.mfangsoft.zhuangjialong.integration.seller.model;

import java.util.List;

import com.mfangsoft.zhuangjialong.app.product.model.SalesProperty;

public class Product {

	private Long product_id;

	private Integer sales_property_id;

	private Integer product_num;

	private Double product_total_price;

	private String customer_name;
	private Integer sales_model;

	private String pay_type_str;

	// 产品标题
	private String product_title;
	// 售价
	private Double price;
	// 品牌Id
	private Integer brand_id;
	// 最小价格
	private Double min_price;
	// 最大价格
	private Double max_price;
	// 销售属性
	private List<SalesProperty> sales_properties;

	private SalesProperty sales_property;
	// 产品单位
	private String unit;
	// 产品型号
	private String model;
	private String color;
	// 产品规格
	private String standard;
	// 产品图片路径
	private String imgurl;
	private String info;

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}

	public Integer getSales_property_id() {
		return sales_property_id;
	}

	public void setSales_property_id(Integer sales_property_id) {
		this.sales_property_id = sales_property_id;
	}

	public Integer getProduct_num() {
		return product_num;
	}

	public void setProduct_num(Integer product_num) {
		this.product_num = product_num;
	}

	public Double getProduct_total_price() {
		return product_total_price;
	}

	public void setProduct_total_price(Double product_total_price) {
		this.product_total_price = product_total_price;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public Integer getSales_model() {
		return sales_model;
	}

	public void setSales_model(Integer sales_model) {
		this.sales_model = sales_model;
	}

	public String getPay_type_str() {
		return pay_type_str;
	}

	public void setPay_type_str(String pay_type_str) {
		this.pay_type_str = pay_type_str;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getProduct_title() {
		return product_title;
	}

	public void setProduct_title(String product_title) {
		this.product_title = product_title;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(Integer brand_id) {
		this.brand_id = brand_id;
	}

	public Double getMin_price() {
		return min_price;
	}

	public void setMin_price(Double min_price) {
		this.min_price = min_price;
	}

	public Double getMax_price() {
		return max_price;
	}

	public void setMax_price(Double max_price) {
		this.max_price = max_price;
	}

	public List<SalesProperty> getSales_properties() {
		return sales_properties;
	}

	public void setSales_properties(List<SalesProperty> sales_properties) {
		this.sales_properties = sales_properties;
	}

	public SalesProperty getSales_property() {
		return sales_property;
	}

	public void setSales_property(SalesProperty sales_property) {
		this.sales_property = sales_property;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

}

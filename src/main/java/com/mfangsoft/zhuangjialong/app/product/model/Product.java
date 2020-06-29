package com.mfangsoft.zhuangjialong.app.product.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.brand.model.CustomCategory;
import com.mfangsoft.zhuangjialong.app.promotion.model.Promotion_type;

/**
* @description：产品模型
* @author：Jianghua.Tang 
* @date：2016年5月26日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class Product {
	  
	private Long id;
	  //产品图片路径
      private String image_url;
      //产品标题
      private String product_name;
      //产品销量
      private Integer sale_num;
      //活动标识：1.满减....
      private List<Integer> promotion_types;
      //产品原价
      private Double old_price;
      //活动价
      private Double promotion_price;
      
      private Double new_price;
      //售价
      private Double price;
      //消费者搜索关键字
      private String search_text;
      //品类ID
      private Integer class_id;
      //品牌Id
      private Integer brand_id;
      //排序方式：热门、销量..
      private String sort_field;
     //最小价格
      private Double min_price;
      //最大价格
      private Double max_price;
      //主材ID
      private Integer advocate_id;
      //材质ID
      private Integer  material_id;
      //工艺ID
      private Integer  craft_id;
      //风格ID
      private Integer style_id;
      //文理id
      private Integer grain_id;
      //规格id
      private Integer standard_id;
      //分类数组
      private List<CustomCategory> category;
      //图片数组
      private List<String> images;
      //销售属性
      private List<SalesProperty> sales_properties;
      
      private Integer sales_property_id;
      private SalesProperty sales_property;
      //产品单位
      private String unit;
      //产品型号
      private String model;
      //产品规格
      private String standard;
      //产品详情信息
      private String product_info;
      //产品案例
      private String product_case;
      //产品参数
      private String product_specification;
      //经纬度
      private String lbs;
     //产品在某消费者购买的数量
      private Integer num;
      private Long product_id;
      private String property;
      private String info;
      private String classify;
      private Integer promotion_id;
      private Integer time_id;
      private Integer promotion_num;
      private Integer promotion_sold;
      private Integer classify_id;
      private String product_url;
      
      
      private Long customer_id;
      //该产品被添加提醒总个数
      private Integer note_count;
      private Integer state;
      private Integer note_state;
      private Long note_id;
      private Long stock;
      @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
      private Date update_time;
      private Integer person_product_num;
      private Integer bili;
      private String title;
      
      @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
  	private Date strtime;
      
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getStrtime() {
		return strtime;
	}
	public void setStrtime(Date strtime) {
		this.strtime = strtime;
	}
	public Integer getBili() {
		return bili;
	}
	public void setBili(Integer bili) {
		this.bili = bili;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getNote_count() {
		return note_count;
	}
	public void setNote_count(Integer note_count) {
		this.note_count = note_count;
	}
	
	public Long getNote_id() {
		return note_id;
	}
	public void setNote_id(Long note_id) {
		this.note_id = note_id;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
	public Integer getNote_state() {
		return note_state;
	}
	public void setNote_state(Integer note_state) {
		this.note_state = note_state;
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
	 * @return the sales_property_id
	 */
	public Integer getSales_property_id() {
		return sales_property_id;
	}
	/**
	 * @param sales_property_id the sales_property_id to set
	 */
	public void setSales_property_id(Integer sales_property_id) {
		this.sales_property_id = sales_property_id;
	}
	/**
	 * @return the product_url
	 */
	public String getProduct_url() {
		return product_url;
	}
	/**
	 * @param product_url the product_url to set
	 */
	public void setProduct_url(String product_url) {
		this.product_url = product_url;
	}
	/**
	 * @return the classify_id
	 */
	public Integer getClassify_id() {
		return classify_id;
	}
	/**
	 * @param classify_id the classify_id to set
	 */
	public void setClassify_id(Integer classify_id) {
		this.classify_id = classify_id;
	}
	/**
	 * @return the promotion_sold
	 */
	public Integer getPromotion_sold() {
		return promotion_sold;
	}
	/**
	 * @param promotion_sold the promotion_sold to set
	 */
	public void setPromotion_sold(Integer promotion_sold) {
		this.promotion_sold = promotion_sold;
	}
	/**
	 * @return the promotion_num
	 */
	public Integer getPromotion_num() {
		return promotion_num;
	}
	/**
	 * @param promotion_num the promotion_num to set
	 */
	public void setPromotion_num(Integer promotion_num) {
		this.promotion_num = promotion_num;
	}
	/**
	 * @return the promotion_id
	 */
	public Integer getPromotion_id() {
		return promotion_id;
	}
	/**
	 * @param promotion_id the promotion_id to set
	 */
	public void setPromotion_id(Integer promotion_id) {
		this.promotion_id = promotion_id;
	}
	/**
	 * @return the classify
	 */
	public String getClassify() {
		return classify;
	}
	/**
	 * @param classify the classify to set
	 */
	public void setClassify(String classify) {
		this.classify = classify;
	}
	/**
	 * @return the sales_property
	 */
	public SalesProperty getSales_property() {
		return sales_property;
	}
	/**
	 * @param sales_property the sales_property to set
	 */
	public void setSales_property(SalesProperty sales_property) {
		this.sales_property = sales_property;
	}
	
	public List<Integer> getPromotion_types() {
		return promotion_types;
	}
	public void setPromotion_types(List<Integer> promotion_types) {
		this.promotion_types = promotion_types;
	}
	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}
	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
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
	public Integer getSale_num() {
		return sale_num;
	}
	/**
	 * @param sale_num the sale_num to set
	 */
	public void setSale_num(Integer sale_num) {
		this.sale_num = sale_num;
	}
	
	/**
	 * @return the old_price
	 */
	public Double getOld_price() {
		return old_price;
	}
	/**
	 * @param old_price the old_price to set
	 */
	public void setOld_price(Double old_price) {
		this.old_price = old_price;
	}

	/**
	 * @return the promotion_price
	 */
	public Double getPromotion_price() {
		return promotion_price;
	}
	/**
	 * @param promotion_price the promotion_price to set
	 */
	public void setPromotion_price(Double promotion_price) {
		this.promotion_price = promotion_price;
	}
	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	/**
	 * @return the search_text
	 */
	public String getSearch_text() {
		return search_text;
	}
	/**
	 * @param search_text the search_text to set
	 */
	public void setSearch_text(String search_text) {
		this.search_text = search_text;
	}
	/**
	 * @return the class_id
	 */
	public Integer getClass_id() {
		return class_id;
	}
	/**
	 * @param class_id the class_id to set
	 */
	public void setClass_id(Integer class_id) {
		this.class_id = class_id;
	}
	/**
	 * @return the brand_id
	 */
	public Integer getBrand_id() {
		return brand_id;
	}
	/**
	 * @param brand_id the brand_id to set
	 */
	public void setBrand_id(Integer brand_id) {
		this.brand_id = brand_id;
	}
	/**
	 * @return the sort_field
	 */
	public String getSort_field() {
		return sort_field;
	}
	/**
	 * @param sort_field the sort_field to set
	 */
	public void setSort_field(String sort_field) {
		this.sort_field = sort_field;
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
	 * @return the advocate_id
	 */
	public Integer getAdvocate_id() {
		return advocate_id;
	}
	/**
	 * @param advocate_id the advocate_id to set
	 */
	public void setAdvocate_id(Integer advocate_id) {
		this.advocate_id = advocate_id;
	}
	/**
	 * @return the material_id
	 */
	public Integer getMaterial_id() {
		return material_id;
	}
	/**
	 * @param material_id the material_id to set
	 */
	public void setMaterial_id(Integer material_id) {
		this.material_id = material_id;
	}
	/**
	 * @return the craft_id
	 */
	public Integer getCraft_id() {
		return craft_id;
	}
	/**
	 * @param craft_id the craft_id to set
	 */
	public void setCraft_id(Integer craft_id) {
		this.craft_id = craft_id;
	}
	/**
	 * @return the style_id
	 */
	public Integer getStyle_id() {
		return style_id;
	}
	/**
	 * @param style_id the style_id to set
	 */
	public void setStyle_id(Integer style_id) {
		this.style_id = style_id;
	}
	/**
	 * @return the grain_id
	 */
	public Integer getGrain_id() {
		return grain_id;
	}
	/**
	 * @param grain_id the grain_id to set
	 */
	public void setGrain_id(Integer grain_id) {
		this.grain_id = grain_id;
	}
	/**
	 * @return the standard_id
	 */
	public Integer getStandard_id() {
		return standard_id;
	}
	/**
	 * @param standard_id the standard_id to set
	 */
	public void setStandard_id(Integer standard_id) {
		this.standard_id = standard_id;
	}
	/**
	 * @return the category
	 */
	public List<CustomCategory> getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(List<CustomCategory> category) {
		this.category = category;
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
	 * @return the sales_properties
	 */
	public List<SalesProperty> getSales_properties() {
		return sales_properties;
	}
	/**
	 * @param sales_properties the sales_properties to set
	 */
	public void setSales_properties(List<SalesProperty> sales_properties) {
		this.sales_properties = sales_properties;
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
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * @return the standard
	 */
	public String getStandard() {
		return standard;
	}
	/**
	 * @param standard the standard to set
	 */
	public void setStandard(String standard) {
		this.standard = standard;
	}
	/**
	 * @return the product_info
	 */
	public String getProduct_info() {
		return product_info;
	}
	/**
	 * @param product_info the product_info to set
	 */
	public void setProduct_info(String product_info) {
		this.product_info = product_info;
	}
	/**
	 * @return the product_case
	 */
	public String getProduct_case() {
		return product_case;
	}
	/**
	 * @param product_case the product_case to set
	 */
	public void setProduct_case(String product_case) {
		this.product_case = product_case;
	}
	/**
	 * @return the product_specification
	 */
	public String getProduct_specification() {
		return product_specification;
	}
	/**
	 * @param product_specification the product_specification to set
	 */
	public void setProduct_specification(String product_specification) {
		this.product_specification = product_specification;
	}

	/**
	 * @return the num
	 */
	public Integer getNum() {
		return num;
	}
	/**
	 * @param num the num to set
	 */
	public void setNum(Integer num) {
		this.num = num;
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
	 * @return the property
	 */
	public String getProperty() {
		return property;
	}
	/**
	 * @param property the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * 
	 */
	public Product() {
		super();
	}
	public Integer getTime_id() {
		return time_id;
	}
	public void setTime_id(Integer time_id) {
		this.time_id = time_id;
	}
	public Long getStock() {
		return stock;
	}
	public void setStock(Long stock) {
		this.stock = stock;
	}
	public Integer getPerson_product_num() {
		return person_product_num;
	}
	public void setPerson_product_num(Integer person_product_num) {
		this.person_product_num = person_product_num;
	}
	public Double getNew_price() {
		return new_price;
	}
	public void setNew_price(Double new_price) {
		this.new_price = new_price;
	}
      
}

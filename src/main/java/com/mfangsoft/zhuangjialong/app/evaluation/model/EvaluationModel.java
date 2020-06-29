package com.mfangsoft.zhuangjialong.app.evaluation.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月22日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class EvaluationModel {
  
    private Long id;

    private Long customer_id;
    
    private String customer_name;
  
    private Long order_id;
  
    private Long product_id;
    
    private Long sales_property_id;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date create_time;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date reply_time;

    private Double quality;
    
    private Double service;
    
    private Integer evaluation_level;

    private String content;
    
    private List<EvaluationImageEntity> images;
    
    private String product_info;
    
    private String color;
    
    private String model;
    
    private Integer select_id;
    
    private Integer state;
    
    private Integer isline;
    
    private String standard;
    
    private String capacity;
    
    private String eva_content;
    
    private List<String> eva_images;
    
    private String reply;
    
    
	/**
	 * @return the reply
	 */
	public String getReply() {
		return reply;
	}

	/**
	 * @param reply the reply to set
	 */
	public void setReply(String reply) {
		this.reply = reply;
	}

	/**
	 * @return the eva_content
	 */
	public String getEva_content() {
		return eva_content;
	}

	/**
	 * @param eva_content the eva_content to set
	 */
	public void setEva_content(String eva_content) {
		this.eva_content = eva_content;
	}



	/**
	 * @return the reply_time
	 */
	public Date getReply_time() {
		return reply_time;
	}

	/**
	 * @param reply_time the reply_time to set
	 */
	public void setReply_time(Date reply_time) {
		this.reply_time = reply_time;
	}

	/**
	 * @return the eva_images
	 */
	public List<String> getEva_images() {
		return eva_images;
	}

	/**
	 * @param eva_images the eva_images to set
	 */
	public void setEva_images(List<String> eva_images) {
		this.eva_images = eva_images;
	}

	/**
	 * @return the capacity
	 */
	public String getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(String capacity) {
		this.capacity = capacity;
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
	 * @return the isline
	 */
	public Integer getIsline() {
		return isline;
	}

	/**
	 * @param isline the isline to set
	 */
	public void setIsline(Integer isline) {
		this.isline = isline;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * @return the select_id
	 */
	public Integer getSelect_id() {
		return select_id;
	}

	/**
	 * @param select_id the select_id to set
	 */
	public void setSelect_id(Integer select_id) {
		this.select_id = select_id;
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
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the sales_property_id
	 */
	public Long getSales_property_id() {
		return sales_property_id;
	}

	/**
	 * @param sales_property_id the sales_property_id to set
	 */
	public void setSales_property_id(Long sales_property_id) {
		this.sales_property_id = sales_property_id;
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
	 * @return the customer_name
	 */
	public String getCustomer_name() {
		return customer_name;
	}

	/**
	 * @param customer_name the customer_name to set
	 */
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	/**
	 * @return the order_id
	 */
	public Long getOrder_id() {
		return order_id;
	}

	/**
	 * @param order_id the order_id to set
	 */
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
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
	 * @return the create_time
	 */
	public Date getCreate_time() {
		return create_time;
	}

	/**
	 * @param create_time the create_time to set
	 */
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
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
	 * @return the evaluation_level
	 */
	public Integer getEvaluation_level() {
		return evaluation_level;
	}

	/**
	 * @param evaluation_level the evaluation_level to set
	 */
	public void setEvaluation_level(Integer evaluation_level) {
		this.evaluation_level = evaluation_level;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	

	/**
	 * @return the images
	 */
	public List<EvaluationImageEntity> getImages() {
		return images;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(List<EvaluationImageEntity> images) {
		this.images = images;
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
    
    
}

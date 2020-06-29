package com.mfangsoft.zhuangjialong.app.appointment.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年5月30日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class AppointmentInfo {
	private Long id;
	private Long class_id;
     @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
     private Date start_time;
     @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
     private Date end_time;
     private Double min_price;
     private Double max_price;
     private Long brand_id;
     private Long shop_id;
     private Long style_id;
     private Long space_id;
     private String needs;
  
     private String brand_name;
     private String shop_name;
     private String style = "预约到家";
     private String space;
     private Long server_id;
     private String server;
     private String service_phone;
     private String class_name;
     private Integer state;
     private Long appointment_id;
     private String deal_info;
     
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
	/**
	 * @return the start_time
	 */
	public Date getStart_time() {
		return start_time;
	}
	/**
	 * @param start_time the start_time to set
	 */
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	/**
	 * @return the end_time
	 */
	public Date getEnd_time() {
		return end_time;
	}
	/**
	 * @param end_time the end_time to set
	 */
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
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
	 * @return the style_id
	 */
	public Long getStyle_id() {
		return style_id;
	}
	/**
	 * @param style_id the style_id to set
	 */
	public void setStyle_id(Long style_id) {
		this.style_id = style_id;
	}
	/**
	 * @return the space_id
	 */
	public Long getSpace_id() {
		return space_id;
	}
	/**
	 * @param space_id the space_id to set
	 */
	public void setSpace_id(Long space_id) {
		this.space_id = space_id;
	}
	/**
	 * @return the needs
	 */
	public String getNeeds() {
		return needs;
	}
	/**
	 * @param needs the needs to set
	 */
	public void setNeeds(String needs) {
		this.needs = needs;
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
	 * @return the style
	 */
	public String getStyle() {
		return style;
	}
	/**
	 * @param style the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}
	/**
	 * @return the space
	 */
	public String getSpace() {
		return space;
	}
	/**
	 * @param space the space to set
	 */
	public void setSpace(String space) {
		this.space = space;
	}
	/**
	 * @return the server_id
	 */
	public Long getServer_id() {
		return server_id;
	}
	/**
	 * @param server_id the server_id to set
	 */
	public void setServer_id(Long server_id) {
		this.server_id = server_id;
	}
	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}
	/**
	 * @param server the server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}
	/**
	 * @return the service_phone
	 */
	public String getService_phone() {
		return service_phone;
	}
	/**
	 * @param service_phone the service_phone to set
	 */
	public void setService_phone(String service_phone) {
		this.service_phone = service_phone;
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
	/**
	 * @return the appointment_id
	 */
	public Long getAppointment_id() {
		return appointment_id;
	}
	/**
	 * @param appointment_id the appointment_id to set
	 */
	public void setAppointment_id(Long appointment_id) {
		this.appointment_id = appointment_id;
	}
	/**
	 * @return the deal_info
	 */
	public String getDeal_info() {
		return deal_info;
	}
	/**
	 * @param deal_info the deal_info to set
	 */
	public void setDeal_info(String deal_info) {
		this.deal_info = deal_info;
	}

	
     
}

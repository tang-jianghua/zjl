package com.mfangsoft.zhuangjialong.app.appointment.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：预约模型
* @author：Jianghua.Tang 
* @date：2016年5月30日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class Appointment {
	    
	private Long id; //预约id
	
    private Long customer_id;  //消费者id
        
    private String customer_name; //消费者名称
        
    private Long address_id; //地址id
        
    private String address; //地址

    private String door_model_img; //户型图
        
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date create_time; //创建时间
       
    private List<AppointmentInfo> appointmentInfos; //预约信息
        
    private String appointment_name; //预约名
        
    private String phone; //手机号
        
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date start_time; //开始时间
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date end_time; //结束时间
 
    private Long brand_id; //品牌id
    
    private String brand_name; //品牌名称
    
    private Long class_id;//品类id
   
    private String class_name;//品类名称
    
    private Long shop_id;//店铺id
   
    private String shop_name;//店铺名称
    
    private Long style_id;//风格id
    
    private String style;// 风格
    
    private Long space_id;//空间id
    
    private String space;//空间
    
    private String needs;//需求
    
    private Double min_price;//最低价
   
    private Double max_price;//最高价
   
    private Long server_id;//店铺导购id
    
    private String server;//店铺导购名称
   
    private String service_phone;//店铺导购联系方式
   
    private Long classify_id;//分类id
   
    private String classify;//分类
   
    private Integer state;//状态
  
    private String deal_info;//处理信息 
   
    private String[] deal_info_array;//处理信息数组
    
    private String note;//备注
    
    private List<AppointmentStateInfo> states;//预约状态数组
    
    private String region_code;//区域编码
   
    private String address_details;//预约地址
   
    private String receiver_name;//预约人姓名
   
    private String phone_num;//预约电话
        
    private Long appointment_info_id;//预约信息id
    
    private Integer seen_state;//是否浏览过
    
    
    
    	/**
	 * @return the seen_state
	 */
	public Integer getSeen_state() {
		return seen_state;
	}
	/**
	 * @param seen_state the seen_state to set
	 */
	public void setSeen_state(Integer seen_state) {
		this.seen_state = seen_state;
	}
		/**
	 * @return the appointment_info_id
	 */
	public Long getAppointment_info_id() {
		return appointment_info_id;
	}
	/**
	 * @param appointment_info_id the appointment_info_id to set
	 */
	public void setAppointment_info_id(Long appointment_info_id) {
		this.appointment_info_id = appointment_info_id;
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
		 * @return the address_details
		 */
		public String getAddress_details() {
			return address_details;
		}
		/**
		 * @param address_details the address_details to set
		 */
		public void setAddress_details(String address_details) {
			this.address_details = address_details;
		}
		/**
		 * @return the receiver_name
		 */
		public String getReceiver_name() {
			return receiver_name;
		}
		/**
		 * @param receiver_name the receiver_name to set
		 */
		public void setReceiver_name(String receiver_name) {
			this.receiver_name = receiver_name;
		}
		/**
		 * @return the phone_num
		 */
		public String getPhone_num() {
			return phone_num;
		}
		/**
		 * @param phone_num the phone_num to set
		 */
		public void setPhone_num(String phone_num) {
			this.phone_num = phone_num;
		}
		public List<AppointmentStateInfo> getStates() {
    		return states;
    	}
    	public void setStates(List<AppointmentStateInfo> states) {
    		this.states = states;
    	}
        
		public Long getClass_id() {
			return class_id;
		}
		public void setClass_id(Long class_id) {
			this.class_id = class_id;
		}
		public String getClass_name() {
			return class_name;
		}
		public void setClass_name(String class_name) {
			this.class_name = class_name;
		}
		public String getNote() {
			return note;
		}
		public void setNote(String note) {
			this.note = note;
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
		 * @return the door_model_img
		 */
		public String getDoor_model_img() {
			return door_model_img;
		}
		/**
		 * @param door_model_img the door_model_img to set
		 */
		public void setDoor_model_img(String door_model_img) {
			this.door_model_img = door_model_img;
		}
		/**
		 * @return the appointment_name
		 */
		public String getAppointment_name() {
			return appointment_name;
		}
		/**
		 * @param appointment_name the appointment_name to set
		 */
		public void setAppointment_name(String appointment_name) {
			this.appointment_name = appointment_name;
		}
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
		 * @return the address_id
		 */
		public Long getAddress_id() {
			return address_id;
		}
		/**
		 * @param address_id the address_id to set
		 */
		public void setAddress_id(Long address_id) {
			this.address_id = address_id;
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
		 * @return the appointmentInfos
		 */
		public List<AppointmentInfo> getAppointmentInfos() {
			return appointmentInfos;
		}
		/**
		 * @param appointmentInfos the appointmentInfos to set
		 */
		public void setAppointmentInfos(List<AppointmentInfo> appointmentInfos) {
			this.appointmentInfos = appointmentInfos;
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
		 * @return the classify_id
		 */
		public Long getClassify_id() {
			return classify_id;
		}
		/**
		 * @param classify_id the classify_id to set
		 */
		public void setClassify_id(Long classify_id) {
			this.classify_id = classify_id;
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
		public String[] getDeal_info_array() {
			return deal_info_array;
		}
		public void setDeal_info_array(String[] deal_info_array) {
			this.deal_info_array = deal_info_array;
		}
  
		
		
        
}

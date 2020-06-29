package com.mfangsoft.zhuangjialong.integration.appointment.model;

import java.util.Date;

public class Appointment implements java.io.Serializable {

	/**
	 * 姓名
	 */
	private String customer_name;		
	/**
	 * 区域
	 */
	private String region_id;
	/**
	 * 店铺名称
	 */
	private String shop_name;		
	/**
	 * 品牌名称
	 */
	private String brand_name;		
	/**
	 * 预约状态(1:预约已提交2:预约已确认)
	 */
	private Integer appointment_state;	
	/**
	 * 户型图
	 */
	private String house_img;		
		
	/**
	 * 联系方式
	 */
	private String phone_num;		
	
	/**
	 * 预约时间
	 */
	private Date order_time	;	
	/**
	 * 服务人员（导购）
	 */
	private String guide_name;		
	/**
	 * 预约状态(1:预约已提交2:预约已确认3服务中4已完成)
	 */
	private Integer order_state;		
	/**
	 * 装修预算
	 */
	private Double budget;		
	/**
	 * 预约空间
	 */
	private String appointment_space;	
	
	
	
	
	


}

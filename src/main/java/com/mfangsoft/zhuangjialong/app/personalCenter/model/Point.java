package com.mfangsoft.zhuangjialong.app.personalCenter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年5月30日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class Point {
	
	private Long customer_id;
    private Integer appointment_point =0;
    private Integer expand_point =0;
    private Integer order_point =0;
    private Integer share_point =0;
    
    private Integer shareType;
    private Long id;//1 产品 2 品牌 3 活动 4 新闻
    private String title;
    
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getShareType() {
		return shareType;
	}
	public void setShareType(Integer shareType) {
		this.shareType = shareType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}
	public Integer getAppointment_point() {
		return appointment_point == null ? 0: appointment_point;
	}
	public void setAppointment_point(Integer appointment_point) {
		this.appointment_point = appointment_point;
	}
	public Integer getExpand_point() {
		return expand_point == null ? 0:expand_point;
	}
	public void setExpand_point(Integer expand_point) {
		this.expand_point = expand_point;
	}
	public Integer getOrder_point() {
		return order_point == null ? 0:order_point;
	}
	public void setOrder_point(Integer order_point) {
		this.order_point = order_point;
	}
	public Integer getShare_point() {
		return share_point == null ? 0:share_point;
	}
	public void setShare_point(Integer share_point) {
		this.share_point = share_point;
	}

}

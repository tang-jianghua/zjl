package com.mfangsoft.zhuangjialong.app.pointmall.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.integration.pointmall.model.BasePointConvertEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年9月22日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class ConvertProductModel extends BasePointConvertEntity{

	/*
	 * 有效状态（true:有效  false：无效）
	 */
	private Boolean valid_state;
	
	private String product_title;
	
	private String product_img;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date end_time;
    //运营商
	private String operator_code;
	//流量套餐
	private String flow_code;
	//流量编号
	private String package_num; 
	//经纬度
	private String lbs;
	
	private String mobile;
    
	private List<String> mobiles;//账号数组
    
	private List<Long> ids;
	
	
	
	/**
	 * @return the ids
	 */
	public List<Long> getIds() {
		return ids;
	}

	/**
	 * @param ids the ids to set
	 */
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	/**
	 * @return the mobiles
	 */
	public List<String> getMobiles() {
		return mobiles;
	}

	/**
	 * @param mobiles the mobiles to set
	 */
	public void setMobiles(List<String> mobiles) {
		this.mobiles = mobiles;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	 * @return the package_num
	 */
	public String getPackage_num() {
		return package_num;
	}

	/**
	 * @param package_num the package_num to set
	 */
	public void setPackage_num(String package_num) {
		this.package_num = package_num;
	}

	/**
	 * @return the operator_code
	 */
	public String getOperator_code() {
		return operator_code;
	}

	/**
	 * @param operator_code the operator_code to set
	 */
	public void setOperator_code(String operator_code) {
		this.operator_code = operator_code;
	}

	/**
	 * @return the flow_code
	 */
	public String getFlow_code() {
		return flow_code;
	}

	/**
	 * @param flow_code the flow_code to set
	 */
	public void setFlow_code(String flow_code) {
		this.flow_code = flow_code;
	}

	/**
	 * @return the valid_state
	 */
	public Boolean getValid_state() {
		return valid_state;
	}

	/**
	 * @param valid_state the valid_state to set
	 */
	public void setValid_state(Boolean valid_state) {
		this.valid_state = valid_state;
	}

	/**
	 * @return the product_title
	 */
	public String getProduct_title() {
		return product_title;
	}

	/**
	 * @param product_title the product_title to set
	 */
	public void setProduct_title(String product_title) {
		this.product_title = product_title;
	}

	/**
	 * @return the product_img
	 */
	public String getProduct_img() {
		return product_img;
	}

	/**
	 * @param product_img the product_img to set
	 */
	public void setProduct_img(String product_img) {
		this.product_img = product_img;
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
	
	
}

package com.mfangsoft.zhuangjialong.app.pointmall.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年9月26日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class FlowPackageModel {
    
	private Long id;
	
	private String package_num;
	
	private String flow;
	
	private String price;
	
	private String operator;
	
	private String operator_code;

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
	 * @return the flow
	 */
	public String getFlow() {
		return flow;
	}

	/**
	 * @param flow the flow to set
	 */
	public void setFlow(String flow) {
		this.flow = flow;
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
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
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
	
	
	
	
}

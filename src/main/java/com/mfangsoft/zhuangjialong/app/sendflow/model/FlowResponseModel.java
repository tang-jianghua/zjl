package com.mfangsoft.zhuangjialong.app.sendflow.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年9月23日
* 
*/

@JsonInclude(value=Include.NON_NULL)
public class FlowResponseModel {

	private String code;
	
	private String msg;
	
	private String batchNo;
	
	private Integer mobileNumber;
	
	private List<String> errorMobiles;
	
	private String mobiles;
	
	private String ctcc;
	
	private String cucc;
	
	private String cmcc;
	
	private String taskNo;
    
	 
	
	/**
	 * @return the taskNo
	 */
	public String getTaskNo() {
		return taskNo;
	}

	/**
	 * @param taskNo the taskNo to set
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	/**
	 * @return the mobiles
	 */
	public String getMobiles() {
		return mobiles;
	}

	/**
	 * @param mobiles the mobiles to set
	 */
	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}

	/**
	 * @return the ctcc
	 */
	public String getCtcc() {
		return ctcc;
	}

	/**
	 * @param ctcc the ctcc to set
	 */
	public void setCtcc(String ctcc) {
		this.ctcc = ctcc;
	}

	/**
	 * @return the cucc
	 */
	public String getCucc() {
		return cucc;
	}

	/**
	 * @param cucc the cucc to set
	 */
	public void setCucc(String cucc) {
		this.cucc = cucc;
	}

	/**
	 * @return the cmcc
	 */
	public String getCmcc() {
		return cmcc;
	}

	/**
	 * @param cmcc the cmcc to set
	 */
	public void setCmcc(String cmcc) {
		this.cmcc = cmcc;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * @param batchNo the batchNo to set
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * @return the mobileNumber
	 */
	public Integer getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(Integer mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @return the errorMobiles
	 */
	public List<String> getErrorMobiles() {
		return errorMobiles;
	}

	/**
	 * @param errorMobiles the errorMobiles to set
	 */
	public void setErrorMobiles(List<String> errorMobiles) {
		this.errorMobiles = errorMobiles;
	}

	
	
	
	
	
}

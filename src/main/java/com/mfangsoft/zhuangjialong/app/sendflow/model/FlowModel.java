package com.mfangsoft.zhuangjialong.app.sendflow.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年9月23日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class FlowModel {
     
	private String mobiles;//手机号
	private String taskNo;//批次号、订单号、兑换号
	private String ctcc;//电信套餐
	private String cucc;//联通套餐
	private String cmcc;//移动套餐
	
	
	
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
	
	
	
}

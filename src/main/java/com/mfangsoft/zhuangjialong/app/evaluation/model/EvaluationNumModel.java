package com.mfangsoft.zhuangjialong.app.evaluation.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月24日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class EvaluationNumModel {
	private Long total;
     private Long good_num;
     private Long general_num;
     private Long bad_num;
     private Long img_num;
	/**
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Long total) {
		this.total = total;
	}
	/**
	 * @return the good_num
	 */
	public Long getGood_num() {
		return good_num;
	}
	/**
	 * @param good_num the good_num to set
	 */
	public void setGood_num(Long good_num) {
		this.good_num = good_num;
	}
	/**
	 * @return the general_num
	 */
	public Long getGeneral_num() {
		return general_num;
	}
	/**
	 * @param general_num the general_num to set
	 */
	public void setGeneral_num(Long general_num) {
		this.general_num = general_num;
	}
	/**
	 * @return the bad_num
	 */
	public Long getBad_num() {
		return bad_num;
	}
	/**
	 * @param bad_num the bad_num to set
	 */
	public void setBad_num(Long bad_num) {
		this.bad_num = bad_num;
	}
	/**
	 * @return the img_num
	 */
	public Long getImg_num() {
		return img_num;
	}
	/**
	 * @param img_num the img_num to set
	 */
	public void setImg_num(Long img_num) {
		this.img_num = img_num;
	}
     
}

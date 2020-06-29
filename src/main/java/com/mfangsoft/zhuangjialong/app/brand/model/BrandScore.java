package com.mfangsoft.zhuangjialong.app.brand.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月18日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class BrandScore {
       private Double quality;
       private Double service;
       private Double totalQuality;
       private Double totalService;
       private Long evaluation_num;
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
	 * @return the totalQuality
	 */
	public Double getTotalQuality() {
		return totalQuality;
	}
	/**
	 * @param totalQuality the totalQuality to set
	 */
	public void setTotalQuality(Double totalQuality) {
		this.totalQuality = totalQuality;
	}
	/**
	 * @return the totalService
	 */
	public Double getTotalService() {
		return totalService;
	}
	/**
	 * @param totalService the totalService to set
	 */
	public void setTotalService(Double totalService) {
		this.totalService = totalService;
	}
	/**
	 * @return the evaluation_num
	 */
	public Long getEvaluation_num() {
		return evaluation_num;
	}
	/**
	 * @param evaluation_num the evaluation_num to set
	 */
	public void setEvaluation_num(Long evaluation_num) {
		this.evaluation_num = evaluation_num;
	}

}

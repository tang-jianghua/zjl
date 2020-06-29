package com.mfangsoft.zhuangjialong.integration.series.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2017年2月15日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class ClassifyModel {

	private String classify_name;//分类名称
	
	private List<SeriesModel> series;//系列

	/**
	 * @return the classify_name
	 */
	public String getClassify_name() {
		return classify_name;
	}

	/**
	 * @param classify_name the classify_name to set
	 */
	public void setClassify_name(String classify_name) {
		this.classify_name = classify_name;
	}

	/**
	 * @return the series
	 */
	public List<SeriesModel> getSeries() {
		return series;
	}

	/**
	 * @param series the series to set
	 */
	public void setSeries(List<SeriesModel> series) {
		this.series = series;
	}
	
	
}

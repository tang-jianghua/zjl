package com.mfangsoft.zhuangjialong.integration.series.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2017年2月15日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class SeriesModel extends BrandSeriesEntity{

     private String series_name;//系列名称
  
     private Integer product_no;//产品数量

	/**
	 * @return the series_name
	 */
	public String getSeries_name() {
		return series_name;
	}

	/**
	 * @param series_name the series_name to set
	 */
	public void setSeries_name(String series_name) {
		this.series_name = series_name;
	}

	/**
	 * @return the product_no
	 */
	public Integer getProduct_no() {
		return product_no;
	}

	/**
	 * @param product_no the product_no to set
	 */
	public void setProduct_no(Integer product_no) {
		this.product_no = product_no;
	}
     
     
     
}

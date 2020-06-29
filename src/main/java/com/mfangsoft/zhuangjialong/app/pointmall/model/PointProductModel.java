package com.mfangsoft.zhuangjialong.app.pointmall.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.integration.pointmall.model.BasePointMallEntity;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年9月22日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class PointProductModel extends BasePointMallEntity{

	private String convert_type_name;//兑换类型名称
	private Long customer_id;//消费者id
	private Integer convertedNo;//已兑换数量
	private Integer stock;//库存
	private List<ShopEntity> shops;

	
	 
	/**
	 * @return the convertedNo
	 */
	public Integer getConvertedNo() {
		return convertedNo;
	}

	/**
	 * @param convertedNo the convertedNo to set
	 */
	public void setConvertedNo(Integer convertedNo) {
		this.convertedNo = convertedNo;
	}

	/**
	 * @return the stock
	 */
	public Integer getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(Integer stock) {
		this.stock = stock;
	}

	/**
	 * @return the shops
	 */
	public List<ShopEntity> getShops() {
		return shops;
	}

	/**
	 * @param shops the shops to set
	 */
	public void setShops(List<ShopEntity> shops) {
		this.shops = shops;
	}

	/**
	 * @return the customer_id
	 */
	public Long getCustomer_id() {
		return customer_id;
	}

	/**
	 * @param customer_id the customer_id to set
	 */
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}

	/**
	 * @return the convert_type_name
	 */
	public String getConvert_type_name() {
		return convert_type_name;
	}

	/**
	 * @param convert_type_name the convert_type_name to set
	 */
	public void setConvert_type_name(String convert_type_name) {
		this.convert_type_name = convert_type_name;
	}
	

	
	
}

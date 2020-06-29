package com.mfangsoft.zhuangjialong.app.brand.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.product.model.ListProductModel;
import com.mfangsoft.zhuangjialong.app.product.model.ProductListModel;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月20日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class BrandNewProduct {
    @JsonFormat(pattern="yyyy-MM-dd" ,timezone="GMT+8")
     private Date new_time;
     private List<ProductListModel> products;
     
     
     

	/**
	 * @return the products
	 */
	public List<ProductListModel> getProducts() {
		return products;
	}
	/**
	 * @param products the products to set
	 */
	public void setProducts(List<ProductListModel> products) {
		this.products = products;
	}
	/**
	 * @return the new_time
	 */
	public Date getNew_time() {
		return new_time;
	}
	/**
	 * @param new_time the new_time to set
	 */
	public void setNew_time(Date new_time) {
		this.new_time = new_time;
	}
     
}

package com.mfangsoft.zhuangjialong.app.product.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProdSeriesEntity;
import com.mfangsoft.zhuangjialong.integration.series.model.BrandSeriesEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月10日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class AppProdSeriesModel extends BaseBrandProdSeriesEntity{

	
	private BrandSeriesEntity brandSeriesEntity;

	/**
	 * @return the brandSeriesEntity
	 */
	public BrandSeriesEntity getBrandSeriesEntity() {
		return brandSeriesEntity;
	}

	/**
	 * @param brandSeriesEntity the brandSeriesEntity to set
	 */
	public void setBrandSeriesEntity(BrandSeriesEntity brandSeriesEntity) {
		this.brandSeriesEntity = brandSeriesEntity;
	}
	
}

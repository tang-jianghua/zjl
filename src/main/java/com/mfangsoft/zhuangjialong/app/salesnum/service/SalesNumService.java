package com.mfangsoft.zhuangjialong.app.salesnum.service;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年10月18日
* 
*/

public interface SalesNumService {
	/*
	 * 定时添加销量
	 */
	void updateSalesNum();
	
	/*
	 * 增加销量
	 */
   Boolean updateSaleNum(Long product_id,Long sale_num);
}

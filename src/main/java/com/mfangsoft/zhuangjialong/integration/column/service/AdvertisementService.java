package com.mfangsoft.zhuangjialong.integration.column.service;

import java.util.List;

import com.mfangsoft.zhuangjialong.integration.column.model.BaseAdvertisement;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2017年1月6日
* 
*/

public interface AdvertisementService {

	/*
	 * 获取广告数组
	 */
	List<BaseAdvertisement> getAds();


	/*
	 * 修改广告
	 */
	Boolean modifyAd(BaseAdvertisement advertisement);

	/*
	 * 添加广告
	 */
	Boolean addAd(BaseAdvertisement advertisement);

	/*
	 * 删除广告
	 */
	Boolean deleteAd(BaseAdvertisement advertisement);
}

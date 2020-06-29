package com.mfangsoft.zhuangjialong.integration.column.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.column.model.BaseAdvertisement;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2017年1月6日
* 
*/
@WriterRepository
public interface AdvertisementMapper extends BaseAdvertisementMapper{

	/*
	 * 查询所有广告图
	 */
	List<BaseAdvertisement> selectAllAdvertisements();
	
	/*
	 * 查询所有广告图app
	 */
	List<String> selectAllAds();
	
}

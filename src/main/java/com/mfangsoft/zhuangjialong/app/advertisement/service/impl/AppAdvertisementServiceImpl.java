package com.mfangsoft.zhuangjialong.app.advertisement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.advertisement.service.AppAdvertisementService;
import com.mfangsoft.zhuangjialong.integration.column.mapper.AdvertisementMapper;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2017年1月6日
* 
*/
@Service
public class AppAdvertisementServiceImpl implements AppAdvertisementService{

	@Autowired
	AdvertisementMapper advertisementMapper;
	
	@Override
	public List<String> getAds() {
		return advertisementMapper.selectAllAds();
	}

}

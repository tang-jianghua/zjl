package com.mfangsoft.zhuangjialong.integration.column.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.column.mapper.AdvertisementMapper;
import com.mfangsoft.zhuangjialong.integration.column.model.BaseAdvertisement;
import com.mfangsoft.zhuangjialong.integration.column.service.AdvertisementService;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2017年1月6日
* 
*/
@Service
public class AdvertisementServiceImpl implements AdvertisementService{

	@Autowired
	AdvertisementMapper advertisementMapper;
	
	@Override
	public List<BaseAdvertisement> getAds() {

		return advertisementMapper.selectAllAdvertisements();
	}

	@Override
	public Boolean modifyAd(BaseAdvertisement advertisement) {
		String value = RedisUtils.getValue(SysConstant.ADVERTISEMENT_CODE);
		if(value == null){
			RedisUtils.setValue(SysConstant.ADVERTISEMENT_CODE, "0");
		}else{
			RedisUtils.setValue(SysConstant.ADVERTISEMENT_CODE, Integer.valueOf(value)+1+"");
		}
		return advertisementMapper.updateByPrimaryKeySelective(advertisement)>0;
	}

	@Override
	public Boolean addAd(BaseAdvertisement advertisement) {
		String value = RedisUtils.getValue(SysConstant.ADVERTISEMENT_CODE);
		if(value == null){
			RedisUtils.setValue(SysConstant.ADVERTISEMENT_CODE, "0");
		}else{
			RedisUtils.setValue(SysConstant.ADVERTISEMENT_CODE, Integer.valueOf(value)+1+"");
		}
		return advertisementMapper.insertSelective(advertisement)>0;
	}

	@Override
	public Boolean deleteAd(BaseAdvertisement advertisement) {
		String value = RedisUtils.getValue(SysConstant.ADVERTISEMENT_CODE);
		if(value == null){
			RedisUtils.setValue(SysConstant.ADVERTISEMENT_CODE, "0");
		}else{
			RedisUtils.setValue(SysConstant.ADVERTISEMENT_CODE, Integer.valueOf(value)+1+"");
		}
		return advertisementMapper.deleteByPrimaryKey(advertisement.getId())>0;
	}

}

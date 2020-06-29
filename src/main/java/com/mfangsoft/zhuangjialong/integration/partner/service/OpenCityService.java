package com.mfangsoft.zhuangjialong.integration.partner.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;

public interface OpenCityService {
	
	
	
	Boolean addOpenCity(Long id);
	
	
	
	List<Map<String,Object>> queryOpenCity();
	
	
	Boolean closeCity(PartnerEntity partnerEntity );
	
	Boolean checkOpenCtiy(Long id);
	
	
	Boolean pushMessage(Long id,String colesTime);
	
	/*
	 * 给该城市有订单的用户发短信
	 */
	Boolean sendSMS(Long id,String colesTime);
	
	
	Boolean forceClose(Long id);

}

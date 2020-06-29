package com.mfangsoft.zhuangjialong.integration.partner.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerModle;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;

public interface PartnerService {
	
	
	Boolean  addPartner(PartnerEntity partnerEntity) ;
	
	Boolean  modifyPartner(PartnerEntity partnerEntity) ;
	
	PartnerEntity  getPartnerById(Long id);
	
	
	Page<Map<String,Object>> queryPartnerForPage(Page<Map<String,Object>> page);
	
	
	Boolean modifyopenOrClosePartner(Long id,Integer state);
	
	
	PartnerEntity  getPartnerEntity(Long userId);
	
	List<PartnerEntity> getPartnerName();
	
	Boolean  modifyBackPartner(PartnerEntity partnerEntity) ;
	
	List<PartnerModle>  selectAllPartner();
	
	
	
	
}

	
	 
package com.mfangsoft.zhuangjialong.integration.autoComplete.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.brand.service.BrandService;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.service.EnterpriseService;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.partner.service.PartnerService;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;
import com.mfangsoft.zhuangjialong.integration.shop.service.ShopService;
@Controller
@RequestMapping("/server")
public class AutoCompleteController {
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private PartnerService partnerService;
	
	@Autowired
	private EnterpriseService enterpriseService;
	@RequestMapping("/brandname")
	@ResponseBody
	public List<String> getBrandName() {
		
		List<String>  list = new ArrayList<>();
		
		
		List<BrandEntity> brandEntities=brandService.selectBrandName();
		
		for (BrandEntity brandEntity : brandEntities) {
			list.add(brandEntity.getBrand_name());
		}
		return list;
	}
	

	@RequestMapping("/shopname")
	@ResponseBody
	public List<String> getShopName() {
		
		List<String>  list = new ArrayList<>();
	List<ShopEntity> entities	=shopService.getShopName();
	
	for (ShopEntity shopEntity : entities) {
		list.add(shopEntity.getShop_name());
	}
		return list;
	}

	@RequestMapping("/partnername")
	@ResponseBody
	public List<String> getPartnerName() {
		
		List<String>  list = new ArrayList<>();
		List<PartnerEntity> entities= partnerService.getPartnerName();
		for (PartnerEntity partnerEntity : entities) {
			list.add(partnerEntity.getPrincipal());
		}
		return list;
	}
	@RequestMapping("/enterprisename")
	@ResponseBody
	public List<String> getEnterpriseName() {
		
		List<String>  list = new ArrayList<>();
		
	List<EnterpriseEntity> enterpriseEntities	=enterpriseService.getEnterpriseName();
	
	for (EnterpriseEntity enterpriseEntity : enterpriseEntities) {
		list.add(enterpriseEntity.getEnterprise_name());
	}
		return list;
	}
	
	public List<String> getProductName() {
		return null;
	}


}

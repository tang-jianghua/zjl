package com.mfangsoft.zhuangjialong.integration.promotion.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionSeckillProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.UnionProductMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.UnionPromotionMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionProduct;
import com.mfangsoft.zhuangjialong.integration.promotion.service.UnionProductService;

@Service
public class UnionProductServiceImpl implements UnionProductService{
	@Autowired
	private UnionProductMapper unionProductMapper;
	@Autowired
	private UnionPromotionMapper unionPromotionMapper;
	@Autowired
	PromotionSeckillProductEntityMapper promotionSeckillProductEntityMapper;
	
	@Override
	public Page<Map<String, Object>> getUnionProductListPage(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		Map<String, Object> param = (Map<String, Object>) page.getParam();
		//不同权限
		switch (UserContext.getCurrentUser().getUser_type()){
		case 4://品牌管理员
			param.put("brand_id", ((BrandEntity) UserContext.getCurrentUserInfo()).getId());
			break;
		}	
		
//		BrandEntity brandEntity =(BrandEntity) UserContext.getCurrentUserInfo();
//		if (page.getParam() != null) {
//			Map<String, Object> map = (Map<String, Object>) page.getParam();
//			map.put("brand_id", brandEntity.getId());
//		} else {
//			Map<String, Object> map = new HashMap<>();
//			map.put("brand_id", brandEntity.getId());
//			page.setParam(map);
//		}
		page.setData(unionProductMapper.findUnionProductListPage(page));
		return page;
	}
	/**
	 * 不分页
	 */
	@Override
	public List<Map<String, Object>> getUnionProductList(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		return unionProductMapper.findUnionProductList(page);
	}
	@Override
	public Page<Map<String, Object>> getNotSelectedBrandProductPage(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		Map<String, Object> param = (Map<String, Object>) page.getParam();
		//不同权限
		switch (UserContext.getCurrentUser().getUser_type()){
		case 4://品牌管理员
			Map<String, Object> upMap = unionPromotionMapper.selectByPrimaryKey(Integer.parseInt(param.get("id").toString()));
			param.put("start_time", upMap.get("start_time"));
			param.put("end_time", upMap.get("end_time"));
			param.put("brand_id", ((BrandEntity) UserContext.getCurrentUserInfo()).getId());
			break;
		}	
//		if(page.getParam()==null){
//			Map<String,Object> param = new HashMap<>();
//			param.put("brand_id", ((BrandEntity)UserContext.getCurrentUserInfo()).getId());
//			page.setParam(param);
//		}else{
//			Map<String,Object> param =(Map<String, Object>) page.getParam();
//			param.put("brand_id", ((BrandEntity)UserContext.getCurrentUserInfo()).getId());
//		}
		
		
		List<String> unionProduct = unionProductMapper.selectedExistUnionPromotionBrandProduct(param);
		List<String> existScendSkillProduct = promotionSeckillProductEntityMapper
				.selectedExistScendSkillPromotionBrandProduct(param);
		List<String> existManProduct = promotionSeckillProductEntityMapper.selectedExistManPromotionBrandProduct(param);

		List<String> existProduct = new ArrayList<String>();
		existProduct.add("0");
		
		if (unionProduct != null && unionProduct.size() > 0) {
			existProduct.addAll(unionProduct);
		}
		if (existScendSkillProduct != null && existScendSkillProduct.size() > 0) {
			existProduct.addAll(existScendSkillProduct);
		}
		if (existManProduct != null && existManProduct.size() > 0) {
			existProduct.addAll(existManProduct);
		}
		
		param.put("existProductList", existProduct);
		
		page.setData(unionProductMapper.findNotSelectedBrandProductPage(page));
		return page;
	}

	@Override
	public Boolean addUnionProductList(List<UnionProduct> unionProducts) {
		// TODO Auto-generated method stub
		try{
			BrandEntity brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
			for (UnionProduct unionProduct : unionProducts){
				unionProduct.setBrand_id(brandEntity.getId());
				unionProductMapper.insertSelective(unionProduct);
			}
		}
		catch (Exception e){
			return false;
		}
		return true;
	}
}

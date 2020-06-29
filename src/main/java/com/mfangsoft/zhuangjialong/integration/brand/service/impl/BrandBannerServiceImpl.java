package com.mfangsoft.zhuangjialong.integration.brand.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.brand.model.Brand;
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BrandBannerEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BrandMainProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.HotClassifyEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.HotClassifyProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandBannerEntity;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandMainProductEntity;
import com.mfangsoft.zhuangjialong.integration.brand.model.HotClassifyEntity;
import com.mfangsoft.zhuangjialong.integration.brand.model.HotClassifyProductEntity;
import com.mfangsoft.zhuangjialong.integration.brand.service.BrandBannerService;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandNewProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;
@Service
public class BrandBannerServiceImpl implements BrandBannerService {

	@Autowired
	private BrandBannerEntityMapper bannerEntityMapper;
	
	@Autowired
	private BrandMainProductEntityMapper brandMainProductEntityMapper;
	
	@Autowired
	private HotClassifyEntityMapper  classifyEntityMapper;
	
	@Autowired
	private HotClassifyProductEntityMapper  classifyProductEntityMapper;
	
	
	@Autowired
	private BrandNewProductEntityMapper brandNewProductEntityMapper;
	
	@Override
	public Boolean addBrandBanner(BrandBannerEntity bannerEntity) {
		// TODO Auto-generated method stub
		
		BrandEntity brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
		
		bannerEntity.setBrand_id(brandEntity.getId());
		
		if(bannerEntityMapper.insertSelective(bannerEntity)>0){
			
			return true;
		}
		return false;
	}
	@Override
	public Boolean modfiyBrandBanner(BrandBannerEntity bannerEntity) {
		// TODO Auto-generated method stub
		
		if(bannerEntityMapper.updateByPrimaryKeySelective(bannerEntity)>0){
			return true;
		}
		return false;
	}
	@Override
	public List<BrandBannerEntity> queryBrandBanner() {
		// TODO Auto-generated method stub
		   BrandEntity brandEntity=(BrandEntity) UserContext.getCurrentUserInfo();
		
		return bannerEntityMapper.queryBrandBanner(brandEntity.getId());
	}
	@Override
	public Boolean addBrandMainProduct(BrandMainProductEntity brandMainProductEntity) {
		// TODO Auto-generated method stub
		
		
		if(brandMainProductEntityMapper.insert(brandMainProductEntity)>0)
			return true;
		return false;
	}
	@Override
	public Boolean modfiyBrandMainProduct(BrandMainProductEntity brandMainProductEntity) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Boolean removeBrandMainProduct(BrandMainProductEntity brandMainProductEntity) {
		// TODO Auto-generated method stub
		if (brandMainProductEntityMapper.deleteByPrimaryKey(brandMainProductEntity.getId()) > 0)
			return true;
		return false;
	}
	@Override
	public Page<Map<String, Object>> getBrandMainProduct(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		BrandEntity brandEntity =(BrandEntity) UserContext.getCurrentUserInfo();
		if (page.getParam() != null) {
			Map<String, Object> map = (Map<String, Object>) page.getParam();
			map.put("brand_id", brandEntity.getId());
		} else {
			Map<String, Object> map = new HashMap<>();
			map.put("brand_id", brandEntity.getId());
			page.setParam(map);
		}
		page.setData(brandMainProductEntityMapper.findBrandMainProductListPage(page));
		return page;
	}
	@Override
	public List<HotClassifyEntity> queryHostClassify() {
		// TODO Auto-generated method stub
		
		BrandEntity brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
		return classifyEntityMapper.queryHotClassify(brandEntity.getId());
	}
	@Override
	public Boolean addHotClassify(HotClassifyEntity classifyEntity) {
		// TODO Auto-generated method stub
		BrandEntity brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
		classifyEntity.setBrand_id(brandEntity.getId());
		if(classifyEntityMapper.insertSelective(classifyEntity)>0)
			return true;
		return false;
	}
	@Override
	public Boolean modfiyHotClassify(HotClassifyEntity classifyEntity) {
		// TODO Auto-generated method stub
		if(classifyEntityMapper.updateByPrimaryKeySelective(classifyEntity)>0)
			return true;
		return false;
	}
	@Override
	public Boolean removeHotClassify(Long id) {
		// TODO Auto-generated method stub
		if(classifyEntityMapper.deleteByPrimaryKey(id)>0)
			return true;
		return false;
	}
	@Override
	public Boolean addHotClassifyProduct(HotClassifyProductEntity classifyProductEntity) {
		// TODO Auto-generated method stub
		
		if(classifyProductEntityMapper.insertSelective(classifyProductEntity)>0){
			return true;
		}
		return false;
	}
	@Override
	public Page<Map<String, Object>> getBrandHotClassifyProductPage(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		
		page.setData(brandNewProductEntityMapper.getBrandHotClassifyProductPage(page));
		return page;
	}
	@Override
	public Boolean removeBrandBanner(Long id) {
		// TODO Auto-generated method stub
		
		
		if(bannerEntityMapper.deleteByPrimaryKey(id)>0)
		{
			return true;
		}
		return false;
	}
	@Override
	public Boolean batchDeleteBrandMainProduct(List<Long> brandMainProductIDList) {
		// TODO Auto-generated method stub
		if (brandMainProductEntityMapper.batchDeleteBrandMainProductByIDs(brandMainProductIDList) > 0)
			return true;
		return false;
	}
	
	
	

}

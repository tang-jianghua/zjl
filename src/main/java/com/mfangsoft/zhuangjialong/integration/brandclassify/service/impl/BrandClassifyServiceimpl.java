package com.mfangsoft.zhuangjialong.integration.brandclassify.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.core.exception.ServiceException;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.brandclassify.mapper.BrandClassifyEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brandclassify.model.BrandClassifyEntity;
import com.mfangsoft.zhuangjialong.integration.brandclassify.service.BrandClassifyService;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.series.mapper.BrandSeriesEntityMapper;
import com.mfangsoft.zhuangjialong.integration.series.model.BrandSeriesEntity;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;


@Service
public class BrandClassifyServiceimpl implements BrandClassifyService {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BrandClassifyServiceimpl.class); 
	
	@Autowired
	private BrandClassifyEntityMapper classifyEntityMapper;
	
	
	@Autowired
	private BrandSeriesEntityMapper brandSeriesEntityMapper;

	@Override
	public Boolean addBrandClassify(BrandClassifyEntity brandClassifyEntity)  {
		// TODO Auto-generated method stub
		
		BrandEntity  brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
		
		brandClassifyEntity.setBrand_id(brandEntity.getId());
		if(classifyEntityMapper.insert(brandClassifyEntity)>0){
			
			return true;
		}else{
			
			LOGGER.info("BrandClassify insert failure");
			
			return false;
		}
		
	}

	@Override
	public Boolean modifyBrandClassify(BrandClassifyEntity brandClassifyEntity)throws ServiceException {
		// TODO Auto-generated method stub
		
		BrandEntity  brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
		
		brandClassifyEntity.setBrand_id(brandEntity.getId());
		if(classifyEntityMapper.updateByPrimaryKeySelective(brandClassifyEntity)>0){
			
			return true;
		}else{
			
			return false;
		}
		
	}

	@Override
	public Boolean removeBrandClassify(Long id)throws ServiceException {
		// TODO Auto-generated method stub
		Map<String,Object>  map = new HashMap<>();
		
		map.put("classify_id", id);
		
		List<BrandSeriesEntity> brandSeriesEntities=brandSeriesEntityMapper.getBrandSeriesListByClassify(map);
		
		if(brandSeriesEntities!=null&&brandSeriesEntities.size()==0){
			classifyEntityMapper.deleteByPrimaryKey(id);
			return true;
		}
		return false;
		
		
	}

	@Override
	public Page<BrandClassifyEntity> queryBrandClassifiesForPage(Page<BrandClassifyEntity> page)throws ServiceException {
		// TODO Auto-generated method stub
		List<BrandClassifyEntity>  brandClassifyEntities= classifyEntityMapper.queryBrandClassifiesForPage(page);
		
		page.setData(brandClassifyEntities);
		
		return page;
	}

	@Override
	public BrandClassifyEntity getBrandClassifyById(Long id) {
		// TODO Auto-generated method stub
		return classifyEntityMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<BrandClassifyEntity> queryBrandClassifies() throws ServiceException {
		// TODO Auto-generated method stub
		
		Map<String,Object> map = new HashMap<>();
		
		UserEntity user = UserContext.getCurrentUser();
		/**
		 * 根据不同的用户查询不同分类
		 */
		if(user.getUser_type().intValue()==UserType.BRAND.getIndex().intValue()){
			
			BrandEntity  brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
			map.put("brand_id", brandEntity.getId());
			
		}else if(user.getUser_type().intValue()==UserType.ENTERPRISE.getIndex().intValue()){
			EnterpriseEntity  enterpriseEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();
			
			map.put("brand_id", "SELECT b.id FROM   t_biz_brand b,  t_biz_enterprise e, t_biz_build_enterprise be WHERE  be.id= e.build_enterpise and be.id=b.enterprise_id and e.id="+enterpriseEntity.getId());
		}
		return classifyEntityMapper.queryBrandClassifies(map);
	}

}

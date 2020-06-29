package com.mfangsoft.zhuangjialong.integration.entclassify.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.entclassify.mapper.EntClassifyEntityMapper;
import com.mfangsoft.zhuangjialong.integration.entclassify.model.EntClassifyEntity;
import com.mfangsoft.zhuangjialong.integration.entclassify.service.EntClassifyService;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.series.mapper.EntSeriesEntityMapper;
import com.mfangsoft.zhuangjialong.integration.series.model.EntSeriesEntity;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;

@Service
public class EntClassifyServiceImpl  implements EntClassifyService{

	@Autowired
	private EntClassifyEntityMapper entClassifyEntityMapper;
	
	@Autowired
	private EntSeriesEntityMapper entSeriesEntityMapper;
	
	@Override
	public Boolean addEntClassify(EntClassifyEntity entClassifyEntity) {
		// TODO Auto-generated method stub
		
		EnterpriseEntity enterpriseEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();
		
		entClassifyEntity.setEnt_id(enterpriseEntity.getId());
		if(entClassifyEntityMapper.insert(entClassifyEntity)>0){
			return true;
		}
		
		return false;
	}

	@Override
	public Boolean modifyEntClassify(EntClassifyEntity entClassifyEntity) {
		// TODO Auto-generated method stub
		EnterpriseEntity enterpriseEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();
		
		entClassifyEntity.setEnt_id(enterpriseEntity.getId());
		if(entClassifyEntityMapper.updateByPrimaryKeySelective(entClassifyEntity)>0){
			
			return true;
		}
		return false;
	}

	@Override
	public List<EntClassifyEntity> queryEntClassify() {
		// TODO Auto-generated method stub
		
		
		UserEntity user = UserContext.getCurrentUser();
		
		if(user.getUser_type().intValue()==UserType.BRAND.getIndex().intValue()){
			
			
			BrandEntity brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
			
			List<Long> list=entClassifyEntityMapper.queryent_id(brandEntity.getId());
			
			if(list!=null&&list.size()>0){
				return entClassifyEntityMapper.queryEntClassify(list.get(0));
			}
			return entClassifyEntityMapper.queryEntClassify(brandEntity.getPartnerEntity().getEnterpriseEntity().getId());
		}else if(user.getUser_type().intValue()==UserType.ENTERPRISE.getIndex().intValue()){
			
			EnterpriseEntity enterpriseEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();
			
			return entClassifyEntityMapper.queryEntClassify(enterpriseEntity.getId());
		}else{
			
			return new ArrayList<>();
		}
		
		
	}

	@Override
	public Boolean romveEntClassify(Long id) {
		// TODO Auto-generated method stub
		
		Map<String,Object> map = new HashMap<>(); 
		
		map.put("classify_id", id);
		
		List<EntSeriesEntity>  entSeriesEntities =entSeriesEntityMapper.getEntSeriesListByClassify(map);
		
		if(entSeriesEntities!=null&&entSeriesEntities.size()==0){
			entClassifyEntityMapper.deleteByPrimaryKey(id);
			return true;
		}
		
		return false;
	}

}

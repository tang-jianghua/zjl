package com.mfangsoft.zhuangjialong.integration.comment.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.evaluation.mapper.EvaluationEntityMapper;
import com.mfangsoft.zhuangjialong.app.evaluation.mapper.EvaluationImageEntityMapper;
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.comment.service.CommentService;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;
@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private EvaluationEntityMapper evaluationEntityMapper;
	
	@Autowired
	private EvaluationImageEntityMapper evaluationImageEntityMapper;
	
	
	
	
	@Override
	public Page<Map<String, Object>> getCommentListForPage(Page<Map<String, Object>> page) {
		
		this.popuPage(page);
		List<Map<String,Object>> list=evaluationEntityMapper.selectEvaluationForPage(page);
		page.setData(list);
		return page;
	}
	private  void popuPage(Page<Map<String, Object>> page){
		
		Map<String,Object> map =null;
		
		if(page.getParam()!=null){
			
			map=(Map<String, Object>) page.getParam();
		}else{
			
			map = new HashMap<>();
			
			page.setParam(map);
		}
		
		UserEntity  user= UserContext.getCurrentUser();
		
		if(user.getUser_type().intValue()==UserType.BRAND.getIndex().intValue()){
			
			
			BrandEntity  brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
			
			map.put("brand_id", brandEntity.getId());
			
		}else if(user.getUser_type().intValue()==UserType.ENTERPRISE.getIndex().intValue()){
			
			EnterpriseEntity  enterpriseEntity =(EnterpriseEntity) UserContext.getCurrentUserInfo();
			
			map.put("ent_id", enterpriseEntity.getId());
			
		}else if(user.getUser_type().intValue()==UserType.SHOP.getIndex().intValue()){
			
			ShopEntity  shopEntity =(ShopEntity) UserContext.getCurrentUserInfo();
			map.put("shop_id", shopEntity.getId());
		}else if(user.getUser_type().intValue()==UserType.PARTNER.getIndex().intValue()){
			
			PartnerEntity  shopEntity =(PartnerEntity) UserContext.getCurrentUserInfo();
			map.put("partner_id", shopEntity.getId());
			
		}
	}	


}

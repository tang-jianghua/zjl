package com.mfangsoft.zhuangjialong.integration.pointmall.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.pointmall.mapper.BaseFlowPackageEntityMapper;
import com.mfangsoft.zhuangjialong.integration.pointmall.mapper.BaseMallFlowEntityMapper;
import com.mfangsoft.zhuangjialong.integration.pointmall.mapper.BasePointConvertEntityMapper;
import com.mfangsoft.zhuangjialong.integration.pointmall.mapper.BasePointMallEntityMapper;
import com.mfangsoft.zhuangjialong.integration.pointmall.mapper.PointMallEntityMapper;
import com.mfangsoft.zhuangjialong.integration.pointmall.model.BaseFlowPackageEntity;
import com.mfangsoft.zhuangjialong.integration.pointmall.model.BaseMallFlowEntity;
import com.mfangsoft.zhuangjialong.integration.pointmall.model.BasePointConvertEntity;
import com.mfangsoft.zhuangjialong.integration.pointmall.model.BasePointMallEntity;
import com.mfangsoft.zhuangjialong.integration.pointmall.model.PointMallEntity;
import com.mfangsoft.zhuangjialong.integration.pointmall.service.PointMallService;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;

@Service
public class PointMallServiceImpl implements PointMallService {
	
	
	private   static Integer FLOW_TYPE=1;
	
	private   static Integer CONVERT_STATE_USED=2;
	@Autowired
	private BasePointMallEntityMapper basePointMallEntityMapper;
	
	@Autowired
	private BaseMallFlowEntityMapper baseMallFlowEntityMapper;
	@Autowired
	private PointMallEntityMapper pointMallEntityMapper;
	@Autowired
	private BasePointConvertEntityMapper basePointConvertEntityMapper;
	@Autowired
	private BaseFlowPackageEntityMapper  baseFlowPackageEntityMapper;
	
	

	@Override
	public Boolean addPoinMall(PointMallEntity pointMallEntity) {
		// TODO Auto-generated method stub
		
		basePointMallEntityMapper.insertSelective(pointMallEntity);
		
		if(pointMallEntity.getConvert_type().intValue()==FLOW_TYPE.intValue()){
			
			List<BaseMallFlowEntity> baseMallFlowEntities=pointMallEntity.getBaseMallFlowEntities();
			
			
			for (BaseMallFlowEntity baseMallFlowEntity : baseMallFlowEntities) {
				
				baseMallFlowEntity.setProduct_id(pointMallEntity.getId());
				
				baseMallFlowEntityMapper.insertSelective(baseMallFlowEntity);
				
			}
			
			
		}
		return true;
	}

	@Override
	public Boolean modifyPoinMall(PointMallEntity pointMallEntity) {
		// TODO Auto-generated method stub
		basePointMallEntityMapper.updateByPrimaryKeyWithBLOBs(pointMallEntity);
		if(pointMallEntity.getConvert_type().intValue()==FLOW_TYPE.intValue()){
			
			List<BaseMallFlowEntity> baseMallFlowEntities=pointMallEntity.getBaseMallFlowEntities();
			for (BaseMallFlowEntity baseMallFlowEntity : baseMallFlowEntities) {
				
				BaseMallFlowEntity  entity=baseMallFlowEntityMapper.selectByPrimaryKey(baseMallFlowEntity.getId());
				baseMallFlowEntity.setId(entity.getId());
				baseMallFlowEntityMapper.updateByPrimaryKeySelective(baseMallFlowEntity);
				
			}
		}
		return true;
	}

	@Override
	public Page<Map<String, Object>> queryPoinMall(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		
		if(UserContext.getCurrentUser().getUser_type().intValue()==UserType.SHOP.getIndex().intValue()){
			  ShopEntity shopEntity	=(ShopEntity) UserContext.getCurrentUserInfo();
			  
			  Map<String, Object> map=(Map<String, Object>) page.getParam();
			  
			  map.put("shop_id", shopEntity.getId());
			}
		page.setData(pointMallEntityMapper.queryPoinMallPage(page));
		return page;
	}

	@Override
	public List<Map<String, Object>> queryOperator() {
		// TODO Auto-generated method stub
		return pointMallEntityMapper.queryOperator();
	}

	@Override
	public List<Map<String, Object>> queryFlowPackage(String operator_code) {
		// TODO Auto-generated method stub
		return pointMallEntityMapper.queryFlowPackage(operator_code);
	}

	@Override
	public List<Map<String, Object>> queryPartner() {
		// TODO Auto-generated method stub
		return pointMallEntityMapper.queryPartner();
	}

	@Override
	public List<Map<String, Object>> queryBrand(List<Long> partnerId) {
		// TODO Auto-generated method stub
		return pointMallEntityMapper.queryBrand(partnerId);
	}

	@Override
	public List<Map<String, Object>> queryShop(List<Long> brandId) {
		// TODO Auto-generated method stub
		return pointMallEntityMapper.queryShop(brandId);
	}

	@Override
	public Page<Map<String, Object>> queryPointConverPage(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		
		
		
		if(UserContext.getCurrentUser().getUser_type().intValue()==UserType.SHOP.getIndex().intValue()){
		  ShopEntity shopEntity	=(ShopEntity) UserContext.getCurrentUserInfo();
		  
		  Map<String, Object> map=(Map<String, Object>) page.getParam();
		  
		  map.put("shop_id", shopEntity.getId());
		}
		
		page.setData(pointMallEntityMapper.queryPointConverPage(page));
		return page;
	}

	@Override
	public Long queryPointConvertByConvertCode(String convertCode) {
		// TODO Auto-generated method stub
		
		
		ShopEntity shopEntity = (ShopEntity) UserContext.getCurrentUserInfo();
		BasePointConvertEntity convertEntity =pointMallEntityMapper.querysPointConvert(convertCode,shopEntity.getId().toString());
		
		if(convertEntity!=null&&convertEntity.getId()!=null){
			
			return convertEntity.getId();
		}
		return null;
	}

	@Override
	public Boolean updatePoinConvert(Long id) {
		// TODO Auto-generated method stub
		BasePointConvertEntity convertEntity = basePointConvertEntityMapper.selectByPrimaryKey(id);
		
	    ShopEntity shopEntity = (ShopEntity) UserContext.getCurrentUserInfo();
		
	    
		BasePointConvertEntity   basePointConvertEntity = new BasePointConvertEntity();
		
		basePointConvertEntity.setConvert_state(CONVERT_STATE_USED);
		basePointConvertEntity.setId(id);
		
		basePointConvertEntity.setShop_id(shopEntity.getId());
		
		basePointConvertEntity.setConvert_time(new Date());
		
		basePointConvertEntity.setBrand_id(shopEntity.getBrandEntity().getId());
		
		basePointConvertEntity.setPartner_id(shopEntity.getBrandEntity().getCitypartner_id());
		
		basePointConvertEntity.setUser_id(UserContext.getCurrentUserId());
		
		basePointConvertEntityMapper.updateByPrimaryKeySelective(basePointConvertEntity);
		
		BasePointMallEntity basePointMallEntity =basePointMallEntityMapper.selectByPrimaryKey(convertEntity.getProduct_id());
		
		
		BasePointMallEntity  pointMallEntity = new BasePointMallEntity();
		
		
		pointMallEntity.setId(basePointMallEntity.getId());
		
		pointMallEntity.setProduct_num(basePointMallEntity.getProduct_num()-1);
		basePointMallEntityMapper.updateByPrimaryKeySelective(pointMallEntity);
		
		
		return true;
	}

	@Override
	public PointMallEntity getPoinMapById(Long id) {
		// TODO Auto-generated method stub
		
		
		PointMallEntity pointMallEntity =pointMallEntityMapper.selectAllByPrimaryKey(id);
		if(pointMallEntity.getConvert_type().intValue()==FLOW_TYPE.intValue()){
			
			
			List<BaseMallFlowEntity> list=pointMallEntityMapper.queryMallFlowByProductId(pointMallEntity.getId());
			
			
			
			for (BaseMallFlowEntity baseMallFlowEntity : list) {
				String flow=baseMallFlowEntity.getFlow_group_id();
				String flows[] = flow.split(",");
				
				List<BaseFlowPackageEntity> flowPackageEntities= new ArrayList<>();
				
				for (String flowid : flows) {
					
					BaseFlowPackageEntity baseFlowPackageEntity =baseFlowPackageEntityMapper.selectByPrimaryKey(new Long(flowid));
					flowPackageEntities.add(baseFlowPackageEntity);
				}
				baseMallFlowEntity.setFlowPackageEntities(flowPackageEntities);
			}
				
			pointMallEntity.setBaseMallFlowEntities(list);
		}
		return pointMallEntity;
	}

	@Override
	public Boolean updatePointMallProduct(Long id,Integer state) {
		// TODO Auto-generated method stub
		
		BasePointMallEntity basePointMallEntity = new BasePointMallEntity();
		
		basePointMallEntity.setId(id);
		basePointMallEntity.setState(state);
		pointMallEntityMapper.updateByPrimaryKeySelective(basePointMallEntity);
		
		return true;
	}

	@Override
	public List<Map<String, Object>> queryBrand() {
		// TODO Auto-generated method stub
		return pointMallEntityMapper.queryAllBrand();
	}

}

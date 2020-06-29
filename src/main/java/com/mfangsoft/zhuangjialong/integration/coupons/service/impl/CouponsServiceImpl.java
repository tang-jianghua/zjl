package com.mfangsoft.zhuangjialong.integration.coupons.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BrandEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.brand.service.BrandService;
import com.mfangsoft.zhuangjialong.integration.coupons.mapper.BrandCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.integration.coupons.model.BrandCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.coupons.service.CouponsService;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.newcoupons.mapper.BaseBrandCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newcoupons.mapper.BaseCustomerCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newcoupons.mapper.BaseScopeCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newcoupons.mapper.ScopeCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.BaseBrandCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.BaseCustomerCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.BaseScopeCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;
@Service
public class CouponsServiceImpl implements CouponsService {
	
	@Autowired
	private BrandCouponsEntityMapper brandCouponsEntityMapper;
	@Autowired
	private BrandService brandService;
	
	
	@Autowired 
	private  BrandEntityMapper brandEntityMapper;
	
	
	
	@Autowired
	private ScopeCouponsEntityMapper scopeCouponsEntityMapper;
	
	@Autowired
	private BaseCustomerCouponsEntityMapper baseCustomerCouponsEntityMapper;
	
	@Autowired
	BaseBrandCouponsEntityMapper baseBrandCouponsEntityMapper;

	@Override
	public Boolean addCoupons(BrandCouponsEntity brandCouponsEntity) {
		// TODO Auto-generated method stub
		
		UserEntity  userEntity = UserContext.getCurrentUser();
		if(userEntity.getUser_type()==UserType.BRAND.getIndex()){
			
			BrandEntity brandEntity=(BrandEntity) UserContext.getCurrentUserInfo();
			brandCouponsEntity.setState(1);
			brandCouponsEntity.setCreate_time(new Date());
			brandCouponsEntity.setUser_id(userEntity.getId());
			brandCouponsEntity.setSurplus_count(brandCouponsEntity.getTotal_count());
			
			brandCouponsEntity.setScope_type(2);
			brandCouponsEntityMapper.insertSelective(brandCouponsEntity);
			
			BaseScopeCouponsEntity baseScopeCouponsEntity = new BaseScopeCouponsEntity();
			
			baseScopeCouponsEntity.setBrand_id(brandEntity.getId());
			
			baseScopeCouponsEntity.setCoupons_id(brandCouponsEntity.getId());
			
			baseScopeCouponsEntity.setPartner_id(brandEntity.getCitypartner_id());
			
			scopeCouponsEntityMapper.insertSelective(baseScopeCouponsEntity);
			
			
		}else{
			

			
			brandCouponsEntity.setState(1);
			brandCouponsEntity.setCreate_time(new Date());
			brandCouponsEntity.setUser_id(userEntity.getId());
			brandCouponsEntity.setSurplus_count(brandCouponsEntity.getTotal_count());
			brandCouponsEntity.setScope_type(1);
			
			
			brandCouponsEntityMapper.insertSelective(brandCouponsEntity);
			
			List<Long> list=brandCouponsEntity.getBrand_id();
			
			for (Long id : list) {
				
				BaseScopeCouponsEntity baseScopeCouponsEntity = new BaseScopeCouponsEntity();
				
				BrandEntity brandEntity= brandEntityMapper.selectByPrimaryKey(id);
				
				//BrandEntity brandEntity=brandService.getBrandById(id);
				
				baseScopeCouponsEntity.setBrand_id(brandEntity.getId());
				
				baseScopeCouponsEntity.setCoupons_id(brandCouponsEntity.getId());
				
				baseScopeCouponsEntity.setPartner_id(brandEntity.getCitypartner_id());
				
				scopeCouponsEntityMapper.insertSelective(baseScopeCouponsEntity);
			}
			
		}
		
		return true;
	}

	@Override
	public Boolean modifyCoupons(BrandCouponsEntity brandCouponsEntity) {
		
		UserEntity  userEntity = UserContext.getCurrentUser();
		if(userEntity.getUser_type().intValue()==UserType.BRAND.getIndex().intValue()){
			
			BrandEntity brandEntity=(BrandEntity) UserContext.getCurrentUserInfo();
			brandCouponsEntity.setState(1);
			brandCouponsEntity.setCreate_time(new Date());
			brandCouponsEntity.setUser_id(userEntity.getId());
			brandCouponsEntity.setSurplus_count(brandCouponsEntity.getTotal_count());
			
			brandCouponsEntity.setScope_type(2);
			brandCouponsEntityMapper.updateByPrimaryKeySelective(brandCouponsEntity);
			
			
			BaseScopeCouponsEntity baseScopeCouponsEntity = new BaseScopeCouponsEntity();
			
			baseScopeCouponsEntity.setBrand_id(brandEntity.getId());
			
			baseScopeCouponsEntity.setCoupons_id(brandCouponsEntity.getId());
			
			baseScopeCouponsEntity.setPartner_id(brandEntity.getCitypartner_id());
			
			scopeCouponsEntityMapper.insertSelective(baseScopeCouponsEntity);
			
		}else{
			
			brandCouponsEntity.setState(1);
			brandCouponsEntity.setCreate_time(new Date());
			brandCouponsEntity.setUser_id(userEntity.getId());
			brandCouponsEntity.setSurplus_count(brandCouponsEntity.getTotal_count());
			
			brandCouponsEntity.setScope_type(1);
			brandCouponsEntityMapper.updateByPrimaryKeySelective(brandCouponsEntity);
			
			List<Long> list = brandCouponsEntity.getBrand_id();
			scopeCouponsEntityMapper.deleteScopeCouponsByCouponsId(brandCouponsEntity.getId());
			for (Long id : list) {
				
				BrandEntity brandEntity=brandEntityMapper.selectByPrimaryKey(id);
					BaseScopeCouponsEntity scopeCouponsEntity = new BaseScopeCouponsEntity();
					
					scopeCouponsEntity.setBrand_id(brandEntity.getId());
					
					scopeCouponsEntity.setCoupons_id(brandCouponsEntity.getId());
					
					scopeCouponsEntity.setPartner_id(brandEntity.getCitypartner_id());
					
					scopeCouponsEntityMapper.insertSelective(scopeCouponsEntity);
			}
			
			
		}
		return true;
	}

	@Override
	public Page<Map<String,Object>> queryCoupons(Page<Map<String,Object>> page) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		Map<String, Object> param = (Map<String, Object>) page.getParam();
		switch (UserContext.getCurrentUser().getUser_type()){
		case 1://开发者
			
			//param.put("scope_type", 1);
			break;
		case 4://品牌管理员
			param.put("brand_id", ((BrandEntity) UserContext.getCurrentUserInfo()).getId());
			
			param.put("scope_type", 2);
			break;
		}	
		page.setParam(param);
		
		page.setData(brandCouponsEntityMapper.queryCouponsForPage(page));
		return page;
	}

	@Override
	public BrandCouponsEntity getCouponsById(Long id) {
		// TODO Auto-generated method stub
		
		
		BaseBrandCouponsEntity baseBrandCouponsEntity=brandCouponsEntityMapper.selectByPrimaryKey(id);
		//BrandCouponsEntity baseBrandCouponsEntity=(BrandCouponsEntity) brandCouponsEntityMapper.selectByPrimaryKey(id);
		UserEntity  userEntity = UserContext.getCurrentUser();
		if(userEntity.getUser_type().intValue()==UserType.BRAND.getIndex().intValue()){
			
			return (BrandCouponsEntity) baseBrandCouponsEntity;
			
			
		}else{
			
			BrandCouponsEntity a=(BrandCouponsEntity) baseBrandCouponsEntity;
			a.setBrand_id(scopeCouponsEntityMapper.selectScopeCouponsByCouponsId(id));
			return a;
			
		}
		
		
	}

	@Override
	public Map<String,Object> queryCouponsByName(BaseBrandCouponsEntity couponsEntity) {
		// TODO Auto-generated method stub
		
		Map<String,Object> parammap = new HashMap<>();
		if(UserContext.getCurrentUser().getUser_type().intValue()==UserType.BRAND.getIndex().intValue()){
			BrandEntity brand = (BrandEntity) UserContext.getCurrentUserInfo();
			//couponsEntity.setBrand_id(brand.getId());
			 parammap.put("brand_id", brand.getId());
			 
			 parammap.put("scope_type", 2);
			 
			 parammap.put("value", couponsEntity.getValue());
			 
			 parammap.put("type", couponsEntity.getType());
			 
			 parammap.put("id", couponsEntity.getId());
			 
		 }else{
			 
			 parammap.put("scope_type", 1);
			 
			 parammap.put("value", couponsEntity.getValue());
			 
			 parammap.put("type", couponsEntity.getType());
			 
			 parammap.put("id", couponsEntity.getId());
		 }
		
		 Map<String,Object> map= brandCouponsEntityMapper.queryCouponsByName(parammap);
		 
		
		if(map!=null&&map.size()>0)
		{
			return map;
		}
		return null;
	}

	@Override
	public Boolean removeCouponsByName(Long id) {
		// TODO Auto-generated method stub
		
		
		if(brandCouponsEntityMapper.deleteByPrimaryKey(id)>0){
			
			scopeCouponsEntityMapper.deleteScopeCouponsByCouponsId(id);
			return false;
		}
		return true;
	}

	@Override
	public Page<Map<String, Object>> queryCustomerCoupons(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		UserEntity  userEntity = UserContext.getCurrentUser();
		
		if(userEntity.getUser_type()==UserType.SHOP.getIndex()){
			
			 Map<String,Object> map=(Map<String, Object>) page.getParam();
			 
			 ShopEntity  brandEntity = (ShopEntity) UserContext.getCurrentUserInfo();
			 
			 map.put("shop_id", brandEntity.getId());
			 
			
		}
		
		page.setData(brandCouponsEntityMapper.selectCustomerCouponsPage(page));
		
		
		return page;
	}

	@Override
	public Long queryCustomerCouponsByCode(String code) {
		// TODO Auto-generated method stub
		
		Map<String,Object> map = new HashMap<>();
		
		UserEntity  userEntity = UserContext.getCurrentUser();
		
		if(userEntity.getUser_type().intValue()==UserType.SHOP.getIndex().intValue()){
			
			
			 ShopEntity  brandEntity = (ShopEntity) UserContext.getCurrentUserInfo();
			 
			 map.put("shop_id", brandEntity.getId());
			 
			 map.put("convert_code", code);
			 
			
		}
		List<Long> list=brandCouponsEntityMapper.selectCustomerCouponsByCode(map);
		
		if(list!=null&&list.size()>0){
			
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public Boolean modifyCustomerCouponsById(Long id) {
		// TODO Auto-generated method stub
		
		BaseCustomerCouponsEntity baseCustomerCouponsEntity =baseCustomerCouponsEntityMapper.selectByPrimaryKey(id);
		
		baseCustomerCouponsEntity.setIsline(false);
		
		baseCustomerCouponsEntity.setUse_time(new Date());
		
		
		baseCustomerCouponsEntity.setIsused(true);
		
		baseCustomerCouponsEntityMapper.updateByPrimaryKeySelective(baseCustomerCouponsEntity);
		
		return true;
	}

	@Override
	public Boolean updatecouponsstate(BaseBrandCouponsEntity couponsEntity) {
		
		BaseBrandCouponsEntity record = new BaseBrandCouponsEntity();
		record.setId(couponsEntity.getId());
		record.setState(couponsEntity.getState());
		
		return baseBrandCouponsEntityMapper.updateByPrimaryKeySelective(record) > 0;
		
	}
	
	

}

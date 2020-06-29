package com.mfangsoft.zhuangjialong.integration.shop.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.MD5Util;
import com.mfangsoft.zhuangjialong.integration.bank.mapper.BankEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BrandEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.brand.service.BrandService;
import com.mfangsoft.zhuangjialong.integration.commerce.mapper.CommerceEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enums.UserState;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.partner.mapper.PartnerEntityMapper;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.role.mapper.UserRoleEntityMapper;
import com.mfangsoft.zhuangjialong.integration.role.model.UserRoleEntity;
import com.mfangsoft.zhuangjialong.integration.shop.mapper.ShopEntityMapper;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;
import com.mfangsoft.zhuangjialong.integration.shop.service.ShopService;
import com.mfangsoft.zhuangjialong.integration.user.mapper.UserEntityMapper;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;
@Service
public class ShopServiceImpl implements ShopService {
	
	
	private final static Long  SHOP_ROLE=74L;

	@Autowired
	private  ShopEntityMapper shopEntityMapper;
	
	@Autowired
    private BankEntityMapper bankEntityMapper;
	
	@Autowired
	private CommerceEntityMapper commerceEntityMapper;
	@Autowired
	private UserEntityMapper userEntityMapper;
	@Autowired
	private UserRoleEntityMapper userRoleEntityMapper;
	@Autowired
	private BrandService brandService;
	

	@Override
	public Boolean addShop(ShopEntity shopEntity) {
		
		
		UserEntity userEntity = shopEntity.getUserEntity();
		
		userEntity.setCreate_time(new Date());
		
		userEntity.setPwd(MD5Util.MD5(userEntity.getPwd()));
		
		userEntity.setUser_type(UserType.SHOP.getIndex());
		
		userEntity.setState(UserState.OPEN.getIndex());
		
		userEntity.setUser(shopEntity.getPrincipal());
		userEntityMapper.insert(userEntity);
		
		shopEntity.setCreate_time(new Date());
		
		BrandEntity brandEntity =(BrandEntity) UserContext.getCurrentUserInfo();
		
		shopEntity.setCitypartner_id(brandEntity.getCitypartner_id());
		
		shopEntity.setBrand_id(brandEntity.getId());
		
		shopEntity.setSys_user_id(userEntity.getId());
		
		UserRoleEntity userRoleEntity = new UserRoleEntity();
		
		
		userRoleEntity.setRole_id(SHOP_ROLE);
		
		userRoleEntity.setUser_id(userEntity.getId());
		
		userRoleEntityMapper.insertSelective(userRoleEntity);
		
		if(shopEntityMapper.insertSelective(shopEntity)>0){
			return true;
		}
		
		return false;
	}

	@Override
	public Boolean modifyShop(ShopEntity shopEntity) {
		// TODO Auto-generated method stub
		ShopEntity entity=shopEntityMapper.selectByPrimaryKey(shopEntity.getId());
		
		UserEntity userEntity=userEntityMapper.selectByPrimaryKey(entity.getSys_user_id());
		
		userEntity.setAccount(shopEntity.getUserEntity().getAccount());
		
		userEntity.setUser(entity.getPrincipal());
		
		userEntity.setPhone_num(entity.getPhone_num());
		userEntityMapper.updateByPrimaryKeySelective(userEntity);
		
		if(shopEntityMapper.updateByPrimaryKeySelective(shopEntity)>0){
			return true;
		}
		return false;
	}

	@Override
	public ShopEntity getShopById(Long id) {
		// TODO Auto-generated method stub
		ShopEntity shopEntity=shopEntityMapper.selectByPrimaryKey(id);
		
		
		 BrandEntity brandEntity =brandService.getBrandById(shopEntity.getBrand_id());
		 
		
		 shopEntity.setBrandEntity(brandEntity);
		return shopEntity;
	}

	@Override
	public Page<Map<String, Object>> getShopForPage(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		
		UserContext.popupPageParam(page,"shop");
		page.setData(shopEntityMapper.getShopForPage(page));
		
		return page ;
	}

	@Override
	public Boolean checkShop(Long id, Integer state) {
		// TODO Auto-generated method stub
		
		ShopEntity shopEntity =shopEntityMapper.selectByPrimaryKey(id);
		
		shopEntity.setState(state);
		
		if(shopEntityMapper.updateByPrimaryKey(shopEntity)>0){
			
			return true;
		}
		return false;
	}

	@Override
	public Boolean openOrCloseShop(Long id, Integer state) {
		// TODO Auto-generated method stub
		
		
		ShopEntity shopEntity=shopEntityMapper.selectByPrimaryKey(id);
		shopEntity.getUserEntity().setState(state);
		
		userEntityMapper.updateByPrimaryKeySelective(shopEntity.getUserEntity());
	
		return true;
	}

	@Override
	public List<ShopEntity> getShopName() {
		// TODO Auto-generated method stub
		
		BrandEntity brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
		
		return shopEntityMapper.getShopName();
	}

	@Override
	public ShopEntity selectshopByUserId(Long userId) {
		// TODO Auto-generated method stub
		return shopEntityMapper.selectshopByUserId(userId);
	}

	@Override
	public Map<String, Object> getShopEntityById(Long shopId) {
		// TODO Auto-generated method stub
		return shopEntityMapper.getShopEntityById(shopId);
	}

	@Override
	public Boolean modifyBackShop(ShopEntity shopEntity) {
		// TODO Auto-generated method stub
		
		if(shopEntityMapper.updateByPrimaryKeySelective(shopEntity)>0){
			
			ShopEntity entity =shopEntityMapper.selectByPrimaryKey(shopEntity.getId());
			
			UserEntity userEntity=userEntityMapper.selectByPrimaryKey(entity.getSys_user_id());
			
			UserEntity userEntityUpdate = new UserEntity();
			
			userEntityUpdate.setUser(shopEntity.getPrincipal());
			userEntityUpdate.setId(userEntity.getId());
			userEntityMapper.updateByPrimaryKeySelective(userEntityUpdate);
			
			
			return true;
		}
		return false;
	}

	@Override
	public Boolean modifyShopState(ShopEntity shopEntity) {
		UserEntity currentUser = UserContext.getCurrentUser();
		if(currentUser.getUser_type()==UserType.BRAND.getIndex()){
			return shopEntityMapper.updateByPrimaryKeySelective(shopEntity)>0;	
		}
	return null;
	}

}

package com.mfangsoft.zhuangjialong.integration.customer.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.applogin.mapper.CustomerEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.customer.service.CustomerService;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;
@Service(value="customerService")
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	private  CustomerEntityMapper customerEntityMapper;


	@Override
	public Page<Map<String, Object>> queryCustomerForPage(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		
		
		
		Map<String, Object> map = null;
		
		if(page.getParam()!=null)
		{
			 map=(Map<String, Object>) page.getParam();
			
			
		}else{
			
			 map = new HashMap<>();
			 page.setParam(map);
			
		}
		UserEntity  user = UserContext.getCurrentUser();
		
		if(user.getUser_type()==UserType.SHOP.getIndex()){
			
			map.put("kfzys", "");
			ShopEntity shopEntity=(ShopEntity) UserContext.getCurrentUserInfo();
			map.put("shop_id", shopEntity.getId());
		}else if(user.getUser_type()==UserType.PARTNER.getIndex()){
			
			map.put("kfzys", "");
			PartnerEntity  partnerEntity = (PartnerEntity) UserContext.getCurrentUserInfo();
			
			map.put("partner_id", partnerEntity.getId());
			
		}else if(user.getUser_type()==UserType.ENTERPRISE.getIndex()){
			
			map.put("kfzys", "");
			EnterpriseEntity enterpriseEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();
			
			map.put("ent_id", enterpriseEntity.getId());
			
		}else if(user.getUser_type()==UserType.BRAND.getIndex()){
			
			map.put("kfzys", "");
			BrandEntity brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
			map.put("brand_id", brandEntity.getId());
		}else {
			
			map.put("kfzys", "1");
		}
		
		
		
		page.setData(customerEntityMapper.queryCustomerForPage(page));
		return page;
	}

	@Override
	public Map<String, Object> selectCustomerById(Long customer_id) {
		// TODO Auto-generated method stub
		return customerEntityMapper.selectCustomerById(customer_id);
	}

	@Override
	public Map<String, Object> selectOrderByCustomer(Long customer_id) {
		// TODO Auto-generated method stub
		return null;
	}

}

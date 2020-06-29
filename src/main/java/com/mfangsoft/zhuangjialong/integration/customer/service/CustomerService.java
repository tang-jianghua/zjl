package com.mfangsoft.zhuangjialong.integration.customer.service;

import java.util.Map;

import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.common.model.Page;

public interface CustomerService {

	
	Page<Map<String,Object>> queryCustomerForPage(Page<Map<String,Object>> page);
	
	
	Map<String,Object>   selectCustomerById(Long customer_id);
	
	
	Map<String,Object> selectOrderByCustomer(Long customer_id);
}

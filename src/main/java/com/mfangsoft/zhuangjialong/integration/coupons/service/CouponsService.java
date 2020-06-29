package com.mfangsoft.zhuangjialong.integration.coupons.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.coupons.model.BrandCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.BaseBrandCouponsEntity;

public interface CouponsService {
	
	
	
	Boolean addCoupons(BrandCouponsEntity brandCouponsEntity);
	
	
	Boolean modifyCoupons(BrandCouponsEntity brandCouponsEntity);
	
	
	Page<Map<String,Object>> queryCoupons(Page<Map<String,Object>> page);
	
	
	BrandCouponsEntity getCouponsById(Long id);
	
	
	Map<String,Object> queryCouponsByName(BaseBrandCouponsEntity couponsEntity);
	
	Boolean removeCouponsByName(Long id);
	
	
	Page<Map<String,Object>> queryCustomerCoupons(Page<Map<String,Object>> page);
	
	
	Long  queryCustomerCouponsByCode(String code);
	
	
	Boolean modifyCustomerCouponsById(Long id);
	
	Boolean updatecouponsstate(BaseBrandCouponsEntity couponsEntity);
}

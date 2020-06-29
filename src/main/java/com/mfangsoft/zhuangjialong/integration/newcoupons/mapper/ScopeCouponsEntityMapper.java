package com.mfangsoft.zhuangjialong.integration.newcoupons.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.BaseScopeCouponsEntity;
@WriterRepository
public interface ScopeCouponsEntityMapper extends BaseScopeCouponsEntityMapper {
 
	
	
	BaseScopeCouponsEntity selectScopeCouponsByBrandId(@Param("coupons_id") Long coupons_id,@Param("brand_id") Long brand_id);
	
	
	
	List<Long> selectScopeCouponsByCouponsId(Long coupons_id);
	
	
	
	int  deleteScopeCouponsByCouponsId(Long coupons_id);
	
	
	
	
}
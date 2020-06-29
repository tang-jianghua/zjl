package com.mfangsoft.zhuangjialong.app.coupons.mapper;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.coupons.model.BrandCouponsModel;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.newcoupons.mapper.BaseCustomerCouponsEntityMapper;

@WriterRepository
public interface CustomerCouponsEntityMapper extends BaseCustomerCouponsEntityMapper{
	
	/*
	 * 添加红包优惠券
	 */
	int addCoupons(BrandCouponsModel param);
	
    /*
     * 查看某优惠券或红包是否被某消费者领过
     */
    int selectCustomerWhetherGainCoupons(BrandCouponsModel param);
    
    Integer selectCouponsCount(@Param("coupons_id") Long coupons_id);
    
}
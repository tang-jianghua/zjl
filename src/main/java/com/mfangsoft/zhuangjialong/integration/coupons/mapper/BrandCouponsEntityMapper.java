package com.mfangsoft.zhuangjialong.integration.coupons.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.coupons.model.BrandCouponsModel;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.coupons.model.BrandCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.coupons.model.CouponsRedbagMessage;
import com.mfangsoft.zhuangjialong.integration.newcoupons.mapper.BaseBrandCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.BaseBrandCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.CustomerCouponsModel;


@WriterRepository
public interface BrandCouponsEntityMapper extends  BaseBrandCouponsEntityMapper {
  
	CustomerCouponsModel selectInfoById(@Param("id") Long id);

    int updateUseStateByPrimaryKeyList(@Param("shop_id")Long shop_id,@Param("isline")Boolean isline,@Param("isused")Boolean isused,@Param("idList")List<Long> idList, @Param("product_order_code")String product_order_code);
    
    int updateUseStaterebackbyList(@Param("idList")List<Long> idList);
    
    List<BrandCouponsEntity> queryCouponsAboutUseState(@Param("isused")Boolean isused,@Param("idList")List<Long> idList);
    

    
    /**
     * 通过品牌id获取优惠券 
     *
     * @MLTH_generated
     */
    List<BrandCouponsModel> selectCouponsForBrandMain(Long id);


    /**
     * 通过品牌id获取红包
     *
     * @MLTH_generated
     */

    List<BrandCouponsModel> selectRedBagsForBrandMain(Long id);
    
    /*
     * 减少红包优惠券的剩余数量
     */
    int updateSurplusById(BrandCouponsModel param);
    
    List<CustomerCouponsModel> selectCouponsByCustomerId(CustomerCouponsModel param);
    
    List<CustomerCouponsModel> selectRedBagByCustomerId(CustomerCouponsModel param);
    
    List<Map<String,Object>> queryCouponsForPage(Page<Map<String,Object>> page);
    
    List<CouponsRedbagMessage> selectPreExpireCouponsRedbagsForMessage();
    
    List<CustomerCouponsModel> selectRedbagsForCartShop(@Param("customer_id")Long customer_id, @Param("brand_id")Long brand_id, @Param("usedIds")List<String> usedIds);
    
    List<CustomerCouponsModel> selectCouponsForCartShop(@Param("customer_id")Long customer_id, @Param("brand_id")Long brand_id, @Param("usedIds")List<String> usedIds, @Param("step_value")Integer step_value, @Param("type")Integer type);
    
    Map<String,Object> queryCouponsByName(Map<String,Object> map);
    
    
    
    
    List<Map<String,Object>> selectCustomerCouponsPage(Page<Map<String,Object>> page);
    
    
    List<Long> selectCustomerCouponsByCode(Map<String,Object> map);
    
    int deleteSurplusById(@Param("surplus_count")Integer surplus_count, @Param("coupons_id")Long coupons_id);
    
    List<Map<String,Object>> queryFirstPageCouponsListForPage(Page<Map<String,Object>> page);
    
    Map<String,Object> queryOneFirstPageCoupons(Map<String, Object> param);
    
    List<Map<String,Object>> queryOneCouponsShopInfo(@Param("id") Long id);
    
    List<BaseBrandCouponsEntity> whetherHaveQuanforcart(@Param("customer_id")Long customer_id, @Param("shop_id")Long shop_id);
}
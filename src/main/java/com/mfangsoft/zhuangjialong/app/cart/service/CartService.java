package com.mfangsoft.zhuangjialong.app.cart.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.mfangsoft.zhuangjialong.app.cart.model.CartEntity;
import com.mfangsoft.zhuangjialong.app.cart.model.CartShop;
import com.mfangsoft.zhuangjialong.app.cart.model.CartShopParam;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.core.exception.ServiceException;
import com.mfangsoft.zhuangjialong.integration.coupons.model.BrandCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.BaseBrandCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.CustomerCouponsModel;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionCustomer;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionPromotion;

/**
 * @description：购物车接口
 * @author：Jianghua.Tang @date：2016年6月4日
 * 
 */
@Service
public interface CartService {

	/**
	 * 
	 * @description：添加购物车 @param：
	 */
	public Long insertSelective(CartEntity record) throws ServiceException;

	/**
	 * 
	 * @description：删除购物车 @param：
	 */
	public void deleteByPrimaryKey(List<CartEntity> cartEntities) throws ServiceException;

	/**
	 * 
	 * @description：编辑购物车 @param：
	 */
	public boolean updateByPrimaryKeySelective(List<CartEntity> cartEntities) throws ServiceException;

	/**
	 * 
	 * @description：获取购物车数据 @param：
	 */
	public List<CartShop> getCartData(CartEntity cartEntity) throws ServiceException;
	
	public List<CustomerCouponsModel> getredbagcouponsforcart(CartShop param);
	
	public CartShop getAllFlagsOfBenefitForCart(CartShop param);

	public CartShopParam getallflagsofbenefitforallshop(CartShopParam param);
	
	public List<UnionPromotion> getCustomerPromotionforcart(CartShop param);
	
	public CartEntity selectByProductIdAndSaleInfoId(Long customer_id, Long product_id, Long sales_property_id, Long shop_id);

	public Map<String,Double> calculateforcart(CartShop param);
	
	public Map<String, Object> calculateforcart_new(CartShop param);
	
	public Integer getpercentpayment(CartShop param);
	
	/**
	 * 移除
	 */
	public List<String> relaseUsedIds(CartShop cartShop);
//	/**
//	 * 移除店铺定金红包
//	 */
//	public List<String> relaseShopCashId(CartShop cartShop);
//	/**
//	 * 移除普通红包优惠券
//	 */
//	public List<String> relaseRedBagId(CartShop cartShop);
//	/**
//	 * 移除联盟现金券
//	 */
//	public List<String> relaseUnionPromotionId(CartShop cartShop);
	
	List<BaseBrandCouponsEntity> whetherHaveQuanforcart(Long customer_id, Long shop_id);
}

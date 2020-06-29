package com.mfangsoft.zhuangjialong.app.cart.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mfangsoft.zhuangjialong.app.cart.mapper.CartEntityMapper;
import com.mfangsoft.zhuangjialong.app.cart.model.CartEntity;
import com.mfangsoft.zhuangjialong.app.cart.model.CartProduct;
import com.mfangsoft.zhuangjialong.app.cart.model.CartShop;
import com.mfangsoft.zhuangjialong.app.cart.model.CartShopParam;
import com.mfangsoft.zhuangjialong.app.cart.service.CartService;
import com.mfangsoft.zhuangjialong.app.prepay.mapper.ShopPrepayEntityMapper;
import com.mfangsoft.zhuangjialong.app.prepay.model.ShopPrepayEntity;
import com.mfangsoft.zhuangjialong.app.product.model.Product;
import com.mfangsoft.zhuangjialong.app.promotion.model.AppPromotionTypeEnum;
import com.mfangsoft.zhuangjialong.common.utils.NumberUtil;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.coupons.mapper.BrandCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.BaseBrandCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.CustomerCouponsModel;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandNewProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProductOneSaleModel;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionZhekouEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.UnionCustomerMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.UnionProductMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.UnionPromotionMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PriceModel;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionEntityStepParam;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionPromotion;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionPromotionParam;

/**
 * @description：
 * 
 * @author：Jianghua.Tang @date：2016年6月4日
 * 
 */
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartEntityMapper cartEntityMapper;
	@Autowired
	BrandCouponsEntityMapper brandCouponsEntityMapper;
	@Autowired
	UnionCustomerMapper unionCustomerMapper;
	@Autowired
	UnionPromotionMapper unionPromotionMapper;
	@Autowired
	ShopPrepayEntityMapper shopPrepayEntityMapper;
	@Autowired
	UnionProductMapper unionProductMapper;
	@Autowired
	BrandNewProductEntityMapper brandNewProductEntityMapper;
	@Autowired
	PromotionEntityMapper promotionEntityMapper;
	@Autowired
	PromotionZhekouEntityMapper promotionZhekouEntityMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

	@Override
	public Long insertSelective(CartEntity record) {

		System.out.println("============checkCartProduct========================= "
				+ checkCartProduct(record.getProduct_id(), record.getSales_property_id()));

		if (checkCartProduct(record.getProduct_id(), record.getSales_property_id())) {

			CartEntity temp = selectByProductIdAndSaleInfoId(record.getCustomer_id(), record.getProduct_id(),
					record.getSales_property_id(), record.getShop_id());
			if (temp != null) {
				temp.setNum(temp.getNum() + record.getNum());
				cartEntityMapper.updateByPrimaryKeySelective(temp);

				return temp.getId();
			} else {
				cartEntityMapper.insertSelective(record);
				return record.getId();
			}

		} else {
			return null;
		}
	}

	private boolean checkCartProduct(Long product_id, Long sales_property_id) {

		boolean flag = true;

		BrandProductOneSaleModel productInfo = brandNewProductEntityMapper.selectProductOneSaleInfo(product_id,
				sales_property_id);
		flag = flag && productInfo.getSalesAttrEntity().getStock() != null
				&& productInfo.getSalesAttrEntity().getStock() > 0;

		// System.out.println("============checkCartProduct=========================
		// " + class_id + "|" + product_id + "|"
		// + sales_property_id);

		return flag;
	}

	@Override
	public void deleteByPrimaryKey(List<CartEntity> cartEntities) {
		for (int i = 0; i < cartEntities.size(); i++) {
			cartEntityMapper.deleteByPrimaryKey(cartEntities.get(i));
		}
	}

	@Override
	public boolean updateByPrimaryKeySelective(List<CartEntity> cartEntities) {
		for (int i = 0; i < cartEntities.size(); i++) {
			int deleteByPrimaryKey = cartEntityMapper.updateByPrimaryKeySelective(cartEntities.get(i));
			if (deleteByPrimaryKey == 0) {
				return false;
			}
		}
		return true;
	}

	// NumberUtil.round(price < 0 ? 0 : price, 2, BigDecimal.ROUND_HALF_DOWN))
	private double getNewPrice(UnionPromotion unionPromotion, double price) {
		logger.debug("----原价-------------------------------------" + price);
		if (unionPromotion != null) {
			double new_price = (unionPromotion.getDiscount() / 10) * price;
			logger.debug("----打折-------------------------------------" + new_price);
			return NumberUtil.round(new_price < 0 ? 0 : new_price, 2, BigDecimal.ROUND_HALF_DOWN);
		}
		return price;
	}

	private double getSecKillPrice(List<Product> promotion_product, Long product_id, double old_price) {
		if (promotion_product != null) {
			for (Product p : promotion_product) {
				if (p.getProduct_id().equals(product_id))
					return p.getPrice();
			}
		}
		return old_price;
	}

	private Product getSecKillProduct(List<Product> promotion_product, Long product_id, Integer num) {
		if (promotion_product != null) {
			for (Product p : promotion_product) {
				if (p.getProduct_id().equals(product_id) && p.getStock() >= num && p.getPerson_product_num() >= num)
					return p;
			}
		}
		return null;
	}

	private boolean isSecKill(List<Product> promotion_product, Long product_id, Integer num) {
		if (promotion_product != null) {
			for (Product p : promotion_product) {
				if (p.getProduct_id().equals(product_id) && p.getStock() >= num)
					return true;
			}
		}
		return false;
	}

	@Override
	public List<CartShop> getCartData(CartEntity cartEntity) {

		List<CartShop> cartShops = new ArrayList<CartShop>();
		try {
			if (cartEntity.getId() != null && cartEntity.getId() > 0) {
				cartShops = cartEntityMapper.selectByCartId(cartEntity.getId());
			} else {
				String region_code = cartEntity.getRegion_code();
				if (region_code != null) {
					if ("110000".equals(cartEntity.getRegion_code())) {
						region_code = "110100";
					}
					if ("120000".equals(cartEntity.getRegion_code())) {
						region_code = "120100";
					}
					if ("310000".equals(cartEntity.getRegion_code())) {
						region_code = "310100";
					}
					if ("500000".equals(cartEntity.getRegion_code())) {
						region_code = "500100";
					}
				}
				cartShops = cartEntityMapper.selectByCustomerId(cartEntity.getCustomer_id(), region_code);
			}

			// 获取可参加的秒杀活动产品列表
			// List<Product> promotion_product = null;
			// if (cartEntity.getRegion_code() != null) {
			// promotion_product =
			// promotionEntityMapper.selectPromotionProductAndPriceForCart(
			// cartEntity.getCustomer_id(), cartEntity.getRegion_code());
			// }

			// List<Long> productLis = new ArrayList<>();
			// List<String> usedPromotionLis = new ArrayList<String>();
			// usedPromotionLis.add("0");

			for (CartShop c : cartShops) {
				// Integer price = 0;
				// Integer old_price = 0;
				// boolean isSecKIll = false;
				getAllFlagsOfBenefitForCart(c);

				for (CartProduct product : c.getProducts()) {

					setInfo(product);
					product.getSales_property().setColor(null);
					product.getSales_property().setModel(null);
					product.getSales_property().setStandard(null);
					product.getSales_property().setProduct_unit(null);
					// getFlagOfonProduct(product, c.getCustomer_id(),
					// c.getBrand_id(), product.getProduct_id(),
					// product.getNum(), c.getPreOrderStr(),
					// c.getRegion_code());

					// product.setIsPercentPay(product.getSales_model() != null
					// && product.getSales_model() == 2);

					// UnionPromotion unionPromotion = unionCustomerMapper
					// .selectCanUseCashListForCartOrderPriceByCustomerId(cartEntity.getCustomer_id(),
					// product.getProduct_id());// 联盟活动
					//
					// Product secKillProduct =
					// getSecKillProduct(promotion_product,
					// product.getProduct_id(),
					// product.getNum());// 秒杀活动

					// product.setBuy_type(false);
					// if (secKillProduct != null) {
					// isSecKIll = true;
					// product.setPrice_type(1);
					// product.setBuy_type(true);
					// product.setStock(secKillProduct.getStock());
					// product.setPerson_product_num(secKillProduct.getPerson_product_num());
					// product.setType_value(secKillProduct.getPromotion_id() +
					// "-" + secKillProduct.getTime_id());
					// product.getSales_property().setNew_price(secKillProduct.getPromotion_price());
					// price = (int) (price +
					// secKillProduct.getPromotion_price() * product.getNum());
					// logger.debug("秒杀价格 " + "-----------" +
					// secKillProduct.getPromotion_price() + "--"
					// + product.getType_value());
					// } else if (unionPromotion != null &&
					// unionPromotion.getDiscount() != null
					// && unionPromotion.getDiscount() < 10) {
					// product.getSales_property()
					// .setNew_price(getNewPrice(unionPromotion,
					// product.getSales_property().getPrice()));
					// product.setPrice_type(2);
					// } else {
					// price = (int) (price +
					// product.getSales_property().getPrice() *
					// product.getNum());
					// logger.debug("原价格 " + "-----------" +
					// product.getSales_property().getPrice());
					// product.setPrice_type(0);

					// UnionPromotion u_product = unionCustomerMapper
					// .selectOneProductByProductId(product.getProduct_id());
					// if (u_product != null) {
					// product.setPrice_type(2);
					// }
					// }

					// old_price = (int) (old_price +
					// product.getSales_property().getPrice() *
					// product.getNum());// 原价

					// productLis.add(product.getProduct_id());
				}
				// List<String> list = new ArrayList<>();
				// list.add("0");
				//
				// List<CustomerCouponsModel> coupons = brandCouponsEntityMapper
				// .selectCouponsForCartShop(c.getCustomer_id(),
				// c.getBrand_id(), list, old_price, 2);
				// c.setIsHaveCoupons(coupons != null && coupons.size() > 0);
				//
				// List<CustomerCouponsModel> zhekou = brandCouponsEntityMapper
				// .selectCouponsForCartShop(c.getCustomer_id(),
				// c.getBrand_id(), list, old_price, 3);
				// c.setIsHaveZhekou(zhekou != null && zhekou.size() > 0);
				//
				// List<CustomerCouponsModel> redbag = brandCouponsEntityMapper
				// .selectRedbagsForCartShop(c.getCustomer_id(),
				// c.getBrand_id(), list);
				// c.setIsHaveRedbags(redbag != null && redbag.size() > 0);
				//
				// List<ShopPrepayEntity> e =
				// shopPrepayEntityMapper.selectByCustomerId(c.getCustomer_id(),
				// c.getShop_id());
				// if (e != null && e.size() > 0) {
				// c.setIsHaveRedbags(true);
				// }
				// Long[] array = new Long[productLis.size()];
				// for (int i = 0; i < productLis.size(); i++) {
				// array[i] = productLis.get(i);
				// }
				// // 拥有未使用的现金券
				// List<UnionPromotion> unionPromotionList = unionCustomerMapper
				// .selectHaveCashListForCart(c.getCustomer_id(), array,
				// usedPromotionLis);
				// c.setIsPromotion(unionPromotionList != null &&
				// unionPromotionList.size() > 0 ? true : false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return cartShops;
		}
	}

	private CartProduct setInfo(CartProduct product) {

		StringBuffer sb = new StringBuffer();
		if (product.getSales_property().getColor() != null) {
			sb.append("颜色:" + product.getSales_property().getColor());
		}
		if (product.getSales_property().getModel() != null) {
			sb.append(" 型号:" + product.getSales_property().getModel());
		}
		if (product.getSales_property().getStandard() != null) {
			sb.append(" 规格:" + product.getSales_property().getStandard());
		}
		if (product.getSales_property().getProduct_unit() != null) {
			sb.append(" 单位:" + product.getSales_property().getProduct_unit());
		}
		product.setInfo(sb.toString());
		return product;
	}

	public List<UnionPromotion> getCustomerPromotionforcart(CartShop param) {

		// 旧参数格式
		if (param.getProductIdList() != null && param.getProductIdList().length > 0) {
			return unionCustomerMapper.selectHaveCashListForCart(param.getCustomer_id(), param.getProductIdList(),
					getUnionPromotionId(param));
		}

		// 新参数格式
		List<Long> productIdList = new ArrayList<Long>();
		productIdList.add(0L);

		for (CartProduct product : param.getProducts()) {
			if (product.isCanUseUnionCash()) {
				productIdList.add(product.getProduct_id());
			}
		}

		int i = 0;

		Long[] productArray = new Long[productIdList.size()];
		for (Long lv : productIdList) {
			productArray[i++] = lv;
		}

		return unionCustomerMapper.selectHaveCashListForCart(param.getCustomer_id(), productArray,
				getUnionPromotionId(param));

	}

	public List<CustomerCouponsModel> getredbagcouponsforcart(CartShop param) {

		for (CartProduct product : param.getProducts()) {

			BrandProductOneSaleModel productInfo = brandNewProductEntityMapper
					.selectProductOneSaleInfo(product.getProduct_id(), product.getSales_property().getId());

			product.setIsPercentPay(productInfo.getSales_model().equals(2));
			product.setPercent(productInfo.getPercent());
			product.getSales_property().setId(productInfo.getSalesAttrEntity().getId());
			product.getSales_property().setPrice(productInfo.getSalesAttrEntity().getPrice());

			String region_code = param.getRegion_code();
			if (region_code != null) {
				if (region_code.startsWith("11")) {
					region_code = "110000";
				}
				if (region_code.startsWith("12")) {
					region_code = "120000";
				}
				if (region_code.startsWith("31")) {
					region_code = "310000";
				}
				if (region_code.startsWith("50")) {
					region_code = "500000";
				}
			}

			getFlagOfonProduct(product, param.getCustomer_id(), param.getBrand_id(), product.getProduct_id(),
					product.getNum(), param.getPreOrderStr(), region_code);

		}

		Double price_c = 0D;
		Double price_z = 0D;
		boolean redbag = false;
		boolean preshopredbag = false;
		boolean isPercentPay = true;

		List<Long> productLis = new ArrayList<>();
		productLis.add(0L);

		for (CartProduct product : param.getProducts()) {

			// 判断现金券
			if (product.getPromotionType().equals(AppPromotionTypeEnum.unionZheKou.getTypeValue())) {
				productLis.add(product.getProduct_id());
			}

			// 判断优惠券
			if (product.isCanUseCoupons()) {
				price_c += product.getSales_property().getNew_price();
			}

			// 判断折扣券
			if (product.isCanUseZhekouquan()) {
				price_z += product.getSales_property().getNew_price() * product.getNum();
			}

			// 判断红包
			redbag |= product.isCanUseRedBag();
			preshopredbag |= product.isCanUseShopPreRedBag();
			isPercentPay &= product.getIsPercentPay();
		}

		if (param.getIsNeedRedbagorCoupons() == 1) {// 红包
			List<CustomerCouponsModel> list_result = new ArrayList<CustomerCouponsModel>();

			if (param.getUsedPromotion() != null && param.getUsedPromotion().length > 0) {
				// 已使用联盟券，不查询店铺现金券
			} else {
				if (preshopredbag) {
					List<ShopPrepayEntity> entity = shopPrepayEntityMapper.selectByCustomerIdForCart(
							param.getCustomer_id(), param.getShop_id(), getShopCashId(param));
					if (entity != null && entity.size() > 0) {
						for (ShopPrepayEntity e : entity) {
							CustomerCouponsModel entityOne = new CustomerCouponsModel();
							entityOne.setId(e.getId().longValue());
							entityOne.setType(11);// 卷类型1：红包 2：优惠卷 3：折扣券 11
													// 商铺定金券
							entityOne.setCustomer_id(e.getCustomer_id());
							entityOne.setName(SysConstant.SHOP_REDBAG);
							entityOne.setValue(e.getValue().doubleValue());
							list_result.add(entityOne);
						}
					}
				}
			}
			if (redbag) {
				List<CustomerCouponsModel> list = brandCouponsEntityMapper
						.selectRedbagsForCartShop(param.getCustomer_id(), param.getBrand_id(), getUsedRedBagId(param));
				list_result.addAll(list);
			}

			return list_result;
		} else if (param.getIsNeedRedbagorCoupons() == 2) {// 查询优惠券
			List<CustomerCouponsModel> list_result = new ArrayList<CustomerCouponsModel>();
			if (price_c > 0) {
				list_result = brandCouponsEntityMapper.selectCouponsForCartShop(param.getCustomer_id(),
						param.getBrand_id(), getUsedRedBagId(param), price_c.intValue(), 2);
			}
			return list_result;
		} else if (param.getIsNeedRedbagorCoupons() == 3) {// 查询折扣券
			List<CustomerCouponsModel> list_result = new ArrayList<CustomerCouponsModel>();
			if (price_z > 0) {// 大于0 有效，小于0 无效
				list_result = brandCouponsEntityMapper.selectCouponsForCartShop(param.getCustomer_id(),
						param.getBrand_id(), getUsedRedBagId(param), price_z.intValue(), 3);
			}
			return list_result;
		}

		return new ArrayList<CustomerCouponsModel>();
	}

	@Override
	public CartShop getAllFlagsOfBenefitForCart(CartShop param) {

		for (CartProduct product : param.getProducts()) {

			BrandProductOneSaleModel productInfo = brandNewProductEntityMapper
					.selectProductOneSaleInfo(product.getProduct_id(), product.getSales_property().getId());

			if (productInfo == null) {
				return param;
			}

			product.setIsPercentPay(productInfo.getSales_model().equals(2));
			product.setPercent(productInfo.getPercent());
			product.getSales_property().setId(productInfo.getSalesAttrEntity().getId());
			product.getSales_property().setPrice(productInfo.getSalesAttrEntity().getPrice());

			List<Integer> promotionTypes = brandNewProductEntityMapper.selectPromotionTypesByProductId(product.getProduct_id());
	        product.setPromotion_types(promotionTypes);
	        
			String region_code = param.getRegion_code();
			if (region_code != null) {
				if (region_code.startsWith("11")) {
					region_code = "110000";
				}
				if (region_code.startsWith("12")) {
					region_code = "120000";
				}
				if (region_code.startsWith("31")) {
					region_code = "310000";
				}
				if (region_code.startsWith("50")) {
					region_code = "500000";
				}
			}

			getFlagOfonProduct(product, param.getCustomer_id(), param.getBrand_id(), product.getProduct_id(),
					product.getNum(), param.getPreOrderStr(), region_code);

		}

		param.setIsPromotion(false);
		param.setIsHaveCoupons(false);
		param.setIsHaveRedbags(false);
		param.setIsHaveZhekou(false);
		param.setIsPrecentPayMent(false);

		Double price_c = 0D;
		Double price_z = 0D;
		boolean redbag = false;
		boolean preshopredbag = false;
		boolean isPercentPay = true;

		List<Long> productLis = new ArrayList<>();
		productLis.add(0L);

		for (CartProduct product : param.getProducts()) {

			// 判断现金券
			if (product.getPromotionType().equals(AppPromotionTypeEnum.unionZheKou.getTypeValue())) {
				productLis.add(product.getProduct_id());
			}

			// 判断优惠券
			if (product.isCanUseCoupons()) {
				price_c += product.getSales_property().getNew_price();
			}

			// 判断折扣券
			if (product.isCanUseZhekouquan()) {
				price_z += product.getSales_property().getNew_price();
			}

			// 判断红包
			redbag |= product.isCanUseRedBag();
			preshopredbag |= product.isCanUseShopPreRedBag();
			isPercentPay &= product.getIsPercentPay();
		}
		/////////////////////////////////////////////////////////////
		// 查询现金券
		List<UnionPromotion> union_list = unionPromotionMapper.selectHaveCashListForCart(param.getCustomer_id(),
				productLis, getusedUnionPromotionids(param.getPreOrderStr()));
		if (union_list != null && union_list.size() > 0) {
			param.setIsPromotion(true);
		}

		// 判断优惠券
		if (price_c.intValue() > 0) {
			List<CustomerCouponsModel> list_coupons = brandCouponsEntityMapper.selectCouponsForCartShop(
					param.getCustomer_id(), param.getBrand_id(), getUsedRedBagId(param), price_c.intValue(), 2);
			if (list_coupons != null && list_coupons.size() > 0) {
				param.setIsHaveCoupons(true);
			}
		}

		// 判断红包
		if (redbag) {
			List<CustomerCouponsModel> list_coupons = brandCouponsEntityMapper.selectCouponsForCartShop(
					param.getCustomer_id(), param.getBrand_id(), getUsedRedBagId(param), null, 1);

			if (list_coupons != null && list_coupons.size() > 0) {
				param.setIsHaveRedbags(true);
			}
		}

		if (preshopredbag) {
			List<ShopPrepayEntity> entity = shopPrepayEntityMapper.selectByCustomerIdForCart(param.getCustomer_id(),
					param.getShop_id(), getShopCashId(param));
			if (entity != null && entity.size() > 0) {
				param.setIsHaveRedbags(true);
			}
		}

		// 判断折扣券
		if (price_z.intValue() > 0) {
			List<CustomerCouponsModel> list_zhekou = brandCouponsEntityMapper.selectCouponsForCartShop(
					param.getCustomer_id(), param.getBrand_id(), getUsedRedBagId(param), price_z.intValue(), 3);

			if (list_zhekou != null && list_zhekou.size() > 0) {
				param.setIsHaveZhekou(true);
			}

		}

		param.setIsPrecentPayMent(isPercentPay);

		return param;

	}

	@Override
	public CartShopParam getallflagsofbenefitforallshop(CartShopParam param) {

		for (CartShop cartshop : param.getCartshop_list()) {
			getAllFlagsOfBenefitForCart(cartshop);
			cartshop.setProducts(null);
		}

		return param;

	}

	public void getFlagOfonProduct(CartProduct product, Long customer_id, Long brand_id, Long product_id, Integer num,
			String preOrderStr, String region_code) {

		// -1 无活动 0秒杀 1 满额折扣 2 满额减 3 满量折扣 4 联盟打折 5 折扣工具

		logger.debug("-------------------------" + preOrderStr);
		Map<String, Object> product_info = RedisUtils.getMap(preOrderStr + "map");
		if (product_info == null || product_info.size() <= 0) {
			product_info = new LinkedHashMap<String, Object>();
		}

		product.setBuy_type(false);// 秒杀标志

		// 秒杀
		if (region_code != null) {
			List<Product> promotion_product = promotionEntityMapper.selectPromotionProductAndPriceForCart(customer_id,
					region_code);
			Product secKillProduct = getSecKillProduct(promotion_product, product_id, num);// 秒杀活动
			if (secKillProduct != null) {
				product.setPromotionType(AppPromotionTypeEnum.secKill.getTypeValue());
				product.setPromotion_id(secKillProduct.getTime_id().longValue());

				product.setCanUseFlag(false, false, false, false, false);

				product.setPrice_type(1);// 兼容旧接口
				product.setBuy_type(true);
				product.setStock(secKillProduct.getStock());
				product.setPerson_product_num(secKillProduct.getPerson_product_num());
				product.setType_value(secKillProduct.getPromotion_id() + "-" + secKillProduct.getTime_id());

				product.getSales_property().setNew_price(secKillProduct.getPromotion_price());
				product.setTotal_price(
						NumberUtil.round(secKillProduct.getPromotion_price() * num, 2, BigDecimal.ROUND_HALF_DOWN));

				setInfoToMap(product.getProduct_id(), "秒杀" + secKillProduct.getPromotion_price(), product_info, true);
				RedisUtils.setMap(preOrderStr + "map", product_info);
				return;
			}
		}

		// 普通产品,初始化
		product.setPromotionType(AppPromotionTypeEnum.non.getTypeValue());
		product.setCanUseFlag(true, true, true, true, true);
		product.getSales_property().setNew_price(product.getSales_property().getPrice());
		product.setTotal_price(product.getSales_property().getPrice() * product.getNum());
		product.setPrice_type(0);// 兼容旧接口

		// 折扣工具
		PromotionZhekouEntity zhekou = promotionZhekouEntityMapper.selectZhekouToolForCartOneProduct(brand_id,
				product_id);
		if (zhekou != null) {
			// product.setPromotionType(AppPromotionTypeEnum.zheKouTool.getTypeValue());
			// product.setPromotion_id(zhekou.getId());
			// product.setCanUseFlag(true, true, true, true, false);

			Double price_modle = zhekou.getDiscount() * product.getSales_property().getPrice();
			price_modle = NumberUtil.round(price_modle, 2, BigDecimal.ROUND_HALF_DOWN);
			product.getSales_property().setNew_price(price_modle);
			product.setTotal_price(NumberUtil.round(price_modle * product.getNum(), 2, BigDecimal.ROUND_HALF_DOWN));

			setInfoToMap(product.getProduct_id(), "折扣工具" + zhekou.getDiscount() * 100 + "%", product_info, false);
			RedisUtils.setMap(preOrderStr + "map", product_info);
		}
		// 联盟现金券
		// UnionPromotion unionPromotion =
		// unionPromotionMapper.selectHaveCashOfOneProductForCart(customer_id,
		// product_id, getusedUnionPromotionids(preOrderStr));
		// 联盟活动打折
		UnionPromotion unionPromotion = unionCustomerMapper
				.selectCanUseCashListForCartOrderPriceByCustomerId(customer_id, product.getProduct_id());

		System.out.println("============== 联盟活动打折 ==========================================================");
		System.out.println(new Gson().toJson(unionPromotion));

		// UnionPromotion unionPromotion =
		// unionCustomerMapper.selectOneProductByProductId(product_id);
		if (unionPromotion != null && unionPromotion.getDiscount() != null) {// &&
																				// unionPromotion.getDiscount()
																				// <
																				// 10
			product.setPromotionType(AppPromotionTypeEnum.unionZheKou.getTypeValue());
			product.setPromotion_id(unionPromotion.getId().longValue());
			product.setCanUseFlag(true, false, false, false, true);

			Double new_price = getNewPrice(unionPromotion, product.getSales_property().getPrice());
			product.getSales_property().setNew_price(new_price);
			product.setTotal_price(NumberUtil.round(new_price * num, 2, BigDecimal.ROUND_HALF_DOWN));

			product.setPrice_type(2);// 兼容旧接口

			setInfoToMap(product.getProduct_id(), "联盟折扣" + unionPromotion.getDiscount() * 10 + "% ", product_info,
					false);
			RedisUtils.setMap(preOrderStr + "map", product_info);
			return;
		}

		// 1 满额折扣 2 满额减 3满量折扣
		PromotionEntityStepParam entity = null;
		entity = promotionEntityMapper.selectManPromotionForCartOneProduct(1, region_code, brand_id, product_id);
		if (entity != null && entity.condition(num, product.getSales_property().getPrice())) {

			product.setPromotionType(AppPromotionTypeEnum.manEZheKou.getTypeValue());
			product.setPromotion_id(entity.getId());
			product.setCanUseFlag(true, true, true, false, false);

			PriceModel price_modle = entity.stepConditionOfProduct(num, product.getSales_property().getPrice());

			product.getSales_property().setNew_price(price_modle.getPrice());
			product.setTotal_price(price_modle.getTotal_price());

			product.setPrice_type(0);// 兼容旧接口

			setInfoToMap(product.getProduct_id(), "满额折扣", product_info, false);
			RedisUtils.setMap(preOrderStr + "map", product_info);
			return;
		}
		entity = promotionEntityMapper.selectManPromotionForCartOneProduct(2, region_code, brand_id, product_id);
		if (entity != null && entity.condition(num, product.getSales_property().getPrice())) {
			product.setPromotionType(AppPromotionTypeEnum.manEJian.getTypeValue());
			product.setPromotion_id(entity.getId());
			product.setCanUseFlag(true, true, false, false, false);

			PriceModel price_modle = entity.stepConditionOfProduct(num, product.getSales_property().getPrice());

			product.getSales_property().setNew_price(price_modle.getPrice());
			product.setTotal_price(price_modle.getTotal_price());

			product.setPrice_type(0);
			setInfoToMap(product.getProduct_id(), "满额减折扣", product_info, false);
			RedisUtils.setMap(preOrderStr + "map", product_info);
			return;
		}
		entity = promotionEntityMapper.selectManPromotionForCartOneProduct(3, region_code, brand_id, product_id);
		if (entity != null && entity.condition(num, product.getSales_property().getPrice())) {
			product.setPromotionType(AppPromotionTypeEnum.manCountZheKou.getTypeValue());
			product.setPromotion_id(entity.getId());
			product.setCanUseFlag(true, true, true, false, false);

			PriceModel price_modle = entity.stepConditionOfProduct(num, product.getSales_property().getPrice());

			product.getSales_property().setNew_price(price_modle.getPrice());
			product.setTotal_price(price_modle.getTotal_price());

			product.setPrice_type(0);// 兼容旧接口
			setInfoToMap(product.getProduct_id(), "满量折扣", product_info, false);
			RedisUtils.setMap(preOrderStr + "map", product_info);
			return;
		}

		return;
	}

	private void getNewPrice(PromotionEntityStepParam param, Integer num, Double price) {

		param.getStepConditionEntityList();

	}

	/**
	 * 保存产品信息
	 * 
	 * @param product_id
	 *            键
	 * @param value
	 *            值
	 * @param map
	 * @param iscover
	 *            是否覆盖
	 * @return
	 */
	private Map<String, Object> setInfoToMap(Long product_id, String value, Map<String, Object> map, boolean iscover) {

		if (!iscover && map.get(product_id) != null) {
			map.put(product_id.toString(), map.get(product_id) + "-" + value);
		} else {
			map.put(product_id.toString(), value);
		}
		return map;
	}

	/**
	 * 提交订单价格计算
	 */
	public Map<String, Object> calculateforcart_new(CartShop param) {
		try {

			System.out.println("-----------------calculateforcart_new--------------------------------------------");
			System.out.println(new Gson().toJson(param));
			System.out.println("-----------------calculateforcart_new--------------------------------------------");

			Double price = 0D;// 原总价
			Double unionTotalPrice = 0D;// 联盟活动总价
			Double percentTotalPrice = 0D;// 比例总价
			boolean flagPercent = true;// 比例规则
			Map<String, Object> priceResult = new HashMap<String, Object>();

			/////////////////////////////// 初步计算/////////////////////////////////////
			Map<Long, Double> productPrice = new HashMap<Long, Double>();
			Double cut_price = 0D;
			for (CartProduct product : param.getProducts()) {

				BrandProductOneSaleModel productInfo = brandNewProductEntityMapper
						.selectProductOneSaleInfo(product.getProduct_id(), product.getSales_property().getId());

				product.setIsPercentPay(productInfo.getSales_model().equals(2));
				product.setPercent(productInfo.getPercent());
				product.getSales_property().setId(productInfo.getSalesAttrEntity().getId());
				product.getSales_property().setPrice(productInfo.getSalesAttrEntity().getPrice());

				getFlagOfonProduct(product, param.getCustomer_id(), param.getBrand_id(), product.getProduct_id(),
						product.getNum(), param.getPreOrderStr(), param.getRegion_code());
				cut_price += (product.getSales_property().getPrice() * product.getNum() - product.getTotal_price());
			}
			cut_price = NumberUtil.round(cut_price > 0 ? cut_price : 0, 2, BigDecimal.ROUND_HALF_DOWN);

			Double total_price = 0D;
			List<UnionPromotionParam> unionPromotion = null;
			if (param.getUsedPromotion() != null && param.getUsedPromotion().length > 0) {
				unionPromotion = unionCustomerMapper.selectListForCartByCustomerAndPromIdNew(param.getCustomer_id(),
						param.getUsedPromotion());
			}

			for (CartProduct product : param.getProducts()) {

				if (unionPromotion != null) {
					for (UnionPromotionParam up : unionPromotion) {
						if (up.getUsedFlag())
							continue;
						for (Product p : up.getProduct()) {
							if (p.getProduct_id().equals(product.getProduct_id())) {
								up.setUsedFlag(true);
								Double new_total_price = (product.getSales_property().getNew_price() * product.getNum())
										- up.getCash_value();
								Double t = NumberUtil.round(new_total_price, 2, BigDecimal.ROUND_HALF_DOWN);

								product.getSales_property().setNew_price(NumberUtil
										.round(new_total_price / product.getNum(), 2, BigDecimal.ROUND_HALF_DOWN));

								product.setTotal_price(t);

								logger.debug("-------------------------" + param.getPreOrderStr());
								Map<String, Object> product_info = RedisUtils.getMap(param.getPreOrderStr() + "map");
								if (product_info == null || product_info.size() <= 0) {
									product_info = new LinkedHashMap<String, Object>();
								}
								setInfoToMap(product.getProduct_id(), "现金券" + up.getCash_value(), product_info, false);

								break;
							}
						}
					}
				}

				addUsedIds(param.getPreOrderStr() + "p", param.getUsedPromotion());

				if (param.getUsedZhekouQuanModle() != null) {
					if (product.isCanUseZhekouquan()) {

						Double p = product.getSales_property().getNew_price()
								* param.getUsedZhekouQuanModle().getValue();
						p = NumberUtil.round(p, 2, BigDecimal.ROUND_HALF_DOWN);
						product.getSales_property().setNew_price(p);
						product.setTotal_price(product.getSales_property().getNew_price() * product.getNum());

					}
				}

				total_price += product.getTotal_price();

			}

			if (param.getUsedZhekouQuanModle() != null) {
				addUsedIds(param.getPreOrderStr(), param.getUsedZhekouQuanModle().getId() + "");
			}

			if (param.getUsedPrecentPayMent() != null && param.getUsedPrecentPayMent()) {

				// 选中定金支付
				Double per_price = 0D;
				Double left_price = 0D;

				for (CartProduct product : param.getProducts()) {
					if (product.getIsPercentPay()) {
						double percent_price = product.getTotal_price() * (product.getPercent() / 100);
						percent_price = NumberUtil.round(percent_price < 0 ? 0 : percent_price, 2,
								BigDecimal.ROUND_HALF_DOWN);
						per_price += percent_price;

						logger.debug("-------------------------" + param.getPreOrderStr());
						Map<String, Object> product_info = RedisUtils.getMap(param.getPreOrderStr() + "map");
						if (product_info == null || product_info.size() <= 0) {
							product_info = new LinkedHashMap<String, Object>();
						}
						setInfoToMap(product.getProduct_id(), "定金比例" + product.getPercent().intValue() + "%",
								product_info, false);
					} else {
						per_price += product.getTotal_price();
					}
				}

				left_price = total_price - per_price;

				if (param.getUsedCouponsModle() != null) {

					left_price -= param.getUsedCouponsModle().getValue();

					addUsedIds(param.getPreOrderStr(), param.getUsedCouponsModle().getId() + "");
				}

				if (param.getUsedRedBagModle() != null) {

					if (param.getUsedRedBagModle().getType() == 1) {
						left_price -= param.getUsedRedBagModle().getValue();

						addUsedIds(param.getPreOrderStr(), param.getUsedRedBagModle().getId() + "");

					} else if (param.getUsedRedBagModle().getType() == 11) {

						left_price -= param.getUsedRedBagModle().getValue();

						addUsedIds(param.getPreOrderStr() + "shopcash", param.getUsedRedBagModle().getId() + "");

					}
				}

				priceResult.put("shop_id", param.getShop_id());
				priceResult.put("cut_price", cut_price);
				priceResult.put("leftprice", NumberUtil.round(left_price > 0 ? left_price : 0 , 2, BigDecimal.ROUND_HALF_DOWN));
				priceResult.put("price",
						NumberUtil.round(per_price < 0 ? 0 : per_price, 2, BigDecimal.ROUND_HALF_DOWN));

			} else {

				if (param.getUsedCouponsModle() != null) {

					total_price -= param.getUsedCouponsModle().getValue();

					addUsedIds(param.getPreOrderStr(), param.getUsedCouponsModle().getId() + "");
				}

				if (param.getUsedRedBagModle() != null) {

					if (param.getUsedRedBagModle().getType() == 1) {
						total_price -= param.getUsedRedBagModle().getValue();

						addUsedIds(param.getPreOrderStr(), param.getUsedRedBagModle().getId() + "");

					} else if (param.getUsedRedBagModle().getType() == 11) {

						total_price -= param.getUsedRedBagModle().getValue();

						addUsedIds(param.getPreOrderStr() + "shopcash", param.getUsedRedBagModle().getId() + "");

					}
				}

				priceResult.put("shop_id", param.getShop_id());
				priceResult.put("cut_price", cut_price);
				priceResult.put("leftprice", NumberUtil.round(0, 2, BigDecimal.ROUND_HALF_DOWN));
				priceResult.put("price",
						NumberUtil.round(total_price < 0 ? 0 : total_price, 2, BigDecimal.ROUND_HALF_DOWN));

			}

			System.out
					.println("-----------------calculateforcart_new---------return-----------------------------------");
			System.out.println(new Gson().toJson(priceResult));
			System.out
					.println("-----------------calculateforcart_new---------return-----------------------------------");

			return priceResult;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 提交订单价格计算
	 */
	public Map<String, Double> calculateforcart(CartShop param) {
		try {
			Double price = 0D;// 原总价
			Double unionTotalPrice = 0D;// 联盟活动总价
			Double percentTotalPrice = 0D;// 比例总价
			boolean flagPercent = true;// 比例规则
			Map<String, Double> priceResult = new HashMap<String, Double>();
			logger.debug("-------------------------" + param.getPreOrderStr());
			Map<String, Object> product_info = RedisUtils.getMap(param.getPreOrderStr() + "map");
			if (product_info == null || product_info.size() <= 0) {
				product_info = new LinkedHashMap<String, Object>();
			}
			/////////////////////////////// 初步计算/////////////////////////////////////
			Map<Long, Double> productPrice = new HashMap<Long, Double>();
			for (CartProduct product : param.getProducts()) {

				UnionPromotion unionPromotion = unionCustomerMapper.selectCanUseCashListForCartOrderPriceByCustomerId(
						param.getCustomer_id(), product.getProduct_id());

				BrandProductOneSaleModel productInfo = brandNewProductEntityMapper
						.selectProductOneSaleInfo(product.getProduct_id(), product.getSales_property().getId());
				double old_price = productInfo.getSalesAttrEntity().getPrice() * product.getNum();
				product.getSales_property().setOld_price_sum(old_price);// 记录原总价
				Double new_price = getNewPrice(unionPromotion, old_price);
				if (!new_price.equals(old_price)) {
					setInfoToMap(product.getProduct_id(), "打折", product_info, false);
				}
				product.getSales_property().setUnionPromotionPrice_sum(new_price);// 记录联盟活动打折总价
				unionTotalPrice += new_price;// 商铺所有商品联盟活动打折总价
				productPrice.put(product.getProduct_id(), new_price);
				price += old_price;// 商铺所有商品总价
				flagPercent = flagPercent && (productInfo.getPercent() != null);
				if (flagPercent) {
					double percent_price = new_price * (productInfo.getPercent() / 100);
					percent_price = NumberUtil.round(percent_price < 0 ? 0 : percent_price, 2,
							BigDecimal.ROUND_HALF_DOWN);
					percentTotalPrice += percent_price;
					product.getSales_property().setPercent(productInfo.getPercent());
					product.getSales_property().setPercent_price(percent_price);
				}
			}
			System.out.println("原价" + "----------------------------------= " + price);

			/////////////////////////// 秒杀////////////////////////////////////////////
			Double seckillPrice = 0D;// 秒杀总价
			List<Boolean> flagList = new ArrayList<>();
			// 获取可参加的秒杀活动产品列表
			List<Product> promotion_product = promotionEntityMapper
					.selectPromotionProductAndPriceForCart(param.getCustomer_id(), param.getRegion_code());
			for (CartProduct product : param.getProducts()) {
				flagList.add(product.getBuy_type());
			}
			if (flagList.contains(true) && flagList.contains(false)) {
				return null;// 混搭，不计算
			}
			if (flagList.contains(true) && !flagList.contains(false)) {// 全是秒杀产品
				for (CartProduct product : param.getProducts()) {
					Product secKill_product = getSecKillProduct(promotion_product, product.getProduct_id(),
							product.getNum());
					if (secKill_product != null) {
						double tempPrice = secKill_product.getPromotion_price() * product.getNum();// 秒杀价
						seckillPrice += tempPrice;

						setInfoToMap(product.getProduct_id(), "秒杀" + tempPrice, product_info, true);
					} else {// 都是秒杀产品，不符合条件
						// seckillPrice += brandNewProductEntityMapper
						// .selectProductOneSaleInfo(product.getProduct_id(),
						// product.getSales_property().getId())
						// .getSalesAttrEntity().getPrice() * product.getNum();
						// //原价
						seckillPrice += product.getSales_property().getOld_price_sum(); // 原价

						setInfoToMap(product.getProduct_id(), "原价" + product.getSales_property().getOld_price_sum(),
								product_info, true);
					}
				}
				priceResult.put("price",
						NumberUtil.round(seckillPrice < 0 ? 0 : seckillPrice, 2, BigDecimal.ROUND_HALF_DOWN));// zongjia

				System.out.println("秒杀结果" + "----------------------------------= " + priceResult.get("price"));

				RedisUtils.setMap(param.getPreOrderStr() + "map", product_info);
				return priceResult;
			}
			/////////////////////////// 秒杀////////////////////////////////////////////

			Double promotionPrice = 0D;
			if (param.getUsedPromotion() != null && param.getUsedPromotion().length > 0) {

				// 算价格
				List<UnionPromotion> unionPromotion = unionCustomerMapper
						.selectListForCartByCustomerAndPromId(param.getCustomer_id(), param.getUsedPromotion());
				if (unionPromotion != null) {
					List<Long> productList = new ArrayList<>();
					List<Integer> PromotionList = new ArrayList<>();

					for (Map.Entry<Long, Double> entity : productPrice.entrySet()) {// 产品
						System.out.println(
								"--unionPromotion----- " + entity.getKey() + " -----------" + productPrice.size());
						for (int j = 0; j < unionPromotion.size(); j++) {
							System.out.println("=unionPromotion======================" + j);
							if (unionPromotion.get(j).getProduct_id().equals(entity.getKey())) {// 活动

								productList.add(entity.getKey());
								promotionPrice += entity.getValue();

								setInfoToMap(entity.getKey(), "联盟折扣" + unionPromotion.get(j).getDiscount() * 10 + "% ",
										product_info, false);

								if (!PromotionList.contains(unionPromotion.get(j).getId())) {
									PromotionList.add(unionPromotion.get(j).getId());
									promotionPrice = promotionPrice - unionPromotion.get(j).getCash_value();

									// setInfoToMap(entity.getKey(), "现金券" +
									// unionPromotion.get(j).getCash_value(),
									// product_info, false);
								}
							}
						}
					}
					for (Map.Entry<Long, Double> entity : productPrice.entrySet()) {// 产品
						if (!productList.contains(entity.getKey())) {
							promotionPrice += entity.getValue();

							setInfoToMap(entity.getKey(), "原价" + entity.getValue(), product_info, false);
						}
					}
				}
				addUsedIds(param.getPreOrderStr() + "p", param.getUsedPromotion());

				System.out.println("折扣券" + "----------------------------------= " + price);

				if (param.getUsedRedBag() != null && param.getUsedRedBagType() != null
						&& param.getUsedRedBagType() == 1) {
					CustomerCouponsModel b = brandCouponsEntityMapper.selectInfoById(param.getUsedRedBag());
					promotionPrice -= b.getValue();

					addUsedIds(param.getPreOrderStr(), param.getUsedRedBag() + "");

					System.out.println("普通红包" + "----------------------------------= " + price + " | " + b.getValue());
				}

				priceResult.put("price",
						NumberUtil.round(promotionPrice < 0 ? 0 : promotionPrice, 2, BigDecimal.ROUND_HALF_DOWN));// zongjia
				System.out.println("结果Promotion" + "----------------------------------= " + priceResult.get("price"));

				RedisUtils.setMap(param.getPreOrderStr() + "map", product_info);
				return priceResult;
			} else {

				// if (param.getCustomer_id() != null && param.getBrand_id() !=
				// null) {// 有券就打折
				// // 活动期间选中都打折
				// UnionPromotion unionPromotion = unionCustomerMapper
				// .selectCanUseCashOneForCartOrderPrice(param.getCustomer_id(),
				// param.getBrand_id());
				// if (unionPromotion != null) {
				// price = price * (unionPromotion.getDiscount() / 10);
				// }
				// }
				// System.out.println("折扣券" +
				// "----------------------------------= " + price);

				if (param.getIsPrecentPayMent() && flagPercent) {

				} else {

					if (param.getUsedCoupons() != null) {

						CustomerCouponsModel b = brandCouponsEntityMapper.selectInfoById(param.getUsedCoupons());
						if (unionTotalPrice >= b.getStep_value()) {
							unionTotalPrice -= b.getValue();

							System.out.println("优惠券" + "--------------= " + price + " | " + b.getValue());

							// 使用成功
							addUsedIds(param.getPreOrderStr(), param.getUsedCoupons() + "");
						} else {
							return null;// 无效优惠券
						}
					}
				}
				if (param.getUsedRedBag() != null) {

					if (param.getUsedRedBagType() != null && param.getUsedRedBagType() == 1) {
						CustomerCouponsModel b = brandCouponsEntityMapper.selectInfoById(param.getUsedRedBag());
						unionTotalPrice -= b.getValue();

						addUsedIds(param.getPreOrderStr(), param.getUsedRedBag() + "");

						System.out.println(
								"普通红包" + "----------------------------------= " + price + " | " + b.getValue());

					} else if (param.getUsedRedBagType() != null && param.getUsedRedBagType() == 3) {
						ShopPrepayEntity b = shopPrepayEntityMapper
								.selectByPrimaryKey(param.getUsedRedBag().intValue());
						unionTotalPrice -= b.getValue();

						addUsedIds(param.getPreOrderStr() + "shopcash", param.getUsedRedBag() + "");

						System.out.println(
								"商铺定金" + "----------------------------------= " + price + " | " + b.getValue());
					}
				}

			}
			if (param.getIsPrecentPayMent() && flagPercent) {
				priceResult.put("leftprice",
						NumberUtil.round(
								(unionTotalPrice - percentTotalPrice) < 0 ? 0 : (unionTotalPrice - percentTotalPrice),
								2, BigDecimal.ROUND_HALF_DOWN));// weikuan
				priceResult.put("price",
						NumberUtil.round(percentTotalPrice < 0 ? 0 : percentTotalPrice, 2, BigDecimal.ROUND_HALF_DOWN));// dingjin

				for (CartProduct product : param.getProducts()) {
					setInfoToMap(product.getProduct_id(),
							"定金比例" + product.getSales_property().getPercent().intValue() + "%", product_info, false);
				}

			} else {
				priceResult.put("price",
						NumberUtil.round(unionTotalPrice < 0 ? 0 : unionTotalPrice, 2, BigDecimal.ROUND_HALF_DOWN));// zongjia
			}
			RedisUtils.setMap(param.getPreOrderStr() + "map", product_info);

			return priceResult;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// private String modifyRedisValue(String key, String str) {
	// String usedIds = RedisUtils.getValue(key);
	// usedIds = StringUtils.isEmpty(usedIds) ? "0" : usedIds;
	// if (usedIds.startsWith(str + ",")) {
	// usedIds = usedIds.replace(str + ",", "");
	// }
	// usedIds = usedIds.replace("," + str + ",", ",");
	// if (usedIds.endsWith("," + str)) {
	// usedIds = usedIds.replace("," + str, "");
	// }
	// if (usedIds.equals(str)) {
	// usedIds = "0";
	// }
	// // RedisUtils.setWithTimeLimit(key, usedIds, 1L *
	// // SysConstant.SECOND_PER_HOUR);
	// System.out.println("--------重新记录筛选值-----------------" + usedIds);
	// return usedIds;
	// }

	private List<String> modifyRedisValue2(String key, String str) {
		List<String> r_List = null;
		String result = RedisUtils.getValue(key);
		if (StringUtils.isEmpty(result)) {
			r_List = new ArrayList<>();
			r_List.add("0");
		} else {
			r_List = Arrays.asList(result.replace("[", "").replace("]", "").split(","));
			r_List = new ArrayList<>(r_List);
		}
		return r_List;
	}

	/**
	 * 增加操作1
	 */
	public List<String> addUsedIds(String key_str, String str) {

		List<String> r_List = null;
		String result = RedisUtils.getValue(key_str);
		if (StringUtils.isEmpty(result)) {
			r_List = new ArrayList<>();
			r_List.add("0");
			r_List.add(str);
		} else {
			r_List = Arrays.asList(result.replaceAll("\\s*", "").replace("[", "").replace("]", "").split(","));
			r_List = new ArrayList<>(r_List);
			System.out.println("判断包含------------------ " + r_List.contains(str));
			if (!r_List.contains(str)) {
				r_List.add(str);
			}
		}
		System.out.println("增加操作1old------------------ " + result);
		System.out.println("增加操作1new------------------ " + getStr(r_List));
		RedisUtils.setWithTimeLimit(key_str, r_List.toString(), 1L * SysConstant.SECOND_PER_HOUR);
		return r_List;
	}

	/**
	 * 增加操作2
	 */
	public List<String> addUsedIds(String key_str, Long[] str) {

		List<String> r_List = null;
		String result = RedisUtils.getValue(key_str);
		if (StringUtils.isEmpty(result)) {
			r_List = new ArrayList<>();
			r_List.add("0");
			if (str != null) {
				for (Long needRemovePromotion : str) {
					r_List.add(needRemovePromotion + "");
				}
			}
		} else {
			r_List = Arrays.asList(result.replace("[", "").replace("]", "").split(","));
			r_List = new ArrayList<>(r_List);
			if (str != null) {
				for (Long needRemovePromotion : str) {
					if (!r_List.contains(str)) {
						r_List.add(needRemovePromotion + "");
					}
				}
			}
		}
		System.out.println("增加操作2old------------------ " + result);
		System.out.println("增加操作2new------------------ " + getStr(r_List));
		RedisUtils.setWithTimeLimit(key_str, r_List.toString(), 1L * SysConstant.SECOND_PER_HOUR);
		return r_List;
	}

	/**
	 * 移除操作
	 */
	public List<String> relaseUsedIds(CartShop cartShop) {
		try {
			String key = cartShop.getPreOrderStr();
			if (cartShop.getNeedRemoveRedBagType() != null && cartShop.getNeedRemoveRedBagType() == 1) {// 普通红包
				System.out.println("移除普通红包------------------ " + cartShop.getNeedRemoveRedBag());
				return relase(cartShop.getPreOrderStr(), cartShop.getNeedRemoveRedBag() + "");
			} else if (cartShop.getNeedRemoveRedBagType() != null && cartShop.getNeedRemoveRedBagType() == 11) {// 店铺定金红包
				System.out.println("移除店铺定金红包------------------ " + cartShop.getNeedRemoveRedBag());
				return relase(cartShop.getPreOrderStr() + "shopcash", cartShop.getNeedRemoveRedBag() + "");
			}
			if (cartShop.getNeedRemoveCoupons() != null) {// 优惠券
				System.out.println("移除优惠券------------------ " + cartShop.getNeedRemoveCoupons());
				return relase(cartShop.getPreOrderStr(), cartShop.getNeedRemoveCoupons() + "");
			}
			if (cartShop.getNeedRemoveZhekou() != null) {// 折扣券
				System.out.println("移除优惠券------------------ " + cartShop.getNeedRemoveZhekou());
				return relase(cartShop.getPreOrderStr(), cartShop.getNeedRemoveZhekou() + "");
			}
			if (cartShop.getNeedRemovePromotion() != null) {// 联盟活动券
				System.out.println("移除联盟活动券------------------ " + cartShop.getNeedRemovePromotion());
				return relase(cartShop.getPreOrderStr() + "p", cartShop.getNeedRemovePromotion());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> relase(String key_str, String str) {
		List<String> r_List = null;
		String result = RedisUtils.getValue(key_str);

		if (StringUtils.isEmpty(result)) {
			r_List = new ArrayList<>();
			r_List.add("0");
		} else {
			r_List = Arrays.asList(result.replaceAll("\\s*", "").replace("[", "").replace("]", "").split(","));
			r_List = new ArrayList(r_List);
			remove(r_List, str);
		}
		System.out.println("移除old------------------ " + result);
		System.out.println("移除new------------------ " + getStr(r_List));
		RedisUtils.setWithTimeLimit(key_str, r_List.toString(), 1L * SysConstant.SECOND_PER_HOUR);
		return r_List;

	}

	private String getStr(List<String> r_List) {
		StringBuffer sb = new StringBuffer();
		if (r_List != null) {
			for (String s : r_List) {
				sb.append(s).append(" ");
			}
		}
		return sb.toString();
	}

	public List<String> relase(String key_str, Long[] str) {
		List<String> r_List = null;
		String result = RedisUtils.getValue(key_str);
		if (StringUtils.isEmpty(result)) {
			r_List = new ArrayList<>();
			r_List.add("0");
		} else {
			r_List = Arrays.asList(result.replaceAll("\\s*", "").replace("[", "").replace("]", "").split(","));
			r_List = new ArrayList(r_List);
			if (str != null) {
				for (Long needRemovePromotion : str) {
					remove(r_List, needRemovePromotion + "");
				}
			}
		}
		System.out.println("移除old------------------ " + result);
		System.out.println("移除new------------------ " + getStr(r_List));
		RedisUtils.setWithTimeLimit(key_str, r_List.toString(), 1L * SysConstant.SECOND_PER_HOUR);
		return r_List;

	}

	private void remove(List<String> r_List, String str) {
		Iterator<String> i = r_List.iterator();
		while (i.hasNext()) {
			String s = i.next();
			// System.out.println("移除------" + s + "--" + str + "--" +
			// s.equalsIgnoreCase(str) + "--" + (Integer.parseInt(s) ==
			// Integer.parseInt(str)));
			System.out.println("移除2------" + (Integer.parseInt(s + "") == Integer.parseInt(str + "")));

			if (s.equals(str)) {
				i.remove();
			}
		}
	}

	// /**
	// * 移除店铺定金红包
	// */
	// public List<String> relaseShopCashId(CartShop cartShop) {
	// String key = cartShop.getPreOrderStr();
	// String str = cartShop.getNeedRemoveShopCash() + "";
	// List<String> r_List = null;
	// String result = RedisUtils.getValue(key + "shopcash");
	// if (StringUtils.isEmpty(result)) {
	// r_List = new ArrayList<>();
	// r_List.add("0");
	// } else {
	// r_List = Arrays.asList(result.replace("[", "").replace("]",
	// "").split(","));
	// r_List.remove(str);
	// }
	// RedisUtils.setWithTimeLimit(key + "shopcash", r_List.toString(), 1L *
	// SysConstant.SECOND_PER_HOUR);
	// return r_List;
	// }
	//
	// /**
	// * 移除普通红包优惠券
	// */
	// public List<String> relaseRedBagId(CartShop cartShop) {
	// String key = cartShop.getPreOrderStr();
	// String str = cartShop.getNeedRemoveCoupons() + "";
	// List<String> r_List = null;
	// String result = RedisUtils.getValue(key);
	// if (StringUtils.isEmpty(result)) {
	// r_List = new ArrayList<>();
	// r_List.add("0");
	// } else {
	// r_List = Arrays.asList(result.replace("[", "").replace("]",
	// "").split(","));
	// r_List.remove(str);
	// }
	// RedisUtils.setWithTimeLimit(key, r_List.toString(), 1L *
	// SysConstant.SECOND_PER_HOUR);
	// return r_List;
	// }
	//
	// /**
	// * 移除联盟现金券
	// */
	// public List<String> relaseUnionPromotionId(CartShop cartShop) {
	// String key = cartShop.getPreOrderStr();
	// Long[] str = cartShop.getNeedRemovePromotion();
	// List<String> r_List = null;
	// String result = RedisUtils.getValue(key + "p");
	// if (StringUtils.isEmpty(result)) {
	// r_List = new ArrayList<>();
	// r_List.add("0");
	// } else {
	// r_List = Arrays.asList(result.replace("[", "").replace("]",
	// "").split(","));
	// if (str != null) {
	// for (Long needRemovePromotion : str) {
	// r_List.remove(needRemovePromotion);
	// }
	// }
	// }
	// RedisUtils.setWithTimeLimit(key + "p", r_List.toString(), 1L *
	// SysConstant.SECOND_PER_HOUR);
	// return r_List;
	// }

	/**
	 * get已使用的店铺定金红包
	 */
	public List<String> getShopCashId(CartShop cartShop) {
		String key = cartShop.getPreOrderStr();
		List<String> r_List = new ArrayList<>();
		r_List.add("0");
		if (key == null)
			return r_List;
		String result = RedisUtils.getValue(key + "shopcash");
		if (StringUtils.isNotEmpty(result)) {
			r_List = Arrays.asList(result.replace("[", "").replace("]", "").split(","));
			r_List = new ArrayList<String>(r_List);
		}
		System.out.println("已使用的店铺定金红包------------------ " + getStr(r_List));
		return r_List;
	}

	/**
	 * get已使用的普通红包优惠券
	 */
	public List<String> getUsedRedBagId(CartShop cartShop) {
		String key = cartShop.getPreOrderStr();
		List<String> r_List = new ArrayList<>();
		r_List.add("0");
		if (key == null)
			return r_List;
		String result = RedisUtils.getValue(key);
		if (StringUtils.isNotEmpty(result)) {
			r_List = Arrays.asList(result.replace("[", "").replace("]", "").split(","));
			r_List = new ArrayList<String>(r_List);
		}
		System.out.println("已使用的普通红包优惠券------------------ " + key + "--" + result + "--" + getStr(r_List));
		return r_List;
	}

	/**
	 * get已使用的联盟现金券
	 */
	public List<String> getUnionPromotionId(CartShop cartShop) {
		String key = cartShop.getPreOrderStr();
		return getusedUnionPromotionids(key);
	}

	public List<String> getusedUnionPromotionids(String key) {
		List<String> r_List = new ArrayList<String>();
		r_List.add("0");
		String result = RedisUtils.getValue(key + "p");
		if (key == null)
			return r_List;
		if (StringUtils.isNotEmpty(result)) {
			r_List = Arrays.asList(result.replace("[", "").replace("]", "").split(","));
			r_List = new ArrayList<String>(r_List);
		}
		System.out.println("已使用的联盟现金券------------------ " + getStr(r_List));
		return r_List;
	}

	public Integer getpercentpayment(CartShop param) {
		Double price = 0D;// 原总价
		Double percentPrice = 0D;// 比例总价
		boolean flagPercent = true;// 比例规则
		for (CartProduct product : param.getProducts()) {

			BrandProductOneSaleModel productInfo = brandNewProductEntityMapper
					.selectProductOneSaleInfo(product.getProduct_id(), product.getSales_property().getId());

			Double p = productInfo.getSalesAttrEntity().getPrice() * product.getNum();
			price += p;// 商铺所有商品总价
			flagPercent = flagPercent && (productInfo.getPercent() != null);
			if (flagPercent) {
				percentPrice += p * (productInfo.getPercent() / 100);
			}

		}
		if (flagPercent) {
			return new Double((percentPrice / price) * 100).intValue();
		} else {
			return null;
		}
	}

	@Override
	public CartEntity selectByProductIdAndSaleInfoId(Long customer_id, Long product_id, Long sales_property_id,
			Long shop_id) {
		return cartEntityMapper.selectByProductIdAndSaleInfoId(customer_id, product_id, sales_property_id, shop_id);
	}

	@Override
	public List<BaseBrandCouponsEntity> whetherHaveQuanforcart(Long customer_id, Long shop_id) {

		return brandCouponsEntityMapper.whetherHaveQuanforcart(customer_id, shop_id);

	}
}

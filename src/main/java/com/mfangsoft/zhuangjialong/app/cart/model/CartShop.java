package com.mfangsoft.zhuangjialong.app.cart.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.integration.coupons.model.BrandCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.CustomerCouponsModel;

/**
 * @description：购物车
 * @author：Jianghua.Tang @date：2016年5月27日
 * 
 */
@JsonInclude(value = Include.NON_NULL)
public class CartShop {
	private String preOrderStr;// 提交订单前奏代号
	private Long customer_id;
	private Long shop_id;
	private String shop_name;
	private String region_code;
	private Long brand_id;
	private List<CartProduct> products;
	private Double price;

	private CustomerCouponsModel usedRedBagModle;
	private CustomerCouponsModel usedCouponsModle;
	private CustomerCouponsModel usedZhekouQuanModle;
	private Boolean usedPrecentPayMent;
	
	private Long usedCoupons;// 已使用的优惠券
	private Long usedRedBag;// 已使用的红包
	private Long[] usedPromotion;// 已使用的活动现金券
	// private Long usedShopCash;//已使用的商铺现金券
	private Long needRemoveCoupons;// 替换的优惠券需要在内存释放
	private Long needRemoveRedBag;// 替换的红包需要在内存释放
	private Long needRemoveZhekou;// 替换的优惠券需要在内存释放
	private Long[] needRemovePromotion;// 放弃使用的活动现金券需要在内存释放
	// private Long needRemoveShopCash;//放弃使用的活动现金券需要在内存释放
	private Integer isNeedRedbagorCoupons;// 需要查询红包还是优惠券 1红包 2优惠券
	private List<BrandCouponsEntity> brandCoupons;// 待使用的优惠券红包列表
	private Boolean isHaveCoupons;
	private Boolean isHaveZhekou;
	private Boolean isHaveRedbags;
	private Boolean isPrecentPayMent;
	private Boolean isPromotion;

	private Integer usedRedBagType;
	private Integer needRemoveRedBagType;

	private Long[] productIdList;

	// private List<BrandCouponsEntity> coupons;
	// private List<BrandCouponsEntity> redbags;

	// public List<BrandCouponsEntity> getCoupons() {
	// return coupons;
	// }
	//
	// public void setCoupons(List<BrandCouponsEntity> coupons) {
	// this.coupons = coupons;
	// }
	//
	// public List<BrandCouponsEntity> getRedbags() {
	// return redbags;
	// }

	public Long[] getProductIdList() {
		return productIdList;
	}

	public Boolean getUsedPrecentPayMent() {
		return usedPrecentPayMent;
	}

	public void setUsedPrecentPayMent(Boolean usedPrecentPayMent) {
		this.usedPrecentPayMent = usedPrecentPayMent;
	}

	public CustomerCouponsModel getUsedZhekouQuanModle() {
		return usedZhekouQuanModle;
	}

	public void setUsedZhekouQuanModle(CustomerCouponsModel usedZhekouQuanModle) {
		this.usedZhekouQuanModle = usedZhekouQuanModle;
	}

	public CustomerCouponsModel getUsedRedBagModle() {
		return usedRedBagModle;
	}

	public void setUsedRedBagModle(CustomerCouponsModel usedRedBagModle) {
		this.usedRedBagModle = usedRedBagModle;
	}

	public CustomerCouponsModel getUsedCouponsModle() {
		return usedCouponsModle;
	}

	public void setUsedCouponsModle(CustomerCouponsModel usedCouponsModle) {
		this.usedCouponsModle = usedCouponsModle;
	}

	public Long getNeedRemoveZhekou() {
		return needRemoveZhekou;
	}

	public void setNeedRemoveZhekou(Long needRemoveZhekou) {
		this.needRemoveZhekou = needRemoveZhekou;
	}

	public Boolean getIsHaveZhekou() {
		return isHaveZhekou;
	}

	public void setIsHaveZhekou(Boolean isHaveZhekou) {
		this.isHaveZhekou = isHaveZhekou;
	}

	public Boolean getIsPromotion() {
		return isPromotion;
	}

	public void setIsPromotion(Boolean isPromotion) {
		this.isPromotion = isPromotion;
	}

	public void setProductIdList(Long[] productIdList) {
		this.productIdList = productIdList;
	}

	public Integer getUsedRedBagType() {
		return usedRedBagType;
	}

	public void setUsedRedBagType(Integer usedRedBagType) {
		this.usedRedBagType = usedRedBagType;
	}

	public Integer getNeedRemoveRedBagType() {
		return needRemoveRedBagType;
	}

	public void setNeedRemoveRedBagType(Integer needRemoveRedBagType) {
		this.needRemoveRedBagType = needRemoveRedBagType;
	}

	// public Long getNeedRemoveShopCash() {
	// return needRemoveShopCash;
	// }
	//
	// public void setNeedRemoveShopCash(Long needRemoveShopCash) {
	// this.needRemoveShopCash = needRemoveShopCash;
	// }
	//
	// public Long getUsedShopCash() {
	// return usedShopCash;
	// }
	//
	// public void setUsedShopCash(Long usedShopCash) {
	// this.usedShopCash = usedShopCash;
	// }

	public Long[] getUsedPromotion() {
		return usedPromotion;
	}

	public String getRegion_code() {
		return region_code;
	}

	public void setRegion_code(String region_code) {
		this.region_code = region_code;
	}

	public void setUsedPromotion(Long[] usedPromotion) {
		this.usedPromotion = usedPromotion;
	}

	public Long[] getNeedRemovePromotion() {
		return needRemovePromotion;
	}

	public void setNeedRemovePromotion(Long[] needRemovePromotion) {
		this.needRemovePromotion = needRemovePromotion;
	}

	// public void setRedbags(List<BrandCouponsEntity> redbags) {
	// this.redbags = redbags;
	// }
	//
	public List<BrandCouponsEntity> getBrandCoupons() {
		return brandCoupons;
	}

	public void setBrandCoupons(List<BrandCouponsEntity> brandCoupons) {
		this.brandCoupons = brandCoupons;
	}

	public Integer getIsNeedRedbagorCoupons() {
		return isNeedRedbagorCoupons;
	}

	public void setIsNeedRedbagorCoupons(Integer isNeedRedbagorCoupons) {
		this.isNeedRedbagorCoupons = isNeedRedbagorCoupons;
	}

	public Long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}

	public Long getNeedRemoveCoupons() {
		return needRemoveCoupons;
	}

	public void setNeedRemoveCoupons(Long needRemoveCoupons) {
		this.needRemoveCoupons = needRemoveCoupons;
	}

	public Long getNeedRemoveRedBag() {
		return needRemoveRedBag;
	}

	public void setNeedRemoveRedBag(Long needRemoveRedBag) {
		this.needRemoveRedBag = needRemoveRedBag;
	}

	public Boolean getIsHaveCoupons() {
		return isHaveCoupons;
	}

	public void setIsHaveCoupons(Boolean isHaveCoupons) {
		this.isHaveCoupons = isHaveCoupons;
	}

	public Boolean getIsHaveRedbags() {
		return isHaveRedbags;
	}

	public void setIsHaveRedbags(Boolean isHaveRedbags) {
		this.isHaveRedbags = isHaveRedbags;
	}

	public Boolean getIsPrecentPayMent() {
		return isPrecentPayMent;
	}

	public void setIsPrecentPayMent(Boolean isPrecentPayMent) {
		this.isPrecentPayMent = isPrecentPayMent;
	}

	/**
	 * @return the shop_idA
	 * 
	 * 
	 */
	public Long getShop_id() {
		return shop_id;
	}

	public String getPreOrderStr() {
		return preOrderStr;
	}

	public void setPreOrderStr(String preOrderStr) {
		this.preOrderStr = preOrderStr;
	}

	public Long getUsedCoupons() {
		return usedCoupons;
	}

	public void setUsedCoupons(Long usedCoupons) {
		this.usedCoupons = usedCoupons;
	}

	public Long getUsedRedBag() {
		return usedRedBag;
	}

	public void setUsedRedBag(Long usedRedBag) {
		this.usedRedBag = usedRedBag;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(Long brand_id) {
		this.brand_id = brand_id;
	}

	/**
	 * @param shop_id
	 *            the shop_id to set
	 */
	public void setShop_id(Long shop_id) {
		this.shop_id = shop_id;
	}

	/**
	 * @return the shop_name
	 */
	public String getShop_name() {
		return shop_name;
	}

	/**
	 * @param shop_name
	 *            the shop_name to set
	 */
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	/**
	 * @return the products
	 */
	public List<CartProduct> getProducts() {
		return products;
	}

	/**
	 * @param products
	 *            the products to set
	 */
	public void setProducts(List<CartProduct> products) {
		this.products = products;
	}

}

package com.mfangsoft.zhuangjialong.app.cart.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.order.model.Salespropiety;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProdSalesAttrEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionType;

/**
 * @description：购物车产品
 * @author：Jianghua.Tang @date：2016年6月4日
 * 
 */
@JsonInclude(value = Include.NON_NULL)
public class CartProduct {
	private Long id;
	private Long cart_id;// 购物车id
	private Long product_id;
	private String product_name;
	private String imgurl;
	private Integer num;
	// 分类+单位+型号+规格
	private String info;
	private Integer sales_model;
	private Double percent;
	private Double total_price;
	private Double cut_price;
	// 活动类型数组
	private List<Integer> promotion_types;
	// 销售属性
	private Salespropiety sales_property;
	private Boolean isPercentPay;
	private Boolean buy_type;
	private String type_value;
	private Long stock;
	private Integer person_product_num;

	private Integer price_type;//0原价 1秒杀价 2打折价
	
	private Integer promotionType;// 0秒杀 1 满额折扣 2 满额减  3 满量折扣
	private Long promotion_id;
	
	private boolean canUseRedBag;
	private boolean canUseShopPreRedBag;
	private boolean canUseCoupons;
	private boolean canUseZhekouquan;
	private boolean canUseUnionCash;
	
	/**
	 * 
	 * @param canUseRedBag 红包
	 * @param canUseShopPreRedBag 店铺订金券
	 * @param canUseCoupons 优惠券
	 * @param canUseZhekouquan 折扣券
	 * @param canUseUnionCash 现金券
	 */
	public void setCanUseFlag(boolean canUseRedBag, boolean canUseShopPreRedBag, boolean canUseCoupons, boolean canUseZhekouquan, boolean canUseUnionCash) {
		this.canUseRedBag = canUseRedBag;
		this.canUseShopPreRedBag = canUseShopPreRedBag;
		this.canUseCoupons = canUseCoupons;
		this.canUseZhekouquan = canUseZhekouquan;
		this.canUseUnionCash = canUseUnionCash;
	}
	
	public Double getCut_price() {
		return cut_price;
	}

	public void setCut_price(Double cut_price) {
		this.cut_price = cut_price;
	}

	public Long getPromotion_id() {
		return promotion_id;
	}

	public void setPromotion_id(Long promotion_id) {
		this.promotion_id = promotion_id;
	}

	public Double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Double total_price) {
		this.total_price = total_price;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public boolean isCanUseRedBag() {
		return canUseRedBag;
	}

	public void setCanUseRedBag(boolean canUseRedBag) {
		this.canUseRedBag = canUseRedBag;
	}

	public boolean isCanUseShopPreRedBag() {
		return canUseShopPreRedBag;
	}

	public void setCanUseShopPreRedBag(boolean canUseShopPreRedBag) {
		this.canUseShopPreRedBag = canUseShopPreRedBag;
	}

	public boolean isCanUseCoupons() {
		return canUseCoupons;
	}

	public void setCanUseCoupons(boolean canUseCoupons) {
		this.canUseCoupons = canUseCoupons;
	}

	public boolean isCanUseZhekouquan() {
		return canUseZhekouquan;
	}

	public void setCanUseZhekouquan(boolean canUseZhekouquan) {
		this.canUseZhekouquan = canUseZhekouquan;
	}

	public boolean isCanUseUnionCash() {
		return canUseUnionCash;
	}

	public void setCanUseUnionCash(boolean canUseUnionCash) {
		this.canUseUnionCash = canUseUnionCash;
	}

	public Integer getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(Integer promotionType) {
		this.promotionType = promotionType;
	}

	public Integer getPrice_type() {
		return price_type;
	}

	public void setPrice_type(Integer price_type) {
		this.price_type = price_type;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public Integer getPerson_product_num() {
		return person_product_num;
	}

	public void setPerson_product_num(Integer person_product_num) {
		this.person_product_num = person_product_num;
	}

	public String getType_value() {
		return type_value;
	}

	public void setType_value(String type_value) {
		this.type_value = type_value;
	}

	public Boolean getBuy_type() {
		return buy_type;
	}

	public void setBuy_type(Boolean buy_type) {
		this.buy_type = buy_type;
	}

	public Integer getSales_model() {
		return sales_model;
	}

	public void setSales_model(Integer sales_model) {
		this.sales_model = sales_model;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public Boolean getIsPercentPay() {
		return isPercentPay;
	}

	public void setIsPercentPay(Boolean isPercentPay) {
		this.isPercentPay = isPercentPay;
	}

	public Salespropiety getSales_property() {
		return sales_property;
	}

	public Long getCart_id() {
		return cart_id;
	}

	public void setCart_id(Long cart_id) {
		this.cart_id = cart_id;
	}

	public void setSales_property(Salespropiety sales_property) {
		this.sales_property = sales_property;
	}

	/**
	 * @return the product_id
	 */
	public Long getProduct_id() {
		return product_id;
	}

	/**
	 * @param product_id
	 *            the product_id to set
	 */
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}

	/**
	 * @return the product_name
	 */
	public String getProduct_name() {
		return product_name;
	}

	/**
	 * @param product_name
	 *            the product_name to set
	 */
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	/**
	 * @return the num
	 */
	public Integer getNum() {
		return num;
	}

	/**
	 * @param num
	 *            the num to set
	 */
	public void setNum(Integer num) {
		this.num = num;
	}

	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * @param info
	 *            the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Integer> getPromotion_types() {
		return promotion_types;
	}

	public void setPromotion_types(List<Integer> promotion_types) {
		this.promotion_types = promotion_types;
	}

}

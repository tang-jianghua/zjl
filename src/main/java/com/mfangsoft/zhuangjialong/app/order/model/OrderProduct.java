package com.mfangsoft.zhuangjialong.app.order.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProdSalesAttrEntity;

@JsonInclude(value = Include.NON_NULL)
public class OrderProduct {

	private Long order_id;
	private Long product_id;
	private String product_name;
	private Salespropiety sales_properity;
	private Integer num;
	private String info;
	private String image_url;
	private String unit;
	private Boolean isPingjia;
	private Boolean isPercentPay;
	private Double percent;
	private Integer buy_type;
	private String type_value;
	private Double total_price;
	
	private Integer promotionType;// 0秒杀 1 满额折扣 2 满额减  3 满量折扣
	private Long promotion_id;
	
	private boolean canUseRedBag;
	private boolean canUseShopPreRedBag;
	private boolean canUseCoupons;
	private boolean canUseZhekouquan;
	private boolean canUseUnionCash;

	public String getcanUse() {
		return canUseRedBag + "," + canUseShopPreRedBag + "," + canUseCoupons+ "," + canUseZhekouquan + "," + canUseUnionCash;
	}
	
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

	public Boolean getIsPercentPay() {
		return isPercentPay;
	}

	public void setIsPercentPay(Boolean isPercentPay) {
		this.isPercentPay = isPercentPay;
	}

	public Integer getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(Integer promotionType) {
		this.promotionType = promotionType;
	}

	public Long getPromotion_id() {
		return promotion_id;
	}

	public void setPromotion_id(Long promotion_id) {
		this.promotion_id = promotion_id;
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

	public Integer getBuy_type() {
		return buy_type;
	}

	public void setBuy_type(Integer buy_type) {
		this.buy_type = buy_type;
	}

	public String getType_value() {
		return type_value;
	}

	public void setType_value(String type_value) {
		this.type_value = type_value;
	}

	public Boolean getIsPingjia() {
		return isPingjia;
	}

	public void setIsPingjia(Boolean isPingjia) {
		this.isPingjia = isPingjia;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}

	public Salespropiety getSales_properity() {
		return sales_properity;
	}

	public void setSales_properity(Salespropiety sales_properity) {
		this.sales_properity = sales_properity;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

}

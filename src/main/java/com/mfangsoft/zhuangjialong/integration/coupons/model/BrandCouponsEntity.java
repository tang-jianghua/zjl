package com.mfangsoft.zhuangjialong.integration.coupons.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.BaseBrandCouponsEntity;



/**
 *   优惠券红包表
 * table t_biz_brand_coupons
 *
 * @MLTH_generated do_not_delete_during_merge
 */
@JsonInclude(value = Include.NON_NULL)
public class BrandCouponsEntity extends  BaseBrandCouponsEntity {
	
	private List<Long> brand_id;

	private String brand_name;

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public List<Long> getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(List<Long> brand_id) {
		this.brand_id = brand_id;
	}
   
}
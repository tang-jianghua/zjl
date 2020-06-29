package com.mfangsoft.zhuangjialong.app.promotion.model;

import java.util.Date;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.product.model.Product;
import com.mfangsoft.zhuangjialong.integration.promotion.model.BrandProduct;
@JsonInclude(value=Include.NON_NULL)
public class PromotionTimer {
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date start_time;
	private Date end_time;
	private String strtime;
	private Long longtime;
	private String region_code;
	private List<Product> product;
	private Boolean iscurrent;
	private Integer state;
	
	private Long brand_id;
	private String brand_name;
	private String principal;
	private String phone_num;
	
	private Integer[] stateList;
	
	private Integer abc;
	
	private List<Product> product_state0;//0未审核 1通过审核 2 审核未通过 3 申请下架 4 下架
	private List<Product> product_state1;
	private List<Product> product_state2;
	private List<Product> product_state3;
	private List<Product> product_state4;
	private List<Product> product_state5;
	
	private List<BrandProduct> brandProduct; 
	/**
	 * 活动id column pid
	 *
	 * @MLTH_generated
	 */
	private Long pid;

	/**
	 * 时间段id column pid
	 *
	 * @MLTH_generated
	 */
	private Long time_id;

	/**
	 * 图片id column pid
	 *
	 * @MLTH_generated
	 */
	private String imgurl;

	/**
	 * 秒杀段开始时间 column pstart_time
	 *
	 * @MLTH_generated
	 */
	@JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
	private Date pstart_time;

	/**
	 * 秒杀段结束时间 column pend_time
	 *
	 * @MLTH_generated
	 */
	@JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
	private Date pend_time;

	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getStrtime() {
		return strtime;
	}

	public void setStrtime(String strtime) {
		this.strtime = strtime;
	}

	public Long getLongtime() {
		return longtime;
	}

	public void setLongtime(Long longtime) {
		this.longtime = longtime;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public Long getTime_id() {
		return time_id;
	}

	public void setTime_id(Long time_id) {
		this.time_id = time_id;
	}

	public Boolean getIscurrent() {
		return iscurrent;
	}

	public void setIscurrent(Boolean iscurrent) {
		this.iscurrent = iscurrent;
	}

	public List<Product> getProduct() {
		return product;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}

	public String getRegion_code() {
		return region_code;
	}

	public void setRegion_code(String region_code) {
		this.region_code = region_code;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Date getPstart_time() {
		return pstart_time;
	}

	public void setPstart_time(Date pstart_time) {
		this.pstart_time = pstart_time;
	}

	public Date getPend_time() {
		return pend_time;
	}

	public void setPend_time(Date pend_time) {
		this.pend_time = pend_time;
	}

	public Long getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(Long brand_id) {
		this.brand_id = brand_id;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public Integer[] getStateList() {
		return stateList;
	}

	public void setStateList(Integer[] stateList) {
		this.stateList = stateList;
	}

	public Integer getAbc() {
		return abc;
	}

	public void setAbc(Integer abc) {
		this.abc = abc;
	}
	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public List<Product> getProduct_state0() {
		return product_state0;
	}

	public void setProduct_state0(List<Product> product_state0) {
		this.product_state0 = product_state0;
	}

	public List<Product> getProduct_state1() {
		return product_state1;
	}

	public void setProduct_state1(List<Product> product_state1) {
		this.product_state1 = product_state1;
	}

	public List<Product> getProduct_state2() {
		return product_state2;
	}

	public void setProduct_state2(List<Product> product_state2) {
		this.product_state2 = product_state2;
	}

	public List<Product> getProduct_state3() {
		return product_state3;
	}

	public void setProduct_state3(List<Product> product_state3) {
		this.product_state3 = product_state3;
	}

	public List<BrandProduct> getBrandProduct() {
		return brandProduct;
	}

	public void setBrandProduct(List<BrandProduct> brandProduct) {
		this.brandProduct = brandProduct;
	}

	public List<Product> getProduct_state4() {
		return product_state4;
	}

	public void setProduct_state4(List<Product> product_state4) {
		this.product_state4 = product_state4;
	}

	public List<Product> getProduct_state5() {
		return product_state5;
	}

	public void setProduct_state5(List<Product> product_state5) {
		this.product_state5 = product_state5;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	
}

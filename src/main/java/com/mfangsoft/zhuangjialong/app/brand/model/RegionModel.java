package com.mfangsoft.zhuangjialong.app.brand.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.integration.region.model.RegionEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月11日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class RegionModel {
    private String region_code;
	private String city;
	private List<RegionEntity> regions;
	
	
	
	
	/**
	 * @return the region_code
	 */
	public String getRegion_code() {
		return region_code;
	}
	/**
	 * @param region_code the region_code to set
	 */
	public void setRegion_code(String region_code) {
		this.region_code = region_code;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the regions
	 */
	public List<RegionEntity> getRegions() {
		return regions;
	}
	/**
	 * @param regions the regions to set
	 */
	public void setRegions(List<RegionEntity> regions) {
		this.regions = regions;
	}
	
	
	
}

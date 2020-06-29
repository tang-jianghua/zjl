package com.mfangsoft.zhuangjialong.app.product.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.brand.model.BrandModel;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月25日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class SelectPropertiesModel implements Serializable{
       private List<String> color;
       private List<String> standard;
       private List<String> color_model;
       private List<BrandModel> brands;
       private List<Salesinfo> crafts;
       private List<Salesinfo> main_materials;
       private List<Salesinfo> spaces;
       private List<Salesinfo> styles;
       private List<Salesinfo> madeins;
       private List<Salesinfo> haspicture;
       
       private String name;
       private List<Salesinfo> conditions;
       private String param;
       private List<Map<String, Object>> color_models;
       private Integer p_num;
       
       
       

	/**
	 * @return the p_num
	 */
	public Integer getP_num() {
		return p_num;
	}
	/**
	 * @param p_num the p_num to set
	 */
	public void setP_num(Integer p_num) {
		this.p_num = p_num;
	}
	/**
	 * @return the color_models
	 */
	public List<Map<String, Object>> getColor_models() {
		return color_models;
	}
	/**
	 * @param color_models the color_models to set
	 */
	public void setColor_models(List<Map<String, Object>> color_models) {
		this.color_models = color_models;
	}
	/**
	 * @return the param
	 */
	public String getParam() {
		return param;
	}
	/**
	 * @param param the param to set
	 */
	public void setParam(String param) {
		this.param = param;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the conditions
	 */
	public List<Salesinfo> getConditions() {
		return conditions;
	}
	/**
	 * @param conditions the conditions to set
	 */
	public void setConditions(List<Salesinfo> conditions) {
		this.conditions = conditions;
	}
	/**
	 * @return the brands
	 */
	public List<BrandModel> getBrands() {
		return brands;
	}
	/**
	 * @param brands the brands to set
	 */
	public void setBrands(List<BrandModel> brands) {
		this.brands = brands;
	}
	/**
	 * @return the crafts
	 */
	public List<Salesinfo> getCrafts() {
		return crafts;
	}
	/**
	 * @param crafts the crafts to set
	 */
	public void setCrafts(List<Salesinfo> crafts) {
		this.crafts = crafts;
	}
	/**
	 * @return the main_materials
	 */
	public List<Salesinfo> getMain_materials() {
		return main_materials;
	}
	/**
	 * @param main_materials the main_materials to set
	 */
	public void setMain_materials(List<Salesinfo> main_materials) {
		this.main_materials = main_materials;
	}
	/**
	 * @return the spaces
	 */
	public List<Salesinfo> getSpaces() {
		return spaces;
	}
	/**
	 * @param spaces the spaces to set
	 */
	public void setSpaces(List<Salesinfo> spaces) {
		this.spaces = spaces;
	}
	/**
	 * @return the styles
	 */
	public List<Salesinfo> getStyles() {
		return styles;
	}
	/**
	 * @param styles the styles to set
	 */
	public void setStyles(List<Salesinfo> styles) {
		this.styles = styles;
	}
	/**
	 * @return the madeins
	 */
	public List<Salesinfo> getMadeins() {
		return madeins;
	}
	/**
	 * @param madeins the madeins to set
	 */
	public void setMadeins(List<Salesinfo> madeins) {
		this.madeins = madeins;
	}
	/**
	 * @return the haspicture
	 */
	public List<Salesinfo> getHaspicture() {
		return haspicture;
	}
	/**
	 * @param haspicture the haspicture to set
	 */
	public void setHaspicture(List<Salesinfo> haspicture) {
		this.haspicture = haspicture;
	}
	/**
	 * @return the color_model
	 */
	public List<String> getColor_model() {
		return color_model;
	}
	/**
	 * @param color_model the color_model to set
	 */
	public void setColor_model(List<String> color_model) {
		this.color_model = color_model;
	}
	/**
	 * @return the color
	 */
	public List<String> getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(List<String> color) {
		this.color = color;
	}
	/**
	 * @return the standard
	 */
	public List<String> getStandard() {
		return standard;
	}
	/**
	 * @param standard the standard to set
	 */
	public void setStandard(List<String> standard) {
		this.standard = standard;
	}
       
}

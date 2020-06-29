package com.mfangsoft.zhuangjialong.app.product.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月26日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class ArrModel {
     
	private Long[] class_ids;
	private Integer[] spaces;
	private Integer[] styles;
	private Long[] brand_id;
	private Integer[] craft_id;
	private Integer[] main_material;
	private Integer[] madein;
	private String[] thickness;
	private Integer[] texture;
	private Integer[] sizes;
	private Integer[] open_models;
	private Integer[] coating;
	private Integer[] gloss;
	private Integer[] ispalette;
	private Long[] ids;
	private String region_code;
    private Integer[] haspicture;
    private String[][] value_ids;
    private List<String> product_name;
        
    
	

	/**
	 * @return the product_name
	 */
	public List<String> getProduct_name() {
		return product_name;
	}
	/**
	 * @param product_name the product_name to set
	 */
	public void setProduct_name(List<String> product_name) {
		this.product_name = product_name;
	}
	/**
	 * @return the value_ids
	 */
	public String[][] getValue_ids() {
		return value_ids;
	}
	/**
	 * @param value_ids the value_ids to set
	 */
	public void setValue_ids(String[][] value_ids) {
		this.value_ids = value_ids;
	}
	/**
	 * @return the haspicture
	 */
	public Integer[] getHaspicture() {
		return haspicture;
	}
	/**
	 * @param haspicture the haspicture to set
	 */
	public void setHaspicture(Integer[] haspicture) {
		this.haspicture = haspicture;
	}
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
	 * @return the ids
	 */
	public Long[] getIds() {
		return ids;
	}
	/**
	 * @param ids the ids to set
	 */
	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	/**
	 * @return the class_ids
	 */
	public Long[] getClass_ids() {
		return class_ids;
	}
	/**
	 * @param class_ids the class_ids to set
	 */
	public void setClass_ids(Long[] class_ids) {
		this.class_ids = class_ids;
	}
	/**
	 * @return the spaces
	 */
	public Integer[] getSpaces() {
		return spaces;
	}
	/**
	 * @param spaces the spaces to set
	 */
	public void setSpaces(Integer[] spaces) {
		this.spaces = spaces;
	}
	/**
	 * @return the styles
	 */
	public Integer[] getStyles() {
		return styles;
	}
	/**
	 * @param styles the styles to set
	 */
	public void setStyles(Integer[] styles) {
		this.styles = styles;
	}
	/**
	 * @return the brand_id
	 */
	public Long[] getBrand_id() {
		return brand_id;
	}
	/**
	 * @param brand_id the brand_id to set
	 */
	public void setBrand_id(Long[] brand_id) {
		this.brand_id = brand_id;
	}
	/**
	 * @return the craft_id
	 */
	public Integer[] getCraft_id() {
		return craft_id;
	}
	/**
	 * @param craft_id the craft_id to set
	 */
	public void setCraft_id(Integer[] craft_id) {
		this.craft_id = craft_id;
	}
	/**
	 * @return the main_material
	 */
	public Integer[] getMain_material() {
		return main_material;
	}
	/**
	 * @param main_material the main_material to set
	 */
	public void setMain_material(Integer[] main_material) {
		this.main_material = main_material;
	}
	/**
	 * @return the madein
	 */
	public Integer[] getMadein() {
		return madein;
	}
	/**
	 * @param madein the madein to set
	 */
	public void setMadein(Integer[] madein) {
		this.madein = madein;
	}
	/**
	 * @return the thickness
	 */
	public String[] getThickness() {
		return thickness;
	}
	/**
	 * @param thickness the thickness to set
	 */
	public void setThickness(String[] thickness) {
		this.thickness = thickness;
	}
	/**
	 * @return the texture
	 */
	public Integer[] getTexture() {
		return texture;
	}
	/**
	 * @param texture the texture to set
	 */
	public void setTexture(Integer[] texture) {
		this.texture = texture;
	}
	/**
	 * @return the sizes
	 */
	public Integer[] getSizes() {
		return sizes;
	}
	/**
	 * @param sizes the sizes to set
	 */
	public void setSizes(Integer[] sizes) {
		this.sizes = sizes;
	}
	/**
	 * @return the open_models
	 */
	public Integer[] getOpen_models() {
		return open_models;
	}
	/**
	 * @param open_models the open_models to set
	 */
	public void setOpen_models(Integer[] open_models) {
		this.open_models = open_models;
	}
	/**
	 * @return the coating
	 */
	public Integer[] getCoating() {
		return coating;
	}
	/**
	 * @param coating the coating to set
	 */
	public void setCoating(Integer[] coating) {
		this.coating = coating;
	}
	/**
	 * @return the gloss
	 */
	public Integer[] getGloss() {
		return gloss;
	}
	/**
	 * @param gloss the gloss to set
	 */
	public void setGloss(Integer[] gloss) {
		this.gloss = gloss;
	}
	/**
	 * @return the ispalette
	 */
	public Integer[] getIspalette() {
		return ispalette;
	}
	/**
	 * @param ispalette the ispalette to set
	 */
	public void setIspalette(Integer[] ispalette) {
		this.ispalette = ispalette;
	}
	
	
	
}

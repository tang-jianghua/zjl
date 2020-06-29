package com.mfangsoft.zhuangjialong.app.product.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月15日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class ParametersModel {

	private String craft;
	private String main_material;
	private String madein;
	private String standard;
	private String brand_name;
	private String style;
	private String unit;
	private Integer haspicture;
	private String space;
	private String thickness;
	private String texture;
	private String gloss;
	private String coating;
	private Integer ispalette;
	private String open_mode;
	private String size;
	
	
	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}
	/**
	 * @return the craft
	 */
	public String getCraft() {
		return craft;
	}
	/**
	 * @param craft the craft to set
	 */
	public void setCraft(String craft) {
		this.craft = craft;
	}
	/**
	 * @return the main_material
	 */
	public String getMain_material() {
		return main_material;
	}
	/**
	 * @param main_material the main_material to set
	 */
	public void setMain_material(String main_material) {
		this.main_material = main_material;
	}
	/**
	 * @return the madein
	 */
	public String getMadein() {
		return madein;
	}
	/**
	 * @param madein the madein to set
	 */
	public void setMadein(String madein) {
		this.madein = madein;
	}
	/**
	 * @return the standard
	 */
	public String getStandard() {
		return standard;
	}
	/**
	 * @param standard the standard to set
	 */
	public void setStandard(String standard) {
		this.standard = standard;
	}
	/**
	 * @return the brand_name
	 */
	public String getBrand_name() {
		return brand_name;
	}
	/**
	 * @param brand_name the brand_name to set
	 */
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	/**
	 * @return the style
	 */
	public String getStyle() {
		return style;
	}
	/**
	 * @param style the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}
	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * @return the haspicture
	 */
	public Integer getHaspicture() {
		return haspicture;
	}
	/**
	 * @param haspicture the haspicture to set
	 */
	public void setHaspicture(Integer haspicture) {
		this.haspicture = haspicture;
	}
	/**
	 * @return the space
	 */
	public String getSpace() {
		return space;
	}
	/**
	 * @param space the space to set
	 */
	public void setSpace(String space) {
		this.space = space;
	}
	/**
	 * @return the thickness
	 */
	public String getThickness() {
		return thickness;
	}
	/**
	 * @param thickness the thickness to set
	 */
	public void setThickness(String thickness) {
		this.thickness = thickness;
	}
	/**
	 * @return the texture
	 */
	public String getTexture() {
		return texture;
	}
	/**
	 * @param texture the texture to set
	 */
	public void setTexture(String texture) {
		this.texture = texture;
	}
	/**
	 * @return the gloss
	 */
	public String getGloss() {
		return gloss;
	}
	/**
	 * @param gloss the gloss to set
	 */
	public void setGloss(String gloss) {
		this.gloss = gloss;
	}
	/**
	 * @return the coating
	 */
	public String getCoating() {
		return coating;
	}
	/**
	 * @param coating the coating to set
	 */
	public void setCoating(String coating) {
		this.coating = coating;
	}

	/**
	 * @return the ispalette
	 */
	public Integer getIspalette() {
		return ispalette;
	}
	/**
	 * @param ispalette the ispalette to set
	 */
	public void setIspalette(Integer ispalette) {
		this.ispalette = ispalette;
	}
	/**
	 * @return the open_mode
	 */
	public String getOpen_mode() {
		return open_mode;
	}
	/**
	 * @param open_mode the open_mode to set
	 */
	public void setOpen_mode(String open_mode) {
		this.open_mode = open_mode;
	}
	
	
}

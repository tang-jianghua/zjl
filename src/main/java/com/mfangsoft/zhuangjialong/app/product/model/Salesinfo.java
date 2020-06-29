package com.mfangsoft.zhuangjialong.app.product.model;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年5月27日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class Salesinfo implements Serializable{
    private Integer craft_id;
    private Integer main_material;
    private Integer space;
    private Integer style_num;
    private Integer haspicture;
    private Integer madein;
    private Integer id;
    private String name;
    private String id_name;
    private Long property_id;
    private Integer isline;
    private Long product_id;
    private String color_model;
    private String value_id;
    private String value;
    
    
    
    
    
    
    
    
	/**
	 * @return the value_id
	 */
	public String getValue_id() {
		return value_id;
	}
	/**
	 * @param value_id the value_id to set
	 */
	public void setValue_id(String value_id) {
		this.value_id = value_id;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the color_model
	 */
	public String getColor_model() {
		return color_model;
	}
	/**
	 * @param color_model the color_model to set
	 */
	public void setColor_model(String color_model) {
		this.color_model = color_model;
	}
	/**
	 * @return the product_id
	 */
	public Long getProduct_id() {
		return product_id;
	}
	/**
	 * @param product_id the product_id to set
	 */
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	/**
	 * @return the isline
	 */
	public Integer getIsline() {
		return isline;
	}
	/**
	 * @param isline the isline to set
	 */
	public void setIsline(Integer isline) {
		this.isline = isline;
	}
	/**
	 * @return the property_id
	 */
	public Long getProperty_id() {
		return property_id;
	}
	/**
	 * @param property_id the property_id to set
	 */
	public void setProperty_id(Long property_id) {
		this.property_id = property_id;
	}
	/**
	 * @return the id_name
	 */
	public String getId_name() {
		return id_name;
	}
	/**
	 * @param id_name the id_name to set
	 */
	public void setId_name(String id_name) {
		this.id_name = id_name;
	}
	/**
	 * @return the craft_id
	 */
	public Integer getCraft_id() {
		return craft_id;
	}
	/**
	 * @param craft_id the craft_id to set
	 */
	public void setCraft_id(Integer craft_id) {
		this.craft_id = craft_id;
	}
	/**
	 * @return the main_material
	 */
	public Integer getMain_material() {
		return main_material;
	}
	/**
	 * @param main_material the main_material to set
	 */
	public void setMain_material(Integer main_material) {
		this.main_material = main_material;
	}

	/**
	 * @return the space
	 */
	public Integer getSpace() {
		return space;
	}
	/**
	 * @param space the space to set
	 */
	public void setSpace(Integer space) {
		this.space = space;
	}

	/**
	 * @return the style_num
	 */
	public Integer getStyle_num() {
		return style_num;
	}
	/**
	 * @param style_num the style_num to set
	 */
	public void setStyle_num(Integer style_num) {
		this.style_num = style_num;
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
	 * @return the madein
	 */
	public Integer getMadein() {
		return madein;
	}
	/**
	 * @param madein the madein to set
	 */
	public void setMadein(Integer madein) {
		this.madein = madein;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
    
    
}

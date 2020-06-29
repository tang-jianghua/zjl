package com.mfangsoft.zhuangjialong.integration.newproduct.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value=Include.NON_NULL)
public class ClassAttrValueEntity extends BaseClassAttrValueEntity {

	
	public final static  Integer  isenum=1;
	
	public final static Integer iscascading=1;
	
	public final static Integer iswrite=1;
	
	public String image;

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	
	
	
	
}

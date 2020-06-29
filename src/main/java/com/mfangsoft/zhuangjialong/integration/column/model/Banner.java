package com.mfangsoft.zhuangjialong.integration.column.model;

import java.io.Serializable;

public class Banner implements Serializable {
	
	private String imgurl;
	
	private String describe;
	
	private String link_content;
	
	private Integer link_type;

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getLink_content() {
		return link_content;
	}

	public void setLink_content(String link_content) {
		this.link_content = link_content;
	}

	public Integer getLink_type() {
		return link_type;
	}

	public void setLink_type(Integer link_type) {
		this.link_type = link_type;
	}
	
	

}

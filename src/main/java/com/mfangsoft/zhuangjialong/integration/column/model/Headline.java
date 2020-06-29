package com.mfangsoft.zhuangjialong.integration.column.model;

import java.io.Serializable;
import java.util.Date;

public class Headline implements Serializable {
	
	
	private String title;
	
	private String content;
	
	private Date create_time;
	
	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	private String imgurl;

}

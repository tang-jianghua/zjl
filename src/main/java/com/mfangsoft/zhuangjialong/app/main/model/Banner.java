package com.mfangsoft.zhuangjialong.app.main.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：轮播图模型
* @author：Jianghua.Tang 
* @date：2016年5月28日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class Banner {
   private String imgurl;
   private Integer linktype;
   private String content;
/**
 * @return the imgurl
 */
public String getImgurl() {
	return imgurl;
}
/**
 * @param imgurl the imgurl to set
 */
public void setImgurl(String imgurl) {
	this.imgurl = imgurl;
}
/**
 * @return the linktype
 */
public Integer getLinktype() {
	return linktype;
}
/**
 * @param linktype the linktype to set
 */
public void setLinktype(Integer linktype) {
	this.linktype = linktype;
}
/**
 * @return the content
 */
public String getContent() {
	return content;
}
/**
 * @param content the content to set
 */
public void setContent(String content) {
	this.content = content;
}
   
}

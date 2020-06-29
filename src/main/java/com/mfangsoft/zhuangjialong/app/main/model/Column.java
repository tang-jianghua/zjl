package com.mfangsoft.zhuangjialong.app.main.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：栏目模型
* @author：Jianghua.Tang 
* @date：2016年5月28日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class Column {
	private Integer id;
    private String imgurl;
    private String title;
    private String select_url;
    private String selected_url;
    
    
    
    
	/**
	 * @return the select_url
	 */
	public String getSelect_url() {
		return select_url;
	}
	/**
	 * @param select_url the select_url to set
	 */
	public void setSelect_url(String select_url) {
		this.select_url = select_url;
	}
	/**
	 * @return the selected_url
	 */
	public String getSelected_url() {
		return selected_url;
	}
	/**
	 * @param selected_url the selected_url to set
	 */
	public void setSelected_url(String selected_url) {
		this.selected_url = selected_url;
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
    
}

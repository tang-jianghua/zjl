package com.mfangsoft.zhuangjialong.app.article.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.main.model.ColumnImg;
import com.mfangsoft.zhuangjialong.common.model.Page;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月5日
* 
*/

@JsonInclude(value=Include.NON_NULL)
public class ArticlesPageModel {
       
	private ColumnImg imgurl;
	
	private Page<ArticleEntity> articles;
	
	
	/**
	 * @return the imgurl
	 */
	public ColumnImg getImgurl() {
		return imgurl;
	}
	/**
	 * @param imgurl the imgurl to set
	 */
	public void setImgurl(ColumnImg imgurl) {
		this.imgurl = imgurl;
	}
	/**
	 * @return the articles
	 */
	public Page<ArticleEntity> getArticles() {
		return articles;
	}
	/**
	 * @param articles the articles to set
	 */
	public void setArticles(Page<ArticleEntity> articles) {
		this.articles = articles;
	}
	
	
	
}

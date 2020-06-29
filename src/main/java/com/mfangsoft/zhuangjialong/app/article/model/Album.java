package com.mfangsoft.zhuangjialong.app.article.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.common.model.Page;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月15日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class Album extends AbumEntity{

	private Page<ArticleEntity> atricles;//文章列表

	/**
	 * @return the atricles
	 */
	public Page<ArticleEntity> getAtricles() {
		return atricles;
	}

	/**
	 * @param atricles the atricles to set
	 */
	public void setAtricles(Page<ArticleEntity> atricles) {
		this.atricles = atricles;
	}
	
	
}

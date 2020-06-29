package com.mfangsoft.zhuangjialong.integration.column.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.article.model.ArticleEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月28日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class ArticleModel extends ArticleEntity{

	private int collection_num;//分享量
	
	private int likes_num;//点赞量
	
	private int evaluation_num;//评论数
	
	

	/**
	 * @return the evaluation_num
	 */
	public int getEvaluation_num() {
		return evaluation_num;
	}

	/**
	 * @param evaluation_num the evaluation_num to set
	 */
	public void setEvaluation_num(int evaluation_num) {
		this.evaluation_num = evaluation_num;
	}

	/**
	 * @return the collection_num
	 */
	public int getCollection_num() {
		return collection_num;
	}

	/**
	 * @param collection_num the collection_num to set
	 */
	public void setCollection_num(int collection_num) {
		this.collection_num = collection_num;
	}

	/**
	 * @return the likes_num
	 */
	public int getLikes_num() {
		return likes_num;
	}

	/**
	 * @param likes_num the likes_num to set
	 */
	public void setLikes_num(int likes_num) {
		this.likes_num = likes_num;
	}
	
	
}

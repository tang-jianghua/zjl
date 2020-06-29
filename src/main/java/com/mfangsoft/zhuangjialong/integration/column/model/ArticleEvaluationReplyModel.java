package com.mfangsoft.zhuangjialong.integration.column.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月28日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class ArticleEvaluationReplyModel {
 
	private String article_title;//文章标题
	private String album_title;//专辑标题
	private List<ArticleEvaluationModel> articleEvaluationModels;//评论内容
	/**
	 * @return the article_title
	 */
	public String getArticle_title() {
		return article_title;
	}
	/**
	 * @param article_title the article_title to set
	 */
	public void setArticle_title(String article_title) {
		this.article_title = article_title;
	}
	/**
	 * @return the album_title
	 */
	public String getAlbum_title() {
		return album_title;
	}
	/**
	 * @param album_title the album_title to set
	 */
	public void setAlbum_title(String album_title) {
		this.album_title = album_title;
	}
	/**
	 * @return the articleEvaluationModels
	 */
	public List<ArticleEvaluationModel> getArticleEvaluationModels() {
		return articleEvaluationModels;
	}
	/**
	 * @param articleEvaluationModels the articleEvaluationModels to set
	 */
	public void setArticleEvaluationModels(List<ArticleEvaluationModel> articleEvaluationModels) {
		this.articleEvaluationModels = articleEvaluationModels;
	}
	
}

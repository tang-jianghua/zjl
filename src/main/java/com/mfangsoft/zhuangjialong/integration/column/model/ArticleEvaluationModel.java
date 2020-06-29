package com.mfangsoft.zhuangjialong.integration.column.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.article.model.BaseArticleEvaluationEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月28日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class ArticleEvaluationModel extends BaseArticleEvaluationEntity{

	
	private String article_title;//文章标题
	
	private String album_title;//专辑名称
	
	private String customer_name;//评价人昵称

	private String parent_cus_name;//父消费者昵称
	
	private int reply_num;//回复数量
	
	
	
	/**
	 * @return the reply_num
	 */
	public int getReply_num() {
		return reply_num;
	}

	/**
	 * @param reply_num the reply_num to set
	 */
	public void setReply_num(int reply_num) {
		this.reply_num = reply_num;
	}

	/**
	 * @return the parent_cus_name
	 */
	public String getParent_cus_name() {
		return parent_cus_name;
	}

	/**
	 * @param parent_cus_name the parent_cus_name to set
	 */
	public void setParent_cus_name(String parent_cus_name) {
		this.parent_cus_name = parent_cus_name;
	}

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
	 * @return the customer_name
	 */
	public String getCustomer_name() {
		return customer_name;
	}

	/**
	 * @param customer_name the customer_name to set
	 */
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	
	
	
}

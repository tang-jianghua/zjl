package com.mfangsoft.zhuangjialong.app.article.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月29日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class AppArticleEvaluationModel extends BaseArticleEvaluationEntity{
	
	
	private String parent_cus_name;//被回复消费者
	
	private String customer_name;//评价人昵称
	
	private String head_url;//用户头像
	
	
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

	/**
	 * @return the head_url
	 */
	public String getHead_url() {
		return head_url;
	}

	/**
	 * @param head_url the head_url to set
	 */
	public void setHead_url(String head_url) {
		this.head_url = head_url;
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

	private List<AppArticleEvaluationModel> replies;//回复

	/**
	 * @return the replies
	 */
	public List<AppArticleEvaluationModel> getReplies() {
		return replies;
	}

	/**
	 * @param replies the replies to set
	 */
	public void setReplies(List<AppArticleEvaluationModel> replies) {
		this.replies = replies;
	}
	
}

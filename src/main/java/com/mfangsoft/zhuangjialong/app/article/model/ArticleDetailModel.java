package com.mfangsoft.zhuangjialong.app.article.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.common.model.Page;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月29日
* 
*/

@JsonInclude(value=Include.NON_NULL)
public class ArticleDetailModel {
	
	     private Long customer_id;//消费者id
	     
	     private Long article_id;//文章id
	
         private Boolean likes_state;//点赞状态
         
         private Boolean dislikes_state;//点赞状态
         
         private int likes_num;//点赞次数
     
         private Boolean collect_state;//收藏状态
         
         private Integer collect_num;//收藏次数
         
         private List<String> scrollComments;//滚动评论
         
         private List<Long> article_ids;//文章id数组
         
         
         
		/**
		 * @return the article_ids
		 */
		public List<Long> getArticle_ids() {
			return article_ids;
		}

		/**
		 * @param article_ids the article_ids to set
		 */
		public void setArticle_ids(List<Long> article_ids) {
			this.article_ids = article_ids;
		}

		/**
		 * @return the dislikes_state
		 */
		public Boolean getDislikes_state() {
			return dislikes_state;
		}

		/**
		 * @param dislikes_state the dislikes_state to set
		 */
		public void setDislikes_state(Boolean dislikes_state) {
			this.dislikes_state = dislikes_state;
		}

		/**
		 * @return the customer_id
		 */
		public Long getCustomer_id() {
			return customer_id;
		}

		/**
		 * @param customer_id the customer_id to set
		 */
		public void setCustomer_id(Long customer_id) {
			this.customer_id = customer_id;
		}

		/**
		 * @return the article_id
		 */
		public Long getArticle_id() {
			return article_id;
		}

		/**
		 * @param article_id the article_id to set
		 */
		public void setArticle_id(Long article_id) {
			this.article_id = article_id;
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

	

		/**
		 * @return the collect_num
		 */
		public Integer getCollect_num() {
			return collect_num;
		}

		/**
		 * @param collect_num the collect_num to set
		 */
		public void setCollect_num(Integer collect_num) {
			this.collect_num = collect_num;
		}

		/**
		 * @return the likes_state
		 */
		public Boolean getLikes_state() {
			return likes_state;
		}

		/**
		 * @param likes_state the likes_state to set
		 */
		public void setLikes_state(Boolean likes_state) {
			this.likes_state = likes_state;
		}

		/**
		 * @return the collect_state
		 */
		public Boolean getCollect_state() {
			return collect_state;
		}

		/**
		 * @param collect_state the collect_state to set
		 */
		public void setCollect_state(Boolean collect_state) {
			this.collect_state = collect_state;
		}

		/**
		 * @return the scrollComments
		 */
		public List<String> getScrollComments() {
			return scrollComments;
		}

		/**
		 * @param scrollComments the scrollComments to set
		 */
		public void setScrollComments(List<String> scrollComments) {
			this.scrollComments = scrollComments;
		}

 
         
}

package com.mfangsoft.zhuangjialong.app.article.mapper;

import com.mfangsoft.zhuangjialong.app.article.model.ArticleDetailModel;
import com.mfangsoft.zhuangjialong.app.article.model.BaseArticleLikesEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月28日
* 
*/
@WriterRepository
public interface ArticleLikesMapper extends BaseArticleLikesEntityMapper{

	
	/*
	 * 查询文章点赞量
	 */
	 int selectArticleLikesNum(Long article_id);
	 
	 /*
	  * 查询消费者是否点赞过某篇文章
	  */
	 Byte selectWhetherLike(ArticleDetailModel articleDetailModel);
	 
	 /*
	  * 删除点赞记录
	  */
	 int deleteByCustomerIdAndArticleId(BaseArticleLikesEntity articleLikesEntity);
	 
	 /*
	  * 查询点赞记录
	  */
	 BaseArticleLikesEntity selectLikeRecordByCustomerIdAndArticleId(BaseArticleLikesEntity articleLikesEntity);
}

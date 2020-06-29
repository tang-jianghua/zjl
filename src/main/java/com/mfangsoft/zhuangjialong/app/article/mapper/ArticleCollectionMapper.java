package com.mfangsoft.zhuangjialong.app.article.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.app.article.model.ArticleDetailModel;
import com.mfangsoft.zhuangjialong.app.article.model.ArticleEntity;
import com.mfangsoft.zhuangjialong.app.article.model.BaseArticleCollectionEntity;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月28日
* 
*/
@WriterRepository
public interface ArticleCollectionMapper extends BaseArticleCollectionEntityMapper{

	/*
	 * 查询文章收藏量
	 */
	 int selectArticleCollectionNum(Long article_id);
	 
	 /*
	  * 查询消费者是否收藏某篇文章
	  */
	 Boolean selectWhetherCollect(ArticleDetailModel articleDetailModel);
	 /*
	  * 查询消费者是否收藏过某篇文章
	  */
	 BaseArticleCollectionEntity selectCollectRecord(BaseArticleCollectionEntity articleCollectionEntity);
	 
	 /*
	  * 查询收藏的文章
	  */
	 List<ArticleEntity> selectCollectArticlesForPage(Page<ArticleEntity> page);
	 
	 /*
	  * 取消收藏文章
	  */
	 int cancleCollectState(ArticleDetailModel articleCollectionEntity);
}

package com.mfangsoft.zhuangjialong.app.article.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.app.article.model.AppArticleEvaluationModel;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.column.model.ArticleEvaluationModel;
import com.mfangsoft.zhuangjialong.integration.column.model.ArticleEvaluationReplyModel;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月28日
* 
*/
@WriterRepository
public interface ArticleEvaluationMapper extends BaseArticleEvaluationEntityMapper{

	/*
	 * 查询文章一级评论
	 */
	List<ArticleEvaluationModel> selectServerArticleEvaluationsForPage(Page<ArticleEvaluationModel> param);
	
	/*
	 * 查询文章二级评论
	 */
	ArticleEvaluationReplyModel selectServerArticleEvaluationReply(int id);
	
	/*
	 * 查询文章滚动评论
	 */
	List<String> selectArticleScrollComments(Long article_id);
	
	/*
	 * 查询文章一级评论
	 */
	List<AppArticleEvaluationModel> selectAppArticleEvaluationsForPage(Page<AppArticleEvaluationModel> param);
	
	/*
	 * 查询文章二级评论
	 */
	List<AppArticleEvaluationModel> selectAppArticleReplies(Integer main_id);
	
	/*
	 * 增加评论点赞
	 */
	int updateArticleEvaluationLikesNum(Integer id);
	
	/*
	 * 查询文章评论数
	 */
	int selectArticleEvaluationNum(Long article_id);
	
	
	/*
	 * 查询评论点赞数
	 */
	int selectLikeNum(Integer id);
	
	/*
	 * 查询回复数量
	 */
	int selectRepliesNum(Integer main_id);
}

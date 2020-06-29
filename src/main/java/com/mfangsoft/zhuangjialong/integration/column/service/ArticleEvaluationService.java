package com.mfangsoft.zhuangjialong.integration.column.service;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.column.model.ArticleEvaluationModel;
import com.mfangsoft.zhuangjialong.integration.column.model.ArticleEvaluationReplyModel;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月28日
* 
*/

public interface ArticleEvaluationService {
          
	/*
	 * 查询文章一级评论
	 */
	Page<ArticleEvaluationModel> queryArticleEvaluations(Page<ArticleEvaluationModel> param);
	
	/*
	 * 查询文章二级评论
	 */
	ArticleEvaluationReplyModel queryArticleEvaluation(int id);
	
	/*
	 * 修改文章一级评论
	 */
	Boolean modifyArticleEvaluatoin(ArticleEvaluationModel param);
}

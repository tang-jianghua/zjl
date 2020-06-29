package com.mfangsoft.zhuangjialong.integration.column.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.article.mapper.ArticleEvaluationMapper;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.column.model.ArticleEvaluationModel;
import com.mfangsoft.zhuangjialong.integration.column.model.ArticleEvaluationReplyModel;
import com.mfangsoft.zhuangjialong.integration.column.service.ArticleEvaluationService;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月28日
* 
*/
@Service
public class ArticleEvaluationServiceImpl implements ArticleEvaluationService{
  
	@Autowired
     ArticleEvaluationMapper 	articleEvaluationMapper;
	
	@Override
	public Page<ArticleEvaluationModel> queryArticleEvaluations(Page<ArticleEvaluationModel> param) {
		List<ArticleEvaluationModel> list = articleEvaluationMapper.selectServerArticleEvaluationsForPage(param);
		for (ArticleEvaluationModel articleEvaluationModel : list) {
			articleEvaluationModel.setReply_num(articleEvaluationMapper.selectRepliesNum(articleEvaluationModel.getId()));
		}
		param.setData(list);
		 return param;
	}

	@Override
	public Boolean modifyArticleEvaluatoin(ArticleEvaluationModel param) {
		return articleEvaluationMapper.updateByPrimaryKeySelective(param)>0;
	}

	@Override
	public ArticleEvaluationReplyModel queryArticleEvaluation(int id) {
		return articleEvaluationMapper.selectServerArticleEvaluationReply(id);
	}

}

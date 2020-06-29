package com.mfangsoft.zhuangjialong.app.article.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.app.article.model.Album;
import com.mfangsoft.zhuangjialong.app.article.model.AppArticleEvaluationModel;
import com.mfangsoft.zhuangjialong.app.article.model.ArticleDetailModel;
import com.mfangsoft.zhuangjialong.app.article.model.ArticleEntity;
import com.mfangsoft.zhuangjialong.app.article.model.BaseArticleCollectionEntity;
import com.mfangsoft.zhuangjialong.app.article.model.BaseArticleEvaluationEntity;
import com.mfangsoft.zhuangjialong.app.article.model.BaseArticleLikesEntity;
import com.mfangsoft.zhuangjialong.app.article.model.BaseMainArticleColumnEntity;
import com.mfangsoft.zhuangjialong.common.model.Page;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月5日
* 
*/

public interface ArticleService {
	
	/**
	 * 
	* @description：获取文章列表
	* @param：param
	 */
	public Album queryAlbum(Page<ArticleEntity> param);
	
	/**
	 * 
	* @description：获取文章图文详情
	* @param：param
	 */
	public ArticleEntity queryArticleDetails(ArticleEntity param);
	
	/**
	 * 
	* @description：获取文章图片
	* @param：param
	 */
	public String queryImage(Long id);
	
	/*
	 * 查询文章栏目
	 */
	List<BaseMainArticleColumnEntity> selectArticleColumns();
	
	/*
	 * 查询文章评论
	 */
	Page<AppArticleEvaluationModel> queryAppArticleEvaluationForPage(Page<AppArticleEvaluationModel> page);
	/*
	 * 查询文章详情其他信息
	 */
	ArticleDetailModel queryArticleDetailSurplus(ArticleDetailModel detailModel);
	
	/*
	 * 删除点赞或踩的记录
	 */
    Boolean removeLikesRecord(BaseArticleLikesEntity articleLikesEntity);
    
	/*
	 * 添加点赞或踩的记录
	 */
    Boolean addLikesRecord(BaseArticleLikesEntity articleLikesEntity);
    
	/*
	 * 添加文章收藏
	 */
    Boolean addArticleCollection(BaseArticleCollectionEntity articleCollectionEntity);
    
	/*
	 * 修改文章收藏
	 */
    Boolean modifyArticleCollection(ArticleDetailModel articleCollectionEntity);
    
	/*
	 * 查询文章收藏
	 */
    Page<ArticleEntity> queryArticleCollectionForPage(Page<ArticleEntity> param);
    
    /*
     * 点赞评论
     */
    Boolean modifyArticleEvaluation(AppArticleEvaluationModel appArticleEvaluationModel);
    
    /*
	 * 添加文章评论
	 */
    Boolean addArticleEvaluation(BaseArticleEvaluationEntity articleEvaluationEntity);
    
    /*
	 * 更新分享量
	 */
    Boolean updateShareNum(ArticleEntity articleEntity);
} 

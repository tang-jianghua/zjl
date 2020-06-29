package com.mfangsoft.zhuangjialong.integration.column.service;

import java.util.List;

import com.mfangsoft.zhuangjialong.app.article.model.BaseMainArticleColumnEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月25日
* 
*/
public interface MainArticleColumnService {
	
	/*
	 * 查询文章栏目
	 */
	List<BaseMainArticleColumnEntity> selectArticleColumns();
	
	/*
	 * 添加文章栏目
	 */
	Boolean addArticleColumn(BaseMainArticleColumnEntity articleColumnEntity);
	
	/*
	 * 修改文章栏目
	 */
	Boolean modifyArticleColumn(BaseMainArticleColumnEntity articleColumnEntity);
	
	/*
	 * 删除文章栏目
	 */
	Boolean removeArticleColumn(int id);
}

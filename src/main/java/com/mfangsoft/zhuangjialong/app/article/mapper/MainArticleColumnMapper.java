package com.mfangsoft.zhuangjialong.app.article.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.app.article.model.BaseMainArticleColumnEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月25日
* 
*/
@WriterRepository
public interface MainArticleColumnMapper extends BaseMainArticleColumnEntityMapper{

	
	/*
	 * 查询文章栏目所有数据
	 */
	List<BaseMainArticleColumnEntity> selectArticleColumns();
	
}

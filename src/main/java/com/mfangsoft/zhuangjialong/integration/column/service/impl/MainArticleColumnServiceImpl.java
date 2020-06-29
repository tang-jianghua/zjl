package com.mfangsoft.zhuangjialong.integration.column.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.article.mapper.MainArticleColumnMapper;
import com.mfangsoft.zhuangjialong.app.article.model.BaseMainArticleColumnEntity;
import com.mfangsoft.zhuangjialong.integration.column.service.MainArticleColumnService;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月25日
* 
*/
@Service
public class MainArticleColumnServiceImpl implements MainArticleColumnService{

	@Autowired
	MainArticleColumnMapper mainArticleColumnMapper;
	
	@Override
	public List<BaseMainArticleColumnEntity> selectArticleColumns() {
		return mainArticleColumnMapper.selectArticleColumns();
	}
	
	@Override
	public Boolean addArticleColumn(BaseMainArticleColumnEntity articleColumnEntity) {
		articleColumnEntity.setCreate_time(new Date());
		return mainArticleColumnMapper.insertSelective(articleColumnEntity)>0 ;
	}

	@Override
	public Boolean modifyArticleColumn(BaseMainArticleColumnEntity articleColumnEntity) {
		return mainArticleColumnMapper.updateByPrimaryKeySelective(articleColumnEntity)>0;
	}

	@Override
	public Boolean removeArticleColumn(int id) {
		return mainArticleColumnMapper.deleteByPrimaryKey(id)>0;
	}


}

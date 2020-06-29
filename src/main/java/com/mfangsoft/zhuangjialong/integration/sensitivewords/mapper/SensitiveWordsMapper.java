package com.mfangsoft.zhuangjialong.integration.sensitivewords.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.sensitivewords.model.BaseSensitiveWordsEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月28日
* 
*/
@WriterRepository
public interface SensitiveWordsMapper extends BaseSensitiveWordsEntityMapper{

	/*
	 * 分页查询敏感词库
	 */
	List<BaseSensitiveWordsEntity>  selectSensitiveWordsForPage(Page<BaseSensitiveWordsEntity> page);
	
	
	/*
	 * 查询敏感词库
	 */
	List<BaseSensitiveWordsEntity> selectAllSensitiveWords();
}

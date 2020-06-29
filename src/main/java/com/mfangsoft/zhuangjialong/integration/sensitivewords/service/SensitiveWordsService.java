package com.mfangsoft.zhuangjialong.integration.sensitivewords.service;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.sensitivewords.model.BaseSensitiveWordsEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月28日
* 
*/

public interface SensitiveWordsService {

	/*
	 * 查询敏感词库
	 */
	 Page<BaseSensitiveWordsEntity> querySensitiveWordsForPage(Page<BaseSensitiveWordsEntity> page);
	
	 /*
	  * 增加敏感词库
	  */
	 Boolean addSensitiveWord(BaseSensitiveWordsEntity sensitiveWordsEntity);
	 /*
	  * 修改敏感词库
	  */
	 Boolean modifySensitiveWord(BaseSensitiveWordsEntity sensitiveWordsEntity);
	/*
	 * 修改敏感词库
	 */
	 Boolean publishSensitiveWord(BaseSensitiveWordsEntity sensitiveWordsEntity);

		/*
		 * 查询敏感词库（单个）
		 */
	 BaseSensitiveWordsEntity querySensitiveWord(Integer id);

	/**
	* @description：删除词库
	* @param：
	*/
	Integer deleteSensitiveWord(Integer id);

}

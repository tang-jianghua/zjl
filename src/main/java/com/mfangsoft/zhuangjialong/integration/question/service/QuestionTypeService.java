package com.mfangsoft.zhuangjialong.integration.question.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.integration.question.model.QuestionTypeEntity;

public interface QuestionTypeService {
	
	/**
	 * 创建问题类型
	 * @param questionTypeEntity
	 * @return
	 */
	Boolean creatQuestionType(QuestionTypeEntity questionTypeEntity);
	
	/**
	 * 查询问题类型
	 * @return
	 */
	List<QuestionTypeEntity>  getQuestionTypeEntityList(Map<String,Object> map);
	
	
	/**
	 * 删除问题类型
	 * @param id
	 * @return
	 */
	Boolean removeQuestionType(Integer id); 
	
	/**
	 * 修改问题类型
	 * @param questionTypeEntity
	 * @return
	 */
	Boolean modfiyQuestionType(QuestionTypeEntity questionTypeEntity);

}

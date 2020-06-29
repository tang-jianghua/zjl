package com.mfangsoft.zhuangjialong.integration.question.service;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.question.model.QuestionEntity;

public interface QuestionService {
	
	
	Boolean createQuestion(QuestionEntity questionEntity);
	
	
	Boolean removeQuestion(Integer id);
	
	
	Boolean  modifyQuestion(QuestionEntity questionEntity);
	
	
	Page<QuestionEntity> getQuestionForPage(Page<QuestionEntity> page);  
	
	QuestionEntity getQuestion(Integer id);

}

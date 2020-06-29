package com.mfangsoft.zhuangjialong.app.question.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.question.service.QuestionService;
import com.mfangsoft.zhuangjialong.integration.question.mapper.QuestionEntityMapper;
import com.mfangsoft.zhuangjialong.integration.question.mapper.QuestionTypeEntityMapper;
import com.mfangsoft.zhuangjialong.integration.question.model.QuestionEntity;
import com.mfangsoft.zhuangjialong.integration.question.model.QuestionTypeEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月12日
* 
*/
@Service(value="app")
public class QuestionServiceImpl implements QuestionService{
	  @Autowired
	    private QuestionTypeEntityMapper questionTypeEntityMapper;
	  
	  @Autowired
	    private QuestionEntityMapper questionEntityMapper;
		@Override
		public List<QuestionTypeEntity> selectQuestionsInTypes() {
			return questionTypeEntityMapper.selectQuestionsInTypes();
		}
		
		
		@Override
		public String selectDetailsByPrimaryKey(QuestionEntity param) {
			return questionEntityMapper.selectDetailsByPrimaryKey(param.getId());
		}
}

package com.mfangsoft.zhuangjialong.integration.question.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.question.mapper.QuestionEntityMapper;

import com.mfangsoft.zhuangjialong.integration.question.model.QuestionEntity;
@Service(value="question")
public class QuestionServiceImpl implements com.mfangsoft.zhuangjialong.integration.question.service.QuestionService {

	@Autowired
	private QuestionEntityMapper questionEntityMapper;
	
	@Override
	public Boolean createQuestion(QuestionEntity questionEntity) {
		// TODO Auto-generated method stub
		
		if(questionEntityMapper.insertSelective(questionEntity)>0){
			return true;
		}
			
		return false;
	}

	@Override
	public Boolean removeQuestion(Integer id) {
		// TODO Auto-generated method stub
		if(questionEntityMapper.deleteByPrimaryKey(id)>0){
			return true;
		}
			
		return false;
	}

	@Override
	public Boolean modifyQuestion(QuestionEntity questionEntity) {
		// TODO Auto-generated method stub
		if(questionEntityMapper.updateByPrimaryKeyWithBLOBs(questionEntity)>0){
			return true;
		}
		return false;
	}

	@Override
	public Page<QuestionEntity> getQuestionForPage(Page<QuestionEntity> page) {
		// TODO Auto-generated method stub
		
		page.setData(questionEntityMapper.getQuestionForPage(page));
		return page;
	}

	@Override
	public QuestionEntity getQuestion(Integer id) {
		// TODO Auto-generated method stub
		return questionEntityMapper.selectByPrimaryKey(id);
	}

}

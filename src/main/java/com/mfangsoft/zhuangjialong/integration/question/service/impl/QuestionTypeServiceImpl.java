package com.mfangsoft.zhuangjialong.integration.question.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.integration.question.mapper.QuestionTypeEntityMapper;
import com.mfangsoft.zhuangjialong.integration.question.model.QuestionTypeEntity;
import com.mfangsoft.zhuangjialong.integration.question.service.QuestionTypeService;
@Service
public class QuestionTypeServiceImpl implements QuestionTypeService {
	
	@Autowired
	private QuestionTypeEntityMapper questionTypeEntityMapper;

	@Override
	public Boolean creatQuestionType(QuestionTypeEntity questionTypeEntity) {
		// TODO Auto-generated method stub
		
		if(questionTypeEntityMapper.insertSelective(questionTypeEntity)>0){
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public List<QuestionTypeEntity> getQuestionTypeEntityList(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return questionTypeEntityMapper.selectQuestionTypes(map);
	}

	@Override
	public Boolean removeQuestionType(Integer id) {
		// TODO Auto-generated method stub
		
		if(questionTypeEntityMapper.deleteByPrimaryKey(id)>0){
			return true;
		}
		return false;
	}

	@Override
	public Boolean modfiyQuestionType(QuestionTypeEntity questionTypeEntity) {
		// TODO Auto-generated method stub
		
		if(questionTypeEntityMapper.updateByPrimaryKeySelective(questionTypeEntity)>0){
			return true;
		}
		return false;
	}

}

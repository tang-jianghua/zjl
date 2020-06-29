package com.mfangsoft.zhuangjialong.app.question.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.personalCenter.model.Coupons;
import com.mfangsoft.zhuangjialong.app.question.model.Question;
import com.mfangsoft.zhuangjialong.app.question.service.QuestionService;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.integration.question.model.QuestionEntity;
import com.mfangsoft.zhuangjialong.integration.question.model.QuestionTypeEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年5月30日
* 
*/
@Controller(value="appquestion")
@RequestMapping("/app")
public class QuestionController {
	@Autowired
	private QuestionService questionServiceImpl;
	@RequestMapping(value = "/queryquestionsintypes", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<QuestionTypeEntity>> queryQuestionsInTypes(){
		List<QuestionTypeEntity> result = questionServiceImpl.selectQuestionsInTypes();
		
		ResponseMessage<List<QuestionTypeEntity>> responseMessage=new ResponseMessage<> ();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		responseMessage.setResult(result);
		return responseMessage;
	}
	
	@RequestMapping(value = "/queryquestiondetails", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> queryQuestionDetails(@RequestBody QuestionEntity param){
		ResponseMessage<String> responseMessage=new ResponseMessage<> ();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		responseMessage.setResult(questionServiceImpl.selectDetailsByPrimaryKey(param));
		return responseMessage;
	}
}

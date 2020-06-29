package com.mfangsoft.zhuangjialong.integration.question.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.question.model.Question;
import com.mfangsoft.zhuangjialong.integration.question.model.QuestionEntity;
import com.mfangsoft.zhuangjialong.integration.question.model.QuestionTypeEntity;
import com.mfangsoft.zhuangjialong.integration.question.service.QuestionService;
import com.mfangsoft.zhuangjialong.integration.question.service.QuestionTypeService;
@Controller
@RequestMapping("/server")
public class QuestionController {
	@Autowired
	private QuestionTypeService questionTypeService;
	
	@Autowired
	private QuestionService questionService;
	@RequestMapping(value="/createquestiontype",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> createQuestionType(@RequestBody QuestionTypeEntity questionTypeEntity){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		if(questionTypeService.creatQuestionType(questionTypeEntity)){
			message.setCode(SysConstant.SUCCESS_CODE);
			
			message.setMessage(SysConstant.SUCCESS_MSG);
			
		}else{
			
			message.setCode(SysConstant.FAILURE_CODE);
			
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	
	
	@RequestMapping(value="/modifyquestiontype",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyQuestionType(@RequestBody QuestionTypeEntity questionTypeEntity){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		if(questionTypeService.modfiyQuestionType(questionTypeEntity))
		{
		message.setCode(SysConstant.SUCCESS_CODE);
		
		message.setMessage(SysConstant.SUCCESS_MSG);
		}else
		{
			
			message.setCode(SysConstant.FAILURE_CODE);
			
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		return message;
		
		
		
	}
	
	
	@RequestMapping(value="/getquestiontypebyid/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> getQuestionType(@PathVariable Long id){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		message.setCode(SysConstant.SUCCESS_CODE);
		
		message.setMessage(SysConstant.SUCCESS_MSG);
		
		return message;
		
		
		
	}
	
	@RequestMapping(value="/removequestiontype/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> removeQuestionType(@PathVariable Integer id){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		if(questionTypeService.removeQuestionType(id)){
			message.setCode(SysConstant.SUCCESS_CODE);
			
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
		
	}
	
	
	
	
	@RequestMapping(value="/queryquestiontype",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public List<QuestionTypeEntity> getQuestionType(@RequestBody  Map<String,Object> map){
		
		
		return questionTypeService.getQuestionTypeEntityList(map);
		
	}
	
	@RequestMapping(value="/createquestion",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> createQuestion(@RequestBody QuestionEntity questionEntity){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		if(questionService.createQuestion(questionEntity)){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			
			message.setCode(SysConstant.FAILURE_MSG);
			
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
		
	}
	
	
	@RequestMapping(value="/modifyquestion",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyQuestion(@RequestBody QuestionEntity questionEntity){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		if(questionService.modifyQuestion(questionEntity)){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			
			message.setCode(SysConstant.FAILURE_MSG);
			
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		return message;
		
		
		
	}
	
	
	@RequestMapping(value="/getquestionbyid/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<QuestionEntity> getQuestion(@PathVariable Integer id){
		
		ResponseMessage<QuestionEntity> message = new ResponseMessage<QuestionEntity>();
		
		
		
		message.setCode(SysConstant.SUCCESS_CODE);
		
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(questionService.getQuestion(id));
		return message;
		
		
		
	}
	
	@RequestMapping(value="/removequestion/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> removeQuestion(@PathVariable Integer id){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		if(questionService.removeQuestion(id)){
			message.setCode(SysConstant.SUCCESS_CODE);
			
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			
			message.setCode(SysConstant.FAILURE_CODE);
			
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
	
		
		return message;
		
		
		
	}
	
	
	
	
	@RequestMapping(value="/queryquestion",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<QuestionEntity> getQuestion(@RequestBody Page<QuestionEntity> page){
		
		return questionService.getQuestionForPage(page);
	}
	/**
	 * 根据问题分类查询问题
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/queryquestionbytype",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<Question>>  queryQuestionByType(@RequestBody Long id){
		
		ResponseMessage<List<Question>> message = new ResponseMessage<List<Question>>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		//message.setResult(enterprise);
		List<Question> questions = new ArrayList<Question>();
		
		Question question = new Question();
	
		questions.add(question);
		
		message.setResult(questions);
		return message;
		
	}
	
	

}

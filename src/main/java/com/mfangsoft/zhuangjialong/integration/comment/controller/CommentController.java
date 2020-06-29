package com.mfangsoft.zhuangjialong.integration.comment.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.evaluation.mapper.EvaluationEntityMapper;
import com.mfangsoft.zhuangjialong.app.evaluation.model.EvaluationEntity;
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.comment.service.CommentService;
@Controller
@RequestMapping("/server")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private EvaluationEntityMapper evaluationEntityMapper;
	
	@RequestMapping(value="/queryproductcomment",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,Object>> queryProductComment(@RequestBody Page<Map<String,Object>> page){
		
		return commentService.getCommentListForPage(page);
	}
	
	@RequestMapping(value="/modifyproductcommentstate/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyProductCommentState(@PathVariable Long id){
		
		EvaluationEntity evaluationEntity = new EvaluationEntity();
		
		evaluationEntity.setId(id);
		
		evaluationEntity.setState(1);
		evaluationEntityMapper.updateByPrimaryKeySelective(evaluationEntity);
		ResponseMessage<String> message = new ResponseMessage<String>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}
	
	@RequestMapping(value="/reply",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyProductCommentState(@RequestBody Map<String,Object> map){
		
		map.put("user_id", UserContext.getCurrentUserId());
		
		map.put("reply_time", new Date());
		evaluationEntityMapper.insertreply(map);
		ResponseMessage<String> message = new ResponseMessage<String>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}

}

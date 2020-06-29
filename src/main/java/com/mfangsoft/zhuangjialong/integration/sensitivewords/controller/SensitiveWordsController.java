package com.mfangsoft.zhuangjialong.integration.sensitivewords.controller;

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
import com.mfangsoft.zhuangjialong.integration.sensitivewords.model.BaseSensitiveWordsEntity;
import com.mfangsoft.zhuangjialong.integration.sensitivewords.service.SensitiveWordsService;
import com.mfangsoft.zhuangjialong.system.model.sysConstantEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月28日
* 
*/
@Controller
@RequestMapping(value="/server")
public class SensitiveWordsController {

	@Autowired
	SensitiveWordsService sensitiveWordsServiceImpl;
	
	
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/querysensitivewords", method = RequestMethod.POST)
	public Page<BaseSensitiveWordsEntity> querySensitiveWords(@RequestBody Page<BaseSensitiveWordsEntity> page){
		return sensitiveWordsServiceImpl.querySensitiveWordsForPage(page);
	}
	
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/querysensitiveword/{id}", method = RequestMethod.POST)
	public ResponseMessage<BaseSensitiveWordsEntity> querySensitiveWord(@PathVariable Integer id){
		ResponseMessage<BaseSensitiveWordsEntity> message = new ResponseMessage<>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(sensitiveWordsServiceImpl.querySensitiveWord(id));
		return message;
	}
	
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/deletesensitiveword/{id}", method = RequestMethod.POST)
	public ResponseMessage<String>  deleteSensitiveWord(@PathVariable Integer id){
		ResponseMessage<String> message = new ResponseMessage<>();
		Integer deleteSensitiveWord = sensitiveWordsServiceImpl.deleteSensitiveWord(id);
		if(deleteSensitiveWord==1){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else if(deleteSensitiveWord==0){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}else if(deleteSensitiveWord==2){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage("发布状态，不能删除。");
		}
		return message;
		
	}
	
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/addsensitivewords", method = RequestMethod.POST)
	public ResponseMessage<String> addSensitiveWords(@RequestBody BaseSensitiveWordsEntity sensitiveWordsEntity){
		ResponseMessage<String> message = new ResponseMessage<>();
		if(sensitiveWordsServiceImpl.addSensitiveWord(sensitiveWordsEntity)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/modifysensitivewords", method = RequestMethod.POST)
	public ResponseMessage<String> modifySensitiveWords(@RequestBody BaseSensitiveWordsEntity sensitiveWordsEntity){
		ResponseMessage<String> message = new ResponseMessage<>();
		if(sensitiveWordsServiceImpl.modifySensitiveWord(sensitiveWordsEntity)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/publishsensitivewords", method = RequestMethod.POST)
	public ResponseMessage<String> publishSensitiveWords(@RequestBody BaseSensitiveWordsEntity sensitiveWordsEntity){
		ResponseMessage<String> message = new ResponseMessage<>();
		if(sensitiveWordsServiceImpl.publishSensitiveWord(sensitiveWordsEntity)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	
}

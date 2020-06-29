package com.mfangsoft.zhuangjialong.app.helpfeedback.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.helpfeedback.model.HelpFeedBackEntity;
import com.mfangsoft.zhuangjialong.app.helpfeedback.service.HelpFeedBackService;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月1日
* 
*/
@Controller
@RequestMapping("/app")
public class HelpFeedBackController {
     @Autowired
     private HelpFeedBackService helpFeedBackServiceImpl;
	
	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> feedBack(@RequestBody HelpFeedBackEntity param) {
		ResponseMessage<String> message = new ResponseMessage<>();
		 if(param.getFeedbacker_id()==null){
			 message.setCode(SysConstant.FAILURE_CODE);
			 message.setMessage(SysConstant.not_login);
			 return message;
		 }

		
		boolean b = helpFeedBackServiceImpl.addFeedBack(param);
		if(b){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
}

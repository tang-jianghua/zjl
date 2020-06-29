package com.mfangsoft.zhuangjialong.integration.constructor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.constructor.model.Constructor;

@Controller
@RequestMapping("/server")
public class ConstructorController {

	
	@RequestMapping(value="/applyconstructor",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> applyconstructor(@RequestBody Constructor constructor ){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}
	
	
}

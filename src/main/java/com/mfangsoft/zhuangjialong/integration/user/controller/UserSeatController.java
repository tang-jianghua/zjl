package com.mfangsoft.zhuangjialong.integration.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;
import com.mfangsoft.zhuangjialong.integration.user.service.UserService;

@RequestMapping("/app")
@Controller
public class UserSeatController {

	
	@Autowired
	private UserService userService; 
	
	@RequestMapping(value="/getSeat/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<UserEntity>  getSeat(@PathVariable Long id)
	{
		ResponseMessage<UserEntity>  message= new  ResponseMessage<>();
			
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(userService.selectUserByDep(id));
		return message;
	
	}
}

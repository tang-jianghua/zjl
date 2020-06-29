package com.mfangsoft.zhuangjialong.integration.user.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.user.service.EasemobIMService;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年12月26日
* 
*/

@Controller
@RequestMapping(value="/easemob")
public class EasemobIMController {

	@Autowired
	EasemobIMService easemobIMServiceImpl;
	
	@RequestMapping(value="/registEasemob",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> registEasemob(@RequestBody Map<String, String> map){
		ResponseMessage<String> message = new ResponseMessage<>();
		
		try {
			Boolean register = easemobIMServiceImpl.register(map.get("account"), map.get("pwd"));
			if(register){
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
			}else{
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(SysConstant.FAILURE_MSG);
			}
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			
			
			e.printStackTrace();
		}
	return message;
	}
}

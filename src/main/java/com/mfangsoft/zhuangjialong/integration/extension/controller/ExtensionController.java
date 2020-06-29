package com.mfangsoft.zhuangjialong.integration.extension.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.extension.model.Extension;
//import com.mfangsoft.zhuangjialong.integration.partner.model.Partner;

@Controller
@RequestMapping("/server")
public class ExtensionController {

	
	@RequestMapping(value="/createextension", method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> addExtension(@RequestBody Extension extension ){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}
//	@RequestMapping(value="/getextensionbyid",method=RequestMethod.GET)
//	@ResponseBody
//	@CrossOrigin
//	public ResponseMessage<Partner> getExtension(@RequestBody Long id)
//	{
//		ResponseMessage<Partner> message = new ResponseMessage<Partner>();
//		
//		Partner partner= new Partner();
//		
//		message.setCode(SysConstant.SUCCESS_CODE);
//		message.setMessage(SysConstant.SUCCESS_MSG);
//		message.setResult(partner);
//		return  message;
//		
//	}
	
	@RequestMapping(value="/modifyextension",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyExtension(@RequestBody Extension extension)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		
		
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		//message.setResult(enterprise);
		return  message;
		
	}
	
	@RequestMapping(value="/queryextension",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<Extension>> queryExtension(@RequestBody Page<Extension> page)
	{
		ResponseMessage<Page<Extension>> message = new ResponseMessage<Page<Extension>>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		//message.setResult(enterprise);
		List<Extension> extensions = new ArrayList<Extension>();
		Extension extension = new Extension();
		extension.setName("李");
		extension.setCreate_time(new Date());
		
		extension.setPhonenum("19828749450");
		
		extension.setPartner_name("王");
		
		extension.setState(1);
		
		extension.setExtend_count(100);
		
		extensions.add(extension);
		//extensions.add(partner2);
		page.setData(extensions);
		message.setResult(page);
		
		return  message;
		
	}
}

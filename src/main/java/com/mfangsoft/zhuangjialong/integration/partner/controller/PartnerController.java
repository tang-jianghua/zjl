package com.mfangsoft.zhuangjialong.integration.partner.controller;

import java.util.HashMap;
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

import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.enums.UserState;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerModle;
import com.mfangsoft.zhuangjialong.integration.partner.service.PartnerService;
@Controller(value="partner")
@RequestMapping("/server")
public class PartnerController {

	@Autowired
	private PartnerService partnerService;
	
	@RequestMapping(value="/createpartner", method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> addPartner(@RequestBody PartnerEntity partnerEntity ){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		if(partnerService.addPartner(partnerEntity))
		{
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		return message;
	}
	@RequestMapping(value="/getpartnerbyid/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<PartnerEntity> getPartner(@PathVariable Long id)
	{
		ResponseMessage<PartnerEntity> message = new ResponseMessage<PartnerEntity>();
		
		
		
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(partnerService.getPartnerById(id));
		return  message;
		
	}
	
	@RequestMapping(value="/modifypartner",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyPartner(@RequestBody PartnerEntity partnerEntity)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		if(partnerService.modifyPartner(partnerEntity))
		{
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return  message;
		
	}
	@RequestMapping(value="/modifybackpartner",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyBackPartner(@RequestBody PartnerEntity partnerEntity)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		if(partnerService.modifyBackPartner(partnerEntity))
		{
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return  message;
		
	}
	@RequestMapping(value="/closepartner/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> closePartner(@PathVariable Long id)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		if(partnerService.modifyopenOrClosePartner(id, UserState.CLOSE.getIndex())){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_CODE);
		}
		
		
		
		return  message;
		
	}
	
	@RequestMapping(value="/openpartner/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> openPartner(@PathVariable Long id)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		
		
		if(partnerService.modifyopenOrClosePartner(id, UserState.OPEN.getIndex())){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_CODE);
		}
		
		
		
		return  message;
		
	}
	
	
	@RequestMapping(value="/querypartner",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,Object>> queryPartner(@RequestBody Page<Map<String,Object>> page)
	{
		
		return  partnerService.queryPartnerForPage(page);
		
	}
	
	@RequestMapping(value="/initpartner",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public Object initPartner()
	{
		
		return  UserContext.getCurrentUserInfo();
		
	}
	
	@RequestMapping(value="/selectAllPartner",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public List<PartnerModle> selectAllPartner()
	{
		return  partnerService.selectAllPartner();
		
	}
	
}

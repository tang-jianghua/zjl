package com.mfangsoft.zhuangjialong.integration.partner.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.applogin.mapper.UserEquipmentEntityMapper;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.partner.service.OpenCityService;
import com.mfangsoft.zhuangjialong.integration.partner.service.PartnerService;

@Controller
@RequestMapping("/server")
public class OpenCityController {
	
	@Autowired
	private OpenCityService openCityService;
	
	@Autowired 
	private PartnerService partnerService ;
	
	@RequestMapping(value="/addopencity/{id}", method=RequestMethod.GET)
	@ResponseBody
	private ResponseMessage<String> addOpenCity(@PathVariable Long id){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		try {
			
			
//			PartnerEntity  partnerEntity = new PartnerEntity();
//			
//			partnerEntity.setCheck_state(2);
			
			//partnerService.modifyBackPartner(partnerEntity);
			openCityService.addOpenCity(id);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
	
		return message;
		
	}
	
	@RequestMapping(value="/queryopencity", method=RequestMethod.GET)
	@ResponseBody
	private ResponseMessage<List<Map<String,Object>>> queryOpenCity(){
		
		ResponseMessage<List<Map<String,Object>>> message = new ResponseMessage<List<Map<String,Object>>>();
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(openCityService.queryOpenCity());
		
		return message;
		
	}
	
	
	@RequestMapping(value="/closecity", method=RequestMethod.POST)
	@ResponseBody
	private ResponseMessage<String> closeOpenCity(@RequestBody Map<String,Object> map){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		try {
			
			PartnerEntity  partnerEntity = new PartnerEntity();
			partnerEntity.setId(new Long(map.get("id").toString()));
			partnerEntity.setClose_time(map.get("close_time").toString());
			
			openCityService.closeCity(partnerEntity);
			//partnerService.modifyPartner(partnerEntity);
			openCityService.pushMessage(new Long(map.get("id").toString()),map.get("close_time").toString());
			//给该城市未完成订单的人发短信
			openCityService.sendSMS(new Long(map.get("id").toString()), map.get("close_time").toString());
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_CODE);
		}
		return message;
		
	}
	
	
	
	@RequestMapping(value="/closecity/{id}", method=RequestMethod.GET)
	@ResponseBody
	private ResponseMessage<String> closeOpenCity(@PathVariable Long id){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		try {
			
			
			
			openCityService.forceClose(id);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_CODE);
		}
		return message;
		
	}
	
	@RequestMapping(value="/checkopenctiy/{id}", method=RequestMethod.GET)
	@ResponseBody
	private ResponseMessage<String> checkOpenCtiy(@PathVariable Long id){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		
		if(openCityService.checkOpenCtiy(id)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
		
	}

}

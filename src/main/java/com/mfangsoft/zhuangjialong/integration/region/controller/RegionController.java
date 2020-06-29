package com.mfangsoft.zhuangjialong.integration.region.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.integration.region.model.RegionEntity;
import com.mfangsoft.zhuangjialong.integration.region.service.RegionService;
@Controller
@RequestMapping("/server")
public class RegionController {
	
	@Autowired
	private RegionService regionService;   
	
	
	@RequestMapping(value="/province",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<RegionEntity>> getProvince(){
		
		ResponseMessage<List<RegionEntity>> message = new ResponseMessage<List<RegionEntity>>();
		message.setResult(regionService.getProvince());
		
		return message;
		
	}
	
	
	@RequestMapping(value="/city/{code}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<RegionEntity>> getCity(@PathVariable String code){
		
		ResponseMessage<List<RegionEntity>> message = new ResponseMessage<List<RegionEntity>>();
		
		message.setResult(regionService.getCity(code));
		
		return message;
		
	}
	
	@RequestMapping(value="/county/{code}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<RegionEntity>> getCounty(@PathVariable String code){
		
		ResponseMessage<List<RegionEntity>> message = new ResponseMessage<List<RegionEntity>>();
		
		message.setResult(regionService.getCounty(code));
		
		return message;
		
	}
	@RequestMapping(value="/getname/{code}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Map<String,RegionEntity>> getName(@PathVariable String code){
		
		ResponseMessage<Map<String,RegionEntity>> message = new ResponseMessage<>();
		
		message.setResult(regionService.getName(code));
		
		return message;
		
	}
	
	@RequestMapping(value="/getregion/{code}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> getRegionName(@PathVariable String code){
		
		ResponseMessage<String> message = new ResponseMessage<>();
		
		message.setResult(regionService.selectreginName(code));
		
		return message;
		
	}
	
	
}

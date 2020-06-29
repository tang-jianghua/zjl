package com.mfangsoft.zhuangjialong.integration.newproduct.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.newproduct.service.BrandProductService;

@Controller
@RequestMapping("/admin")
public class AdminProductController {
	
	
	@Autowired
	private BrandProductService brandProductService;
	
	@RequestMapping(value="/allproducts",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> getAllProduct(@RequestBody Map<String,Object> map){
		
		
		return brandProductService.selectAll(map);
	}
	
	@RequestMapping(value="/deleteproductbyid/{id}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<String> deleteProduct(@PathVariable Long id){
		
		ResponseMessage<String> message = new ResponseMessage<>();
		
		brandProductService.deleteProduct(id);
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}
	
	
	
	@RequestMapping(value="/allentproducts",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> getAllEntProduct(@RequestBody Map<String,Object> map){
		
		
		return brandProductService.selectEntAll(map);
	}
	
	@RequestMapping(value="/deleteentproductbyid/{id}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<String> deleteEntProduct(@PathVariable Long id){
		
		ResponseMessage<String> message = new ResponseMessage<>();
		
		brandProductService.deleteEntProduct(id);
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}

}

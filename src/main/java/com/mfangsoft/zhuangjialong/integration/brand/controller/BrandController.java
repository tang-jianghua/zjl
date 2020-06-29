package com.mfangsoft.zhuangjialong.integration.brand.controller;
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

import com.mfangsoft.zhuangjialong.app.brand.model.Brand;
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.brand.model.BaseBrandStateApplyEntity;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.brand.service.BrandService;
import com.mfangsoft.zhuangjialong.integration.brand.service.impl.BrandServiceImpl;
import com.mfangsoft.zhuangjialong.integration.enums.UserState;
@Controller
@RequestMapping("/server")
public class BrandController {
	
	@Autowired
	private BrandService brandService;
	
	@RequestMapping(value="/createbrand", method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> addBrand(@RequestBody BrandEntity brandEntity){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		try {
			brandService.addBrand(brandEntity);
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
	@RequestMapping(value="/getbrandbyid/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<BrandEntity> getBrand(@PathVariable Long id)
	{
		ResponseMessage<BrandEntity> message =  new ResponseMessage<>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(brandService.getBrandById(id));
		return  message;
		
	}
	
	/**
	 * 获取品牌线上线下属性
	 * @return -1 失败 0线下实体  1线上
	 */
	@RequestMapping(value="/getbrandIdintifyType",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Integer> getbrandIdintifyType()
	{
		ResponseMessage<Integer> message =  new ResponseMessage<>();
		Integer type = brandService.getbrandIdintifyType();
		if(type != null && type >= 0){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(type);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			message.setResult(-1);
		}
		return  message;
		
	}
	
	@RequestMapping(value="/modifybrand",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyBrand(@RequestBody BrandEntity brandEntity)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		
		try {
			brandService.modifyBrand(brandEntity);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return  message;
		
	}
	
	@RequestMapping(value="/modifybackbrand",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyBackBrand(@RequestBody BrandEntity brandEntity)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		
		try {
			brandService.modifyBackBrand(brandEntity);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return  message;
		
	}
	@RequestMapping(value="/querybrand",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,Object>> queryBrand(@RequestBody Page<Map<String,Object>> page)
	{
		
		return  brandService.queryBrandForPage(page);
		
	}
	@RequestMapping(value="/checkbrand/{id}",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> checkBrand(@PathVariable Long id)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		
		return  message;
		
	}
	@RequestMapping(value="/closebrand/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> closeBrand(@PathVariable Long id)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		
		if(brandService.modifyopenOrCloseBrand(id, UserState.CLOSE.getIndex())){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}
		
		
		return  message;
		
	}
	@RequestMapping(value="/openbrand/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> openBrand(@PathVariable Long id)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		if(brandService.modifyopenOrCloseBrand(id, UserState.OPEN.getIndex())){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}
		
		
		
		return  message;
		
	}
	
	
	
	@RequestMapping(value="/getAllBrandName",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<BrandEntity>> getAllBrandName()
	{
		ResponseMessage<List<BrandEntity>> message = new ResponseMessage<List<BrandEntity>>();
		
		
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			
			message.setResult(brandService.selectBrandName());
			
		return  message;
		
	}
	
	@RequestMapping(value="/initbrand",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public   Object initBrand()
	{
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("partner",  UserContext.getCurrentUserInfo());
		
		map.put("enterprise", UserContext.getParnetUserInfo());
		
		return map;
	}
	
	@RequestMapping(value="/putBrandOnShelf",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> putBrandOnShelf(@RequestBody BrandEntity brandEntity){
       return brandService.modifyBrandOnShelf(brandEntity);		
	}
	
	@RequestMapping(value="/applyBrandOffShelf",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> applyBrandOffShelf(@RequestBody BaseBrandStateApplyEntity brandEntity){
		ResponseMessage<String> message = new ResponseMessage<>();
		Integer result = brandService.modifyBrandOffingShelf(brandEntity);
	if(result==BrandServiceImpl.APPLY_RESULT_SUCCESS){
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
	}else if(result==BrandServiceImpl.APPLY_RESULT_INTRO){
		message.setCode(SysConstant.FAILURE_CODE);
		message.setMessage(BrandServiceImpl.APPLY_INTRO_MSG);
	}else{
		message.setCode(SysConstant.FAILURE_CODE);
		message.setMessage(SysConstant.FAILURE_MSG);
	}
	return  message;
	}

	
	@RequestMapping(value="/putBrandOffShelf",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> putBrandOffShelf(@RequestBody BaseBrandStateApplyEntity brandEntity){
		ResponseMessage<String> message = new ResponseMessage<>();
	if(brandService.modifyBrandOffShelf(brandEntity)){
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
	}else{
		message.setCode(SysConstant.FAILURE_CODE);
		message.setMessage(SysConstant.FAILURE_MSG);
	}
	return  message;
	}
	
	@RequestMapping(value="/getApplyOffStateBrands",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String, Object>> getApplyOffStateBrands(@RequestBody Page<Map<String, Object>> page)
	{
		return brandService.getApplyOffStateBrands(page);
	}
}

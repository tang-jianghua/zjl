package com.mfangsoft.zhuangjialong.integration.coupons.controller;

import java.util.ArrayList;
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

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.coupons.model.BrandCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.coupons.service.CouponsService;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.BaseBrandCouponsEntity;
@Controller(value="coupons")
@RequestMapping("/server")
public class CouponsController {
	
	@Autowired
	private CouponsService couponsService;

	@RequestMapping(value="/createcoupons", method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> addcoupons(@RequestBody BrandCouponsEntity  entity ){
		ResponseMessage<String> message = new ResponseMessage<String>();
		try {
			couponsService.addCoupons(entity);
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
	@RequestMapping(value="/getcouponsbyid/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<BaseBrandCouponsEntity> getCouponsByid(@PathVariable Long id)
	{
		ResponseMessage<BaseBrandCouponsEntity> message = new ResponseMessage<BaseBrandCouponsEntity>();
		
	
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(couponsService.getCouponsById(id));
		return  message;
		
	}
	
	@RequestMapping(value="/modifycoupons",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyCoupons(@RequestBody BrandCouponsEntity brandCouponsEntity)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		
		try {
			couponsService.modifyCoupons(brandCouponsEntity);
		} catch (Exception e) {
			// TODO: handle exception
		}
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		//message.setResult(enterprise);
		return  message;
		
	}
	
	@RequestMapping(value="/querycoupons",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,Object>> queryCoupons(@RequestBody Page<Map<String,Object>> page)
	{
		
		return  couponsService.queryCoupons(page);
		
	}
	
	@RequestMapping(value="/checkname",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Map<String,Object>> checkName(@RequestBody BaseBrandCouponsEntity couponsEntity)
	{
		ResponseMessage<Map<String,Object>> message = new ResponseMessage<Map<String,Object>>();
		Map<String,Object> map=couponsService.queryCouponsByName(couponsEntity);
			if(map!=null){
				
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
				message.setResult(map);
				
			}else{
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(SysConstant.FAILURE_MSG);
				
			}
			
		
		return  message;
		
	}
	
	@RequestMapping(value="/removeByname/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> removeByName(@PathVariable Long id)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		try {
			couponsService.removeCouponsByName(id);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			
		} catch (Exception e) {
			// TODO: handle exception
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
			
		
		return  message;
		
	}
	
	@RequestMapping(value="/queryCustomerCoupons",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,Object>> queryCustomerCoupons(@RequestBody Page<Map<String,Object>> page)
	{
		
		return  couponsService.queryCustomerCoupons(page);
		
	}
	
	
	
	@RequestMapping(value="/queryCustomerCouponsByCode/{code}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Long> queryCustomerCouponsByCode(@PathVariable String code)
	{
		
		ResponseMessage<Long> message = new ResponseMessage<Long>();
		
		try {
			Long id=couponsService.queryCustomerCouponsByCode(code);
			if(id !=null){
				
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
				message.setResult(id);
			}else{
				
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(SysConstant.FAILURE_MSG);
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
			
		
		return  message;
		
	}
	
	
	@RequestMapping(value="/modifyCustomerCouponsById/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> queryCustomerCoupons(@PathVariable  Long id )
	{
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		try {
			
			if(couponsService.modifyCustomerCouponsById(id)){
				
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
		
			}
		} catch (Exception e) {
			// TODO: handle exception
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
			
		
		return  message;
		
	}

/**
 * 修改优惠券红包状态，不影响用户使用
 * @param brandCouponsEntity
 * @return
 */
	@RequestMapping(value="/updateCouponsState",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> updatecouponsstate(@RequestBody BrandCouponsEntity brandCouponsEntity)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		if(couponsService.updatecouponsstate(brandCouponsEntity)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			return  message;
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			return  message;
		}
	}
	
}

package com.mfangsoft.zhuangjialong.integration.shop.controller;

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

import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.enums.UserState;
import com.mfangsoft.zhuangjialong.integration.series.model.BrandSeriesEntity;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;
import com.mfangsoft.zhuangjialong.integration.shop.service.ShopService;
@Controller
@RequestMapping("/server")
public class ShopController {

	@Autowired
	private  ShopService shopService;
	
	@RequestMapping(value="/createshop", method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> addShop(@RequestBody ShopEntity shopEntity){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		
		if(shopService.addShop(shopEntity)){
		
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		}else
		{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	@RequestMapping(value="/getshopbyid/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<ShopEntity> getSeries(@PathVariable Long id)
	{
		ResponseMessage<ShopEntity> message = new ResponseMessage<ShopEntity>();
		
		message.setResult(shopService.getShopById(id));
		
		return  message;
		
	}
	
	@RequestMapping(value="/removeshop/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<BrandSeriesEntity> removeShop(@PathVariable Long id)
	{
		ResponseMessage<BrandSeriesEntity> message = new ResponseMessage<BrandSeriesEntity>();
		
		BrandSeriesEntity series= new BrandSeriesEntity();
		
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(series);
		return  message;
		
	}
	
	@RequestMapping(value="/modifyshop",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyShop(@RequestBody ShopEntity shopEntity)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		
		if(shopService.modifyShop(shopEntity)){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			}else
			{
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(SysConstant.FAILURE_MSG);
			}
		
	
		//message.setResult(enterprise);
		return  message;
		
	}
	
	@RequestMapping(value="/checkshop",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> checkShop(@RequestBody Map<String,Object> map)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		if(map.get("id")==null){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			
			return message;
		}
		if(map.get("state")==null){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			return message;
		}
		
		if(shopService.checkShop(new Long(map.get("id").toString()), new Integer(map.get("state").toString()))){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			
			return message;
		}
		
		
		//message.setResult(enterprise);
		return  message;
		
	}
	
	
	@RequestMapping(value="/queryshop",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String, Object>> queryShopForPage(@RequestBody Page<Map<String, Object>> page)
	{
		
		return  shopService.getShopForPage(page);
		
	}
	
	@RequestMapping(value="/openshop/{id}",method=RequestMethod.GET)
	@ResponseBody
	public  ResponseMessage<String> openShop(@PathVariable Long id){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		if(shopService.openOrCloseShop(id, UserState.OPEN.getIndex())){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}
		return message;
	}
	
	@RequestMapping(value="/closeshop/{id}",method=RequestMethod.GET)
	@ResponseBody
	public  ResponseMessage<String> CloseShop(@PathVariable Long id){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		if(shopService.openOrCloseShop(id, UserState.CLOSE.getIndex())){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}
		return message;
	}
	@RequestMapping(value="/initshop",method=RequestMethod.GET)
	@ResponseBody
	public Object initShop(){
		
		
		return UserContext.getCurrentUserInfo();
	}
	@RequestMapping(value="/modifybackshop",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyBackShop(@RequestBody ShopEntity shopEntity)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		
		if(shopService.modifyBackShop(shopEntity)){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			}else
			{
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(SysConstant.FAILURE_MSG);
			}
		
	
		//message.setResult(enterprise);
		return  message;
		
	}
	
	
	@RequestMapping(value = "/modifyShopState", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyShopState(@RequestBody ShopEntity shopEntity) {
		ResponseMessage<String> message = new ResponseMessage<String>();

		if (shopService.modifyShopState(shopEntity)) {
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} else {
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;

	}
}

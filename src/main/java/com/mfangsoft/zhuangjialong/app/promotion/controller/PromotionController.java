package com.mfangsoft.zhuangjialong.app.promotion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.product.model.Product;
import com.mfangsoft.zhuangjialong.app.promotion.model.PromotionNoteEntity;
import com.mfangsoft.zhuangjialong.app.promotion.model.PromotionTimer;
import com.mfangsoft.zhuangjialong.app.promotion.service.PromotionAppService;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionEntityStepParam;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionSeckillProductEntity;

/**
 * @description：
 * 
 * @author：Liyanchen
 * 
 */
@Controller(value = "apppromotion")
@RequestMapping("/app")
public class PromotionController {

	@Autowired
	PromotionAppService promotionAppService;

	//////////////////秒杀///////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/querymainbrandpromotion", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<PromotionTimer> querymainbrandpromotion(@RequestBody PromotionTimer param) {
		ResponseMessage<PromotionTimer> responseMessage = new ResponseMessage<>();

		PromotionTimer promotionTimer = promotionAppService.queryMainBrandPromotion(param);
		if (promotionTimer != null) {
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(promotionTimer);
		} else {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
		}
		return responseMessage;
	}

	@RequestMapping(value = "/getpromotiontime", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<PromotionTimer>> getpromotiontime(@RequestBody PromotionTimer param) {

		ResponseMessage<List<PromotionTimer>> responseMessage = new ResponseMessage<List<PromotionTimer>>();

		List<PromotionTimer> p = promotionAppService.getpromotiontime(param);
		if(p != null){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(p);
		} else {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
		}
		return responseMessage;
	}

	@RequestMapping(value = "/getpromotiontime2", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<PromotionTimer> queryMainBrandPromotion2(@RequestBody PromotionTimer param) {

		ResponseMessage<PromotionTimer> responseMessage = new ResponseMessage<PromotionTimer>();

		PromotionTimer p = promotionAppService.getpromotiontime2(param);
		if(p != null){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(p);
		} else {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/queryplatformpromotionproductforpage", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<Product>> queryPlatformPromotionProduct(@RequestBody Page<Product> page) {
		ResponseMessage<Page<Product>> responseMessage = new ResponseMessage<>();
		
		List<Product> p = promotionAppService.queryPlatformPromotionProduct(page);
		
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		
		page.setData(p);
		
		responseMessage.setResult(page);
		return responseMessage;
	}
	
	@RequestMapping(value = "/addpromotioncustomernote", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Long> addPromotionCustomerNote(@RequestBody PromotionNoteEntity param) {

		ResponseMessage<Long> responseMessage = new ResponseMessage<Long>();
		Long note_id = null;
		if((note_id = promotionAppService.addPromotionCustomerNote(param)) != null){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(note_id);
		} else {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/getpromotioncustomernoteproducts", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<PromotionTimer>> getpromotioncustomernoteproducts(@RequestBody PromotionNoteEntity param) {

		ResponseMessage<List<PromotionTimer>> responseMessage = new ResponseMessage<List<PromotionTimer>>();
		List<PromotionTimer> list = promotionAppService.getpromotioncustomernoteproducts(param.getCustomer_id());
		if(list!= null){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(list);
		} else {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/removepromotioncustomernote", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<PromotionTimer>> removepromotioncustomernote(@RequestBody PromotionNoteEntity param) {

		ResponseMessage<List<PromotionTimer>> responseMessage = new ResponseMessage<List<PromotionTimer>>();

		if(promotionAppService.removepromotioncustomernote(param) > 0){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		} else {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/checkpromotionconditionforcustomer", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> checkpromotionconditionforcustomer(@RequestBody PromotionSeckillProductEntity param) {

		return promotionAppService.checkpromotionconditionforcustomer(param);
	}
	
	//////////////////满///////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value = "/querypromotionforfirstpage", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<PromotionEntityStepParam>> querypromotionforfirstpage(@RequestBody PromotionEntity param) {
		ResponseMessage<List<PromotionEntityStepParam>> responseMessage = new ResponseMessage<>();

		List<PromotionEntityStepParam> list = promotionAppService.querypromotionforfirstpage(param.getRegion_code());
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(list);
		return responseMessage;
	}
	
	@RequestMapping(value = "/getpromotionfordetail", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<PromotionEntityStepParam> getpromotionfordetail(@RequestBody PromotionEntityStepParam param) {
		ResponseMessage<PromotionEntityStepParam> responseMessage = new ResponseMessage<>();

		PromotionEntityStepParam result = promotionAppService.getpromotionfordetail(param);
		if(result != null){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(result);
		}else{
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/getapppromotionproductforpage", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<Product>> queryapppromotionproductforpage(@RequestBody Page<Product> page) {
		ResponseMessage<Page<Product>> responseMessage = new ResponseMessage<>();
		
		List<Product> p = promotionAppService.getapppromotionproductforpage(page);
		
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		
		page.setData(p);
		
		responseMessage.setResult(page);
		return responseMessage;
	}
	
}

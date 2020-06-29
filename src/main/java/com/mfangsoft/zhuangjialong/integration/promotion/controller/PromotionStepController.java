package com.mfangsoft.zhuangjialong.integration.promotion.controller;

import java.text.ParseException;
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

import com.mfangsoft.zhuangjialong.app.product.model.Product;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionEntityStepParam;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionSeckillProductEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionStepConditionEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionStepProductEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.service.PromotionStepService;

@Controller(value = "PromotionStepController")
@RequestMapping("/server")
public class PromotionStepController {

	@Autowired
	PromotionStepService promotionStepService;

	@RequestMapping(value = "/addPlatformStepPromotion", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> addPlatformStepPromotion(@RequestBody PromotionEntityStepParam record) {

		ResponseMessage<String> responseMessage = new ResponseMessage<String>();
		try {
			responseMessage = promotionStepService.addPlatformStepPromotion(record);
		} catch (Exception e) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
		if (responseMessage != null) {
			return responseMessage;
		} else {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}

	}

	@RequestMapping(value = "/queryPlatformStepPromotionListByPage", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String, Object>> queryPlatformStepPromotionListByPage(@RequestBody Page<Map<String, Object>> page) {

		List<Map<String, Object>> list = promotionStepService.queryPlatformStepPromotionListByPage(page);
		if (list != null) {
			page.setData(list);
			return page;
		}
		return page;

	}

	@RequestMapping(value = "/getPlatformStepPromotionByid/{id}", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Map<String, Object>> getPlatformStepPromotionByid(@PathVariable Long id) {

		ResponseMessage<Map<String, Object>> responseMessage = new ResponseMessage<Map<String, Object>>();

		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(promotionStepService.getPlatformStepPromotionByid(id));
		return responseMessage;

	}

	@RequestMapping(value = "/modifyPlatformStepPromotion", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyPlatformPromotion(@RequestBody PromotionEntityStepParam param) {

		ResponseMessage<String> message = new ResponseMessage<String>();
		try {
			promotionStepService.modifyPlatformPromotion(param);
		} catch (Exception e) {
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			return message;
		}
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;

	}

	@RequestMapping(value = "/deletePlatformStepPromotion", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage deletePlatformPromotion(@RequestBody PromotionStepConditionEntity param) {

		ResponseMessage message = new ResponseMessage();
			if(promotionStepService.deletePlatformPromotion(param)){
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
				
			return message;
		}
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		return message;

	}
	
	@RequestMapping(value = "/registerPlatformStepPromotion", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> registerPlatformPromotion(@RequestBody PromotionEntity param) {

		ResponseMessage<String> message = new ResponseMessage<String>();
		try {
			promotionStepService.registerPlatformPromotion(param);
		} catch (Exception e) {
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			return message;
		}
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;

	}
	
	@RequestMapping(value = "/cancleRegisterPlatformStepPromotion/{id}", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> cancleRegisterPlatformPromotion(@PathVariable Long id) {

			return promotionStepService.cancleRegisterPlatformPromotion(id);

	}
	
	@RequestMapping(value = "/addStepPromotionProducts", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> addStepPromotionProducts(@RequestBody PromotionEntityStepParam param) {

		ResponseMessage<String> message = new ResponseMessage<String>();
		if (promotionStepService.addStepPromotionProducts(param)) {

			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);

		} else {
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}

	@RequestMapping(value = "/getStepPromotionProductForBrand", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<Product>> getStepPromotionProductListByPage(
			@RequestBody PromotionStepProductEntity param) {

		ResponseMessage<List<Product>> message = new ResponseMessage<List<Product>>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(promotionStepService.getStepPromotionProductList(param));
		return message;

	}
	
	@RequestMapping(value = "/getStepPromotionProductForPlatform", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<PromotionEntityStepParam> getStepPromotionProductForPlatform(
			@RequestBody PromotionEntityStepParam param) {

		ResponseMessage<PromotionEntityStepParam> message = new ResponseMessage<PromotionEntityStepParam>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(promotionStepService.getStepPromotionProductForPlatform(param));
		return message;

	}
	
	@RequestMapping(value = "/selectAllProductForBrandToAddByPage", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,Object>> selectAllProductForBrandToAddByPage(@RequestBody Page<Map<String,Object>> page) {

		return promotionStepService.selectAllProductForBrandToAddByPage(page);

	}
	
	@RequestMapping(value = "/removeStepPromotionProductForBrand", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> removeStepPromotionProductForBrand(@RequestBody List<Long> param) {

		ResponseMessage<String> message = new ResponseMessage<String>();
		
		promotionStepService.removeStepPromotionProductForBrand(param);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}
	
	@RequestMapping(value = "/modifyStepPromotionProductForBrand", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyStepPromotionProductForBrand(@RequestBody List<PromotionStepProductEntity> param) {

		ResponseMessage<String> message = new ResponseMessage<String>();
		
		promotionStepService.modifyStepPromotionProductForBrand(param);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}
	
}

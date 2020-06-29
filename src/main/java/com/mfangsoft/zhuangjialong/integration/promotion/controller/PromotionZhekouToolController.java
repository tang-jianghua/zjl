package com.mfangsoft.zhuangjialong.integration.promotion.controller;

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
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouProductEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.service.PromotionZhekouService;

@Controller(value="backZhekouToolController")
@RequestMapping("/server")
public class PromotionZhekouToolController {
	
	@Autowired
	PromotionZhekouService promotionZhekouService;
	
	
	
	@RequestMapping(value = "/addZhekouPromotion", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage addZhekouPromotion(@RequestBody PromotionZhekouEntity record) {

		ResponseMessage responseMessage = new ResponseMessage<>();
		
		return promotionZhekouService.addZhekouPromotion(record, responseMessage);

	}

	@RequestMapping(value = "/queryZhekouPromotionForPage", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,String>> queryZhekouPromotionForPage(@RequestBody Page<Map<String,String>> page) {
		
		List<Map<String,String>> list = promotionZhekouService.queryZhekouPromotionForPage(page);
		
		page.setData(list);
			
		return page;
		
	}
	
	@RequestMapping(value = "/queryOneZhekouPromotion/{id}", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public PromotionZhekouEntity queryOneZhekouPromotionForPage(@PathVariable Long id) {
		
		return promotionZhekouService.queryOneZhekouPromotion(id);
		
	}

	@RequestMapping(value = "/modifyZhekouPromotion", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage modifyZhekouPromotion(@RequestBody PromotionZhekouEntity record) {

		ResponseMessage responseMessage = new ResponseMessage<>();
		
		return promotionZhekouService.modifyZhekouPromotion(record, responseMessage);

	}
	
	@RequestMapping(value = "/addZhekouPromotionPorduct", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage addZhekouPromotionPorduct(@RequestBody List<PromotionZhekouProductEntity> record) {

		ResponseMessage responseMessage = new ResponseMessage<>();
		
		return promotionZhekouService.addZhekouPromotionPorduct(record, responseMessage);

	}
	
	@RequestMapping(value = "/queryZhekouPromotionPorduct", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,Object>> queryZhekouPromotionPorduct(@RequestBody Page<Map<String,Object>> page) {

		return promotionZhekouService.queryZhekouPromotionPorduct(page);

	}
	
	@RequestMapping(value = "/queryAllZhekouPorductBrandToChooseByPage", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,Object>> queryAllZhekouPorductBrandToChooseByPage(@RequestBody Page<Map<String,Object>> page) {

		List<Map<String,Object>> list = promotionZhekouService.queryAllZhekouPorductBrandToChooseByPage(page);
		page.setData(list);
		return page;
		
	}
	
	@RequestMapping(value = "/deleteZhekouPromotionPorduct", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage deleteZhekouPromotionPorduct(@RequestBody List<Long> id_list) {

		ResponseMessage responseMessage = new ResponseMessage<>();
		
		promotionZhekouService.deleteZhekouPromotionPorduct(id_list);
		
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		return responseMessage;
		
	}
}

package com.mfangsoft.zhuangjialong.app.seller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.seller.service.ShopGuiderService;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.enums.ShopGuiderCertificationState;
import com.mfangsoft.zhuangjialong.integration.shop.model.BaseShopGuiderEntity;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopGuiderModel;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年12月27日
* 
*/
@RequestMapping(value="/app")
@Controller("/app")
public class ShopGuiderController {
	
	@Autowired
	ShopGuiderService shopGuiderServiceImpl;


	/*
	 * 店铺导购认证信息回显
	 */
	@CrossOrigin
	@RequestMapping(value="/getshopGuiderCertificateInfo")
	@ResponseBody
	public ResponseMessage<ShopGuiderModel> getshopGuiderCertificateInfo(@RequestBody ShopGuiderModel shopGuiderModel){
		ResponseMessage<ShopGuiderModel> message = new ResponseMessage<>();
		ShopGuiderModel shopGuiderCertificationInfo = shopGuiderServiceImpl.getShopGuiderCertificationInfo(shopGuiderModel);
		if(shopGuiderCertificationInfo!=null){
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(shopGuiderCertificationInfo);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	
	/*
	 * 店铺导购认证
	 */
	@CrossOrigin
	@RequestMapping(value="/shopGuiderCertificate")
	@ResponseBody
	public ResponseMessage<String> shopGuiderCertificate(@RequestBody ShopGuiderModel shopGuiderModel){
		ResponseMessage<String> message = new ResponseMessage<>();
		if(shopGuiderServiceImpl.updateShopGuider(shopGuiderModel)){
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	
	/*
	 * 查询店铺导购认证状态
	 */
	@CrossOrigin
	@RequestMapping(value="/getShopGuiderCertificate")
	@ResponseBody
	public ResponseMessage<String> getShopGuiderCertificate(@RequestBody ShopGuiderModel shopGuiderModel){
		ResponseMessage<String> message = new ResponseMessage<>();
		/*		int state = shopGuiderServiceImpl.getShopGuiderCertificationState(shopGuiderModel);
		if(state ==ShopGuiderCertificationState.NoCommit.getIndex()){
			message.setCode("-1");
			message.setMessage("未提交");
		}else if(state ==ShopGuiderCertificationState.NoPass.getIndex()){
			message.setCode("0");
			message.setMessage("未通过");
		}else if(state ==ShopGuiderCertificationState.Pass.getIndex()){
			message.setCode("1");
			message.setMessage("已通过");
		}else if(state ==ShopGuiderCertificationState.Checking.getIndex()){
			message.setCode("2");
			message.setMessage("审核中");
		}*/
		message.setCode(shopGuiderServiceImpl.getShopGuiderCertificationState(shopGuiderModel)+"");
		message.setMessage(SysConstant.SUCCESS_CODE);
		return message;
	}
	
}

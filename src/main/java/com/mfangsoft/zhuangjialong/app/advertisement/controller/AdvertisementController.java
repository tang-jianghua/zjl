package com.mfangsoft.zhuangjialong.app.advertisement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.advertisement.service.AppAdvertisementService;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2017年1月6日
* 
*/
@RequestMapping(value="/app")
@Controller
public class AdvertisementController {

	@Autowired
	AppAdvertisementService advertisementServiceImpl;
	
	@RequestMapping(value="/getAdvertisements",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public ResponseMessage<List<String>> getAdvertisements(){
		ResponseMessage<List<String>> message = new ResponseMessage<>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(advertisementServiceImpl.getAds());
		return message;
	}
	
	@RequestMapping(value="/getAdvertisementCode",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public ResponseMessage<Integer> getAdvertisementCode(){
		ResponseMessage<Integer> message = new ResponseMessage<>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		String value = RedisUtils.getValue(SysConstant.ADVERTISEMENT_CODE);
		if(value == null){
			message.setResult(0);
		}else{
			message.setResult(Integer.valueOf(value));
		}
		return message;
	}
}

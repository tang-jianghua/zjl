package com.mfangsoft.zhuangjialong.app.prepay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.prepay.model.ShopPrepayEntity;
import com.mfangsoft.zhuangjialong.app.prepay.service.ShopPrepayService;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;

@Controller
@RequestMapping("/shopperpay")
public class ShopPerpayController {
	@Autowired
	ShopPrepayService shopPrepayService;
	
	/**
	 * 保存用户选择的定金券记录
	 * @param shopPerpay
	 * @return
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Object> addShopPerpayRecord(@RequestBody ShopPrepayEntity shopPerpay){
		ResponseMessage<Object> message = new ResponseMessage<Object>();
		if (shopPrepayService.addShopPrepay(shopPerpay)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}
		else
		{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
}

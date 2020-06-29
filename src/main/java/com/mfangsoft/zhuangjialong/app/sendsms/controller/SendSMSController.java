package com.mfangsoft.zhuangjialong.app.sendsms.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Producer;
import com.mfangsoft.zhuangjialong.app.applogin.model.Customer;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.app.applogin.model.UserEquipmentEntity;
import com.mfangsoft.zhuangjialong.app.applogin.service.CustomerService;
import com.mfangsoft.zhuangjialong.app.seller.mapper.SellerEntityMapper;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerEntity;
import com.mfangsoft.zhuangjialong.app.seller.service.SellerService;
import com.mfangsoft.zhuangjialong.app.sendsms.service.SendSMSService;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;

import cn.jpush.api.utils.StringUtils;

/**
 * @description：
 * 
 * @author：Jianghua.Tang @date：2016年5月28日
 * 
 */
@Controller
@RequestMapping("/app")
public class SendSMSController {

	@Autowired
	private SendSMSService sendSMSServiceImpl;
	

	@RequestMapping(value = "/sendSMS", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Customer> sendSMS(@RequestBody Map<String, String> param) {
		ResponseMessage<Customer> message = new ResponseMessage<>();
		boolean sendSMS = sendSMSServiceImpl.sendSMS(param.get("phone"), param.get("message"),SysConstant.ZJL_MESSAGE_SUF);
		if(sendSMS){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
}
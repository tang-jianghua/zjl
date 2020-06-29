package com.mfangsoft.zhuangjialong.app.applogin.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
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
import com.mfangsoft.zhuangjialong.app.applogin.model.BaseSellerLogEntity;
import com.mfangsoft.zhuangjialong.app.applogin.model.Customer;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.app.applogin.model.UserEquipmentEntity;
import com.mfangsoft.zhuangjialong.app.applogin.service.CustomerService;
import com.mfangsoft.zhuangjialong.app.seller.mapper.SellerEntityMapper;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerEntity;
import com.mfangsoft.zhuangjialong.app.seller.service.SellerService;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.common.utils.TokenUtil;


/**
 * @description：
 * 
 * @author：Jianghua.Tang @date：2016年5月28日
 * 
 */
@Controller
@RequestMapping("/app")
public class AppLoginController {

	@Autowired
	private CustomerService customerServiceImpl;
	@Autowired
	private SellerService sellerService;
	  @Autowired 
	  private Producer captchaProducer;
	  @Autowired
	  private SellerEntityMapper sellerEntityMapper;
	
	@RequestMapping(value = "/imgverificationcode", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	  public ResponseMessage<Map<String, String>> imgVerificationCode(){ {
	  
	  String capText = captchaProducer.createText();
	 
	  // store the text in the session
	  String vkey = TokenUtil.getIns().getAndIncreaseKey();
	  RedisUtils.setWithTimeLimit(vkey, capText.toLowerCase(),10*60L);
	  System.out.println("******************验证码是: " + capText +
	  "******************");
	  
	  // create the image with the text 
	  BufferedImage bi =captchaProducer.createImage(capText);
	  
	  ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
	  
	  
	  // write the data out try 
	  try {
		ImageIO.write(bi, "jpg", arrayOutputStream);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  
	  byte [] b=arrayOutputStream.toByteArray();
	  
	  String imageStr=Base64Utils.encodeToString(b); 
	  Map<String, String>  map = new HashMap<>();
	  map.put("imageStr", imageStr);
	  map.put("vkey", vkey);
	  
	  ResponseMessage<Map<String, String>> message=new ResponseMessage<>();
	  message.setCode(SysConstant.SUCCESS_CODE);
	  message.setMessage(SysConstant.SUCCESS_MSG);
	  message.setResult(map);
	  return message; 
	  } 
	  }
	 

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Customer> login(@RequestBody Customer customer) {
		ResponseMessage<Customer> message = new ResponseMessage<>();
		message = customerServiceImpl.login(customer);
		if (message.getCode().equals("0")) {
			RedisUtils.setValue(message.getResult().getKey(), message.getResult().getToken());
		}
		return message;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Customer> logout(@RequestBody Customer customer, HttpSession session) {
		ResponseMessage<Customer> message = new ResponseMessage<>();
		RedisUtils.delValue(customer.getKey());
		message.setCode("0");
		message.setMessage("退出成功");
		return message;
	}

	@RequestMapping(value = "/mesvcode", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> mesvcode(@RequestBody CustomerEntity param) {
		boolean sendMS = customerServiceImpl.sendMS(param);
		ResponseMessage<String> responseMessage = new ResponseMessage<>();
		if (sendMS) {
			responseMessage.setCode("0");
			responseMessage.setMessage("发送成功");
		} else {
			responseMessage.setCode("1");
			responseMessage.setMessage("发送失败");
		}
		return responseMessage;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> register(@RequestBody Customer customer, HttpSession session) {

		return customerServiceImpl.addAccount(customer);
	}
	/**
	 * 判断卖家手机号是否注册
	 * @param seller
	 * @return
	 */
	@RequestMapping(value = "/seller/isaccountexists", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> sellerIsAccountExists(@RequestBody SellerEntity seller) {
		ResponseMessage<String> message=new ResponseMessage<>();
		if (sellerEntityMapper.selectByAccount(seller.getAccount()) != null) {
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage("此手机号已被注册");
		}else{
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			
		}
		return message;
	}
	/**
	 * 卖家注册
	 * @param customer
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/seller/register", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> sellerRegister(@RequestBody SellerEntity seller) {
		return sellerService.register(seller);
	}
	
	@RequestMapping(value = "/forgetpwd", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> forgetPwd(@RequestBody Customer customer) {

		return customerServiceImpl.modifyPassword(customer);
	}

	@RequestMapping(value = "/isaccountexists", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> isAccountExists(@RequestBody CustomerEntity customerEntity) {
		ResponseMessage<String> responseMessage = new ResponseMessage<>();
		if (customerServiceImpl.selectByAccount(customerEntity.getAccount()) != null) {
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage("账号存在");
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage("账号不存在");
		return responseMessage;
	}

	@RequestMapping(value = "/saveuserequipmentinfo", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> saveuserequipmentinfo(@RequestBody UserEquipmentEntity record) {
		ResponseMessage<String> responseMessage = new ResponseMessage<>();
		try {
			record.setTime(new Date());
			customerServiceImpl.insertOrUpdateSelective(record);
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			return responseMessage;
		} catch (Exception e) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
	}
//
//	@RequestMapping(value = "/updateuserequipmentinfo", method = RequestMethod.POST)
//	@ResponseBody
//	@CrossOrigin
//	public ResponseMessage<String> updateuserequipmentinfo(@RequestBody UserEquipmentEntity record) {
//		ResponseMessage<String> responseMessage = new ResponseMessage<>();
//
//		record.setTime(new Date());
//		if (customerServiceImpl.updateByPrimaryKeySelective(record) > 0) {
//			responseMessage.setCode(SysConstant.SUCCESS_CODE);
//			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
//			return responseMessage;
//		}
//		responseMessage.setCode(SysConstant.FAILURE_CODE);
//		responseMessage.setMessage(SysConstant.FAILURE_MSG);
//		return responseMessage;
//	}

	@RequestMapping(value = "/savesellerlog", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> saveSellerLog(@RequestBody BaseSellerLogEntity record) {
		ResponseMessage<String> responseMessage = new ResponseMessage<>();
		     if(customerServiceImpl.insertSellerLog(record)){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		     }else{
			 responseMessage.setCode(SysConstant.FAILURE_CODE);
			 responseMessage.setMessage(SysConstant.FAILURE_MSG);
		     }
			return responseMessage;
	}
}

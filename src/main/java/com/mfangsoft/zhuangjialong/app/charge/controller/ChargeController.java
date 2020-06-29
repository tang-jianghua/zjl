package com.mfangsoft.zhuangjialong.app.charge.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfangsoft.zhuangjialong.app.applogin.model.Customer;
import com.mfangsoft.zhuangjialong.app.charge.model.ChargeBackEntity;
import com.mfangsoft.zhuangjialong.app.charge.model.ChargeBatchEntity;
import com.mfangsoft.zhuangjialong.app.charge.service.ChargeService;
import com.mfangsoft.zhuangjialong.app.sendflow.model.CallBackModel;
import com.mfangsoft.zhuangjialong.app.sendflow.model.FlowModel;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年10月28日
* 
*/
@Controller
@RequestMapping("/app")
public class ChargeController {

	@Autowired
	ChargeService chargeServiceImpl;
	
	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Customer> reCharge(@RequestBody ChargeBatchEntity chargeBatchEntity) {
		ResponseMessage<Customer> message = new ResponseMessage<>();
		boolean addChargeBatch = chargeServiceImpl.addChargeBatch(chargeBatchEntity.getCharge_package(), chargeBatchEntity.getOrder_id(), chargeBatchEntity.getMobile());
		if(addChargeBatch){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	
	
	@RequestMapping(value = "/callbackcharge", method = RequestMethod.POST)
	@CrossOrigin
	public void callBackCharge(HttpServletRequest request ,HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		ChargeBackEntity chargeBackEntity=new ChargeBackEntity();
		chargeBackEntity.setMessage(request.getParameter("message"));
		chargeBackEntity.setTask_id(request.getParameter("taskid"));
		chargeBackEntity.setSign( request.getParameter("sign"));
		chargeBackEntity.setState(Integer.parseInt(request.getParameter("state")));
		chargeBackEntity.setMobile(request.getParameter("mobile"));
		
		response.getOutputStream().write(chargeServiceImpl.addChargeBack(chargeBackEntity).getBytes());
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
}

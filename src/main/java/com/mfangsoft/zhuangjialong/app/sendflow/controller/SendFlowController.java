package com.mfangsoft.zhuangjialong.app.sendflow.controller;


import java.io.IOException;
import java.util.List;

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
import com.mfangsoft.zhuangjialong.app.applogin.mapper.UserEquipmentEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.model.Customer;
import com.mfangsoft.zhuangjialong.app.applogin.model.UserEquipmentEntity;
import com.mfangsoft.zhuangjialong.app.pointmall.model.ConvertProductModel;
import com.mfangsoft.zhuangjialong.app.pointmall.service.PointMallService;
import com.mfangsoft.zhuangjialong.app.sendflow.model.CallBackModel;
import com.mfangsoft.zhuangjialong.app.sendflow.model.FlowModel;
import com.mfangsoft.zhuangjialong.app.sendflow.service.SendFlowService;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.JPushUtil;
import com.mfangsoft.zhuangjialong.common.utils.MobileUtil;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.core.quartz.Quest;
import com.mfangsoft.zhuangjialong.core.quartz.QuestsManagerBean;
import com.mfangsoft.zhuangjialong.integration.coupons.mapper.BrandCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.integration.pointmall.mapper.PointConvertEntityMapper;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年9月23日
* 
*/
@Controller
@RequestMapping("/app")
public class SendFlowController {

	@Autowired
	private SendFlowService sendFlowServiceImpl;
	@Autowired
	BrandCouponsEntityMapper brandCouponsEntityMapper;
	@Autowired
	PointConvertEntityMapper  pointConvertEntityMapper;
	@Autowired
	UserEquipmentEntityMapper userEquipmentEntityMapper;
	@Autowired
	PointMallService pointMallServiceImpl;

	@RequestMapping(value = "/sendflow", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Customer> sendFlow(@RequestBody FlowModel flowModel) {
		ResponseMessage<Customer> message = new ResponseMessage<>();
		Integer sendFlow = sendFlowServiceImpl.InsertSendFlow(flowModel.getMobiles(), flowModel.getTaskNo(), flowModel.getCtcc(), flowModel.getCucc(), flowModel.getCmcc());
		if(sendFlow == sendFlowServiceImpl.SUCCESS){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else if(sendFlow == sendFlowServiceImpl.FAILURE){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}else if(sendFlow == sendFlowServiceImpl.NOT_ENOUGH){
			message.setCode("2");
			message.setMessage("余额不足");
		}
		return message;
	}
	
	
	@RequestMapping(value = "/callbackflow", method = RequestMethod.POST)
	@CrossOrigin
	public void callBackFlow(HttpServletRequest request ,HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		String data = request.getParameter("data");
		ObjectMapper mapper = new ObjectMapper();
		CallBackModel readValue = mapper.readValue(data, CallBackModel.class);
		response.getOutputStream().write(sendFlowServiceImpl.InsertFlowBack(readValue).getBytes());
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	@RequestMapping(value = "/flowtest", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public int flowTest(){
		List<ConvertProductModel> list = pointConvertEntityMapper.selectUnConvertRecordAtTime();
		int ctcc_num_22 = 0;
		int cmcc_num_22 = 0;
		int cucc_num_23 = 0;
		int cucc_num_24 = 0;
		int ctcc_num_24 = 0;
		int cmcc_num_24 = 0;
		for (ConvertProductModel convertProductModel : list) {
   if(convertProductModel.getProduct_id() == 22){
	   
			switch (MobileUtil.validateMobile(convertProductModel.getMobile())){
			case SysConstant.CTCC:
				ctcc_num_22+=1;
				break;
			case SysConstant.CMCC:
				cmcc_num_22+=1;
				break;
			}
			
		}else if(convertProductModel.getProduct_id() == 23){
				cucc_num_23+=1;
		}else if(convertProductModel.getProduct_id() == 24){
			
			switch (MobileUtil.validateMobile(convertProductModel.getMobile())){
			case SysConstant.CUCC:
				cucc_num_24+=1;
				break;
			case SysConstant.CTCC:
				ctcc_num_24+=1;
				break;
			case SysConstant.CMCC:
				cmcc_num_24+=1;
				break;
			}
			
		}
   
		}
		int price=ctcc_num_22*2+cmcc_num_22*3+cucc_num_23*3+cucc_num_24*6+ctcc_num_24*7+cmcc_num_24*11;
		return price;
	}
	
	
	@RequestMapping(value = "/sendunconvertflow", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public void sendUnconvertFlow(){
		List<ConvertProductModel> list = pointConvertEntityMapper.selectUnConvertRecordAtTime();
		for (ConvertProductModel convertProductModel : list) {
			//查询消费者设备信息
			final UserEquipmentEntity userEquipmentEntity = userEquipmentEntityMapper.selectByCustomerId(convertProductModel.getCustomer_id());
			//推送成功消息
			if(userEquipmentEntity != null){
				QuestsManagerBean.addQuest(new Quest() {
					@Override
					public boolean execute() {
						JPushUtil.sendMessage(userEquipmentEntity.getPlatform(), userEquipmentEntity.getPushstr(), "流量充值提醒", 
								"");
						if(convertProductModel.getCustomer_id()!=null&&convertProductModel.getConvert_code()!=null){
							pointMallServiceImpl.updateConvertFlow(convertProductModel);
						}
						return true;
					}
					
					@Override
					public boolean condition() {
						return true;
					}
					
					@Override
					public boolean delete() {
						return true;
					}
				});
		}
	}
	} 
	
	@RequestMapping(value = "/convertflowbyaccounts", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public void convertFlowByAccounts(@RequestBody List<String> accounts){
		List<ConvertProductModel> list = pointConvertEntityMapper.selectUnConvertRecordAtTime();
		for (ConvertProductModel convertProductModel : list) {
			//查询消费者设备信息
			final UserEquipmentEntity userEquipmentEntity = userEquipmentEntityMapper.selectByCustomerId(convertProductModel.getCustomer_id());
			//推送成功消息
			if(userEquipmentEntity != null){
				QuestsManagerBean.addQuest(new Quest() {
					@Override
					public boolean execute() {
						JPushUtil.sendMessage(userEquipmentEntity.getPlatform(), userEquipmentEntity.getPushstr(), "流量充值提醒", 
								"");
						if(convertProductModel.getCustomer_id()!=null&&convertProductModel.getConvert_code()!=null){
							pointMallServiceImpl.updateConvertFlow(convertProductModel);
						}
						return true;
					}
					
					@Override
					public boolean condition() {
						return true;
					}
					
					@Override
					public boolean delete() {
						return true;
					}
				});
		}
	}
	} 
	
}

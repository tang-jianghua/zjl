package com.mfangsoft.zhuangjialong.app.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.appointment.model.Appointment;
import com.mfangsoft.zhuangjialong.app.appointment.model.AppointmentInfoEntity;
import com.mfangsoft.zhuangjialong.app.appointment.service.AppointmentService;
import com.mfangsoft.zhuangjialong.app.buildClass.model.BuildClass;
import com.mfangsoft.zhuangjialong.app.product.model.SelectPropertiesModel;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;


/**
 * @description：
 * 
 * @author：Jianghua.Tang @date：2016年7月7日
 * 
 */
@Controller(value = "appappointment")
@RequestMapping("/app")
public class AppointmentServiceController {

	@Autowired
	AppointmentService appointmentServiceImpl;
	@Autowired
	com.mfangsoft.zhuangjialong.integration.appointment.service.AppointmentService intAppointmentServiceImpl;

	@RequestMapping(value = "/getstyleandspacebyclass", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<SelectPropertiesModel> getStyleAndSpaceByClass(@RequestBody BuildClass param) {
		ResponseMessage<SelectPropertiesModel> responseMessage = new ResponseMessage<>();
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage("获取成功");
		responseMessage.setResult(appointmentServiceImpl.getStyleAndSpaceByClass(param));
		return responseMessage;
	}
	
	
	@RequestMapping(value = "/appointment", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> appointment(@RequestBody Appointment appointment){
		ResponseMessage<String> responseMessage=new ResponseMessage<>();
		boolean b = appointmentServiceImpl.insertAppointment(appointment);
		if(b){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage("预约成功");
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage("预约失败");
		return responseMessage;
	}
	
	@RequestMapping(value = "/selectAppointmentForShopGuider", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<Appointment>> selectAppointmentForShopGuider(@RequestBody Page<Appointment> page){
		ResponseMessage<Page<Appointment>> responseMessage=new ResponseMessage<>();
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(appointmentServiceImpl.selectAppointmentsForShopGuider(page));
		return responseMessage;
	}
	
	@RequestMapping(value = "/selectAppointmentDetailForShopGuider", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Appointment> selectAppointmentDetailForShopGuider(@RequestBody Appointment appointment){
		ResponseMessage<Appointment> responseMessage=new ResponseMessage<>();
		Appointment result = appointmentServiceImpl.selectAppointmentDetailForShopGuider(appointment);
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
	
	@RequestMapping(value = "/modifyAppointmentState", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyAppointmentState(@RequestBody AppointmentInfoEntity appointmentInfoEntity){
		ResponseMessage<String> responseMessage=new ResponseMessage<>();
		if(intAppointmentServiceImpl.updateAppointmentInfo(appointmentInfoEntity.getId(), appointmentInfoEntity.getState())){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage(SysConstant.FAILURE_MSG);
		return responseMessage;
	}
	
}

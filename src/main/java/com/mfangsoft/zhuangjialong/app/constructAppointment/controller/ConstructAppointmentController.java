package com.mfangsoft.zhuangjialong.app.constructAppointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructAppointment;
import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructAppointmentModel;
import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructStateRelation;
import com.mfangsoft.zhuangjialong.app.constructAppointment.service.ConstructAppointmentService;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月4日
* 
*/
@Controller(value="appConstructAppointment")
@RequestMapping("/app")
public class ConstructAppointmentController {

	@Autowired
	private ConstructAppointmentService  constructAppointmentService;
	
	@RequestMapping(value = "/addconstruct", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> addConstruct(@RequestBody ConstructAppointment param){
		ResponseMessage<String> responseMessage=new ResponseMessage<> ();
		if(constructAppointmentService.addConstructAppointment(param)){
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
		}
		return responseMessage;
	}
	

	@RequestMapping(value = "/queryconstructappointments", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<ConstructAppointmentModel>> queryConstructAppointments(@RequestBody Page<ConstructAppointmentModel> param){
		ResponseMessage<Page<ConstructAppointmentModel>> responseMessage=new ResponseMessage<> ();
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(constructAppointmentService.selectConstructAppointmentByCustomerIdForPage(param));
		return responseMessage;
	}
	
	
	@RequestMapping(value = "/cancelconstruct", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> cancelConstruct(@RequestBody ConstructStateRelation param){
		ResponseMessage<String> responseMessage=new ResponseMessage<> ();
		if(constructAppointmentService.updateConstruct(param)){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			}else{
				responseMessage.setCode(SysConstant.FAILURE_CODE);
				responseMessage.setMessage(SysConstant.FAILURE_MSG);
			}
		return responseMessage;
	}
	
	
	@RequestMapping(value = "/commentconstruct", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> commentConstruct(@RequestBody ConstructAppointment param){
		
		ResponseMessage<String> responseMessage=new ResponseMessage<> ();
		if(constructAppointmentService.addCommentConstruct(param)){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			}else{
				responseMessage.setCode(SysConstant.FAILURE_CODE);
				responseMessage.setMessage(SysConstant.FAILURE_MSG);
			}
		return responseMessage;
	}
	
	@RequestMapping(value = "/queryconstructevaluation", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<ConstructAppointment>> queryConstructEvaluation(@RequestBody Page<ConstructAppointment> param){
		ResponseMessage<Page<ConstructAppointment>> responseMessage=new ResponseMessage<> ();

		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(constructAppointmentService.queryConstructEvaluation(param));
		return responseMessage;
	}
	
}

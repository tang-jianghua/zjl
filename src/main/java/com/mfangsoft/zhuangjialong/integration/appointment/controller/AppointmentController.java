package com.mfangsoft.zhuangjialong.integration.appointment.controller;

import java.util.ArrayList;
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
import com.mfangsoft.zhuangjialong.integration.appointment.model.Appointment;
import com.mfangsoft.zhuangjialong.integration.appointment.service.AppointmentService;
import com.mfangsoft.zhuangjialong.integration.shop.model.EmployeeEntity;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;

@Controller
@RequestMapping("/server")
public class AppointmentController {
	
	@Autowired
	private AppointmentService appointmentService;
	@RequestMapping(value="/querybrandappointment",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String, Object>>  queryBrandAppointment(@RequestBody Page<Map<String, Object>> page)
	{
		
		return appointmentService.getAppointmentListForPage(page);
				
		
	}
	/**
	 * 查看预约明细
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/querybrandappdetail/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Map<String, Object>> queryBrandappDetail(@PathVariable Long id){
		
		ResponseMessage<Map<String, Object>> message = new ResponseMessage<Map<String, Object>>();
		
		
		message.setCode(SysConstant.SUCCESS_CODE);
		
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(appointmentService.queryBrandAppDetail(id));
		
		return message;
		
	}
	
	
	/**
	 * 服务人员
	 * @param id
	 * @return
	 *//*
	@RequestMapping(value="/queryemployee",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public List<EmployeeEntity> queryemployee(){
		
		return appointmentService.getServiceUser();
		
	}*/
	
	
	/**
	 * 服务人员
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/queryshopname",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public List<ShopEntity> queryShopName(){
		
		return appointmentService.getShopName();
		
	}
	/**
	 * 预约指定店铺
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/modfiyappointmentinfoshop",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modfiyAppointmentinfoshop(@RequestBody Map<String,Object> map){
		
		ResponseMessage<String> message = new ResponseMessage<>();
		
		if(map.get("id")==null){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			
			return message;
		}
		if(map.get("shopId")==null){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			
			return message;
		}
		if(appointmentService.updateAppointmentInfoShopId(new Long(map.get("id").toString()), new Long(map.get("shopId").toString()))){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			
			return message;
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			
			return message;
		}
	}
	/**
	 * 预约修改状态
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/modfiyappointmentinfo",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modfiyAppointmentinfo(@RequestBody Map<String,Object> map){
		
		ResponseMessage<String> message = new ResponseMessage<>();
		
		if(map.get("id")==null){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			
			return message;
		}
		if(map.get("state")==null){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			
			return message;
		}
		if(appointmentService.updateAppointmentInfo(new Long(map.get("id").toString()), new Integer(map.get("state").toString()))){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			
			return message;
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			
			return message;
		}
	}
	
	/**
	 * 指定预约服务人员
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/modfiyappointmentinfoservice",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modfiyAppointmentinfoService(@RequestBody Map<String,Object> map){
		
		ResponseMessage<String> message = new ResponseMessage<>();
		
		if(map.get("id")==null){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			
			return message;
		}
		if(map.get("service_id")==null){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			
			return message;
		}
		if(appointmentService.updateAppointmentInfoService(new Long(map.get("id").toString()), new Long(map.get("service_id").toString()))){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			
			return message;
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			
			return message;
		}
	}

}

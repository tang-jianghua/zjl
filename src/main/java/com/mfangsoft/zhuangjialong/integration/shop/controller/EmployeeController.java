package com.mfangsoft.zhuangjialong.integration.shop.controller;

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
import com.mfangsoft.zhuangjialong.integration.series.model.BrandSeriesEntity;
import com.mfangsoft.zhuangjialong.integration.shop.model.EmployeeEntity;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;
import com.mfangsoft.zhuangjialong.integration.shop.service.EmployeeService;
import com.mfangsoft.zhuangjialong.integration.shop.service.ShopService;

@Controller
@RequestMapping("/server")
public class EmployeeController {
	
	
	
	@Autowired
	private  EmployeeService employeeService;
	
	@RequestMapping(value="/createemployee", method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> addEmployee(@RequestBody EmployeeEntity employeeEntity){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		
		if(employeeService.addEmployee(employeeEntity)){
		
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		}else
		{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	@RequestMapping(value="/getemployeebyid/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<EmployeeEntity> getEmployeeByid(@PathVariable Integer id)
	{
		ResponseMessage<EmployeeEntity> message = new ResponseMessage<EmployeeEntity>();
		
		message.setResult(employeeService.getEmployeeById(id));
		
		return  message;
		
	}
	
	@RequestMapping(value="/removeemployee/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> removeEmployee(@PathVariable Integer id)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		try {
			
			employeeService.removeEmployeeById(id);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		//message.setResult(series);
		return  message;
		
	}
	
	@RequestMapping(value="/modifyemployee",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyEmployee(@RequestBody EmployeeEntity employeeEntity)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		
		if(employeeService.modifyEmployee(employeeEntity)){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			}else
			{
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(SysConstant.FAILURE_MSG);
			}
		
	
		//message.setResult(enterprise);
		return  message;
		
	}
	
	
	
	
	@RequestMapping(value="/queryemployeepage",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<EmployeeEntity> queryEmployeeForPage(@RequestBody Page<EmployeeEntity> page)
	{
		
		return  employeeService.getEmployeeForPage(page);
		
	}

}

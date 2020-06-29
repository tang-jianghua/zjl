package com.mfangsoft.zhuangjialong.integration.customer.controller;

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

import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.customer.service.CustomerService;



@Controller
@RequestMapping("/server")

public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value="/querycustomer",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,Object>> queryCustomerForPage(@RequestBody Page<Map<String,Object>> page){
		
		return customerService.queryCustomerForPage(page);
		
	}
	
	@RequestMapping(value="/getcustomerbyid/{customer_id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	
	public Map<String,Object> getCustomerByid(@PathVariable Long customer_id){
		
		return customerService.selectCustomerById(customer_id);
		
	}
}

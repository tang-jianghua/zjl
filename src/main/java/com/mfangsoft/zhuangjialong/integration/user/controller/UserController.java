package com.mfangsoft.zhuangjialong.integration.user.controller;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;
import com.mfangsoft.zhuangjialong.integration.user.service.UserService;

import cn.jpush.api.report.UsersResult.User;

@Controller(value="user")
@RequestMapping("/server")
public class UserController {

	@Autowired
	private UserService userService; 
	/**
	 * 创建用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/createuser",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> createUser(@RequestBody UserEntity userEntity) {
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		
		
		if(StringUtils.isEmpty(userEntity.getAccount())){
			
			 message.setCode(SysConstant.FAILURE_CODE);
		     message.setMessage(SysConstant.FAILURE_MSG);
		     
		     return message;
		}
		if(StringUtils.isEmpty(userEntity.getPwd())){
			 message.setCode(SysConstant.FAILURE_CODE);
		     message.setMessage(SysConstant.FAILURE_MSG);
		     return message;
		}
		
		try {
			if(userService.addUser(userEntity)){
				
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
			}else{
				
		        message.setCode(SysConstant.FAILURE_CODE);
		        message.setMessage(SysConstant.FAILURE_MSG);
			}
		} catch (Exception e) {
			
			 message.setCode(SysConstant.FAILURE_CODE);
		     message.setMessage(e.getMessage());
			// TODO: handle exception
		}
		return message;
		
	}
	/**
	 * 停用账号
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/closeuser/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> closeUser(@PathVariable Long id) {
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		
		try {
			if(userService.closeUser(id)){
				
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
			}else{
				
		        message.setCode(SysConstant.FAILURE_CODE);
		        message.setMessage(SysConstant.FAILURE_MSG);
			}
		} catch (Exception e) {
			
			 message.setCode(SysConstant.FAILURE_CODE);
		     message.setMessage(e.getMessage());
			// TODO: handle exception
		}
		return message;
		
	}
	
	/**
	 * 停用账号
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/openuser/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> openUser(@PathVariable Long id) {
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		try {
			if(userService.openUser(id)){
				
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
			}else{
				
		        message.setCode(SysConstant.FAILURE_CODE);
		        message.setMessage(SysConstant.FAILURE_MSG);
			}
		} catch (Exception e) {
			
			 message.setCode(SysConstant.FAILURE_CODE);
		     message.setMessage(e.getMessage());
			// TODO: handle exception
		}
		
		return message;
	
		
	}
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/modifyuser",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyUser(@RequestBody UserEntity user) {
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		try {
			if(userService.modifyUser(user)){
				
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
			}else{
				
		        message.setCode(SysConstant.FAILURE_CODE);
		        message.setMessage(SysConstant.FAILURE_MSG);
			}
		} catch (Exception e) {
			
			 message.setCode(SysConstant.FAILURE_CODE);
		     message.setMessage(e.getMessage());
			// TODO: handle exception
		}
		
		return message;
		
	}
	@RequestMapping(value="/modifypassword",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyPassword(@RequestBody Map<String,Object> map) {
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		if(map.get("id")==null){
			
			 message.setCode(SysConstant.FAILURE_CODE);
		     message.setMessage(SysConstant.FAILURE_MSG);
		     return message;
		}
		
		if(map.get("old_pw")==null){
			
			 message.setCode(SysConstant.FAILURE_CODE);
		     message.setMessage(SysConstant.FAILURE_MSG);
		     return message;
		}
		
		if(map.get("new_pw")==null){
			
			 message.setCode(SysConstant.FAILURE_CODE);
		     message.setMessage(SysConstant.FAILURE_MSG);
		     return message;
		}
		
		
		try {
			if(userService.modifyPassword(new Long(map.get("id").toString()), map.get("old_pw").toString(), map.get("new_pw").toString(), message)){
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
			}
		} catch (Exception e) {
			
			 message.setCode(SysConstant.FAILURE_CODE);
		     message.setMessage(e.getMessage());
			// TODO: handle exception
		}
		
		return message;
	}
//	
	@RequestMapping(value="/queryuserlist",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<UserEntity>  queryUserList(@RequestBody Page<UserEntity> page) {
		ResponseMessage<Page<UserEntity>> message = new ResponseMessage<Page<UserEntity>>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		userService.getUserPage(page);
		message.setResult(page);
		return page;
	}
	@RequestMapping(value="/getUserType",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public  ResponseMessage<Map<String,Object>> getUserType()
	{
		
		Map<String,Object> map = new HashMap<>();
		
		for (UserType userType : UserType.values()) {
			
			map.put(userType.getName(), userType.getIndex());
			userType.name();
		}
		ResponseMessage<Map<String,Object>> message = new ResponseMessage<>();
		
		message.setResult(map);
		return message;
	
	}
	
	@RequestMapping(value="/userbyid/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<UserEntity>  getUserById(@PathVariable Long id)
	{
		ResponseMessage<UserEntity>  message= new  ResponseMessage<>();
			
		UserEntity userEntity=userService.getUserById(id);
			if(userEntity!=null&& userEntity.getId()!=null){
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
				message.setResult(userEntity);
			}
		
		return message;
	
	}
	
	@RequestMapping(value="/checkuser",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<UserEntity>  getCheckUser(@RequestBody Map<String,Object> map)
	{
		ResponseMessage<UserEntity>  message= new  ResponseMessage<>();
			
		UserEntity userEntity=userService.getUserByAccount(map.get("account").toString());
		
		if(userEntity!=null){
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}else{
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}
		
		return message;
	
	}
	
	@RequestMapping(value="/resetpassword/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String>  getCheckUser(@PathVariable Long id)
	{
		ResponseMessage<String>  message= new  ResponseMessage<>();
			
		
		
		if(userService.resetpassword(id)){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		return message;
	
	}
	
	
	@RequestMapping(value="/getSeat/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<UserEntity>  getSeat(@PathVariable Long id)
	{
		ResponseMessage<UserEntity>  message= new  ResponseMessage<>();
			
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(userService.selectUserByDep(id));
		return message;
	
	}
	
	 
}

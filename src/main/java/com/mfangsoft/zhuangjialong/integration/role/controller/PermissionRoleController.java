package com.mfangsoft.zhuangjialong.integration.role.controller;

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

import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.role.service.RoleService;

@Controller
@RequestMapping("/server")
public class PermissionRoleController {
	@Autowired
	private  RoleService roleService;
	
	@RequestMapping(value="/updatepermission2role",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> updatePermission2Role(@RequestBody Map<String,Object> map){
		
		ResponseMessage<String> message=new ResponseMessage<String>();
		try {
			roleService.updatePermission2Role(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}
	@RequestMapping(value="/updaterole2position",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> updateRole2Position(@RequestBody Map<String,Object> map){
		
		ResponseMessage<String> message=new ResponseMessage<String>();
		
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}
	
	@RequestMapping(value="/updateuser2position",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> updateUser2Position(@RequestBody Map<String,Object> map){
		
		ResponseMessage<String> message=new ResponseMessage<String>();
		
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}
	
	@RequestMapping(value="/updateuserpermission",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> updateUserPermission(@RequestBody Map<String,Object> map){
		
		ResponseMessage<String> message=new ResponseMessage<String>();
		
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}
	

	@RequestMapping(value="/permissionbyroleid/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public List<Long> getPermissionByRoleId(@PathVariable Long id){
		
		
		
	
		return roleService.getPermissionByRoleId(id);
	}

}

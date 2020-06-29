package com.mfangsoft.zhuangjialong.integration.permission.contrller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.model.Tree;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.permission.model.PermissionEntity;
import com.mfangsoft.zhuangjialong.integration.permission.service.PermissionService;

@Controller
@RequestMapping("/server")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping(value="/createpermission",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> createPermission(@RequestBody PermissionEntity permission)
	{
		
		
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		try{
		
		if(permissionService.createPermission(permission)){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			
		}
		}catch(Exception ex)
		{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(ex.getMessage());
			return message;
			
		}
		
		return message;
	}
	
	@RequestMapping(value="/modifypermission",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyPermission(@RequestBody PermissionEntity permission)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		
	
		
		try{
		
		if(permissionService.modifyPermission(permission)){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			
		}
		}catch(Exception ex)
		{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(ex.getMessage());
			return message;
			
		}
		
		return message;
		
	}
	@RequestMapping(value="/removepermission",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> removePermission(@RequestBody Long id)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		try{
			
			if(permissionService.removePermission(id)){
				
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
				
			}
			}catch(Exception ex)
			{
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(ex.getMessage());
				return message;
				
			}
			
			return message;
	}
	@RequestMapping(value="/getpermissiontree",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public List<Tree> getPermissionTree()
	{
		return permissionService.getPermission();
	}
	
	@RequestMapping(value="/getpermissionZtree",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public List<Map<String,Object>> getPermissionForZTree()
	{
		return permissionService.getPermissionForZTree();
	}
	
}

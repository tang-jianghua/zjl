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

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.role.model.RoleEntity;
import com.mfangsoft.zhuangjialong.integration.role.service.RoleService;

@Controller
@RequestMapping("/server")
public class roleController {
	@Autowired
	private RoleService roleService;
	
	/**
	 * 创建角色
	 * @param roleEntities
	 * @return
	 */
	@RequestMapping(value="/createrole",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> createRole(@RequestBody Map<String,Object> roleEntities){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		try {
			roleService.createRole(roleEntities);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(e.getMessage());
		}
		
		
		return message;
		
	}
	/**
	 * 修改角色
	 * @param roleEntities
	 * @return
	 */
	@RequestMapping(value="/modifyrole",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyRole(@RequestBody Map<String,Object> roleEntities){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		try {
			roleService.modifyRole(roleEntities);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(e.getMessage());
		}
		
		return message;
		
	}
	@RequestMapping(value="/removerole/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> removeRole(@PathVariable  Long id){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		try {
			if(roleService.removeRole(id)){
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
			}else{
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(SysConstant.FAILURE_MSG);
			}
		} catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
		
	}
	@RequestMapping(value="/rolepositioncombox",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public  List<RoleEntity> getRolePositionCombox(@RequestBody Map<String, Long> map){
		
		try {
			return roleService.getRolePosition(map.get("id"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	@RequestMapping(value="/queryrole",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public  Page<RoleEntity> queryrole(@RequestBody Page<RoleEntity> page){
		
		try {
			Map<String,Object> o=(Map<String, Object>) page.getParam();
			if(o.get("position_id")==null||"".equals(o.get("position_id").toString())){
				return page;
			}else{
				return roleService.getRoleListForPage(page);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	

}

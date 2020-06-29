package com.mfangsoft.zhuangjialong.integration.department.controller;

import java.util.HashMap;
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
import com.mfangsoft.zhuangjialong.common.model.Tree;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.department.model.DepartmentEntity;
import com.mfangsoft.zhuangjialong.integration.department.service.DepatmentService;

@Controller
@RequestMapping("/server")
public class DepartmentController {
	
	@Autowired
	private  DepatmentService depatmentService;
	
	@RequestMapping(value="/createdep",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public DepartmentEntity createDep(@RequestBody DepartmentEntity department){
		try {
			depatmentService.addDepatment(department);
			return department;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		return null;
	} 
	
	@RequestMapping(value="/modifydep",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public DepartmentEntity modifyDep(@RequestBody DepartmentEntity department){
			
		   try {
			   depatmentService.modifyDepatment(department);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	
		return department;
		
		
	} 
	
	
	@RequestMapping(value="/removedep/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> removeDep(@PathVariable Long id){
			
		ResponseMessage<String> message =new ResponseMessage<String>();
		   try {
			   depatmentService.removeDepatment(id);
			   message.setCode(SysConstant.SUCCESS_CODE);
			   message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
	
		return message;
		
		
	} 
	
	@RequestMapping(value="/createposition",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> createPosition(@RequestBody DepartmentEntity department){
		
		ResponseMessage<String> message =new ResponseMessage<String>();
		
if(depatmentService.addPosition(department)){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else
		{

			message.setCode(SysConstant.FAILURE_CODE);
			
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
	
		return message;
		
	
		
		
	} 
	@RequestMapping(value="/modifyposition",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyPosition(@RequestBody DepartmentEntity department){
		
		ResponseMessage<String> message =new ResponseMessage<String>();
		
		
		if(depatmentService.modifyPosition(department)){
					
					message.setCode(SysConstant.SUCCESS_CODE);
					
					message.setMessage(SysConstant.SUCCESS_MSG);
				}else
				{

					message.setCode(SysConstant.FAILURE_CODE);
					
					message.setMessage(SysConstant.FAILURE_MSG);
				}
				
		
		return message;
		
		
	} 
	@RequestMapping(value="/querydeporposition",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public List<Tree> queryDepOrPosition()
	{
		ResponseMessage<List<Tree>> message = new ResponseMessage<List<Tree>>();
		
//		Tree<Tree> tree= new Tree<Tree>();
//		
//		tree.setId("1");
//		
//		tree.setLabel("部门1");
//		
//		Tree<Tree> tree2= new Tree<Tree>();
//		
//		tree2.setId("2");
//		
//		tree2.setLabel("部门2");
//		
//		tree.getChildren().add(tree2);
		
		DepartmentEntity entity = new DepartmentEntity();
		
		//message.setResult();
		
		return depatmentService.queryDepOrPosition(entity);
		
	}
	@RequestMapping(value="/querydepCombox",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public List<DepartmentEntity>  getDepForCombox(){
		
		Map<String,List<DepartmentEntity>> map= new HashMap<>();
		map.put("results", depatmentService.getDepForCombox());
		
		return depatmentService.getDepForCombox();
		
	}
	
	
	@RequestMapping(value="/queryPositionCombox",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public List<DepartmentEntity>  getPositionForCombox(@RequestBody Map<String,Object> o){
		return depatmentService.getPositionForCombox(new Long(o.get("id").toString()));
		
	}
	
	@RequestMapping(value="/querydeporpositionList",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public List<DepartmentEntity>  querydeporpositionList(){
		DepartmentEntity entity = new DepartmentEntity();
		
		return depatmentService.queryDepOrPositionList(entity);
		
	}

}

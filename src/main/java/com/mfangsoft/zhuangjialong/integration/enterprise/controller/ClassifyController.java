package com.mfangsoft.zhuangjialong.integration.enterprise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.model.Tree;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.BuildClassEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.BuildEnterpriseEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildClassEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildEnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildMaterialsEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.service.ClassifyService;

@Controller(value="serverclassify")
@RequestMapping("/server")
public class ClassifyController {

	@Autowired
	private ClassifyService classifyService;
	
	@Autowired
	private BuildEnterpriseEntityMapper buildEnterpriseEntityMapper;
	
	@Autowired
	private  BuildClassEntityMapper  buildClassEntityMapper;

	@RequestMapping(value = "/addbuildclass", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<BuildClassEntity> addBuildClass(@RequestBody BuildClassEntity buildClassEntity) {

		ResponseMessage<BuildClassEntity> message = new ResponseMessage<>();

		try {
			classifyService.addBuildClass(buildClassEntity);

			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(buildClassEntity);

			return message;
		} catch (Exception e) {
			// TODO: handle exception

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}

		return message;

	}

	@RequestMapping(value = "/addbuildmaterials", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<BuildMaterialsEntity> addBuildMaterials(
			@RequestBody BuildMaterialsEntity buildMaterialsEntity) {

		ResponseMessage<BuildMaterialsEntity> message = new ResponseMessage<>();

		try {
			classifyService.addBuildMaterials(buildMaterialsEntity);

			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(buildMaterialsEntity);
			return message;
		} catch (Exception e) {
			// TODO: handle exception

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}

		return message;

	}

	@RequestMapping(value = "/addbuildenterpise", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<BuildEnterpriseEntity> addBuildEnterpise(
			@RequestBody BuildEnterpriseEntity buildEnterpriseEntity) {

		ResponseMessage<BuildEnterpriseEntity> message = new ResponseMessage<>();

		try {
			classifyService.addBuildEnterpise(buildEnterpriseEntity);

			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(buildEnterpriseEntity);

			return message;
		} catch (Exception e) {
			// TODO: handle exception

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}

		return message;

	}
	
	
	@RequestMapping(value = "/removebuildenterpise/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<String> removeBuildEnterpise(
			@PathVariable Long id) {

		ResponseMessage<String> message = new ResponseMessage<>();

		try {
			if(classifyService.removeBuildEnterpise(id)){
				
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
			}else{
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(SysConstant.FAILURE_MSG);
			}

			
			//message.setResult(buildEnterpriseEntity);

			return message;
		} catch (Exception e) {
			// TODO: handle exception

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}

		return message;

	}
	
	@RequestMapping(value = "/removebuildclass/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<String> removeBuildClass(
			@PathVariable Long id) {

		ResponseMessage<String> message = new ResponseMessage<>();

		try {
			if(classifyService.removeBuildClass(id)){
				
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
			}else{
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(SysConstant.FAILURE_MSG);
			}

			
			//message.setResult(buildEnterpriseEntity);

			return message;
		} catch (Exception e) {
			// TODO: handle exception

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}

		return message;

	}

	@RequestMapping(value = "/modifybuildclass", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String> modifyBuildClass(@RequestBody BuildClassEntity buildClassEntity) {

		ResponseMessage<String> message = new ResponseMessage<>();

		try {
			classifyService.modifyBuildClass(buildClassEntity);

			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);

			return message;
		} catch (Exception e) {
			// TODO: handle exception

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}

		return message;

	}

	@RequestMapping(value = "/modifybuildmaterials", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String> modifyBuildMaterials(@RequestBody BuildMaterialsEntity buildMaterialsEntity) {

		ResponseMessage<String> message = new ResponseMessage<>();

		try {
			classifyService.modifyBuildMaterials(buildMaterialsEntity);

			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);

			return message;
		} catch (Exception e) {
			// TODO: handle exception

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}

		return message;

	}

	@RequestMapping(value = "/modifybuildenterpise", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String> modifyBuildEnterpise(@RequestBody BuildEnterpriseEntity buildEnterpriseEntity) {

		ResponseMessage<String> message = new ResponseMessage<>();

		try {
			classifyService.modifyBuildEnterpise(buildEnterpriseEntity);

			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);

			return message;
		} catch (Exception e) {
			// TODO: handle exception

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}

		return message;

	}
	
	@RequestMapping(value = "/buildenterpriselist", method = RequestMethod.GET)
	@ResponseBody
	public List<BuildEnterpriseEntity> getBuildEnterpriseList(){
		
		return  classifyService.getBuildEnterpriseList();
	}
	@RequestMapping(value = "/buildmaterialslist", method = RequestMethod.GET)
	@ResponseBody
	public  List<BuildMaterialsEntity>  getBuildMaterialsList(){
		
		return classifyService.getBuildMaterialsList();
	}
	@RequestMapping(value = "/buildclassentities", method = RequestMethod.GET)
	@ResponseBody
	public List<BuildClassEntity> getBuildClassEntities(){
		
		return classifyService.geBuildClassEntities();
		
	}
	
	
	
    @RequestMapping(value = "/buildenterpriselistByClassId/{class_id}", method = RequestMethod.GET)
	@ResponseBody
	public List<BuildEnterpriseEntity> getBuildEnterpriseListByClassId(@PathVariable Long class_id){
		
		return  buildEnterpriseEntityMapper.getBuildEnterpriseListByclassId(class_id);
	}
	
	@RequestMapping(value = "/buildclassentitiesByBuildId/{build_id}", method = RequestMethod.GET)
	@ResponseBody
	public List<BuildClassEntity> getBuildClassEntities(@PathVariable Long build_id){
		
		return buildClassEntityMapper.geBuildClassEntitiesBybuildId(build_id);
		
	}
	
	
	


}

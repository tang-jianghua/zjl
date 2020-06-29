package com.mfangsoft.zhuangjialong.integration.enterprise.controller;

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
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildClassEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.service.EnterpriseService;
import com.mfangsoft.zhuangjialong.integration.enums.UserState;

@Controller
@RequestMapping("/server")
public class EnterpriseController {
	@Autowired
	private EnterpriseService enterpriseService;

	@RequestMapping(value = "/createenterprise", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> addEnterprise(@RequestBody EnterpriseEntity enterprise) {

		ResponseMessage<String> message = new ResponseMessage<String>();

		try {
			if (enterpriseService.addEnterprise(enterprise)) {
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
			}
		} catch (Exception e) {
			e.printStackTrace();

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}

		return message;
	}

	@RequestMapping(value = "/getenterprisebyid/{id}", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<EnterpriseEntity> getEnterprise(@PathVariable Long id) {
		ResponseMessage<EnterpriseEntity> message = new ResponseMessage<EnterpriseEntity>();

		message.setResult(enterpriseService.getEnterpriseById(id));
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;

	}

	@RequestMapping(value = "/modifyenterprise")
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyEnterprise(@RequestBody EnterpriseEntity enterprise) {
		ResponseMessage<String> message = new ResponseMessage<String>();

		try {

			if (enterpriseService.modifyEnterprise(enterprise)) {
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
			}
		} catch (Exception e) {
			e.printStackTrace();

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}

		// message.setResult(enterprise);
		return message;

	}
	
	@RequestMapping(value = "/modifybackenterprise")
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyBackEnterprise(@RequestBody EnterpriseEntity enterprise) {
		ResponseMessage<String> message = new ResponseMessage<String>();

		try {

			if (enterpriseService.modifyBackEnterprise(enterprise)) {
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
			}
		} catch (Exception e) {
			e.printStackTrace();

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}

		// message.setResult(enterprise);
		return message;

	}

	@RequestMapping(value = "/queryenterprise")
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,Object>> queryEnterprise(@RequestBody Page<Map<String,Object>> page) {
		
		return enterpriseService.getEnterpriseForPage(page);

	}

	@RequestMapping(value = "/closeenterprise/{id}", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> closeEnterprise(@PathVariable Long id) {
		
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		try {
			enterpriseService.closeOrOpenEnterprise(id, UserState.CLOSE.getIndex());
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		
		
		// message.setResult(enterprise);
		return message;

	}

	@RequestMapping(value = "/openenterprise/{id}", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> openEnterprise(@PathVariable Long id) {
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		
		try {
			enterpriseService.closeOrOpenEnterprise(id, UserState.OPEN.getIndex());
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
	@RequestMapping(value = "/getbuildclassentities", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public List<BuildClassEntity> getBuildClassEntities(){
		
		
		return enterpriseService.getBuildClassEntities();
	}
	
	@RequestMapping(value = "/getentbrandname", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public List<EnterpriseEntity> getEnterpriseBrandName(){
		
		
		return enterpriseService.getEnterpriseBrandName();
	}

}

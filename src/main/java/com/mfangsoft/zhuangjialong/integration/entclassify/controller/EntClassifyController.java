package com.mfangsoft.zhuangjialong.integration.entclassify.controller;

import java.util.List;

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
import com.mfangsoft.zhuangjialong.integration.entclassify.model.EntClassifyEntity;
import com.mfangsoft.zhuangjialong.integration.entclassify.service.EntClassifyService;

@Controller
@RequestMapping("/server")
public class EntClassifyController {
	
	@Autowired
	private EntClassifyService entClassifyService;
	
	@RequestMapping(value = "/ent/createclassify", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> addEnterprise(@RequestBody EntClassifyEntity entClassifyEntity) {

		ResponseMessage<String> message = new ResponseMessage<String>();

		try {
			if (entClassifyService.addEntClassify(entClassifyEntity)) {
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

	

	@RequestMapping(value = "/ent/modifyclassify", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyClassify(@RequestBody EntClassifyEntity entClassifyEntity ) {
		ResponseMessage<String> message = new ResponseMessage<String>();

		try {

			if (entClassifyService.modifyEntClassify(entClassifyEntity)) {
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
	
	
	@RequestMapping(value = "/ent/removeclassify/{id}", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> removeClassify(@PathVariable Long id ) {
		ResponseMessage<String> message = new ResponseMessage<String>();

		try {

			if (entClassifyService.romveEntClassify(id)) {
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
			}else{
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(SysConstant.FAILURE_MSG);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}

		// message.setResult(enterprise);
		return message;

	}
	
	@RequestMapping(value = "/ent/queryclassifies")
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<EntClassifyEntity>> queryClassifies() {
		ResponseMessage<List<EntClassifyEntity>> message = new ResponseMessage<List<EntClassifyEntity>>();

			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(entClassifyService.queryEntClassify());
		// message.setResult(enterprise);
		return message;

	}

}

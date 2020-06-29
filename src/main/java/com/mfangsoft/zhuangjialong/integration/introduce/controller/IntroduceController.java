package com.mfangsoft.zhuangjialong.integration.introduce.controller;

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
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildClassEntity;
import com.mfangsoft.zhuangjialong.integration.introduce.mapper.IntroduceClassEntityMapper;
import com.mfangsoft.zhuangjialong.integration.introduce.model.IntroduceClass;
import com.mfangsoft.zhuangjialong.integration.introduce.model.IntroduceClassEntity;
import com.mfangsoft.zhuangjialong.integration.introduce.model.IntroduceProductEntity;
import com.mfangsoft.zhuangjialong.integration.introduce.model.IntroduceProductParam;
import com.mfangsoft.zhuangjialong.integration.introduce.model.PartnerClass;
import com.mfangsoft.zhuangjialong.integration.introduce.service.IntroduceClassService;

@Controller(value = "ServiceIntroduceController")
@RequestMapping("/server")
public class IntroduceController {

	@Autowired
	IntroduceClassService introduceClassService;

	/**
	 * 合伙人的品类
	 * 
	 * @param record
	 *            : class_id
	 * @return
	 */
	@RequestMapping(value = "getAllPartnerClassWithIntroState", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<PartnerClass>> getAllPartnerClassWithIntroState() {

		ResponseMessage<List<PartnerClass>> responseMessage = new ResponseMessage<List<PartnerClass>>();
		List<PartnerClass> list = introduceClassService.getAllPartnerClassWithIntroState();
		if (list != null) {
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(list);
		} else {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
		}
		return responseMessage;
	}

	/**
	 * 添加推荐品类
	 * 
	 * @param record
	 *            : class_id
	 * @return
	 */
	@RequestMapping(value = "addIntroduceClass", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage addIntroduceClass(@RequestBody IntroduceClassEntity record) {

		ResponseMessage responseMessage = new ResponseMessage<>();
		if (introduceClassService.addIntroduceClass(record)) {
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		} else {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
		}
		return responseMessage;
	}

	/**
	 * 查询已推荐品类
	 * 
	 * @param record
	 *            : class_id
	 * @return
	 */
	@RequestMapping(value = "queryIntroduceClass", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<IntroduceClassEntity>> queryIntroduceClass() {

		ResponseMessage<List<IntroduceClassEntity>> responseMessage = new ResponseMessage<>();
		List<IntroduceClassEntity> list = introduceClassService.queryIntroduceClass();
		if (list != null) {
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(list);
		} else {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
		}
		return responseMessage;
	}

	/**
	 * 删除推荐品类
	 * 
	 * @param record
	 *            : class_id
	 * @return
	 */
	@RequestMapping(value = "deleteIntroduceClass/{id}", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage deleteIntroduceClass(@PathVariable Long id) {

		ResponseMessage responseMessage = new ResponseMessage<>();
		introduceClassService.deleteIntroduceClass(id);
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		return responseMessage;
	}

	/**
	 * 删除推荐产品
	 * 
	 * @param record
	 *            : class_id
	 * @return
	 */
	@RequestMapping(value = "deleteIntroduceProduce/{introduce_id}/{introduce_product_id}", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage deleteIntroduceProduct(@PathVariable Long introduce_id,@PathVariable Long introduce_product_id) {

		ResponseMessage responseMessage = new ResponseMessage<>();
		if(introduceClassService.deleteIntroduceProduct(introduce_id, introduce_product_id)){
			
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			
		}else{
			
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
		}
		return responseMessage;
	}
	
	/**
	 * 修改推荐品类顺序
	 * 
	 * @param record
	 *            : class_id
	 * @return
	 */
	@RequestMapping(value = "modifyIntroduceClass/{id1}/{id2}", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage modifyIntroduceClass(@PathVariable Long id1, @PathVariable Long id2) {

		ResponseMessage responseMessage = new ResponseMessage<>();
		if (introduceClassService.modifyIntroduceClass(id1, id2)) {
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		} else {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
		}
		return responseMessage;
	}

	/**
	 * 添加推荐产品
	 * 
	 * @param record
	 *            : class_id
	 * @return
	 */
	@RequestMapping(value = "addIntroduceProduct", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage addIntroduceProduct(@RequestBody IntroduceProductParam record) {

		ResponseMessage responseMessage = new ResponseMessage<>();
		return introduceClassService.addIntroduceProduct(record,responseMessage);
	}

	/**
	 * 查询已推荐产品
	 * 
	 * @return
	 */
	@RequestMapping(value = "queryIntroduceProduct", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public List<IntroduceClass> queryIntroduceProduct() {
		return introduceClassService.queryIntroduceProduct();
	}
	
	/**
	 * 增加品类示意图上传，色值输入
	 * @param record : id, appshow_imgurl, rgb
	 * @return
	 */
	@RequestMapping(value = "addClassShowImg", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage addClassShowImg(@RequestBody BuildClassEntity record) {
		ResponseMessage responseMessage = new ResponseMessage<>();
		if (introduceClassService.addClassShowImg(record)) {
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		} else {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
		}
		return responseMessage;
	}
	
}

package com.mfangsoft.zhuangjialong.app.pointmall.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerPointEntity;
import com.mfangsoft.zhuangjialong.app.pointmall.model.ConvertProductModel;
import com.mfangsoft.zhuangjialong.app.pointmall.model.PointMallModel;
import com.mfangsoft.zhuangjialong.app.pointmall.model.PointProductModel;
import com.mfangsoft.zhuangjialong.app.pointmall.service.PointMallService;
import com.mfangsoft.zhuangjialong.app.sendflow.service.SendFlowService;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;

/**
 * @description：
 * 
 * @author：Jianghua.Tang @date：2016年5月28日
 * 
 */
@Controller
@RequestMapping("/app")
public class AppPointMallController {

	@Autowired
	PointMallService pointMallServiceImpl;
	
	
	@RequestMapping(value = "/getpointmall", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<PointMallModel> getPointMall(@RequestBody Page<PointProductModel> pages) {
		ResponseMessage<PointMallModel> message = new ResponseMessage<>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(pointMallServiceImpl.selectPointMall(pages));
		return message;
	}
   
	@RequestMapping(value = "/getpoint", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<PointMallModel> getPoint(@RequestBody Page<CustomerPointEntity> pages) {
		ResponseMessage<PointMallModel> message = new ResponseMessage<>();
		message.setResult(pointMallServiceImpl.selectPoint(pages));
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}
	
	//IP:139.224.29.231
	@RequestMapping(value = "/convertproduct", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<PointMallModel> convertProduct(@RequestBody ConvertProductModel convertProductModel) {
		ResponseMessage<PointMallModel> message = new ResponseMessage<>();
		if(convertProductModel.getCustomer_id() == null){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.not_login);
		}
		String addConvertProduct = pointMallServiceImpl.addConvertProduct(convertProductModel);
		switch (addConvertProduct){
		case SysConstant.SUCCESS_CODE:
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			break;
		case SysConstant.FAILURE_CODE:
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			break;
		case SysConstant.POINT_NOT_ENOUGH_CODE:
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.POINT_NOT_ENOUGH_MSG);
			break;
		}
		return message;
	}
	
	@RequestMapping(value = "/getconvertrecord", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<ConvertProductModel>> getConvertRecord(@RequestBody Page<ConvertProductModel> page) {
		ResponseMessage<Page<ConvertProductModel>> message = new ResponseMessage<>();
		Map<String, Integer> map = (Map<String, Integer>)page.getParam();
		if(map.get("customer_id") == null){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.not_login);
		}
		message.setResult(pointMallServiceImpl.selectConvertRecordForPage(page));
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}
	

	@RequestMapping(value = "/convertflow", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> convertFlow(@RequestBody ConvertProductModel convertProductModel) {
		ResponseMessage<String> message = new ResponseMessage<>();
		Integer convertFlow = pointMallServiceImpl.updateConvertFlow(convertProductModel);
		if(convertFlow==SendFlowService.SUCCESS){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else if(convertFlow==SendFlowService.FAILURE){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}else if(convertFlow==SendFlowService.NOT_ENOUGH){
			message.setCode("2");//余额不足
			message.setMessage("余额不足");
		}
		return message;
	}
	
	@RequestMapping(value = "/convertflows", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Map<String, Object>> convertFlows(@RequestBody ConvertProductModel convertProductModel) {
		ResponseMessage<Map<String, Object>> message = new ResponseMessage<>();
			message.setCode(SysConstant.SUCCESS_CODE);//余额不足
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(pointMallServiceImpl.updateConvertFlows(convertProductModel));
		return message;
	}
	
	@RequestMapping(value = "/getpointproductdetails", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<PointProductModel> getPointProductDetails(@RequestBody ConvertProductModel convertProductModel) {
		ResponseMessage<PointProductModel> message = new ResponseMessage<>();
		 PointProductModel productModel = pointMallServiceImpl.selectPointProductDetails(convertProductModel);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
               message.setResult(productModel);
			return message;
	}
	

	/*IP:139.224.29.231
	 * param:
	 * {
	 *     "product_id":22,
	 *     "mobiles":[
	 *     "",
	 *     ""
	 *     ]
	 * }
	 */
	@RequestMapping(value = "/addpointconvert", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Map<String, Object>> addPointConvert(@RequestBody ConvertProductModel convertProductModel) {
		ResponseMessage<Map<String, Object>> message = new ResponseMessage<>();
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(pointMallServiceImpl.addPointConvert(convertProductModel));
		return message;
	}
}

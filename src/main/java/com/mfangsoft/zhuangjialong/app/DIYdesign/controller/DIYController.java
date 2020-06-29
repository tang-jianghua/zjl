package com.mfangsoft.zhuangjialong.app.DIYdesign.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.DIYdesign.model.ClassBrandModel;
import com.mfangsoft.zhuangjialong.app.DIYdesign.service.DIYService;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月27日
* 
*/
@Controller
@RequestMapping("/app")
public class DIYController {
	
	@Autowired
	DIYService dIYServiceImpl;
	
	
	
	@RequestMapping(value = "/getclassbrands", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<ClassBrandModel>> getClassBrands(@RequestBody Map<String, String> param) {
		ResponseMessage<List<ClassBrandModel>> message = new ResponseMessage<>();
		String region_code = param.get("region_code");
		if(StringUtils.isEmpty(region_code)){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			return message;
			}
		List<ClassBrandModel> diyClassBrand = dIYServiceImpl.getDIYClassBrand(param);
		if(diyClassBrand.size()==0){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			return message;
		}else{
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(diyClassBrand);
			return message;
		}
	}
		
		@RequestMapping(value = "/getproductidbymodel", method = RequestMethod.POST)
		@ResponseBody
		@CrossOrigin
		public ResponseMessage<String> getProductIdByModel(@RequestBody Map<String, String> param) {
			ResponseMessage<String> message = new ResponseMessage<>();
			String region_code = param.get("region_code");
			if(StringUtils.isEmpty(region_code)){
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(SysConstant.FAILURE_MSG);
				return message;
				}
			Long productId = dIYServiceImpl.getProductIdByModel(param);
			if(productId ==null){
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(SysConstant.FAILURE_MSG);
				return message;
			}else{
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
				message.setResult(productId+"");
				return message;
			}
}
}
package com.mfangsoft.zhuangjialong.app.unionpromotion.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.product.model.ListProductModel;
import com.mfangsoft.zhuangjialong.app.product.model.ProductListModel;
import com.mfangsoft.zhuangjialong.app.unionpromotion.service.UnionPromService;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionCustomer;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionPromotion;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月21日
* 
*/
@Controller
@RequestMapping("/app")
public class UnionPromController {

	@Autowired
	UnionPromService unionPromServiceImpl;
	
	@RequestMapping(value = "/querymainunitepromotion", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<UnionPromotion>> getUnionList(@RequestBody Map<String, String> param){
		ResponseMessage<List<UnionPromotion>> message = new ResponseMessage<>();
		if(param.get("region_code") == null){
         	message.setCode(SysConstant.FAILURE_CODE);
        	message.setMessage(SysConstant.FAILURE_MSG);			
        	return message;
		}
		
		List<UnionPromotion> unionList = unionPromServiceImpl.selectUnionList(param);
        if(unionList == null && unionList.size()==0){
         	message.setCode(SysConstant.FAILURE_CODE);
        	message.setMessage(SysConstant.FAILURE_MSG);
      return message;
        }else{
          	message.setCode(SysConstant.SUCCESS_CODE);
        	message.setMessage(SysConstant.SUCCESS_MSG);
        	message.setResult(unionList);
        	return message;
        }
	}
	
	@RequestMapping(value = "/queryuniondetails", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<UnionPromotion> queryUnionDetails(@RequestBody UnionCustomer param){
		ResponseMessage<UnionPromotion> message = new ResponseMessage<>();
		if(param.getPromotion_id()== null){
         	message.setCode(SysConstant.FAILURE_CODE);
        	message.setMessage(SysConstant.FAILURE_MSG);			
        	return message;
		}
		
	 UnionPromotion unionPromotion = unionPromServiceImpl.selectUnionDetails(param);
        if(unionPromotion == null){
         	message.setCode(SysConstant.FAILURE_CODE);
        	message.setMessage(SysConstant.FAILURE_MSG);
      return message;
        }else{
          	message.setCode(SysConstant.SUCCESS_CODE);
        	message.setMessage(SysConstant.SUCCESS_MSG);
        	message.setResult(unionPromotion);
        	return message;
        }
	}
	
	@RequestMapping(value = "/querycashdetails", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<UnionPromotion> queryCashDetails(@RequestBody UnionCustomer param){
		ResponseMessage<UnionPromotion> message = new ResponseMessage<>();
		if(param.getPromotion_id()== null){
         	message.setCode(SysConstant.FAILURE_CODE);
        	message.setMessage(SysConstant.FAILURE_MSG);			
        	return message;
		}
		
	 UnionPromotion unionPromotion = unionPromServiceImpl.selectCashDetails(param);
        if(unionPromotion == null){
         	message.setCode(SysConstant.FAILURE_CODE);
        	message.setMessage(SysConstant.FAILURE_MSG);
      return message;
        }else{
          	message.setCode(SysConstant.SUCCESS_CODE);
        	message.setMessage(SysConstant.SUCCESS_MSG);
        	message.setResult(unionPromotion);
        	return message;
        }
	}
	
	@RequestMapping(value = "/addcash", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<UnionPromotion> addCash(@RequestBody UnionCustomer param){
		ResponseMessage<UnionPromotion> message = new ResponseMessage<>();
		if(unionPromServiceImpl.addCash(param)){
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
        }else{
        		message.setCode(SysConstant.FAILURE_CODE);
        		message.setMessage(SysConstant.FAILURE_MSG);
        }
		return message;
	}
	
/*	@RequestMapping(value = "/queryunionproducts", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<Map<String, Object>>> queryunionproducts(@RequestBody Page<Map<String, Object>> page){
		ResponseMessage<Page<Map<String, Object>>> message = new ResponseMessage<>();
//		Page<Map<String, Object>> queryunionproducts = unionPromServiceImpl.queryunionproducts(page);
		Page<Map<String, Object>> queryunionproducts = unionPromServiceImpl.getUnionProducts(page);
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(queryunionproducts);
		return message;
	}
*/	@RequestMapping(value = "/queryunionproducts", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<ProductListModel>> queryunionproducts(@RequestBody Page<ProductListModel> page){
		ResponseMessage<Page<ProductListModel>> message = new ResponseMessage<>();
//		Page<Map<String, Object>> queryunionproducts = unionPromServiceImpl.queryunionproducts(page);
		Page<ProductListModel> queryunionproducts = unionPromServiceImpl.getSqlUnionProducts(page);
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(queryunionproducts);
		return message;
	}
	
}

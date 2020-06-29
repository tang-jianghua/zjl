package com.mfangsoft.zhuangjialong.app.mapservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.buildClass.model.BuildClass;
import com.mfangsoft.zhuangjialong.app.mapservice.model.AskForHelpModel;
import com.mfangsoft.zhuangjialong.app.mapservice.model.BaseAskhelpEntity;
import com.mfangsoft.zhuangjialong.app.mapservice.model.BaseSellerServiceEntity;
import com.mfangsoft.zhuangjialong.app.mapservice.model.SellerServiceModel;
import com.mfangsoft.zhuangjialong.app.mapservice.service.MapServiceService;
import com.mfangsoft.zhuangjialong.app.shop.model.Shop;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;


/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年12月14日
* 
*/
@Controller
@RequestMapping("/app")
public class MapServiceController {

	@Autowired
	MapServiceService mapServiceServiceImpl;
	
	/*
	 * 通过城市合伙人获取品类
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/getClassesByPartnerId",method=RequestMethod.POST)
	public ResponseMessage<List<BuildClass>> getClassesByPartnerId(@RequestBody SellerServiceModel sellerServiceModel){
		ResponseMessage<List<BuildClass>> message = new ResponseMessage<>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(mapServiceServiceImpl.getClasses(sellerServiceModel.getPartner_id()));
		return message;
	}
	
	/*
	 * 通过城市合伙人和品类获取品牌
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/getBrandsByPartnerIdAndClassId",method=RequestMethod.POST)
	public ResponseMessage<List<Map<String, Object>>> getBrandsByPartnerIdAndClassId(@RequestBody Map<String, Long> param){
		ResponseMessage<List<Map<String, Object>>> message = new ResponseMessage<>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(mapServiceServiceImpl.getBrands(param));
		return message;
	}
	
	/*
	 * 通过品牌获取店铺
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/getShopsByBrandId",method=RequestMethod.POST)
	public ResponseMessage<List<Shop>> getShopsByBrandId(@RequestBody Map<String, Long> param){
		ResponseMessage<List<Shop>> message = new ResponseMessage<>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(mapServiceServiceImpl.getShops(param.get("brand_id")));
		return message;
	}
	
	/*
	 * 发布服务
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/publishservice",method=RequestMethod.POST)
	public ResponseMessage<String> publishService(@RequestBody BaseSellerServiceEntity entity){
		return mapServiceServiceImpl.addSellerService(entity);
	}
	
	/*
	 *查询服务
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/getSellerServices",method=RequestMethod.POST)
	public ResponseMessage<List<SellerServiceModel>> getSellerServices(@RequestBody SellerServiceModel serviceModel){
		ResponseMessage<List<SellerServiceModel>> message = new ResponseMessage<>();
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(mapServiceServiceImpl.getSellerServices(serviceModel));
		return message;
	}
	/*
	 *查询服务
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/getConstructServices",method=RequestMethod.POST)
	public ResponseMessage<List<SellerServiceModel>> getConstructServices(@RequestBody SellerServiceModel serviceModel){
		ResponseMessage<List<SellerServiceModel>> message = new ResponseMessage<>();
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(mapServiceServiceImpl.getConstructServices(serviceModel));
		return message;
	}
	
	/*
	 *查询服务详情
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/getSellerServiceDetail",method=RequestMethod.POST)
	public ResponseMessage<SellerServiceModel> getSellerServiceDetail(@RequestBody SellerServiceModel serviceModel){
		ResponseMessage<SellerServiceModel> message = new ResponseMessage<>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(mapServiceServiceImpl.getSellerServiceDetail(serviceModel));
		return message;
	}
	
	/*
	 *查询单个服务
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/getSellerService",method=RequestMethod.POST)
	public ResponseMessage<SellerServiceModel> getSellerService(@RequestBody SellerServiceModel serviceModel){
		ResponseMessage<SellerServiceModel> message = new ResponseMessage<>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(mapServiceServiceImpl.getSellerService(serviceModel));
		return message;
	}
	
	
	/*
	 *查询求助
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/getAskForHelps",method=RequestMethod.POST)
	public ResponseMessage<List<AskForHelpModel>> getAskForHelps(@RequestBody AskForHelpModel serviceModel){
		ResponseMessage<List<AskForHelpModel>> message = new ResponseMessage<>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(mapServiceServiceImpl.getAskForHelps(serviceModel));
		return message;
	}
	
	/*
	 *查询求助详情
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/getAskForHelp",method=RequestMethod.POST)
	public ResponseMessage<AskForHelpModel> getAskForHelp(@RequestBody AskForHelpModel serviceModel){
		ResponseMessage<AskForHelpModel> message = new ResponseMessage<>();
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(mapServiceServiceImpl.getAskForHelp(serviceModel));
		return message;
	}
	
	/*
	 *发布求助
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/publishAskForHelp",method=RequestMethod.POST)
	public ResponseMessage<String> publishAskForHelp(@RequestBody BaseAskhelpEntity askhelpEntity){
		ResponseMessage<String> message = new ResponseMessage<>();
		if(mapServiceServiceImpl.addAskForHelp(askhelpEntity)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
}

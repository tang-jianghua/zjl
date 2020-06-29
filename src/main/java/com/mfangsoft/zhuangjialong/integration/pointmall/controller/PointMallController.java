package com.mfangsoft.zhuangjialong.integration.pointmall.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.pointmall.model.PointMallEntity;
import com.mfangsoft.zhuangjialong.integration.pointmall.service.PointMallService;

@Controller
@RequestMapping("/server")
public class PointMallController {

	@Autowired
	private PointMallService pointMallService;
	/**
	 * 创建积分商城产品
	 * @param pointMallEntity
	 * @return
	 */
	@RequestMapping(value="/createpoinmall", method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String>  addPoinMall(@RequestBody PointMallEntity pointMallEntity){
		ResponseMessage<String>  message = new ResponseMessage<>();
		try {
			pointMallService.addPoinMall(pointMallEntity);
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			// TODO: handle exception
		}
		return message;
	}
	/**
	 * 修改积分商城产品
	 * @param pointMallEntity
	 * @return
	 */
	@RequestMapping(value="/modifypoinmall", method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String>   modifyPoinMall(@RequestBody PointMallEntity pointMallEntity){
		
		ResponseMessage<String>  message = new ResponseMessage<>();
		try {
			pointMallService.modifyPoinMall(pointMallEntity);
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			// TODO: handle exception
		}
		return message;
	}
	/**
	 * 查询积分商城列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/querypoinmall", method=RequestMethod.POST)
	@ResponseBody
	public Page<Map<String,Object>> queryPoinMall(@RequestBody Page<Map<String,Object>> page){
		
		return pointMallService.queryPoinMall(page);
	}
	
	/**
	 * 查询运营商
	 * @return
	 */
	@RequestMapping(value="/queryoperator", method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<List<Map<String,Object>>> queryOperator(){
		
		ResponseMessage<List<Map<String,Object>>>  message = new ResponseMessage<>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(pointMallService.queryOperator());
		
		return message;
	}
	
	/**
	 * 查询流量包
	 * @param operator_code
	 * @return
	 */
	@RequestMapping(value="/queryflowpackage/{operator_code}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<List<Map<String,Object>>> queryFlowPackage(@PathVariable String operator_code){
		ResponseMessage<List<Map<String,Object>>>  message = new ResponseMessage<>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(pointMallService.queryFlowPackage(operator_code));
		
		return message;
	}
	
	/**
	 * 查询合伙人
	 * @return
	 */
	@RequestMapping(value="/queryPartner", method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<List<Map<String,Object>>>  queryPartner(){
		
		ResponseMessage<List<Map<String,Object>>>  message = new ResponseMessage<>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(pointMallService.queryPartner());
		return message;
		
	}
	
	/**
	 * 查询品牌
	 * @return
	 */
	@RequestMapping(value="/querybrands", method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<List<Map<String,Object>>>  queryBrand(@RequestBody List<Long> partnerId){
		
		ResponseMessage<List<Map<String,Object>>>  message = new ResponseMessage<>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(pointMallService.queryBrand(partnerId));
		return message;
		
	}
	
	/**
	 * 查询品牌
	 * @return
	 */
	@RequestMapping(value="/querybrands", method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<List<Map<String,Object>>>  queryBrand(){
		
		ResponseMessage<List<Map<String,Object>>>  message = new ResponseMessage<>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(pointMallService.queryBrand());
		return message;
		
	}
	/**
	 * 查询店铺
	 * @return
	 */
	@RequestMapping(value="/queryshops", method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<List<Map<String,Object>>> queryShop(@RequestBody List<Long>  brandId){
		
		ResponseMessage<List<Map<String,Object>>>  message = new ResponseMessage<>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(pointMallService.queryShop(brandId));
		return message;
	}
	
	/**
	 * 查询积分兑换列表
	 * @return
	 */
	@RequestMapping(value="/querypointconver", method=RequestMethod.POST)
	@ResponseBody
	public Page<Map<String,Object>> queryPointConverPage(@RequestBody Page<Map<String,Object>> page){
		
		return pointMallService.queryPointConverPage(page);
	}
	/**
	 * 通过兑换码查询积分兑换记录
	 * @return
	 */
	@RequestMapping(value="/querypointconvertbyconvertcode/{convertCode}", method=RequestMethod.GET)
	@ResponseBody
	ResponseMessage<Long>    queryPointConvertByConvertCode(@PathVariable String convertCode){
		ResponseMessage<Long>  message = new ResponseMessage<>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(pointMallService.queryPointConvertByConvertCode(convertCode));
		return message;
	}
	
	/**
	 * 修改积分兑换记录
	 * @return
	 */
	@RequestMapping(value="/updatepoinconvert/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<String>    updatePoinConvert(@PathVariable Long id){
		
		ResponseMessage<String>  message = new ResponseMessage<>();
		try {
			pointMallService.updatePoinConvert(id);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			// TODO: handle exception
		}
		return message;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/getpoinmapbyid/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<PointMallEntity>     getPoinMapById(@PathVariable Long id){
		
		ResponseMessage<PointMallEntity>  message = new ResponseMessage<>();
	
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		
			message.setResult(pointMallService.getPoinMapById(id));
		return message;
	}
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/onpointproduct/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<String>     onPointProduct(@PathVariable Long id){
		
		ResponseMessage<String>  message = new ResponseMessage<>();
		   try {
			   pointMallService.updatePointMallProduct(id, 1);
			   message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
			
				
		} catch (Exception e) {
			e.printStackTrace();
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		
		
			// TODO: handle exception
		}
			
		return message;
	}
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/offpointproduct/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<String>     offPointProduct(@PathVariable Long id){
		
		ResponseMessage<String>  message = new ResponseMessage<>();
		   try {
			   pointMallService.updatePointMallProduct(id, 2);
			   message.setCode(SysConstant.SUCCESS_CODE);
			   message.setMessage(SysConstant.SUCCESS_MSG);
			
				
		} catch (Exception e) {
			e.printStackTrace();
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		
		
			// TODO: handle exception
		}
		
			
		return message;
	}
	
}

package com.mfangsoft.zhuangjialong.integration.seller.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructAppointmentModel;
import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructStateRelation;
import com.mfangsoft.zhuangjialong.app.seller.model.ConstructModel;
import com.mfangsoft.zhuangjialong.app.seller.model.ConstructServiceType;
import com.mfangsoft.zhuangjialong.app.seller.model.Guild;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerEntity;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.seller.model.Order;
import com.mfangsoft.zhuangjialong.integration.seller.model.SellerModel;
import com.mfangsoft.zhuangjialong.integration.seller.service.SellerService;

@Controller
@RequestMapping("/server")
public class SellerController {
	
	@Autowired
	private SellerService sellerService;
	
	/**
	 * 增加卖家人员，非施工
	 * @param sellerEntity
	 * @return
	 */
	@RequestMapping(value="/addseller",method=RequestMethod.POST)
	@ResponseBody
	 public ResponseMessage<String>  addSeller(@RequestBody SellerEntity sellerEntity){
		 return sellerService.addSeller(sellerEntity);
	 }
	
	/**
	 * 保存导购 推广人，非施工
	 * @param sellerEntity
	 * @return
	 */
	@RequestMapping(value="/modifyseller",method=RequestMethod.POST)
	@ResponseBody
	 public ResponseMessage<String> modifySeller(@RequestBody SellerEntity sellerEntity){
		 
		 ResponseMessage<String> message = new ResponseMessage<String>();
			if(sellerService.modifySeller(sellerEntity)){
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
			}else{
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(SysConstant.FAILURE_MSG);
			}
			 return message;
	}
	
	/**
	 * 修改可用状态
	 * @param sellerEntity
	 * @return
	 */
	@RequestMapping(value="/modifysellerstate",method=RequestMethod.POST)
	@ResponseBody
	 public ResponseMessage<Integer> modifySellerState(@RequestBody SellerEntity sellerEntity){
			 return sellerService.modifySellerState(sellerEntity);
	}
	
	/**
	 * 获取一个
	 * @param sellerEntity
	 * @return
	 */
	@RequestMapping(value="/getoneseller",method=RequestMethod.POST)
	@ResponseBody
	 public ResponseMessage<Map<String,Object>> getOneSeller(@RequestBody SellerEntity sellerEntity){
		
		ResponseMessage<Map<String,Object>> message = new ResponseMessage<Map<String,Object>>();
		Map<String,Object> map = sellerService.getOneSeller(sellerEntity);
		
		if(map !=null && map.size() >0){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(map);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		 return message;
	}
	
	/**
	 * 根据条件筛选卖家列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/getTuiguangSeller",method=RequestMethod.POST)
	@ResponseBody	
	public Page<Map<String,Object>> getTuiguangSeller(@RequestBody Page<Map<String,Object>> page){
		
		return sellerService.getTuiguangSellerForPage(page);
	}
	
	/**
	 * 根据条件筛选卖家列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/getSeller",method=RequestMethod.POST)
	@ResponseBody	
	public Page<Map<String,Object>> getSellerForPage(@RequestBody Page<Map<String,Object>> page){
		
		return sellerService.getSellerForPage(page);
	}
	
	/**
	 * 分页查单个卖家推广用户表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/querycustomerback",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,Object>> queryCustomerBackForPage(@RequestBody Page<Map<String,Object>> page){
		
		return sellerService.queryCustomerBackForPage(page);
		
	}
	
	/**
	 * 开发者查询施工预约分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/selectConstructAppointmentBackForPage",method=RequestMethod.POST)
	@ResponseBody	
	public Page<Map<String,Object>> selectConstructAppointmentBackForPage(@RequestBody Page<Map<String,Object>> param){
		
		return sellerService.selectConstructAppointmentBackForPage(param);
	}
	
	/**
	 * 开发者查询订单分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/selectOrderListBackForPage",method=RequestMethod.POST)
	@ResponseBody	
	public Page<Order> selectOrderListBackForPage(@RequestBody Page<Order> param){
		
		return sellerService.selectOrderListBackForPage(param);
	}
	
	/**
	 * 后台个人中心查询一个用户的施工详情
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/selectConstructerAppointmentAndTraceingBackByAppopintId",method=RequestMethod.POST)
	@ResponseBody	
	public ResponseMessage<ConstructAppointmentModel> selectConstructerAppointmentAndTraceingBackByAppopintId(@RequestBody ConstructAppointmentModel param){
		
		ResponseMessage<ConstructAppointmentModel> message = new ResponseMessage<ConstructAppointmentModel>();
		ConstructAppointmentModel c = sellerService.selectConstructerAppointmentAndTraceingBackByAppopintId(param);
		
		if(c !=null){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(c);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		 return message;
	}
	/**
	 * 施工认证
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/queryconstructdetailbackbyid", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<ConstructModel> queryconstructdetailbackbyid(@RequestBody ConstructModel param){
		ResponseMessage<ConstructModel> responseMessage=new ResponseMessage<ConstructModel>();
		ConstructModel m = sellerService.queryconstructdetailbackbyid(param);
		if(m!=null){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(m);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage(SysConstant.FAILURE_MSG);
		return responseMessage;
	}
	/**
	 * 施工认证
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/saveconstructdetailback", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<ConstructModel> saveconstructdetailback(@RequestBody ConstructModel param){
		ResponseMessage<ConstructModel> responseMessage=new ResponseMessage<ConstructModel>();
		
		if(sellerService.saveconstructdetailback(param)){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage(SysConstant.FAILURE_MSG);
		return responseMessage;
	}
	
	
	@RequestMapping(value = "/queryservicetypeback", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<ConstructServiceType>> queryservicetypeback(){
		ResponseMessage<List<ConstructServiceType>> responseMessage=new ResponseMessage<>();
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(sellerService.queryservicetypeback());
			return responseMessage;
	}
	/**
	 * 改变施工认证状态
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/changeverifystate", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Boolean> changeverifystate(@RequestBody ConstructModel param){
		ResponseMessage<Boolean> responseMessage=new ResponseMessage<>();
		boolean flag = sellerService.modifyVerifystate(param);
			if(flag){
				responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			}else{
				responseMessage.setCode(SysConstant.FAILURE_CODE);
				responseMessage.setMessage(SysConstant.FAILURE_MSG);
			}
			return responseMessage;
	}
	
	/**
	 * 查所有施工预约状态
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/getconstructstate", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public List<ConstructStateRelation> getconstructstate(){
		return sellerService.getconstructstate();
	}
	
	/**
	 * 查施工预约评价
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/getEvaluationsForPage", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,String>> getEvaluationsForPage(@RequestBody Page<Map<String,String>> param){
		return sellerService.getEvaluationsForPage(param);
	}
	

	
}

package com.mfangsoft.zhuangjialong.integration.order.controller;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
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

import com.mfangsoft.zhuangjialong.app.order.model.OrderStateRelationEntity;
import com.mfangsoft.zhuangjialong.app.order.util.OrderStatusEnum;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.order.model.OrderShellInfoEntity;
import com.mfangsoft.zhuangjialong.integration.order.model.OrderShellListEntity;
import com.mfangsoft.zhuangjialong.integration.order.model.OrderShellStateRelationEntity;
import com.mfangsoft.zhuangjialong.integration.order.service.OrderService;
import com.mfangsoft.zhuangjialong.integration.seller.model.Order;

@Controller
@RequestMapping("/server")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	
	
	
	
	
	@RequestMapping(value="/customerorderlist",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,Object>> customerorderlist(@RequestBody  Page<Map<String,Object>> page){
		
		
		return orderService.getOrderListForPage(page);
		
	}
	
	@RequestMapping(value="/orderlist",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,Object>> orderlist(@RequestBody  Page<Map<String,Object>> page){
		
		
		return orderService.getOrderNewListForPage(page);
		
	}
	
	
//	@RequestMapping(value="/orderproductlist",method=RequestMethod.POST)
//	@ResponseBody
//	@CrossOrigin
//	public List<Map<String,Object>> getOrderProductList(@RequestBody  List<Long> list){
//		
//		
//		return orderService.getOrderListDetails(list);
//		
//	}
	
	@RequestMapping(value="/customerorderdetails/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Map<String,Object>> customerOrderDetails(@PathVariable Long id){
		ResponseMessage<Map<String,Object>> message = new ResponseMessage<Map<String,Object>>();
		
		    message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(orderService.getOrderDetails(id));
		return message;
		
	}
	
	@RequestMapping(value="/customercancelorder",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> customerCancelOrder(@PathVariable  String order_code ){
		ResponseMessage<String> message = new ResponseMessage<String>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		    
		return message;
		
	}
	

	@RequestMapping(value = "/customerorder", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage customerCanceOrder(@RequestBody OrderStateRelationEntity record  ) {
		ResponseMessage responseMessage = new ResponseMessage();

//		OrderStateRelationEntity record = new OrderStateRelationEntity();
//		record.setOrder_id(orderStateRelationEntity.getOrder_id());
//		record.setOrder_state_code(orderStateRelationEntity.getOrder_state_code());
		record.setTime(new Date());

		if (orderService.insertOrderState(record)) {
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage(SysConstant.ORDER_UPDATE_FAIL);

		return responseMessage;
	}
	
	@RequestMapping(value = "/orderstate", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<Map<String,Object>>> getOrderState() {
		ResponseMessage<List<Map<String,Object>>> responseMessage = new ResponseMessage<>();

		
		
		
		List<Map<String,Object>> list = new ArrayList<>();
		OrderStatusEnum obj[] =OrderStatusEnum.values();
		for (OrderStatusEnum orderStatusEnum : obj) {
			
			Map<String,Object> map = new HashMap<>();
			
			if(new Integer(orderStatusEnum.value)>=1000){
			
			map.put("name", orderStatusEnum.name);
			map.put("value", orderStatusEnum.value);
			list.add(map);
			}
		}
		
		responseMessage.setResult(list);
		

		return responseMessage;
	}
	
	
	
	@RequestMapping(value="/selectOrdercashcouponList",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,Object>> selectOrdercashcouponList(@RequestBody  Page<Map<String,Object>> page ){
		ResponseMessage<String> message = new ResponseMessage<String>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		    
		return orderService.selectOrdercashcouponList(page);
		
	}
	
	@RequestMapping(value="/selectOrdercashcouponNewList",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,Object>> selectOrdercashcouponNewList(@RequestBody  Page<Map<String,Object>> page ){
		ResponseMessage<String> message = new ResponseMessage<String>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		    
		return orderService.selectOrdercashcouponNewList(page);
		
	}
	
	@RequestMapping(value="/selectordertotalprice",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Map<String,Object>> selectOrdercashcouponNewList(){
		ResponseMessage<Map<String,Object>> message = new ResponseMessage<Map<String,Object>>();
		
		
		List<Map<String,Object>> list=orderService.selectorderTotal();
		
		if(list!=null&&list.size()>0){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(list.get(0));   
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			message.setResult(null);   
		}
		
		return message;
		
	}
	
	@RequestMapping(value="/queryOrderBrandCoupons",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,Object>> queryOrderBrandCoupons(@RequestBody  Page<Map<String,Object>> page ){
		ResponseMessage<String> message = new ResponseMessage<String>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		    
		return orderService.selectOrderCouponsList(page);
		
	}
	
	@RequestMapping(value="/selectShellOrderInfo",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<OrderShellInfoEntity> selectShellOrderInfo(){
		
		ResponseMessage<OrderShellInfoEntity> message = new ResponseMessage<OrderShellInfoEntity>();
		    
		OrderShellInfoEntity entity = orderService.selectShellOrderInfo();
		if(entity != null){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(entity);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	
	@RequestMapping(value="/setShellOrderDay",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Boolean> setShellOrderDay(@RequestBody OrderShellInfoEntity record ){
		
		ResponseMessage<Boolean> message = new ResponseMessage<Boolean>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(orderService.setShellOrderDay(record));
		return message;
		
	}
	
	@RequestMapping(value="/queryShellOrderListBypage",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,Object>> queryShellOrderListBypage(@RequestBody  Page<Map<String,Object>> page ){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		    
		return orderService.queryShellOrderListByPage(page);
		
	}
	
	/**
	 * @param 
	 * id 结算单id
	 */
	@RequestMapping(value="/queryOneShellOrderById/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage queryOneShellOrder(@PathVariable Long id){
		
		ResponseMessage<Map<String,Object>> message = new ResponseMessage<Map<String,Object>>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(orderService.queryOneShellOrderById(id));
		return message;
		
	}
	
	/**
	 * @param 
	 * id 结算单id
	 */
	@RequestMapping(value="/queryShellRealOrdersListById",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Order> queryShellRealOrdersById(@RequestBody  Page<Order> page){
		
		return orderService.queryShellRealOrdersListByIdPage(page);
		
	}
	
	/**
	 * @param 
	 * id 结算单id
	 */
	@RequestMapping(value="/updateShellRealOrdersState",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Boolean> updateShellRealOrdersState(@RequestBody OrderShellStateRelationEntity record){
		
		ResponseMessage<Boolean> message = new ResponseMessage<Boolean>();
		
		if(orderService.updateShellRealOrdersState(record)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		return message;
	}
}

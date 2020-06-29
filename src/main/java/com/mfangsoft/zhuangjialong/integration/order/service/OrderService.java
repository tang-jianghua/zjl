package com.mfangsoft.zhuangjialong.integration.order.service;

import java.util.List;

import java.util.Map;

import com.mfangsoft.zhuangjialong.app.order.model.OrderStateRelationEntity;
import com.mfangsoft.zhuangjialong.app.order.util.OrderStatusEnum;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.order.model.OrderShellInfoEntity;
import com.mfangsoft.zhuangjialong.integration.order.model.OrderShellListEntity;
import com.mfangsoft.zhuangjialong.integration.order.model.OrderShellStateRelationEntity;
import com.mfangsoft.zhuangjialong.integration.seller.model.Order;


public interface OrderService  {
	
	
	
	Page<Map<String,Object>> getOrderListForPage(Page<Map<String,Object>> page);
	
	Page<Map<String,Object>> getOrderNewListForPage(Page<Map<String,Object>> page);
	
	//List<Map<String,Object>>   getOrderListDetails(List<Long> list);
	
	Map<String,Object>   getOrderDetails(Long id);
	
	
	Boolean  insertOrderState(OrderStateRelationEntity orderStateRelationEntity);
	
	
	Page<Map<String,Object>> selectOrdercashcouponList(Page<Map<String,Object>> page);
	
	
	Page<Map<String,Object>> selectOrdercashcouponNewList(Page<Map<String,Object>> page);
	
	
	List<Map<String,Object>>  selectorderTotal();
	
	Page<Map<String,Object>> selectOrderCouponsList(Page<Map<String,Object>> page);            

	OrderShellInfoEntity selectShellOrderInfo();
	
	Boolean setShellOrderDay(OrderShellInfoEntity record);
	
	Page<Map<String,Object>> queryShellOrderListByPage(Page<Map<String,Object>> page);
	
	Map<String,Object> queryOneShellOrderById(Long id);
	
	Page<Order> queryShellRealOrdersListByIdPage(Page<Order> page);
	
	Boolean updateShellRealOrdersState(OrderShellStateRelationEntity record);
	
}

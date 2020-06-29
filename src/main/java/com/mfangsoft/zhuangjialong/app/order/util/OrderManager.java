package com.mfangsoft.zhuangjialong.app.order.util;

import java.util.Date;
import java.util.List;
import java.util.Random;

import com.mfangsoft.zhuangjialong.app.order.model.OrderEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderProduct;
import com.mfangsoft.zhuangjialong.common.utils.DateUtils;

public class OrderManager {
	
	/*
	 * 
	 */
	public static boolean validOrder(OrderEntity order){
		List<OrderProduct> orderProductList = order.getProducts();
		try{
			for(OrderProduct orderProduct : orderProductList){
				if(orderProduct == null || orderProduct.getSales_properity() == null) return false;
				if(orderProduct.getNum() > orderProduct.getSales_properity().getStock()){
					return false;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static  Double getOrderTotalPrice(OrderEntity order){//精确小数2位
		Double totalPrice = 0D;
		List<OrderProduct> salesProperty = order.getProducts();
		for(OrderProduct orderProduct : salesProperty){
			if(orderProduct != null && orderProduct.getSales_properity() != null){
				//需要实现活动红包优惠算法
				totalPrice += orderProduct.getNum() * orderProduct.getSales_properity().getPrice();
			}
		}
		return totalPrice;
	}
	
	/**
	 * 创建UUID
	 * @return
	 */
	public static synchronized String makeUUID() {
	    Date date = new Date();
	    StringBuffer s = new StringBuffer(DateUtils.formatDateNospace(date));
	    return s.append((new Random().nextInt(900) + 100)).toString();
	}
}

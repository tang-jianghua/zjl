package com.mfangsoft.zhuangjialong.app.order.util;

public enum OrderStatusEnum {
	
	ALI("支付宝",Integer.valueOf(1)),
	WEIXIN("微信",Integer.valueOf(2)),
	PLATFORM("平台",Integer.valueOf(3)),
	
	PADY("已支付",Integer.valueOf(1)),
	UNPADY("未支付",Integer.valueOf(0)),
	
	NEEDPADY("待付款",Integer.valueOf(1000)),
	VISTEANDMETER("上门测量",Integer.valueOf(7000)),
	NEEDDELIVERY("待发货",Integer.valueOf(1001)),
	NEEDRECEIVE("待收货",Integer.valueOf(2000)),
	NEEDCOMMENT("待评价/验收",Integer.valueOf(2001)),
	SUCCESS("交易完成",Integer.valueOf(3000)),
	CANCEl("已取消",Integer.valueOf(4000)),
	DELETED("已删除",Integer.valueOf(5000)),
	
//	DEPOSITPAYED("待付尾款",Integer.valueOf(6000)),
	
	
	SHOPCASH("店铺订金券",Integer.valueOf(0)),//对应t_biz_order_cash_new
	CONSTRUCT("施工预约",Integer.valueOf(1)),
	CONPONS("红包优惠券",Integer.valueOf(2));
	
	public String name;
	public Integer value;
	
	
	private OrderStatusEnum(String name, Integer value){
		this.name = name;
		this.value = value;
	}
	

}

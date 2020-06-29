package com.mfangsoft.zhuangjialong.integration.order.util;

public enum ShellorderstateEnum {

	CREATE(new Integer(100000),"生成"),
	SHOP_CONFIRM(new Integer(200000),"商家已确认"),
	PLATFORM_CONFIRM(new Integer(300000),"平台已确认"),
	PAYED(new Integer(400000),"已支付结算"),
	COMPLETE(new Integer(500000),"完成"),
	CONFUSE(new Integer(600000),"拒绝");
	
	public Integer state;
	public String description;
	
	private ShellorderstateEnum(Integer state, String description){
		this.state = state;
		this.description = description;
	}
}

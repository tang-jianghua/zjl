package com.mfangsoft.zhuangjialong.app.constructAppointment.util;

public enum ConstructAppointmentEnum {

	UNRECEIVE("未接受","1001"),
	CELIANG("上门测量","1002"),
	WORKING("开始施工","2001"),
	NEEDPAY("待支付","3001"),
	NEEDCOMMENT("待评价","3002"),
	FINISHED("已完成","4001"),
	CANCLED("已取消","5001");
	
	public String code;
	public String state;
	
	private ConstructAppointmentEnum(String state, String code) {
		this.state = state;
		this.code = code;
	}
	
}

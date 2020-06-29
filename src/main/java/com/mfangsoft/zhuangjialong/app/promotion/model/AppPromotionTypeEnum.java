package com.mfangsoft.zhuangjialong.app.promotion.model;

public enum AppPromotionTypeEnum {
	
	// -1 无活动 0秒杀 1 满额折扣 2 满额减 3 满量折扣 4 联盟打折 5 折扣工具
	
	non("无活动",-1),
	secKill("秒杀",0),
	manEZheKou("满额折扣",1),
	manEJian("满额减",2),
	manCountZheKou("满量折扣",3),
	unionZheKou("联盟打折",4),
	zheKouTool("折扣工具",5);
	
	private String typeName;
	private Integer typeValue;
	
	private AppPromotionTypeEnum(String name, int typeValue) {
		this.typeName = name;
		this.typeValue = typeValue;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(Integer typeValue) {
		this.typeValue = typeValue;
	}

}

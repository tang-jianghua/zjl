package com.mfangsoft.zhuangjialong.integration.enums;

//卖家类型
public enum SellerType {
	

	Promoter("推广员",1),StepPromoter("递推员",2),Constructor("施工员",3),ShopGuider("递推",4);
	
	private String name;
	
	private Integer index;
	
	private SellerType(String name,Integer index){
		
		this.name = name;
        this.index = index;
	}
	
	
	public String getName()
	{
		return this.name;
	}
	
	public Integer getIndex()
	{
		
		return this.index;
	}
	

}
